package com.simplicite.extobjects.Artil;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import java.io.File;
import javax.imageio.ImageIO;
import com.simplicite.commons.Artil.ArtLireHelper;
import org.json.JSONObject;

/**
 * External object ArtImageRecognition
 */
public class ArtImageRecognition extends ExternalObject {
	private static final long serialVersionUID = 1L;

	/**
	 * Display method
	 * @param params Request parameters
	 */
	@Override
	public Object display(Parameters params) {
		try {
			if(params.isPost()){
				File img = params.getDocument("air-file").getTmpFile();
				
				LinkedHashMap<String, String> rslt = ArtLireHelper.searchImage(ImageIO.read(img));
				JSONObject data = new JSONObject();
				
				if(rslt.size()>0){
					String sql = "t.row_id in (";
					for (Map.Entry<String, String> entry : rslt.entrySet()) {
					    String id = entry.getKey();
					    String score = entry.getValue();
					    AppLog.info(getClass(), "---------------------------", "Row id "+id+" = "+score, getGrant());
					    sql += id+",";
					}
					sql = sql.substring(0,sql.lastIndexOf(","))+")";

					
					ObjectDB pic = getGrant().getObject("air_ArtPiece", "ArtPiece");
					pic.resetValues();
					pic.resetFilters();
					pic.resetOrders();
					pic.setSearchSpec(sql);
					pic.setParameter("AIR_ARTPIECE_RESULTS", rslt);
					
					data.put("success", true);
				}
				else{
					data.put("success", false);
				}
				
				setJSONMIMEType();
				return data;
			}
			else{
				//addMustache();
				return javascript("Air.fire()");
			}
		} catch (Exception e) {
			AppLog.error(getClass(), "display", null, e, getGrant());
			return e.getMessage();
		}
	}
}
