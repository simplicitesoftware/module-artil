package com.simplicite.commons.Artil;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.LinkedHashMap;

import javax.imageio.ImageIO;

import org.apache.lucene.index.*;
import org.apache.lucene.analysis.*;
import org.apache.lucene.store.*;
import org.apache.lucene.document.*;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;

/*import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;*/

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

import net.semanticmetadata.lire.builders.DocumentBuilder;
import net.semanticmetadata.lire.builders.GlobalDocumentBuilder;
import net.semanticmetadata.lire.imageanalysis.features.global.CEDD;
import net.semanticmetadata.lire.searchers.GenericFastImageSearcher;
import net.semanticmetadata.lire.searchers.ImageSearchHits;
import net.semanticmetadata.lire.searchers.ImageSearcher;

public class ArtLireHelper implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private static final String lireIndex = "lireIndex";
	private static final String classLogName = "-------------ArtLireHelper--------------";

	// ========= INDEX ===========
	public static void indexImage(InputStream img, String identifier) throws Exception{
		indexImage(ImageIO.read(img), identifier);
	}

	private static void indexImage(BufferedImage img, String identifier) throws Exception{
		Document document = getLireDocumentBuilder().createDocument(img, identifier);
		IndexWriter iw = getLuceneIndexWriter();
		deleteFromIndex(iw, identifier);
		iw.addDocument(document);
		iw.close();
	}

	public static void deleteFromIndex(String identifier) throws Exception{
		IndexWriter iw = getLuceneIndexWriter();
		deleteFromIndex(iw, identifier);
		iw.close();
	}

	public static void deleteFromIndex(IndexWriter iw, String identifier) throws Exception{
		iw.deleteDocuments(new Term(DocumentBuilder.FIELD_NAME_IDENTIFIER, identifier));
	}

	public static void deleteAllIndex() throws Exception{
		IndexWriter iw = getLuceneIndexWriter();
		iw.deleteAll();
		iw.commit();
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
