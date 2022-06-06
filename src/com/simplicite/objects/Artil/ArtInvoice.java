package com.simplicite.objects.Artil;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.exceptions.*;
import com.simplicite.util.tools.Parameters.InlineParam;
import com.simplicite.util.tools.*;
import com.simplicite.webapp.web.WebPage;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 * Business object ArtInvoice
 */
public class ArtInvoice extends ObjectDB {
	private static final long serialVersionUID = 1L;
	
	public void calculateTotal(){
		setFieldValue("artInvTotal", getGrant().simpleQueryAsDouble("SELECT SUM(art_picinv_price) FROM art_pic_inv WHERE art_picinv_inv_id="+getRowId()));
		save();
	}
	
	public byte[] pubInvoicePdf(){
		try{
			return HTMLToPDFTool.toPDF(pubInvoiceHtml());
		}catch(Exception e){
			AppLog.error("Error generating invoice : "+e.getMessage(), e, Grant.getSystemAdmin());
			return null;
		}
	}
	
	public String pubInvoiceHtml(){
		return MustacheTool.apply(
			this,
			"HTML_INVOICE", 
			pubInvoiceJson().toString()
		);
	}
	
	public JSONObject pubInvoiceJson(){
		JSONObject json = new JSONObject(toJSON());
		json.put("rows", getInvoiceRows());
		AppLog.info("-------------"+json.toString(), Grant.getSystemAdmin());
		return json;
	}
	
	private JSONArray getInvoiceRows(){
		ObjectDB inv = getGrant().getTmpObject("ArtPicInv");
		synchronized(inv.getLock()){
			inv.resetFilters();
			inv.setFieldFilter("artPicinvInvId", getRowId());
			return new JSONArray(inv.toJSON(
				inv.search(), 
				new InlineParam(false, true, false), 
				false,
				false
			));
		}
	}
}
