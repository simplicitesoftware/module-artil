package com.simplicite.objects.Artil;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

import com.simplicite.commons.Artil.ArtLireHelper;
import java.io.InputStream;

/**
 * Business object ArtPiece
 */
public class ArtPiece extends ObjectDB {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void postLoad() {
		if(!getGrant().hasResponsibility("ART_ADMIN")){
			getField("artPicPrice").setVisibility(ObjectField.VIS_FORBIDDEN);
		}
	}
	
	@Override
	public String postSave() {
		tryLireIndexing();
		return null;
	}
	
	private void tryLireIndexing(){
		try{
			InputStream img = getField("artPicPicture").getDocument(getGrant()).getInputStream();
			ArtLireHelper.indexImage(img, getRowId());
		}
		catch(Exception e){
			AppLog.error(getClass(), "postSave", e.getMessage(), e, getGrant());
		}		
	}
}
