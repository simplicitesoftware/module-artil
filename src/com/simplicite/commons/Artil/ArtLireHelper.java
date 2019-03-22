package com.simplicite.commons.Artil;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

import net.semanticmetadata.lire.builders.GlobalDocumentBuilder;
import net.semanticmetadata.lire.imageanalysis.features.global.AutoColorCorrelogram;
import net.semanticmetadata.lire.imageanalysis.features.global.CEDD;
import net.semanticmetadata.lire.imageanalysis.features.global.FCTH;
import net.semanticmetadata.lire.utils.FileUtils;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.analysis.Analyzer;

import net.semanticmetadata.lire.builders.DocumentBuilder;
import net.semanticmetadata.lire.imageanalysis.features.global.CEDD;
import net.semanticmetadata.lire.searchers.BitSamplingImageSearcher;
import net.semanticmetadata.lire.searchers.GenericFastImageSearcher;
import net.semanticmetadata.lire.searchers.ImageSearchHits;
import net.semanticmetadata.lire.searchers.ImageSearcher;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import java.nio.file.Path;
import java.nio.file.FileSystems;

/**
 * Shared code ArtLireHelper
 * 
 * 
 * wget http://archive.apache.org/dist/lucene/java/7.7.1/lucene-7.7.1.tgz
 * tar -xvf lucene-7.7.1.tgz && rm -f lucene-7.7.1.tgz
 * cp lucene-7.7.1/queryparser/lucene-queryparser-7.7.1.jar tomcat/webapps/ROOT/WEB-INF/lib/
 * cp lucene-7.7.1/analysis/common/lucene-analyzers-common-7.7.1.jar tomcat/webapps/ROOT/WEB-INF/lib/
 * cp lucene-7.7.1/codecs/lucene-codecs-7.7.1.jar tomcat/webapps/ROOT/WEB-INF/lib/
 * cp lucene-7.7.1/backward-codecs/lucene-backward-codecs-7.7.1.jar tomcat/webapps/ROOT/WEB-INF/lib/
 * rm -rf lucene-7.7.1
 * 
 * wget https://github.com/dermotte/LIRE/releases/download/gradle/simpleapplication-2016-11-24.zip
 * unzip -d lire simpleapplication-2016-11-24.zip && rm -f simpleapplication-2016-11-24.zip
 * cp lire/lib/lire.jar tomcat/webapps/ROOT/WEB-INF/lib/
 * rm -rf lire
 * 
 * sim tomcat-stop
 * sim tomcat-start
 * 
 * 
 */
public class ArtLireHelper implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private static final String lireIndex = "lireIndex";
	private static final String classLogName = "-------------ArtLireHelper--------------";
	
/*	public static void testIndex(Grant g){
		ObjectDB prd = g.getTmpObject("DemoProduct");
		prd.resetFilters();
		List<String[]> rslt = prd.search();
		
		try{
			for(int i=0; i<rslt.size(); i++){
				prd.setValues(rslt.get(i));
				InputStream img = prd.getField("demoPrdPicture").getDocument(g).getInputStream();
				indexImage(img, prd.getRowId());
			}
		}catch(Exception e){err(e);}
	}
	
	public static void testSearch(Grant g){
		ObjectDB prd = g.getTmpObject("DemoProduct");
		prd.select("2");
		try{
			List<String[]> rslt = searchImage(prd.getField("demoPrdPicture").getDocument(g).getInputStream());
			for(int i=0; i<rslt.size(); i++){
				disp(rslt.get(i)[0]+" : "+rslt.get(i)[1]);
			}
		}
		catch(Exception e){err(e);}
	}*/
	
	// ========= INDEX ===========
	public static void indexImage(InputStream img, String identifier) throws Exception{
		indexImage(ImageIO.read(img), identifier);
	}
	
	private static void indexImage(BufferedImage img, String identifier) throws Exception{
		Document document = getLireDocumentBuilder().createDocument(img, identifier);
		IndexWriter iw = getLuceneIndexWriter();
		iw.addDocument(document);
		iw.close();
	}
	
	private static IndexWriter getLuceneIndexWriter() throws Exception{
		FSDirectory index = FSDirectory.open(getIndexPath());
		IndexWriterConfig config = new IndexWriterConfig(getAnalyzer());
		return new IndexWriter(index, config);
	}
	
	private static GlobalDocumentBuilder getLireDocumentBuilder(){
		// Creating a CEDD document builder and indexing all files.
        GlobalDocumentBuilder globalDocumentBuilder = new GlobalDocumentBuilder(CEDD.class);
        // and here we add those features we want to extract in a single run:
        //globalDocumentBuilder.addExtractor(FCTH.class);
        //globalDocumentBuilder.addExtractor(AutoColorCorrelogram.class);
        return globalDocumentBuilder;
	}
	
	// =========SEARCH===========
	public static LinkedHashMap<String, String> searchImage(InputStream img) throws Exception{
		return searchImage(ImageIO.read(img));
	}
	
	public static LinkedHashMap<String, String> searchImage(BufferedImage img) throws Exception{
		LinkedHashMap<String, String> results = new LinkedHashMap();
		IndexReader ir = getLuceneIndexReader();
		
        ImageSearcher searcher = new GenericFastImageSearcher(30, CEDD.class);
		//ImageSearcher searcher = new GenericFastImageSearcher(30, AutoColorCorrelogram.class);
		//...
		
		ImageSearchHits hits = searcher.search(img, getLuceneIndexReader());
        for (int i = 0; i < hits.length(); i++) {
            String identifier = ir.document(hits.documentID(i)).getValues(DocumentBuilder.FIELD_NAME_IDENTIFIER)[0];
            results.put(""+identifier, ""+hits.score(i));
        }
        
		return results;
	}
	
	private static IndexReader getLuceneIndexReader() throws Exception{
		return DirectoryReader.open(FSDirectory.open(getIndexPath()));
	}
	
	//========== COMMONS ==========
	private static Analyzer getAnalyzer(){
		return new WhitespaceAnalyzer();
	}
	
	private static Path getIndexPath(){
		String path = Grant.getSystemAdmin().getIndexDir();
		Path indexPath = FileSystems.getDefault().getPath(path, lireIndex);
		disp(indexPath.toAbsolutePath().toString());
		return indexPath;
	}
	
	private static void disp(String str){
		AppLog.info(ArtLireHelper.class, classLogName, str, Grant.getSystemAdmin());
    }
    
    private static void err(Exception e){
    	AppLog.error(ArtLireHelper.class, classLogName, e.getMessage(), e, Grant.getSystemAdmin());
    }
    
	private static void err(String e){
    	AppLog.error(ArtLireHelper.class, classLogName, e, null, Grant.getSystemAdmin());
    }
}
