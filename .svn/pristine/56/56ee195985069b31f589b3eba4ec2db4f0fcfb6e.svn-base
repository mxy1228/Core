package com.changyou.mgp.sdk.mbi.utils;

import java.security.MessageDigest;

import android.content.Context;

public class SignUtils {

	/**
	 * gateway签名算法
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String createGatewaySign(Context context,String data) throws Exception {
		String appKey=MetaDataValueUtils.getAppKey(context);
		String appSecret=MetaDataValueUtils.getAppSecret(context);
		String param = appKey+appSecret+data;
		StringBuffer result = new StringBuffer();
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(param.getBytes("UTF-8"));
		byte[] b = md5.digest();
		for (int i = 0; i < b.length; ++i) {
			int x = b[i] & 0xFF;
			int h = x >>> 4;
			int l = x & 0x0F;
			result.append((char) (h + ((h < 10) ? '0' : 'a' - 10)));
			result.append((char) (l + ((l < 10) ? '0' : 'a' - 10)));
		}
		return result.toString().substring(8, 24);
	}
	
	public static String stringToMD5(String string) throws Exception{  
	    byte[] hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));    
	    StringBuilder hex = new StringBuilder(hash.length * 2);  
	    for (byte b : hash) {  
	        if ((b & 0xFF) < 0x10) {
	        	hex.append("0");  
	        } 
	        hex.append(Integer.toHexString(b & 0xFF));  
	    }  
	    return hex.toString();  
	}

}
