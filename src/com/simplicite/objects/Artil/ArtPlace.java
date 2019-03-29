package com.simplicite.objects.Artil;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object ArtPlace
 */
public class ArtPlace extends ObjectDB {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void postLoad() {
		if(!getGrant().hasResponsibility("ART_ADMIN")){
			setDefaultSearchSpec(getDefaultSearchSpec() + " and plc_public=1");
			getField("artPlcPublic").setVisibility(ObjectField.VIS_FORBIDDEN);
		}
	}	
}
