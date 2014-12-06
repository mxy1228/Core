package com.changyou.mgp.sdk.mbi.update.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.changyou.mgp.sdk.downloader.bean.DownloadAppInfo;
import com.changyou.mgp.sdk.mbi.account.ui.BaseDialogFragment;
import com.changyou.mgp.sdk.mbi.config.Contants;
import com.changyou.mgp.sdk.mbi.update.id.UpdateResource;
/**
 * 
 * @ClassName: CYMGUpdateWifiNoticeDialogFragment 
 * @Description: WIFI提示Dialog
 * @author J.Beyond 
 * @date 2014年8月6日 上午10:33:42 
 *
 */
public class CYMGUpdateWifiNoticeDialogFragment extends BaseDialogFragment implements OnClickListener{

	private static Activity mActivity;
	private LinearLayout mLlUpdateConfirmBtn;
	private LinearLayout mLlUpdateCancelBtn;
	private FragmentManager mFragmentManager;
	private static CYMGUpdateWifiNoticeDialogFragment mWifiNoticeDialogFragment;
	private DownloadAppInfo mAppInfo;

	
	public void showDialog() {
		mFragmentManager = ((FragmentActivity)mActivity).getSupportFragmentManager();
		FragmentTransaction transaction=mFragmentManager.beginTransaction();
		if (!this.isVisible()) {
//			show(transaction, Contants.DialogFragmentTag.UPDATE_WIFI_NOTICE);
			transaction.add(this, Contants.DialogFragmentTag.UPDATE_WIFI_NOTICE);
			transaction.commitAllowingStateLoss();
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(UpdateResource.getInstance(mActivity).mgp_sdk_2_0_dialog_update_wifi_notice, container,false);
		initView(view);
		initData();
		initEvent();
		return view;
	}
	
	@Override  
	protected void initView(View view) {
		mLlUpdateCancelBtn = (LinearLayout) view.findViewById(UpdateResource.getInstance(mActivity).mgp_update_download_cancel_btn_ll);
		mLlUpdateConfirmBtn = (LinearLayout) view.findViewById(UpdateResource.getInstance(mActivity).mgp_update_download_confirm_btn_ll);
	}

	@Override
	protected void initData() {
		//获取到下载参数
		Bundle bundle = getArguments();
		mAppInfo = (DownloadAppInfo) bundle.getSerializable("DownloadAppInfo");
	}

	@Override
	protected void initEvent() {
		mLlUpdateConfirmBtn.setOnClickListener(this);
		mLlUpdateCancelBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		if (view == mLlUpdateConfirmBtn) {
			//切换至下载Dialog
			mFragmentManager.popBackStack();
			((CYMGSplashActivity)mActivity).setWillingDownloadWithoutWifi(true);
			((CYMGSplashActivity)mActivity).changeDialogFragment(Contants.DialogFragmentTag.UPDATE_DOWNLOAD);
			dismiss();
		}else if(view == mLlUpdateCancelBtn){
			//强制更新，关闭游戏
			if (mAppInfo.getUpdate_level() == 2) {
//				android.os.Process.killProcess(android.os.Process.myPid());
//	            System.exit(0); 
				((CYMGSplashActivity)mActivity).changeDialogFragment(Contants.DialogFragmentTag.UPDATE_FORCE_NOTICE);
			}else {
				//销毁Dialog
				((CYMGSplashActivity)mActivity).setDownloadCancled(true);
				dismiss();
			}
		}
	}

	public static CYMGUpdateWifiNoticeDialogFragment newInstance(Activity activity,Bundle bundle) {
		mActivity = activity;
		if (mWifiNoticeDialogFragment == null) {
			mWifiNoticeDialogFragment = new CYMGUpdateWifiNoticeDialogFragment();
			mWifiNoticeDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, UpdateResource.getInstance(mActivity).mgp_sdk_2_0_update_mian_dialog);
			mWifiNoticeDialogFragment.setCancelable(false);
			mWifiNoticeDialogFragment.setArguments(bundle);
		}
		return mWifiNoticeDialogFragment;
	}
	
	

}
