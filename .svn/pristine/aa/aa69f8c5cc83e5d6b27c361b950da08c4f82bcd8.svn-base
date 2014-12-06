package com.changyou.mgp.sdk.mbi.account.ui;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;
import org.json.JSONObject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
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
import com.changyou.mgp.sdk.mbi.utils.JSONUtil;
import com.changyou.mgp.sdk.mbi.utils.MetaDataValueUtils;
import com.changyou.mgp.sdk.mbi.utils.NetWorkUtils;

public class CYMGActivateCodeDialogFragment extends BaseDialogFragment implements OnClickListener{
	
	private CYLog log = CYLog.getInstance();
	
	private static Activity mActivity;
	
	private ImageButton mBackBtn;
	private ImageButton mCloseBtn;
	private EditText mActivateCodeET;
	private Button mCommitBtn;
	private TextView mNotCodeTv;
	
	private String mUid;
	private String mToken;
	private String mType;
	
	private Bundle mBundle;
	
	private String mActivateCodeStr;
	
	private CYMGLoadingDialogFragment mLoadingDialog;
	
	private boolean isRequest = false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(AccResource.getInstance(mActivity).mgp_sdk_2_0_dialog_activate_code, container,false);
		initView(view);
		initData();
		initEvent();
		return view;
	}
	

	@Override
	protected void initView(View view) {
		mBackBtn=(ImageButton) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_activate_code_back_ImageButton);
		mCloseBtn=(ImageButton) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_activate_code_close_ImageButton);
		mActivateCodeET=(EditText) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_activate_code_EditText);
		mCommitBtn=(Button) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_activate_code_commit_Button);
		mNotCodeTv=(TextView) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_activate_code_notcode_TextView);
		mLoadingDialog=CYMGLoadingDialogFragment.newInewInstance(mActivity);
	}

	@Override
	protected void initData() {
		mBundle=getArguments();
		mUid=mBundle.getString(Params.UID);
		mToken=mBundle.getString(Params.TOKEN);
		mType=mBundle.getString(Params.TYPE);
	}

	@Override
	protected void initEvent() {
		mBackBtn.setOnClickListener(this);
		mCloseBtn.setOnClickListener(this);
		mCommitBtn.setOnClickListener(this);
		mNotCodeTv.setOnClickListener(this);
	}
	
	public static CYMGActivateCodeDialogFragment newInewInstance(Activity activity,Bundle bundle){
		mActivity=activity;
		CYMGActivateCodeDialogFragment cymgActivateCodeDialogFragment=new CYMGActivateCodeDialogFragment();
		cymgActivateCodeDialogFragment.setArguments(bundle);
		cymgActivateCodeDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, AccResource.getInstance(mActivity).mgp_sdk_2_0_mian_dialog);
		return cymgActivateCodeDialogFragment;
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==AccResource.getInstance(mActivity).mgp_sdk_2_0_activate_code_back_ImageButton){
			dismiss();
		}else if(v.getId()==AccResource.getInstance(mActivity).mgp_sdk_2_0_activate_code_close_ImageButton){
			mActivity.finish();
		}else if(v.getId()==AccResource.getInstance(mActivity).mgp_sdk_2_0_activate_code_commit_Button){
			if(checkET()){
				if(isRequest==false){
					isRequest=true;
					ActivateCodeVerify();
				}
			}
		}else if(v.getId()==AccResource.getInstance(mActivity).mgp_sdk_2_0_activate_code_notcode_TextView){
			try {
				String appkey=MetaDataValueUtils.getAppKey(mActivity);
				Intent intent = new Intent();        
				intent.setAction("android.intent.action.VIEW");
				Uri content_url = Uri.parse(HttpContants.CNENGYOU_ACTIVATE_CODE+"/"+appkey+"/"+mToken);
				intent.setData(content_url);  
				startActivity(intent);
			} catch (Exception e) {
				log.e(e);
			}
		}
	}
	
	private boolean checkET(){
		mActivateCodeStr=mActivateCodeET.getText().toString().trim();
		if(TextUtils.isEmpty(mActivateCodeStr)){
			Toast.makeText(mActivity, AccResource.getInstance(mActivity).mgp_sdk_2_0_error_activate_code, Toast.LENGTH_SHORT).show();
			return false;
		}else if(!NetWorkUtils.isNetworkConnected(mActivity)){
			Toast.makeText(mActivity, AccResource.getInstance(mActivity).mgp_sdk_2_0_error_common_net, Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	
	@Override
	public void onCancel(DialogInterface dialog) {
		mActivity.finish();
	}
	
	private void ActivateCodeVerify(){
		MyHttpClient myHttpClient=new MyHttpClient(mActivity);
		Map map = new HashMap();
		map.put("code", mActivateCodeStr);
		map.put("sys_type", "android");
		map.put("user_id", mUid);
		myHttpClient.post(HttpContants.getURL(HttpContants.ACTIVATE_CODE_USE), map, new MyAsyncResponseHandler("ActivateCodeVerify"){
			@Override
			public void onStart() {
				mLoadingDialog.setMessage(mActivity.getString(AccResource.getInstance(mActivity).mgp_sdk_2_0_loading_verify));
				mLoadingDialog.showDialog();
			}
			
			@Override
			public void onSuccess(int statusCode, String content) {
				try {
					log.d("statusCode:"+statusCode+",content:"+content);
					mLoadingDialog.dismissDialog();
					isRequest=false;
					if (!TextUtils.isEmpty(content)) {
						JSONObject jsonObject=new JSONObject(content);
						int status = jsonObject.getInt("status");
						if(status==1){
							CYMGLoginResultUtil.loginCallbackResult(mActivity, mUid, mToken, mType);
						}
					}else{
						Toast.makeText(mActivity, AccResource.getInstance(mActivity).mgp_sdk_2_0_error_common_server, Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					log.e(e);
				}
			}
			
			@Override
			public void onFailure(int statusCode, Throwable error,
					String content) {
				try {
					log.d("statusCode:"+statusCode+",content:"+content);
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
	
	public void showDialog() {
		FragmentTransaction transaction=((FragmentActivity)mActivity).getSupportFragmentManager().beginTransaction();
        transaction.add(this, Contants.DialogFragmentTag.ACTIVATE_CODE);
        transaction.commitAllowingStateLoss(); 
	}
}
