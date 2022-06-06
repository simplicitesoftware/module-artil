package com.simplicite.objects.Artil;

import java.util.*;

import com.simplicite.util.*;
import com.simplicite.util.exceptions.*;
import com.simplicite.util.tools.*;

/**
 * Business object ArtPicInv
 */
public class ArtPicInv extends ObjectDB {
	private static final long serialVersionUID = 1L;
	
	@Override
	public String preCreate() {
		setFieldValue("artPicinvPrice", getFieldValue("artPicinvPicId.artPicPrice"));
		return null;
	}
}
