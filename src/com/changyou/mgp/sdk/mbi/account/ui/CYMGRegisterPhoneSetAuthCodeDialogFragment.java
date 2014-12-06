package com.changyou.mgp.sdk.mbi.account.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.Header;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.changyou.mgp.sdk.mbi.account.id.AccResource;
import com.changyou.mgp.sdk.mbi.account.utils.CYMGLoginResultUtil;
import com.changyou.mgp.sdk.mbi.config.Contants;
import com.changyou.mgp.sdk.mbi.config.HttpContants;
import com.changyou.mgp.sdk.mbi.config.Params;
import com.changyou.mgp.sdk.mbi.entity.ErrorInfo;
import com.changyou.mgp.sdk.mbi.http.MyAsyncResponseHandler;
import com.changyou.mgp.sdk.mbi.http.MyHttpClient;
import com.changyou.mgp.sdk.mbi.log.CYLog;
import com.changyou.mgp.sdk.mbi.mbi.manager.CMBILogManager;
import com.changyou.mgp.sdk.mbi.utils.JSONUtil;
import com.changyou.mgp.sdk.mbi.utils.NetWorkUtils;
import com.changyou.mgp.sdk.mbi.utils.SystemUtils;

public class CYMGRegisterPhoneSetAuthCodeDialogFragment extends BaseDialogFragment implements OnClickListener{
	
	private CYLog log = CYLog.getInstance();
	
	private static final int CODE_MIN = 6;
	
	private static Activity mActivity;
	
	private ImageButton mBackBtn;
	private ImageButton mCloseBtn;
	private ImageButton mGetAuthCodeBtn;
	private TextView mPhoneNumTv;
	private TextView mTreatyTv;
	private TextView mCountTv;
	private EditText mAuthCodeET;
	private Button mCommitBtn;
	
	private Bundle mBundle;
	private String mPhoneNumStr;
	private String mAuthCodeStr;
	
	private Timer mTimer;
	private int mCount = 60;
	
	private CYMGLoadingDialogFragment mLoadingDialog;
	
