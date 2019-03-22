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
				
				List<String[]> rslt = ArtLireHelper.searchImage(ImageIO.read(img));
				
				JSONObject data = new JSONObject();
				for(int i=0; i<rslt.size(); i++){
					data.put(rslt.get(i)[0], rslt.get(i)[1]);
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
