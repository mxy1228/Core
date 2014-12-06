package com.changyou.mgp.sdk.mbi.update.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.changyou.mgp.sdk.mbi.account.ui.BaseDialogFragment;
import com.changyou.mgp.sdk.mbi.config.Contants;
import com.changyou.mgp.sdk.mbi.log.CYLog;
import com.changyou.mgp.sdk.mbi.update.id.UpdateResource;
import com.changyou.mgp.sdk.mbi.utils.NetWorkUtils;
/**
 * 
 * @ClassName: CYMGUpdateNetErrorDialogFragment 
 * @Description: 网络异常Dialog
 * @author J.Beyond 
 * @date 2014年8月6日 上午10:33:42 
 *
 */
public class CYMGUpdateNetErrorDialogFragment extends BaseDialogFragment implements OnClickListener{
	CYLog mLog = CYLog.getInstance();
	private LinearLayout mLlNetErrorRetryBtn;
	private static Activity mActivity;
	private static CYMGUpdateNetErrorDialogFragment mErrorDialogFragment;

	
	public static CYMGUpdateNetErrorDialogFragment newInstance(Activity activity){
		mActivity = activity;
//		if (mErrorDialogFragment == null) {
			mErrorDialogFragment = new CYMGUpdateNetErrorDialogFragment();
			mErrorDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, UpdateResource.getInstance(mActivity).mgp_sdk_2_0_update_mian_dialog);
			mErrorDialogFragment.setCancelable(true);
//		}
		return mErrorDialogFragment;
	}
	
	public void showDialog() {
		FragmentTransaction transaction=((FragmentActivity)mActivity).getSupportFragmentManager().beginTransaction();
//		show(transaction, Contants.DialogFragmentTag.UPDATE_NETERROR_NOTICE);
		transaction.add(this, Contants.DialogFragmentTag.UPDATE_NETERROR_NOTICE);
		transaction.commitAllowingStateLoss();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(UpdateResource.getInstance(mActivity).mgp_sdk_2_0_dialog_update_neterror, container,false);
		initView(view);
		initData();
		initEvent();
		return view;
	}
	
	@Override
	protected void initView(View view) {
		mLlNetErrorRetryBtn = (LinearLayout) view.findViewById(UpdateResource.getInstance(mActivity).mgp_update_neterror_retry_btn_ll);
	}

	@Override
	protected void initData() {
		
	}

	@Override
	protected void initEvent() {
		mLlNetErrorRetryBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		if (view == mLlNetErrorRetryBtn) {
			if (mActivity == null) {
				mLog.e("mActivity is null");
				return;
			}
			((CYMGSplashActivity)mActivity).setNetConnected(true);
			//判断是否网络连接
			if (NetWorkUtils.isNetworkConnected(mActivity)) {
				//判断Wifi是否连接
				if (NetWorkUtils.isWifiConnected(mActivity)) {
					//Wifi环境直接切换到下载Dialog
					((CYMGSplashActivity)mActivity).changeDialogFragment(Contants.DialogFragmentTag.UPDATE_DOWNLOAD);
					((CYMGSplashActivity)mActivity).setWifiConnected(true);
				}else {
					//数据流量环境，切换到用户选择Dialog
					boolean isWilling = ((CYMGSplashActivity)mActivity).isWillingDownloadWithoutWifi();
					if (isWilling) {
						((CYMGSplashActivity)mActivity).changeDialogFragment(Contants.DialogFragmentTag.UPDATE_DOWNLOAD);
					}else {
						((CYMGSplashActivity)mActivity).changeDialogFragment(Contants.DialogFragmentTag.UPDATE_WIFI_NOTICE);
						((CYMGSplashActivity)mActivity).setWifiConnected(false);
					}
				}
				dismiss();
			}else {
				((CYMGSplashActivity)mActivity).setNetConnected(false);
				Toast.makeText(mActivity, "当前网络异常！", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	@Override
	public void onDismiss(DialogInterface dialog) {
		//销毁Dialog
//		((CYMGSplashActivity)mActivity).setDownloadCancled(true);
		super.onDismiss(dialog);
	}

}