	private boolean isRequest = false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(AccResource.getInstance(mActivity).mgp_sdk_2_0_dialog_register_phone_set_authcode, container,false);
		initView(view);
		initData();
		initEvent();
		return view;
	}
	

	@Override
	protected void initView(View view) {
		mBackBtn=(ImageButton) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_register_phone_set_authcode_back_ImageButton);
		mCloseBtn=(ImageButton) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_register_phone_set_authcode_close_ImageButton);
		mGetAuthCodeBtn=(ImageButton) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_register_phone_set_authcode_get_authcode_ImageButton);
		mPhoneNumTv=(TextView) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_register_phone_set_authcode_num_TextView);
		mTreatyTv=(TextView) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_register_phone_set_authcode_treaty_TextView);
		mCountTv=(TextView) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_register_phone_set_authcode_get_authcode_TextView);
		mAuthCodeET=(EditText) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_register_phone_set_authcode_authcode_EditText);
		mCommitBtn=(Button) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_register_phone_set_authcode_commit_Button);
		mLoadingDialog=CYMGLoadingDialogFragment.newInewInstance(mActivity);
		mLoadingDialog.setMessage(mActivity.getString(AccResource.getInstance(mActivity).mgp_sdk_2_0_loading_register));
		CMBILogManager.printEventLog(mActivity, "0", "130006", "");
	}

	@Override
	protected void initData() {
		mBundle=getArguments();
		mPhoneNumStr=mBundle.getString(Params.PHONENUM);
		if(!TextUtils.isEmpty(mPhoneNumStr)){
			StringBuffer buffer = new StringBuffer();
			buffer.append(mPhoneNumStr);
			buffer.insert(3,' ');
			buffer.insert(8,' ');
			String numFormat=mActivity.getString(AccResource.getInstance(mActivity).mgp_sdk_2_0_register_phone_set_authcode_str_2);
			String numString=String.format(numFormat, buffer.toString());
			SpannableString spannableString=setStringColor(mActivity,numString,numString.indexOf("号")+1,numString.indexOf("会"),mActivity.getResources().getColor(AccResource.getInstance(mActivity).mgp_sdk_2_0_c1));
			mPhoneNumTv.setText(spannableString);
		}
		startTimer();
	}

	@Override
	protected void initEvent() {
		mBackBtn.setOnClickListener(this);
		mCloseBtn.setOnClickListener(this);
		mGetAuthCodeBtn.setOnClickListener(this);
		mTreatyTv.setOnClickListener(this);
		mCommitBtn.setOnClickListener(this);
	}
	
	public static CYMGRegisterPhoneSetAuthCodeDialogFragment newInewInstance(Activity activity,Bundle bundle){
		mActivity=activity;
		CYMGRegisterPhoneSetAuthCodeDialogFragment cymgRegisterPhoneSetAuthCodeDialogFragment=new CYMGRegisterPhoneSetAuthCodeDialogFragment();
		cymgRegisterPhoneSetAuthCodeDialogFragment.setArguments(bundle);
		cymgRegisterPhoneSetAuthCodeDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, AccResource.getInstance(mActivity).mgp_sdk_2_0_mian_dialog);
		return cymgRegisterPhoneSetAuthCodeDialogFragment;
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==AccResource.getInstance(mActivity).mgp_sdk_2_0_register_phone_set_authcode_back_ImageButton){
			CMBILogManager.printEventLog(mActivity, "0", "136003", "");
			dismiss();
		}else if(v.getId()==AccResource.getInstance(mActivity).mgp_sdk_2_0_register_phone_set_authcode_close_ImageButton){
			CMBILogManager.printEventLog(mActivity, "0", "136004", "");
			mActivity.finish();
		}else if(v.getId()==AccResource.getInstance(mActivity).mgp_sdk_2_0_register_phone_set_authcode_get_authcode_ImageButton){
			CMBILogManager.printEventLog(mActivity, "0", "136001", "");
			if(NetWorkUtils.isNetworkConnected(mActivity)){
				startTimer();
				getAuthCodeFromServer();
			}else{
				Toast.makeText(mActivity, AccResource.getInstance(mActivity).mgp_sdk_2_0_error_common_net, Toast.LENGTH_SHORT).show();
			}
		}else if(v.getId()==AccResource.getInstance(mActivity).mgp_sdk_2_0_register_phone_set_authcode_treaty_TextView){
			try {
				Intent intent = new Intent();        
				intent.setAction("android.intent.action.VIEW");    
				Uri content_url = Uri.parse(HttpContants.CNENGYOU_PROTOCOL);
				intent.setData(content_url);  
				startActivity(intent);
			} catch (Exception e) {
				log.e(e);
			}
		}else if(v.getId()==AccResource.getInstance(mActivity).mgp_sdk_2_0_register_phone_set_authcode_commit_Button){
			CMBILogManager.printEventLog(mActivity, "0", "136002", "");
			if(checkET()){
				if(isRequest==false){
					isRequest=true;
					chengYouPhoneRegister();
				}
			}
		}
	}
	
	private static SpannableString setStringColor(Context context,String str,int start, int end,int color){
		SpannableString sstr = new SpannableString(str);
		ForegroundColorSpan span = new ForegroundColorSpan(color);
		sstr.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return sstr;
	}
	
	private boolean checkET(){
		mAuthCodeStr=mAuthCodeET.getText().toString().trim();
		if(TextUtils.isEmpty(mAuthCodeStr)){
			Toast.makeText(mActivity, AccResource.getInstance(mActivity).mgp_sdk_2_0_error_register_auth_code, Toast.LENGTH_SHORT).show();
			return false;
		}else if(mAuthCodeStr.length()<CODE_MIN){
			Toast.makeText(mActivity, AccResource.getInstance(mActivity).mgp_sdk_2_0_error_register_auth_code, Toast.LENGTH_SHORT).show();
			return false;
		}else if(!NetWorkUtils.isNetworkConnected(mActivity)){
			Toast.makeText(mActivity, AccResource.getInstance(mActivity).mgp_sdk_2_0_error_common_net, Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	
	private void startTimer(){
		changeGetAuthCodeBtn(false);
		TimerTask task = new TimerTask(){

			@Override
			public void run() {
				if(mCount==0){
					changeGetAuthCodeBtn(true);
					mCount=60;
					mTimer.cancel();
				}
				changeGetAuthCodeTv(mCount);
				mCount--;
			}
		};
		mTimer=new Timer();
		mTimer.schedule(task,0,1000);
	}
	
	private void changeGetAuthCodeBtn(final boolean b){
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				mGetAuthCodeBtn.setClickable(b);
				if(b){
					mCountTv.setVisibility(View.GONE);
					mGetAuthCodeBtn.setImageDrawable(mActivity.getResources().getDrawable(AccResource.getInstance(mActivity).mgp_sdk_2_0_dialog_get_authcode_before));
				}else{
					mCountTv.setVisibility(View.VISIBLE);
					mGetAuthCodeBtn.setImageDrawable(mActivity.getResources().getDrawable(AccResource.getInstance(mActivity).mgp_sdk_2_0_dialog_get_authcode_after));
				}
			}
		});
	}
	
	private void changeGetAuthCodeTv(final int b){
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				mCountTv.setText(String.valueOf(b)+"s");
			}
		});
	}
	
	private void chengYouPhoneRegister(){
		String deviceid = SystemUtils.getDeviceId(mActivity);
		MyHttpClient myHttpClient=new MyHttpClient(mActivity);
		Map map=new HashMap();
		map.put("account", mPhoneNumStr);
		map.put("device_id", deviceid);
		map.put("captcha", mAuthCodeStr);
		myHttpClient.post(HttpContants.getURL(HttpContants.CHENGYOU_PHONE_REGISTER), map, new MyAsyncResponseHandler("chengYouPhoneRegister"){
			@Override
			public void onStart() {
				CMBILogManager.printEventLog(mActivity, "0", "110031", "");
				mLoadingDialog.showDialog();
			}
			
			@Override
			public void onSuccess(int statusCode, String content) {
				try {
					CMBILogManager.printEventLog(mActivity, "0", "120031", "");
					log.d("statusCode:"+statusCode+",content:"+content);
					mLoadingDialog.dismissDialog();
					isRequest=false;
					if(!TextUtils.isEmpty(content)){
						Toast.makeText(mActivity, AccResource.getInstance(mActivity).mgp_sdk_2_0_toast_register_success, Toast.LENGTH_SHORT).show();
						JSONObject jsonObject=new JSONObject(content);
						String uid=jsonObject.getString("uid");
						String token=jsonObject.getString("token");
						String cn = jsonObject.getString("cn");
						CYMGLoginResultUtil.loginCallback(mActivity, Contants.ACC_TYPE_CHENGYOU+cn, uid, token, Contants.LOGIN_CHENGYOU_TYPE);
					}else{
						Toast.makeText(mActivity, AccResource.getInstance(mActivity).mgp_sdk_2_0_error_common_server, Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					log.e(e);
				}
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable error, String content) {
				try {
					CMBILogManager.printEventLog(mActivity, "0", "120031", "");
					log.d("statusCode:"+statusCode+",Header:"+headers+",content:"+content);
					mLoadingDialog.dismissDialog();
					isRequest=false;
					if (!TextUtils.isEmpty(content)) {
						ErrorInfo info = JSONUtil.getMapper().readValue(content,new TypeReference<ErrorInfo>() {});
						if (info != null) {
							Toast.makeText(mActivity, info.getClient_message(), Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(mActivity, AccResource.getInstance(mActivity).mgp_sdk_2_0_error_common_server, Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(mActivity, AccResource.getInstance(mActivity).mgp_sdk_2_0_error_common_server, Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					log.e(e);
				}
			}
		});
	}
	
	private void getAuthCodeFromServer(){
		String deviceid = SystemUtils.getDeviceId(mActivity);
		MyHttpClient myHttpClient=new MyHttpClient(mActivity);
		Map map=new HashMap();
		map.put("account", mPhoneNumStr);
		myHttpClient.post(HttpContants.getURL(HttpContants.CHENGYOU_AUTH_CODE), map, new MyAsyncResponseHandler("getAuthCodeFromServer"){
			@Override
			public void onStart() {
				mLoadingDialog.setMessage(mActivity.getString(AccResource.getInstance(mActivity).mgp_sdk_2_0_loading_commit));
				mLoadingDialog.showDialog();
			}
			
			@Override
			public void onSuccess(int statusCode, String content) {
				try {
					log.d("statusCode:"+statusCode+",content:"+content);
					mLoadingDialog.dismissDialog();
				} catch (Exception e) {
					log.e(e);
				}
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable error, String content) {
				try {
					log.d("statusCode:"+statusCode+",Header:"+headers+",content:"+content);
					mLoadingDialog.dismissDialog();
					mTimer.cancel();
					mCount=60;
					changeGetAuthCodeTv(mCount);
					changeGetAuthCodeBtn(true);
					if (!TextUtils.isEmpty(content)) {
						ErrorInfo info = JSONUtil.getMapper().readValue(content,new TypeReference<ErrorInfo>() {});
						if (info != null) {
							Toast.makeText(mActivity, info.getClient_message(), Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(mActivity, AccResource.getInstance(mActivity).mgp_sdk_2_0_error_common_server, Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(mActivity, AccResource.getInstance(mActivity).mgp_sdk_2_0_error_common_server, Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					log.e(e);
				}
			}
		});
	}
	
	public void showDialog() {
		FragmentTransaction transaction=((FragmentActivity)mActivity).getSupportFragmentManager().beginTransaction();
        transaction.add(this, Contants.DialogFragmentTag.REGISTER_SET_AUTH_CODE);
        transaction.commitAllowingStateLoss(); 
	}
	
}