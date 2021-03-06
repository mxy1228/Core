/*******************************************************************************
 *    工程名称   ： CYMGSDK
 *    文件名    ： RequestToken.java
 *              (C) Copyright Cyou-inc Corporation 2014
 *               All Rights Reserved.
 *   
 ******************************************************************************/
package com.changyou.mgp.sdk.mbi.pay.weixin;

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
 *       获取从cy server微信token类,token是app全局的，不分ios或者android，都公用一个token，过期后服务器会判断重新从微信那边生成
 *       客户端不关心token失效问题
 * 限制
 *       无。
 * 注意事项
 *       无。
 * </PRE>
 */

public class RequestToken {
	private static CYLog log = CYLog.getInstance();
	/**
	 * 从cy server端获取微信支付token
	 * @param listener 
	 * @param args
	 */
	private static void requestToken(final Context context, final WeixinTokenListener listener) throws WeixinPayException{
		
		RequestParams params= new RequestParams() ;
		params.put("app_id", WeixinPayModel.APP_ID);
		params.put("app_secret", WeixinPayModel.APP_SECRET);
		
		MyHttpClient client=new MyHttpClient(context);
		client.addHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
		
		client.post(HttpContants.getWeixinPayTokenUrl(),params, new MyAsyncResponseHandler(){
			@Override
			public void onStart() {
				
			}
			
			@Override
			public void onSuccess(int statusCode, String content) {

				log.d("get getWeixin token  content = "+content);
				//{"access_token":"PdMz6FkcAuLuKWyAtV2T_G2Zo4moJAc2nNI8r_UPIxm5PN2PwE-H2tgCGZtHALQKBgOyTknpO7UU20DzAI7ioQ","expires_in":"7200.0"}
				JSONObject data;
				try {
					data = new JSONObject(content);

					String token=(String) data.get("access_token");
					if((token!=null) && !StringUtils.isEmpty(token)){
						
						
						listener.handleToken(token);
					}else{
						log.d("token is null");
						throwException("token is null");
					}
				} catch (JSONException e) {
					throwException(e.getMessage());
				} catch (WeixinPayException e) {
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
	
	public static void getToken(Context context,WeixinTokenListener listener) throws WeixinPayException{
		requestToken(context,listener);
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
	static interface  WeixinTokenListener{
		public void handleToken(String  token) throws WeixinPayException;
	}
	
}
