package com.simplicite.commons.Artil;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Shared code ArtilCommons
 */
public class ArtilCommons implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	public static HashMap<String, String> getMapOf(String... args){
		HashMap<String, String> mp = new HashMap<String, String>();
		for(int i=0; i<args.length; i=i+2){
			mp.put(args[i], i+1!=args.length?args[i+1]:null);
		}
		return mp;
	}
	
	public static boolean isAdmin(Grant g){
		return g.hasResponsibility("ART_ADMIN");
	}
}
