package com.changyou.mgp.sdk.mbi.account.ui;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.codehaus.jackson.type.TypeReference;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.changyou.mgp.sdk.mbi.account.id.AccResource;
import com.changyou.mgp.sdk.mbi.config.Contants;
import com.changyou.mgp.sdk.mbi.config.HttpContants;
import com.changyou.mgp.sdk.mbi.config.Params;
import com.changyou.mgp.sdk.mbi.entity.ErrorInfo;
import com.changyou.mgp.sdk.mbi.http.MyAsyncResponseHandler;
import com.changyou.mgp.sdk.mbi.http.MyHttpClient;
import com.changyou.mgp.sdk.mbi.log.CYLog;
import com.changyou.mgp.sdk.mbi.mbi.manager.CMBILogManager;
import com.changyou.mgp.sdk.mbi.ui.base.BaseFragment;
import com.changyou.mgp.sdk.mbi.utils.JSONUtil;
import com.changyou.mgp.sdk.mbi.utils.NetWorkUtils;
import com.changyou.mgp.sdk.mbi.utils.SystemUtils;

public class CYMGResgisrForPhoneFragment extends BaseFragment implements OnClickListener{
	
	private CYLog log = CYLog.getInstance();
	
	private static final int NUM_MIN = 11;
	
	private Activity mActivity;
	
	private EditText mPhoneNumEt;
	private Button mCommitBtn;
	
	private String mPhoneNumStr;
	
	private CYMGLoadingDialogFragment mLoadingDialog;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity=activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(AccResource.getInstance(mActivity).mgp_sdk_2_0_dialog_register_for_phone, container, false);
		initView(view);
		initData();
		initEvent();
		return view;
	}
	
	private void initView(View view){
		mPhoneNumEt=(EditText) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_register_for_phone_num_EditText);
		mCommitBtn=(Button) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_register_for_phone_commit_Button);
		mLoadingDialog=CYMGLoadingDialogFragment.newInewInstance(mActivity);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initEvent() {
		mCommitBtn.setOnClickListener(this);
		mPhoneNumEt.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					CMBILogManager.printEventLog(mActivity, "0", "135003", "");
				}
			}
		});
		setEditTextListener(mPhoneNumEt, 13);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==AccResource.getInstance(mActivity).mgp_sdk_2_0_register_for_phone_commit_Button){
			CMBILogManager.printEventLog(mActivity, "0", "135004", "");
			if(checkET()){
				getAuthCodeFromServer();
			}
		}
	}
	
	private boolean checkET(){
		mPhoneNumStr=mPhoneNumEt.getText().toString().trim().replace(" ","");
		if(TextUtils.isEmpty(mPhoneNumStr)){
			Toast.makeText(mActivity, AccResource.getInstance(mActivity).mgp_sdk_2_0_error_register_phone_num, Toast.LENGTH_SHORT).show();
			return false;
		}else if(mPhoneNumStr.length()<NUM_MIN){
			Toast.makeText(mActivity, AccResource.getInstance(mActivity).mgp_sdk_2_0_error_register_phone_num, Toast.LENGTH_SHORT).show();
			return false;
		}else if(!NetWorkUtils.isNetworkConnected(mActivity)){
			Toast.makeText(mActivity, AccResource.getInstance(mActivity).mgp_sdk_2_0_error_common_net, Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	
	private void getAuthCodeFromServer(){
		String deviceid = SystemUtils.getDeviceId(mActivity);
		MyHttpClient myHttpClient=new MyHttpClient(mActivity);
		Map map=new HashMap();
		map.put("account", mPhoneNumStr);
		myHttpClient.post(HttpContants.getURL(HttpContants.CHENGYOU_AUTH_CODE), map, new MyAsyncResponseHandler(){
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
					Bundle bundle=new Bundle();
					bundle.putString(Params.PHONENUM, mPhoneNumStr);
					((CYMGMainDialogFragmentActivity)mActivity).changeDialogFragment(Contants.DialogFragmentTag.REGISTER_SET_AUTH_CODE, bundle);
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
				} catch (Exception e) {
					log.e(e);
				}
			}
		});
	}

	private void setEditTextListener(final EditText editText,final int a){
		editText.addTextChangedListener(new TextWatcher() {  
            int beforeTextLength = 0;  
            int onTextLength = 0;  
            boolean isChanged = false;  
  
            int location = 0;// 记录光标的位置  
            private char[] tempChar;  
            private StringBuffer buffer = new StringBuffer();  
            int konggeNumberB = 0;  
  
            @Override  
            public void onTextChanged(CharSequence s, int start, int before, int count) {  
                // TODO Auto-generated method stub  
                onTextLength = s.length();  
                buffer.append(s.toString());  
                if (onTextLength == beforeTextLength || onTextLength <= 2 || isChanged) {  
                    isChanged = false;  
                    return;  
                }  
                isChanged = true;  
            }  
  
            @Override  
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  
                // TODO Auto-generated method stub  
                beforeTextLength = s.length();  
                if (buffer.length() > 0) {  
                    buffer.delete(0, buffer.length());  
                }  
                konggeNumberB = 0;  
                for (int i = 0; i < s.length(); i++) {  
                    if (s.charAt(i) == ' ') {  
                        konggeNumberB++;  
                    }  
                }  
            }  
  
            @Override  
            public void afterTextChanged(Editable s) {  
                // TODO Auto-generated method stub  
                if (isChanged) {  
                    location = editText.getSelectionEnd();  
                    int index = 0;  
                    while (index < buffer.length()) {  
                        if (buffer.charAt(index) == ' ') {  
                            buffer.deleteCharAt(index);  
                        } else {  
                            index++;  
                        }  
                    }  
  
                    index = 0;  
                    int konggeNumberC = 0;  
                    while (index < buffer.length()) {  
                        if ((index == 3 || index == 8)) {  
                            buffer.insert(index, ' ');  
                            konggeNumberC++;  
                        }  
                        index++;  
                    }  
  
                    if (konggeNumberC > konggeNumberB) {  
                        location += (konggeNumberC - konggeNumberB);
                    }  
  
                    tempChar = new char[buffer.length()];  
                    buffer.getChars(0, buffer.length(), tempChar, 0); 
                    String str = buffer.toString();
                    if (location > str.length()) {  
                        location = str.length();
                    } else if (location < 0) {  
                        location = 0;  
                    }  
  
                    editText.setText(str);  
                    if(str.length()<=a){
                    	Editable etable = editText.getText();  
                    	Selection.setSelection(etable, location);  
                    }
                    isChanged = false;  
                }  
            }  
        });
	}

}
