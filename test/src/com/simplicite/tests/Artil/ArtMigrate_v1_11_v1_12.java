package com.simplicite.tests.Artil;

import java.util.*;

import com.simplicite.util.*;
import com.simplicite.util.exceptions.*;
import com.simplicite.util.tools.*;

import java.util.*;

import com.simplicite.util.*;
import com.simplicite.util.exceptions.*;
import com.simplicite.util.tools.*;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Unit tests ArtMigrate_v1_11_v1_12
 */
public class ArtMigrate_v1_11_v1_12 {
	private static Grant g;
	
	@BeforeClass
	public static void setup(){
		g = new Grant("designer");
	}
	
	@AfterClass
	public static void tearDown(){
		g.destroy();
	}
	
	@Test
	public void migrateTechnique() {
		List<String> exist = new ArrayList<>();
		
		try {
			ObjectDB art = g.getTmpObject("ArtPiece");
			synchronized(art.getLock()){
				art.resetFilters();
				for(String[] row : art.search()){
					art.setValues(row);
					
					String tec = art.getFieldValue("artPicTechnique");
					
					if(!Tool.isEmpty(tec)){
						if(!exist.contains(tec)){
							ObjectDB obj = g.getTmpObject("ArtTechnique");
							obj.setFieldValue("artTecName", tec);
							try{
								obj.getTool().validateAndCreate();
							}
							catch(Exception e){} // ignore creation error (probably already userkey already exists)
							exist.add(tec);
						}
						art.setFieldValue("artPicTecId.artTecName", tec);
					}
					
					
					if(art.getField("artPicTecId.artTecName").hasChanged()){
						try{
							art.completeForeignKeys();
							art.getTool().validateAndUpdate();
						}
						catch(Exception e){
							AppLog.simpleError(e.getMessage());
						}
					}
				}
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
}
