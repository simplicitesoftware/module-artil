package com.simplicite.tests.Artil;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.simplicite.commons.Artil.ArtLireHelper;
import java.io.InputStream;
import com.simplicite.commons.Artil.ArtilCommons;
import java.io.IOException;

/**
 * Unit tests ArtLireTest
 */
public class ArtLireTest {
	@Test
	public void test() {
		try {
			Grant g = Grant.getSystemAdmin();
			testIndex(g);
			testSearch(g);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	private static void testIndex(Grant g) throws Exception{
		ObjectDB prd = g.getTmpObject("DemoProduct");
		synchronized(prd){
			prd.resetFilters();
			for(String[] row : prd.search()){
				prd.setValues(row);
				ArtLireHelper.indexImage(
					prd.getField("demoPrdPicture").getDocument(g).getInputStream(),
					prd.getRowId()
				);
			}
		}
	}
	
	public static void testSearch(Grant g) throws Exception{
		ObjectDB prd = g.getTmpObject("DemoProduct");
		synchronized(prd){
			prd.select("2");
			for(Map.Entry<String, String> res : ArtLireHelper.searchImage(prd.getField("demoPrdPicture").getDocument(g).getInputStream()).entrySet()){
				AppLog.info(ArtLireTest.class, "testSearch", "Object ID : "+res.getKey()+" --- Score : "+res.getValue(), Grant.getSystemAdmin());
			}
		}
	}
}
