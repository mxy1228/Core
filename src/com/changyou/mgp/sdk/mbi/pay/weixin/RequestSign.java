/*******************************************************************************
 *    工程名称   ： CYMGSDK
 *    文件名    ： RequestToken.java
 *              (C) Copyright Cyou-inc Corporation 2014
 *               All Rights Reserved.
 *   
 ******************************************************************************/
package com.changyou.mgp.sdk.mbi.pay.weixin;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import com.changyou.mgp.sdk.mbi.config.HttpContants;
import com.changyou.mgp.sdk.mbi.http.MyAsyncResponseHandler;
import com.changyou.mgp.sdk.mbi.http.MyHttpClient;
import com.changyou.mgp.sdk.mbi.log.CYLog;
import com.changyou.mgp.sdk.mbi.utils.StringUtils;
import com.loopj.android.http.RequestParams;

/**
 * <PRE>
 * 作用

 * 限制
 *       无。
 * 注意事项
 *       无。
 * </PRE>
 */

public class RequestSign {

	
	private static CYLog log = CYLog.getInstance();
	
	private static  RequestParams buildRequstParams(RequestSignEntity entity) throws UnsupportedEncodingException{

		//参数：app_id、app_key、app_secret、body、out_trade_no、total_fee、notify_url、spbill_create_ip、nonceStr、timestamp、traceid使用x-www-form-urlencoded方式传输
				RequestParams params= new RequestParams() ;
				params.put("app_id", entity.app_id);
				params.put("app_key", entity.app_key);
				params.put("app_secret", entity.app_secret);
				params.put("body", entity.body);
				params.put("out_trade_no", entity.out_trade_no);
				params.put("total_fee", entity.total_fee);
				params.put("notify_url",entity.notify_url);
				params.put("spbill_create_ip", entity.spbill_create_ip);
				params.put("nonceStr", entity.nonceStr);
				params.put("timestamp", String.valueOf(entity.timestamp));
				params.put("traceid", entity.traceid);
				log.d("====build requestParams===\n"+params);
				return params;
	}
	/**
	 * 从cy server端获取微信支付token
	 * @param listener 
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	private static void requestSign(final Context context, final WeixinSignListener listener,RequestSignEntity entity) throws WeixinPayException, UnsupportedEncodingException{
		MyHttpClient client=new MyHttpClient(context);
		client.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
		//client.addHeader("Accept-Language", "zh-CN,en;q=0.5");
		
		client.post(HttpContants.getWeixinPaySignUrl(),buildRequstParams(entity), new MyAsyncResponseHandler(){
			@Override
			public void onStart() {
				
			}
			
			@Override
			public void onSuccess(int statusCode, String content) {

				log.d("get getWeixin sign  content = "+content);

				JSONObject data;
				try {
					data = new JSONObject(content);
					//{"appSignature":appSignature,"partnerId":partnerId,"packageValue":packageValue}
					String appSignature=(String) data.get("appSignature");
					String partnerId=(String) data.get("partnerId");
					String packageValue=(String) data.get("packageValue");
					if( !StringUtils.isEmpty(appSignature) && !StringUtils.isEmpty(partnerId)&& !StringUtils.isEmpty(packageValue)){
						
						
						listener.handleSign(appSignature,partnerId,packageValue);
					}else{
						log.d("sign is null");
						throwException("sing is null");
					}
				} catch (JSONException e) {
					throwException(e.getMessage());
				}
				
			}
			
			@Override
			public void onFailure(Throwable error, String content) {
				log.d("get getWeixin token error = "+content);
				throwException(content);

			}
		});
	}
	
	private static void throwException(String content){
		try {
			throw new WeixinPayException(content);
		} catch (WeixinPayException e) {}
	}
	
	public static void getSign(Context context,WeixinSignListener listener,RequestSignEntity entity) throws WeixinPayException, UnsupportedEncodingException{
		requestSign(context,listener,entity);
	}
	
	

	/***
	 * 
	 * <PRE>
	 * 作用
	 *       微信token获取回调接口
	 * 限制
	 *       无。
	 * 注意事项
	 *       无。
	 * </PRE>
	 */
	static interface  WeixinSignListener{

		public void handleSign(String  appSignature,String partnerId,String packageValue);
	}
	
}
