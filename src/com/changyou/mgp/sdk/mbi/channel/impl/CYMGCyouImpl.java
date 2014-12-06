package com.changyou.mgp.sdk.mbi.channel.impl;

import org.apache.http.Header;
import org.codehaus.jackson.type.TypeReference;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;

import com.changyou.mgp.sdk.mbi.account.ui.CYMGLoginToast;
import com.changyou.mgp.sdk.mbi.account.ui.CYMGMainDialogFragmentActivity;
import com.changyou.mgp.sdk.mbi.channel.CYMGChannel;
import com.changyou.mgp.sdk.mbi.channel.CYMGChannelEntity;
import com.changyou.mgp.sdk.mbi.common.CYMGCallbackHelper;
import com.changyou.mgp.sdk.mbi.common.CYMGLoginCallback;
import com.changyou.mgp.sdk.mbi.config.CYMGPrompt;
import com.changyou.mgp.sdk.mbi.config.CYMGProtocolKeys;
import com.changyou.mgp.sdk.mbi.config.Contants;
import com.changyou.mgp.sdk.mbi.config.HttpContants;
import com.changyou.mgp.sdk.mbi.config.Params;
import com.changyou.mgp.sdk.mbi.config.SDKConfig;
import com.changyou.mgp.sdk.mbi.db.CYMGAccBean;
import com.changyou.mgp.sdk.mbi.db.CYMGDBMaster;
import com.changyou.mgp.sdk.mbi.entity.GlobalParamItem;
import com.changyou.mgp.sdk.mbi.entity.GlobalParamList;
import com.changyou.mgp.sdk.mbi.http.MyAsyncResponseHandler;
import com.changyou.mgp.sdk.mbi.http.MyHttpClient;
import com.changyou.mgp.sdk.mbi.log.CYLog;
import com.changyou.mgp.sdk.mbi.pay.ui.CYMGPaymentActivity;
import com.changyou.mgp.sdk.mbi.platform.CYMGGameParams;
import com.changyou.mgp.sdk.mbi.platform.CYMGPlatformConfiguration;
import com.changyou.mgp.sdk.mbi.ui.widget.CYMGFlowWinManager;
import com.changyou.mgp.sdk.mbi.utils.JSONUtil;
import com.changyou.mgp.sdk.mbi.utils.UserInfoSPUtil;
import com.loopj.android.http.RequestParams;

public class CYMGCyouImpl implements CYMGChannel {

	private CYLog log = CYLog.getInstance();
	
	private static final String VERSION = "2.02.008";

	private String mUid=null;
	
	private boolean mInited=false;
	
	private static CYMGLoginCallback mLoginCallback;

	private Context mContext;
	
	public static CYMGLoginCallback getCYMGLoginCallback(){
		return mLoginCallback;
	}
	
	@Override
	public void init(CYMGPlatformConfiguration config) {
		log.d("------MGPCyouImpl:init");
		mContext = config.getmContext();
		CYMGFlowWinManager.getInstance().reset();
		requestGlobalParams(mContext);
		initAccDB(config.getmContext());
		if(CYMGGameParams.getInstance().getHandler() != null){
			CYMGCallbackHelper.callbackResult(CYMGCallbackHelper.getCommonResult(CYMGPrompt.CODE_INIT_SUCCESS, null),config.getmContext());
		}else{
			CYMGCallbackHelper.callbackResult(CYMGCallbackHelper.getCommonResult(CYMGPrompt.CODE_INIT_FAILED, null),config.getmContext());
		}
	}
	
	private void initAccDB(Context context){
		CYMGDBMaster master = new CYMGDBMaster(context);
//		
	}
	
	@Override
	public void doLogin(CYMGChannelEntity entity) {
		log.d("------MGPCyouImpl:doLogin");
		final Context context=entity.getmContext();
		Bundle bundle=entity.getBundle();
		Intent intent=new Intent(context, CYMGMainDialogFragmentActivity.class);
		intent.putExtra(Params.BUNDLE, bundle);
		context.startActivity(intent);
		mLoginCallback=new CYMGLoginCallback(){
			
			@Override
			public void onFinished() {
				String acc=UserInfoSPUtil.getUsername(context);
				CYMGLoginToast.showLoginToast(context, acc.substring(6, acc.length()));
			}
		};
	}
	
	@Override
	public void doPay(CYMGChannelEntity entity) {
		log.d("------MGPCyouImpl:doPay");
		Context context = entity.getmContext();
		if(context == null){
			log.e("context is null");
			return;
		}
		Bundle bundle=entity.getBundle();
		if (TextUtils.isEmpty(bundle.getString(CYMGProtocolKeys.UID)) || bundle.getString(CYMGProtocolKeys.UID) == null) {
			log.e("UID is null");
		}else{
			((CYMGPaymentActivity)context).changeFragment(Contants.FragmentTag.PAYMENT_WAY_FRAGMENT_TAG, bundle);
		}
	}

