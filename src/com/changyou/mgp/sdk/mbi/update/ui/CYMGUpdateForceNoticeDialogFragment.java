package com.changyou.mgp.sdk.mbi.update.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.changyou.mgp.sdk.downloader.bean.DownloadAppInfo;
import com.changyou.mgp.sdk.downloader.utils.DownloadUtil;
import com.changyou.mgp.sdk.mbi.R;
import com.changyou.mgp.sdk.mbi.account.ui.BaseDialogFragment;
import com.changyou.mgp.sdk.mbi.config.Contants;
import com.changyou.mgp.sdk.mbi.mbi.manager.CMBILogManager;
import com.changyou.mgp.sdk.mbi.update.id.UpdateResource;
import com.changyou.mgp.sdk.mbi.utils.NetWorkUtils;
/**
 * 
 * @ClassName: CYMGUpdateForceNoticeDialogFragment 
 * @Description: 强制更新提示Dialog
 * @author J.Beyond 
 * @date 2014年8月6日 上午10:33:42 
 *
 */
public class CYMGUpdateForceNoticeDialogFragment extends BaseDialogFragment implements OnClickListener{

	private static Activity mActivity;
	private TextView mTvForceUpdateTitle;
	private TextView mTvForceUpdateDesc;
	private LinearLayout mLlForceUpdateBtn;
	private DownloadAppInfo mAppInfo;
	private static CYMGUpdateForceNoticeDialogFragment mForceNoticeDialogFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(UpdateResource.getInstance(mActivity).mgp_sdk_2_0_dialog_update_force_notice, container,false);
		initView(view);
		initData();
		initEvent();
		return view;
	}
	
	@Override
	protected void initView(View view) {
		mTvForceUpdateTitle = (TextView) view.findViewById(UpdateResource.getInstance(mActivity).mgp_update_force_title_txt_tv);
		mTvForceUpdateDesc = (TextView) view.findViewById(UpdateResource.getInstance(mActivity).mgp_update_force_desc_txt_tv);
		mLlForceUpdateBtn = (LinearLayout) view.findViewById(UpdateResource.getInstance(mActivity).mgp_update_force_immediately_btn_ll);
	}

	@Override
	protected void initData() {
		//获取到下载参数
		Bundle bundle = getArguments();
		mAppInfo = (DownloadAppInfo) bundle.getSerializable("DownloadAppInfo");
		int packageSize = bundle.getInt("package_size");
		//绑定界面控件
//		mTvForceUpdateTitle.setText(mAppInfo.getUpdate_title());
		String formatSize = DownloadUtil.bytesFormat(packageSize * 1024);
		mTvForceUpdateDesc.setText(mAppInfo.getUpdate_description()+"("+formatSize+")");
	}

	@Override
	protected void initEvent() {
		mLlForceUpdateBtn.setOnClickListener(this);
	}
	
	public static CYMGUpdateForceNoticeDialogFragment newInstance(Activity activity, Bundle bundle){
		mActivity = activity;
		if (mForceNoticeDialogFragment == null) {
			mForceNoticeDialogFragment = new CYMGUpdateForceNoticeDialogFragment();
			mForceNoticeDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, UpdateResource.getInstance(mActivity).mgp_sdk_2_0_update_mian_dialog);
			mForceNoticeDialogFragment.setArguments(bundle);
		}
		//强制更新不可按后退键
		mForceNoticeDialogFragment.setCancelable(false);
		return mForceNoticeDialogFragment;
	}
	
	public void showDialog() {
		FragmentTransaction transaction=((FragmentActivity)mActivity).getSupportFragmentManager().beginTransaction();
//		show(transaction, Contants.DialogFragmentTag.UPDATE_FORCE_NOTICE);
		transaction.add(this, Contants.DialogFragmentTag.UPDATE_FORCE_NOTICE);
		transaction.commitAllowingStateLoss();
		CMBILogManager.printEventLog(mActivity, "0", "130008", "");
	}

	@Override
	public void onClick(View view) {
		if (view == mLlForceUpdateBtn) {
			CMBILogManager.printEventLog(mActivity, "0", "138001", "");
			//先判断网络条件
			if (NetWorkUtils.isNetworkConnected(mActivity)) {
				//判断是否是WIFI环境
				if (NetWorkUtils.isWifiConnected(mActivity)) {
					//切换至下载Dialog
					((CYMGSplashActivity)mActivity).changeDialogFragment(Contants.DialogFragmentTag.UPDATE_DOWNLOAD);
				}else {
					((CYMGSplashActivity)mActivity).changeDialogFragment(Contants.DialogFragmentTag.UPDATE_WIFI_NOTICE);
				}
			}else {
				((CYMGSplashActivity)mActivity).changeDialogFragment(Contants.DialogFragmentTag.UPDATE_NETERROR_NOTICE);
			}
			dismiss();
		}
	}

}
