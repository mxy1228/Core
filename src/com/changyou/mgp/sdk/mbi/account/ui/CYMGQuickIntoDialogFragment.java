package com.changyou.mgp.sdk.mbi.account.ui;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONObject;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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

public class CYMGQuickIntoDialogFragment extends BaseDialogFragment implements OnClickListener{
	
	private CYLog log = CYLog.getInstance();
	
	private static Activity mActivity;
	
	private ImageButton mCloseBtn;
	private Button mQuickIntoBtn;
	private Button mChangYouBtn;
	private Button mChengYouBtn;
	
	private String mPhoneNumStr;
	
	private CYMGLoadingDialogFragment mLoadingDialog;
	
	private boolean isRequest = false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(AccResource.getInstance(mActivity).mgp_sdk_2_0_dialog_quick_into, container,false);
		initView(view);
		initData();
		initEvent();
		return view;
	}
	

	@Override
	protected void initView(View view) {
		mCloseBtn=(ImageButton) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_quick_into_close_ImageButton);
		mQuickIntoBtn=(Button) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_quick_into_kuaisu_Button);
		mChengYouBtn=(Button) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_quick_into_chengyou_Button);
		mChangYouBtn=(Button) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_quick_into_changyou_Button);
		mLoadingDialog=CYMGLoadingDialogFragment.newInewInstance(mActivity);
		mLoadingDialog.setMessage(mActivity.getString(AccResource.getInstance(mActivity).mgp_sdk_2_0_loading_quick_into));
		CMBILogManager.printEventLog(mActivity, "0", "130001", "");
	}

	@Override
	protected void initData() {
		mPhoneNumStr=SystemUtils.getNativePhoneNumber(mActivity);
	}

	@Override
	protected void initEvent() {
		mCloseBtn.setOnClickListener(this);
		mQuickIntoBtn.setOnClickListener(this);
		mChengYouBtn.setOnClickListener(this);
		mChangYouBtn.setOnClickListener(this);
		
	}
	
	public static CYMGQuickIntoDialogFragment newInewInstance(Activity activity,Bundle bundle){
		mActivity=activity;
		CYMGQuickIntoDialogFragment cYMGQuickIntoDialogFragment=new CYMGQuickIntoDialogFragment();
		cYMGQuickIntoDialogFragment.setArguments(bundle);
		cYMGQuickIntoDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, AccResource.getInstance(mActivity).mgp_sdk_2_0_mian_dialog);
		return cYMGQuickIntoDialogFragment;
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		mActivity.finish();
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId()==AccResource.getInstance(mActivity).mgp_sdk_2_0_quick_into_close_ImageButton){
			CMBILogManager.printEventLog(mActivity, "0", "131004", "");
			mActivity.finish();
		}else if(v.getId()==AccResource.getInstance(mActivity).mgp_sdk_2_0_quick_into_kuaisu_Button){
			CMBILogManager.printEventLog(mActivity, "0", "131001", "");
			if(checkET()){
				if(isRequest==false){
					isRequest=true;
					chengYouPhoneRegister();
				}
			}else{
				Bundle bundle=new Bundle();
				bundle.putString(Params.REGISTER_MODE, "user");
				((CYMGMainDialogFragmentActivity)mActivity).changeDialogFragment(Contants.DialogFragmentTag.REGISTER_CONTAINER, bundle);
			}
		}else if(v.getId()==AccResource.getInstance(mActivity).mgp_sdk_2_0_quick_into_chengyou_Button){
			CMBILogManager.printEventLog(mActivity, "0", "131002", "");
			((CYMGMainDialogFragmentActivity)mActivity).changeDialogFragment(Contants.DialogFragmentTag.CHENGYOU_LOGIN, null);
		}else if(v.getId()==AccResource.getInstance(mActivity).mgp_sdk_2_0_quick_into_changyou_Button){
			CMBILogManager.printEventLog(mActivity, "0", "131003", "");
			((CYMGMainDialogFragmentActivity)mActivity).changeDialogFragment(Contants.DialogFragmentTag.CHANGYOU_LOGIN, null);
		}
	}
	
	private boolean checkET(){
		if(TextUtils.isEmpty(mPhoneNumStr)){
			return false;
		}else if(!NetWorkUtils.isNetworkConnected(mActivity)){
			return false;
		}
		return true;
	}
	
	private void chengYouPhoneRegister(){
		String deviceid = SystemUtils.getDeviceId(mActivity);
		MyHttpClient myHttpClient=new MyHttpClient(mActivity);
		Map map=new HashMap();
		map.put("account", mPhoneNumStr);
		map.put("device_id", deviceid);
		myHttpClient.post(HttpContants.getURL(HttpContants.CHENGYOU_PHONE_REGISTER), map, new MyAsyncResponseHandler("chengYouPhoneRegister"){
			@Override
			public void onStart() {
				mLoadingDialog.showDialog();
			}
			
			@Override
			public void onSuccess(int statusCode, String content) {
				try {
					log.d("statusCode:"+statusCode+",content:"+content);
					mLoadingDialog.dismissDialog();
					isRequest=false;
					if(!TextUtils.isEmpty(content)){
						JSONObject jsonObject=new JSONObject(content);
						String uid=jsonObject.getString("uid");
						String token=jsonObject.getString("token");
						String cn = jsonObject.getString("cn");
						CYMGLoginResultUtil.loginCallback(mActivity, Contants.ACC_TYPE_CHENGYOU+cn, uid, token, Contants.LOGIN_CHENGYOU_TYPE);
					}else{
						Toast.makeText(mActivity, AccResource.getInstance(mActivity).mgp_sdk_2_0_error_common_server, Toast.LENGTH_SHORT).show();
						Bundle bundle=new Bundle();
						bundle.putString(Params.REGISTER_MODE, "user");
						((CYMGMainDialogFragmentActivity)mActivity).changeDialogFragment(Contants.DialogFragmentTag.REGISTER_CONTAINER, bundle);
					}
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
					Bundle bundle=new Bundle();
					bundle.putString(Params.REGISTER_MODE, "user");
					((CYMGMainDialogFragmentActivity)mActivity).changeDialogFragment(Contants.DialogFragmentTag.REGISTER_CONTAINER, bundle);
				} catch (Exception e) {
					log.e(e);
				}
			}
		});
	}
	
	public void showDialog() {
		FragmentTransaction transaction=((FragmentActivity)mActivity).getSupportFragmentManager().beginTransaction();
        transaction.add(this, Contants.DialogFragmentTag.QUICK_INTO);
        transaction.commitAllowingStateLoss(); 
	}
	
}
