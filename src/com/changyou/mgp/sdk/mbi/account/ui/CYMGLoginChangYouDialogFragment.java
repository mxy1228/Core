package com.changyou.mgp.sdk.mbi.account.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.codehaus.jackson.type.TypeReference;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.changyou.mgp.sdk.mbi.account.id.AccResource;
import com.changyou.mgp.sdk.mbi.account.utils.CYMGLoginResultUtil;
import com.changyou.mgp.sdk.mbi.config.Contants;
import com.changyou.mgp.sdk.mbi.config.HttpContants;
import com.changyou.mgp.sdk.mbi.entity.ErrorInfo;
import com.changyou.mgp.sdk.mbi.entity.LoginInfo;
import com.changyou.mgp.sdk.mbi.http.MyAsyncResponseHandler;
import com.changyou.mgp.sdk.mbi.http.MyHttpClient;
import com.changyou.mgp.sdk.mbi.log.CYLog;
import com.changyou.mgp.sdk.mbi.mbi.manager.CMBILogManager;
import com.changyou.mgp.sdk.mbi.utils.DesUtil;
import com.changyou.mgp.sdk.mbi.utils.JSONUtil;
import com.changyou.mgp.sdk.mbi.utils.NetWorkUtils;
import com.changyou.mgp.sdk.mbi.utils.SystemUtils;

public class CYMGLoginChangYouDialogFragment extends BaseDialogFragment implements OnClickListener,OnEditorActionListener{
	
	private CYLog log = CYLog.getInstance();
	
	private static final int ACC_MIN = 5;
	private static final int PSWD_MIN = 4;
	
	private static Activity mActivity;
	
	private ImageButton mBackBtn;
	private ImageButton mCloseBtn;
	private ImageButton mForgetBtn;
	private EditText mAccountET;
	private EditText mPasswordET;
	private Button mLoginBtn;
	
	private String mAccountStr;
	private String mPasswordStr;
	
	private CYMGLoadingDialogFragment mLoadingDialog;
	
