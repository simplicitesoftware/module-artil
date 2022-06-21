package com.simplicite.objects.Artil;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

//import com.simplicite.commons.Artil.ArtLireHelper;
import java.io.InputStream;
import com.simplicite.commons.Artil.ArtilCommons;
import com.simplicite.util.exceptions.ActionException;

/**
 * Business object ArtPiece
 */
public class ArtPiece extends ObjectDB {
	private static final long serialVersionUID = 1L;
	private static final String[] adminOnlyFields = new String[]{
		"artPicPrice",
		"artPicEstimatedPrice",
		"artPicDocument"
	};
	
	private void reGenerateThumbnails(){
		ObjectField pic = getField("artPicPicture");
		DocTool.buildThumbImage(pic.getValue(), pic.getDocument().getName(), pic.getName());
	}
	
	@Override
	public String getUserKeyLabel(String[] row) {
		String label = row!=null ? row[getFieldIndex("artPicTitle")] : getFieldValue("artPicTitle");
		return label;
	}
	
	@Override
	public void postLoad() {
		boolean isAdmin = ArtilCommons.isAdmin(getGrant());
		Arrays.stream(adminOnlyFields).forEach(fieldName -> {
			ObjectField f = getField(fieldName);
			f.setVisibility(isAdmin ? f.getVisibilityDefault() : ObjectField.VIS_FORBIDDEN);
		});
	}
	
	public boolean canExport(){
		return ArtilCommons.isAdmin(getGrant());
	}
	
	@Override
	public String postSave() {
		tryLireIndexing();
		return null;
	}
	
	@Override
	public String postDelete() {
		/*try{
			ArtLireHelper.deleteFromIndex(getRowId());
		}
		catch(Exception e){
			AppLog.error(getClass(), "postDelete", e.getMessage(), e, getGrant());
		}*/
		return null;
	}
	
	private void tryLireIndexing(){
		/*try{
			InputStream img = getField("artPicPicture").getDocument(getGrant()).getInputStream();
			ArtLireHelper.indexImage(img, getRowId());
		}
		catch(Exception e){
			AppLog.error(getClass(), "tryLireIndexing", e.getMessage(), e, getGrant());
		}	*/	
	}
	
	public String reIndexAll(){
		/*try{
			ArtLireHelper.deleteAllIndex();
		}
		catch(Exception e){
			AppLog.error(getClass(), "reIndexAll", e.getMessage(), e, getGrant());
			return "Could not empty index";
		}
		
		ArtPiece pic = (ArtPiece) getGrant().getTmpObject("ArtPiece");
		long c = 0;
		synchronized(pic){
			pic.resetFilters();
			pic.search().forEach(row -> {
				pic.setValues(row);
				pic.tryLireIndexing();
			});
			c = pic.getCount();
		}
		return c+" objects indexed.";*/
		return "Image recognition index is broken at the moment.";
	}
	
	private static List<String[]> getActionRows(ObjectDB obj) throws ActionException{
		List<String[]> rows = new ArrayList<>();
		// List actions AND Row action (row action not supported)
		if(obj.getContext().isList()){
			if(obj.getSelectedIds()!=null){
				for(String id : obj.getSelectedIds())
					if(obj.select(id))
						rows.add(obj.getValues());
			}
			else{
				rows = obj.search();
			}
		}
		// Form action
		else
			rows.add(obj.getValues());
		
		if(rows.size()==0)
			throw new ActionException("No record selected for action. ");
		
		return rows;
	}
	
	
	public String calculateCalculatedPrice(){
		int updated = 0;
		try{
			for(String[] row : getActionRows(this)){
				setValues(row);
				String expr = getFieldValue("artPicArtistId.artArtRatingFormula");
				if(!Tool.isEmpty(expr))
					setFieldValue("artPicEstimatedPrice",evalExpression(expr,ObjectCore.CONTEXT_ACTION));
				getTool().validateAndUpdate();
				updated++;
			}
		}
		catch(Exception e){
			return Message.formatSimpleError(e.getMessage()+"Records updated before exception: "+updated);
		}
		
		return Message.formatSimpleInfo("Records updated : "+updated);
	}
	
	@Override
	public List<String[]> postSearch(List<String[]> rows) {
		Map<String, String> results = (LinkedHashMap<String, String>) getObjectParameter("AIR_ARTPIECE_RESULTS");
		ObjectField sco = getField("artPicScore");
		
		if("air_ArtPiece".equals(getInstanceName()) && results!=null){
			int scoIndex = sco.getIndex(this);
			sco.setVisibility(ObjectField.VIS_BOTH);
			rows.forEach(row -> row[scoIndex] = results.get(row[0]));
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
	}
}
