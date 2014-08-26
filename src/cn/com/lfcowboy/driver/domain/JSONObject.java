package cn.com.lfcowboy.driver.domain;

import java.util.HashMap;
import java.util.Map;

public class JSONObject {
	public final static String JSON_ITEMS = "items";
	public final static String JSON_SELECT_LABEL = "label";
	public final static String JSON_SELECT_IDENTIFIER = "identifier";
	
	Map<String, Object> jsonResult = new HashMap<String, Object>();

	public Map<String, Object> getJsonResult() {
		return jsonResult;
	}
}
