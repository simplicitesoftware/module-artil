package com.simplicite.objects.Artil;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object ArtTag
 */
public class ArtTag extends ObjectDB {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void postLoad() {
		if(!getGrant().hasResponsibility("ART_ADMIN")){
			setDefaultSearchSpec(getDefaultSearchSpec() + " and tag_public=1");
			getField("artTagPublic").setVisibility(ObjectField.VIS_FORBIDDEN);
		}
	}
}