	@Override
	public void doLogout(CYMGChannelEntity entity) {
		log.d("------MGPCyouImpl:doLogout");
		final Context context=entity.getmContext();
		getUserSP(context);
		CYMGAccBean bean=new CYMGAccBean();
		bean.setmUID(mUid);
		new CYMGDBMaster(context).logout(bean);
		clearUserSP(context);
		CYMGCallbackHelper.callbackResult(CYMGCallbackHelper.getCommonResult(CYMGPrompt.CODE_LOGOUT_SUCCESS, null),context);
		//隐藏悬浮窗
		SDKConfig.HAD_LOGIN = false;
		CYMGFlowWinManager.getInstance().gone();
	}
	
	/**
	 * 
	 * 功能描述：从SP中取出USER数据
	 * 
	 * @author 严峥(yanzheng)
	 * @param
	 * @return void
	 * @date 2014-1-16 下午4:52:19
	 * 
	 *       修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	private void getUserSP(Context context) {
		mUid = UserInfoSPUtil.getUid(context);
	}

	/**
	 * 
	 * 功能描述：从SP中清除USER数据
	 * 
	 * @author 严峥(yanzheng)
	 * @param
	 * @return void
	 * @date 2014-1-20 下午1:17:35
	 * 
	 *       修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	private void clearUserSP(Context context) {
		UserInfoSPUtil.setUsername(context, "");
		UserInfoSPUtil.setUid(context, "");
		UserInfoSPUtil.setToken(context, "");
		UserInfoSPUtil.setType(context, "");
	}
	
	@Override
	public void doDestroy(CYMGChannelEntity entity) {
		log.d("------MGPCyouImpl:doDestroy");
	}

	/**
	 * 
	 * 功能描述：向服务器请求全局参数
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param 
	 * @return void
	 * @date 2014-3-18 上午11:22:13
	 *
	 */
	private void requestGlobalParams(final Context context){
		RequestParams params = new RequestParams();
		new MyHttpClient(context).get(HttpContants.getURL(HttpContants.GLOBAL_PARAMS), params, new MyAsyncResponseHandler("requestGlobalParams"){
			@Override
			public void onSuccess(int statusCode, String content) {
				log.d("statusCode:"+statusCode+",requestGlobalParams result = "+content);
				try {
					GlobalParamList list = JSONUtil.getMapper().readValue(JSONUtil.initJsonArrayStr(content), new TypeReference<GlobalParamList>() {
					});
					if(list != null){
						SharedPreferences sp = context.getSharedPreferences(Contants.SP_NAME.SETTING, Context.MODE_PRIVATE);
						for(GlobalParamItem item : list.getData()){
							Editor e = sp.edit();
							e.putString(item.getMap_key(), item.getMap_value());
							e.commit();
						}
						mInited = true;
//						CYMGCallbackHelper.callbackResult(CYMGCallbackHelper.getCommonResult(CYMGPrompt.CODE_INIT_SUCCESS, null),context);
					}else{
						log.e("list is null");
					}
				} catch (Exception e) {
					log.e(e);
				}
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable error, String content) {
				log.d("statusCode:"+statusCode+",Header:"+headers+",requestGlobalParams result = "+content);
//				CYMGCallbackHelper.callbackResult(CYMGCallbackHelper.getCommonResult(CYMGPrompt.CODE_INIT_FAILED, null),context);
			}
		});
	}

	@Override
	public void doCustomerService(CYMGChannelEntity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doShowUserCenter(CYMGChannelEntity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doResume(CYMGChannelEntity entity) {
//		log.d("doResume");
		//显示悬浮窗
		if(SDKConfig.HAD_LOGIN){
			CYMGFlowWinManager.getInstance().show();
		}
	}

	@Override
	public void doPause(CYMGChannelEntity entity) {
	}

	@Override
	public void doStop(CYMGChannelEntity entity) {
//		log.d("doStop");
		//隐藏悬浮窗
		CYMGFlowWinManager.getInstance().gone();
	}

	@Override
	public void doFloatWindow(CYMGChannelEntity entity) {
		Bundle b = entity.getBundle();
		if(b.getBoolean(CYMGProtocolKeys.SHOW_FLOAT_WINDOW)){
			if(SDKConfig.HAD_LOGIN){
				CYMGFlowWinManager.getInstance().show();
			}
		}else{
			CYMGFlowWinManager.getInstance().gone();
		}
	}

	@Override
	public void doExtCommomAPI(CYMGChannelEntity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSdkVersion() {
		return VERSION;
	}
	
}