	private boolean isRequest = false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(AccResource.getInstance(mActivity).mgp_sdk_2_0_dialog_changyou_login, container,false);
		initView(view);
		initData();
		initEvent();
		return view;
	}
	

	@Override
	protected void initView(View view) {
		mBackBtn=(ImageButton) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_login_changyou_back_ImageButton);
		mCloseBtn=(ImageButton) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_login_changyou_close_ImageButton);
		mForgetBtn=(ImageButton) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_login_changyou_forget_ImageButton);
		mAccountET=(EditText) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_login_changyou_account_EditText);
		mPasswordET=(EditText) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_login_changyou_password_EditText);
		mLoginBtn=(Button) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_login_changyou_login_Button);
		mLoadingDialog=CYMGLoadingDialogFragment.newInewInstance(mActivity);
		mLoadingDialog.setMessage(mActivity.getString(AccResource.getInstance(mActivity).mgp_sdk_2_0_loading_login));
		CMBILogManager.printEventLog(mActivity, "0", "130004", "");
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initEvent() {
		mBackBtn.setOnClickListener(this);
		mCloseBtn.setOnClickListener(this);
		mForgetBtn.setOnClickListener(this);
		mLoginBtn.setOnClickListener(this);
		mAccountET.setOnEditorActionListener(this);
		mPasswordET.setOnEditorActionListener(this);
	}
	
	public static CYMGLoginChangYouDialogFragment newInewInstance(Activity activity,Bundle bundle){
		mActivity=activity;
		CYMGLoginChangYouDialogFragment cYMGChangYouLoginDialogFragment=new CYMGLoginChangYouDialogFragment();
		cYMGChangYouLoginDialogFragment.setArguments(bundle);
		cYMGChangYouLoginDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, AccResource.getInstance(mActivity).mgp_sdk_2_0_mian_dialog);
		return cYMGChangYouLoginDialogFragment;
	}
	
	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		switch (actionId) {
		case EditorInfo.IME_ACTION_NEXT:
			mPasswordET.requestFocus();
			break;
		case EditorInfo.IME_ACTION_GO:
			CMBILogManager.printEventLog(mActivity, "0", "134002", "");
			if(checkET()){
				if(isRequest==false){
					isRequest=true;
					changYouLogin();
				}
			}
			break;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==AccResource.getInstance(mActivity).mgp_sdk_2_0_login_changyou_back_ImageButton){
			CMBILogManager.printEventLog(mActivity, "0", "134003", "");
			dismiss();
		}else if(v.getId()==AccResource.getInstance(mActivity).mgp_sdk_2_0_login_changyou_close_ImageButton){
			CMBILogManager.printEventLog(mActivity, "0", "134004", "");
			mActivity.finish();
		}else if(v.getId()==AccResource.getInstance(mActivity).mgp_sdk_2_0_login_changyou_forget_ImageButton){
			try {
				CMBILogManager.printEventLog(mActivity, "0", "134001", "");
				Intent intent = new Intent();        
				intent.setAction("android.intent.action.VIEW");    
				Uri content_url = Uri.parse(HttpContants.CNANGYOU_FORGET_PASSWORD);
				intent.setData(content_url);  
				startActivity(intent);
			} catch (Exception e) {
				log.e(e);
			}
		}else if(v.getId()==AccResource.getInstance(mActivity).mgp_sdk_2_0_login_changyou_login_Button){
			CMBILogManager.printEventLog(mActivity, "0", "134002", "");
			if(checkET()){
				if(isRequest==false){
					isRequest=true;
					changYouLogin();
				}
			}
		}
	}
	
	private boolean checkET(){
		mAccountStr=mAccountET.getText().toString().toLowerCase().trim();
		mPasswordStr=mPasswordET.getText().toString();
		if(TextUtils.isEmpty(mAccountStr)){
			Toast.makeText(mActivity, AccResource.getInstance(mActivity).mgp_sdk_2_0_error_login_account, Toast.LENGTH_SHORT).show();
			return false;
		}else if(mAccountStr.length()<ACC_MIN){
			Toast.makeText(mActivity, AccResource.getInstance(mActivity).mgp_sdk_2_0_error_login_account, Toast.LENGTH_SHORT).show();
			return false;
		}else if(TextUtils.isEmpty(mPasswordStr)){
			Toast.makeText(mActivity, AccResource.getInstance(mActivity).mgp_sdk_2_0_error_login_Password, Toast.LENGTH_SHORT).show();
			return false;
		}else if(mPasswordStr.length()<PSWD_MIN){
			Toast.makeText(mActivity, AccResource.getInstance(mActivity).mgp_sdk_2_0_error_login_Password, Toast.LENGTH_SHORT).show();
			return false;
		}else if(!checkPass(mPasswordStr)){
			Toast.makeText(mActivity, AccResource.getInstance(mActivity).mgp_sdk_2_0_error_login_sign, Toast.LENGTH_SHORT).show();
			return false;
		}else if(!NetWorkUtils.isNetworkConnected(mActivity)){
			Toast.makeText(mActivity, AccResource.getInstance(mActivity).mgp_sdk_2_0_error_common_net, Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	
	private boolean checkPass(String password) {
		Pattern p = Pattern.compile("^[^\'“'\'”'\'‘'\'’'\"\\ ,，]+$");
		Matcher m = p.matcher(password);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}
	
	private void changYouLogin(){
		String deviceid = SystemUtils.getDeviceId(mActivity);
//		String phone = SystemUtils.getNativePhoneNumber(mActivity);
//		if (phone == null) {
//			phone = "";
//		}
		Map map = new HashMap();
		map.put("device_id", deviceid);
		map.put("password", encryPassword(mPasswordStr));
//		map.put("phone", phone);
		map.put("account", mAccountStr);
		MyHttpClient myHttpClient=new MyHttpClient(mActivity);
		myHttpClient.post(HttpContants.getURL(HttpContants.CHANGYOU_LOGIN_NEW), map, new MyAsyncResponseHandler(){
			@Override
			public void onStart() {
				CMBILogManager.printEventLog(mActivity, "0", "110021", "");
				mLoadingDialog.showDialog();
			}
			
			@Override
			public void onSuccess(int statusCode, String content) {
				try {
					CMBILogManager.printEventLog(mActivity, "0", "120021", "");
					log.d("statusCode:"+statusCode+",content:"+content);
					mLoadingDialog.dismissDialog();
					isRequest=false;
					if (!TextUtils.isEmpty(content)) {
						LoginInfo info=JSONUtil.getMapper().readValue(content, new TypeReference<LoginInfo>() {
						});
						String uid = info.getUid();
						String token = info.getToken();
						CYMGLoginResultUtil.loginCallback(mActivity, Contants.ACC_TYPE_CHANGYOU+mAccountStr,uid, token, Contants.LOGIN_CHANGYOU_TYPE);
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
					CMBILogManager.printEventLog(mActivity, "0", "120021", "");
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
	
	private String encryPassword(String password) {
		String result = null;
		try {
			result = new DesUtil().encrypt(password);
		} catch (Exception e) {
			log.e(e);
		}
		return result;
	}
	
	public void showDialog() {
		FragmentTransaction transaction=((FragmentActivity)mActivity).getSupportFragmentManager().beginTransaction();
        transaction.add(this, Contants.DialogFragmentTag.CHANGYOU_LOGIN);
        transaction.commitAllowingStateLoss(); 
	}
	
}
