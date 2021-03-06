package com.changyou.mgp.sdk.mbi.account.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.changyou.mgp.sdk.mbi.account.id.AccResource;
import com.changyou.mgp.sdk.mbi.account.ui.CYMGMainDialogFragmentActivity;
import com.changyou.mgp.sdk.mbi.common.CYMGCallbackHelper;
import com.changyou.mgp.sdk.mbi.config.CYMGPrompt;
import com.changyou.mgp.sdk.mbi.config.CYMGProtocolKeys;
import com.changyou.mgp.sdk.mbi.config.HttpContants;
import com.changyou.mgp.sdk.mbi.config.SDKConfig;
import com.changyou.mgp.sdk.mbi.db.CYMGAccBean;
import com.changyou.mgp.sdk.mbi.entity.ErrorInfo;
import com.changyou.mgp.sdk.mbi.http.MyAsyncResponseHandler;
import com.changyou.mgp.sdk.mbi.http.MyHttpClient;
import com.changyou.mgp.sdk.mbi.log.CYLog;
import com.changyou.mgp.sdk.mbi.mbi.manager.CMBILogManager;
import com.changyou.mgp.sdk.mbi.utils.JSONUtil;
import com.changyou.mgp.sdk.mbi.utils.NetWorkUtils;
import com.changyou.mgp.sdk.mbi.utils.SystemUtils;
import com.changyou.mgp.sdk.mbi.utils.UserInfoSPUtil;

public class CYMGLoginTokenVerifyUtil {
	
	private static CYLog log = CYLog.getInstance();
	
	public static void loginTokenVerify(final Activity activity,final String acc,final String uid,final String token,final String type,final MyLoginTokenVerifyListener listener){
		String deviceid = SystemUtils.getDeviceId(activity);
		MyHttpClient myHttpClient=new MyHttpClient(activity);
		Map map=new HashMap();
		map.put("uid", uid);
		map.put("token", token);
		map.put("device_id", deviceid);
		myHttpClient.post(HttpContants.getURL(HttpContants.TOKEN_VERIFY), map, new MyAsyncResponseHandler("loginTokenVerify"){
			@Override
			public void onStart() {
				CMBILogManager.printEventLog(activity, "0", "110022", "");
				listener.onStart();
			}
			
			@Override
			public void onSuccess(int statusCode, String content) {
				CMBILogManager.printEventLog(activity, "0", "120022", "");
				log.d("content:"+content);
				try {
					listener.onSuccess();
					if(!TextUtils.isEmpty(content)){
						JSONObject jsonObject=new JSONObject(content);
						int status=jsonObject.getInt("status");
						if(status==1){
							CYMGAccBean bean=new CYMGAccBean();
							bean.setmAcc(acc);
							bean.setmUID(uid);
							bean.setmToken(token);
							bean.setmState(1);
							bean.setmTimeStamp(new Date(System.currentTimeMillis()).getTime()/1000);
							((CYMGMainDialogFragmentActivity)activity).getmCYMGDBMaster().login(bean);
							UserInfoSPUtil.setUid(activity, uid);
							UserInfoSPUtil.setType(activity, type);
							String userIp = NetWorkUtils.getLocalIpAddress(activity);
							String deviceid = SystemUtils.getDeviceId(activity);
							Bundle bundle = new Bundle();
							bundle.putString(CYMGProtocolKeys.UID, uid);
							bundle.putString(CYMGProtocolKeys.TOKEN, token);
							bundle.putString(CYMGProtocolKeys.TYPE, type);
							bundle.putString(CYMGProtocolKeys.USERIP, userIp);
							bundle.putString(CYMGProtocolKeys.DEVICEID, deviceid);
							bundle.putString(CYMGProtocolKeys.ISDEBUG, SDKConfig.DEBUG ? "true" : "false");
							bundle.putString(CYMGProtocolKeys.CHANNEL_ID,activity.getString(AccResource.getInstance(activity).mgp_channel_cy));
							bundle.putString(CYMGProtocolKeys.OPCODE, "10001");
							CYMGCallbackHelper.callbackResult(CYMGCallbackHelper.getLoginResult(CYMGPrompt.CODE_LOGIN_SUCCESS, bundle,activity),activity);
							activity.finish();
						}
					}else{
						Toast.makeText(activity, AccResource.getInstance(activity).mgp_sdk_2_0_error_common_server, Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					log.e(e);
				}
			}
			
			@Override
			public void onFailure(int statusCode, Throwable error,
					String content) {
				CMBILogManager.printEventLog(activity, "0", "120022", "");
				log.d("content:"+content);
				try {
					listener.onFailure();
					if (!TextUtils.isEmpty(content)) {
						ErrorInfo info = JSONUtil.getMapper().readValue(content,new TypeReference<ErrorInfo>() {});
						if (info != null) {
							Toast.makeText(activity, info.getClient_message(), Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(activity, AccResource.getInstance(activity).mgp_sdk_2_0_error_common_server, Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(activity, AccResource.getInstance(activity).mgp_sdk_2_0_error_common_server, Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					log.e(e);
				}
			}
		});
	}
	
	public interface MyLoginTokenVerifyListener{
		public void onStart();
		public void onSuccess();
		public void onFailure();
	}

}
