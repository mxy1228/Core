package com.changyou.mgp.sdk.mbi.utils;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONUtil {
	
	/**
	 * 
	 * 功能描述：获取JacksonMapper
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param @return
	 * @return ObjectMapper
	 * @date 2013-12-22 下午2:00:24
	 *
	 */
	public static ObjectMapper getMapper(){
		return new ObjectMapper().configure(
				DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
				true).configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	public static String initJsonArrayStr(String content) throws Exception{
		JSONArray array = new JSONArray(content);
		JSONObject obj = new JSONObject();
		obj.put("data", array);
		return obj.toString();
	}
}
