package com.simplicite.objects.Artil;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/*import com.simplicite.commons.Artil.ArtLireHelper;
import java.io.InputStream;*/

/**
 * Business object ArtPiece
 */
public class ArtPiece extends ObjectDB {
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getUserKeyLabel(String[] row) {
		String label = row!=null ? row[getFieldIndex("artPicTitle")] : getFieldValue("artPicTitle");
		return label;
	}
	
	@Override
	public void postLoad() {
		if(!getGrant().hasResponsibility("ART_ADMIN")){
			getField("artPicPrice").setVisibility(ObjectField.VIS_FORBIDDEN);
			getField("artPicDocument").setVisibility(ObjectField.VIS_FORBIDDEN);
			getField("artPicPlaceId").setVisibility(ObjectField.VIS_FORBIDDEN);
		}
	}
	
	/*@Override
	public String postSave() {
		tryLireIndexing();
		return null;
	}
	
	@Override
	public String postDelete() {
		try{
			ArtLireHelper.deleteFromIndex(getRowId());
		}
		catch(Exception e){
			AppLog.error(getClass(), "postDelete", e.getMessage(), e, getGrant());
		}
		return null;
	}
	
	private void tryLireIndexing(){
		try{
			InputStream img = getField("artPicPicture").getDocument(getGrant()).getInputStream();
			ArtLireHelper.indexImage(img, getRowId());
		}
		catch(Exception e){
			AppLog.error(getClass(), "tryLireIndexing", e.getMessage(), e, getGrant());
		}		
	}
	
	public String reIndexAll(){
		try{
			ArtLireHelper.deleteAllIndex();
		}
		catch(Exception e){
			AppLog.error(getClass(), "reIndexAll", e.getMessage(), e, getGrant());
			return "Could not empty index";
		}
		
		AppLog.info(getClass(), "reIndexAll", "====================", getGrant());
		
		ArtPiece pic = (ArtPiece) getGrant().getTmpObject("ArtPiece");
		int i;
		synchronized(pic){
			pic.resetFilters();
			List<String[]> rslt = pic.search();
			for(i=0; i<rslt.size(); i++){
				pic.setValues(rslt.get(i));
				
				AppLog.info(getClass(), "reIndexAll", "Indexing ArtPiece #"+pic.getRowId(), getGrant());
				pic.tryLireIndexing();
			}
		}
		return i+" objects indexed.";
	}
	
	@Override
	public List<String[]> postSearch(List<String[]> rows) {
		Map<String, String> results = (LinkedHashMap<String, String>) getObjectParameter("AIR_ARTPIECE_RESULTS");
		ObjectField sco = getField("artPicScore");
		
		if("air_ArtPiece".equals(getInstanceName()) && results!=null){
			int scoIndex = sco.getIndex(this);
			sco.setVisibility(ObjectField.VIS_BOTH);
			for(int i=0; i<rows.size(); i++){
				rows.get(i)[scoIndex] = results.get(rows.get(i)[0]);
			}
			Collections.sort(rows, new ScoredPieceComparator(scoIndex));
		}
		else{
			sco.setVisibility(ObjectField.VIS_NOT);
		}
		return rows;
	}
	
	public class ScoredPieceComparator implements Comparator<String[]> {
		int sco;
		
		public ScoredPieceComparator(int scoreFieldIndex){
			sco = scoreFieldIndex;
		}
		
		public int compare(String[] row1, String[] row2) {
			double sco1 = Tool.parseDouble(row1[sco]);
			double sco2 = Tool.parseDouble(row2[sco]);
			return sco2-sco1<0?1:-1;
		}
	}*/
}
