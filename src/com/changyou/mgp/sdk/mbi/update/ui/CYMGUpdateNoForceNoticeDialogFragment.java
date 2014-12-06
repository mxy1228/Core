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
import com.changyou.mgp.sdk.mbi.account.ui.BaseDialogFragment;
import com.changyou.mgp.sdk.mbi.config.Contants;
import com.changyou.mgp.sdk.mbi.mbi.manager.CMBILogManager;
import com.changyou.mgp.sdk.mbi.update.id.UpdateResource;
import com.changyou.mgp.sdk.mbi.utils.NetWorkUtils;
/**
 * 
 * @ClassName: CYMGUpdateNoForceNoticeDialogFragment 
 * @Description: 非强制更新提示Dialog
 * @author J.Beyond 
 * @date 2014年8月6日 上午10:33:42 
 *
 */
public class CYMGUpdateNoForceNoticeDialogFragment extends BaseDialogFragment implements OnClickListener{

	private static Activity mActivity;
	private LinearLayout mLlNoForceUpdateImmediatelyBtn;
	private LinearLayout mLlNoForceUpdateNexttimeBtn;
	private DownloadAppInfo mAppInfo;
	private TextView mTvNoForceUpdateTitle;
	private TextView mTvNoForceUpdateDesc;
	private static CYMGUpdateNoForceNoticeDialogFragment mNoForceNoticeDialogFragment;

	public static CYMGUpdateNoForceNoticeDialogFragment newInstance(Activity activity, Bundle bundle){
		mActivity = activity;
		if (mNoForceNoticeDialogFragment == null) {
			mNoForceNoticeDialogFragment = new CYMGUpdateNoForceNoticeDialogFragment();
			mNoForceNoticeDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, UpdateResource.getInstance(mActivity).mgp_sdk_2_0_update_mian_dialog);
			mNoForceNoticeDialogFragment.setCancelable(false);
			mNoForceNoticeDialogFragment.setArguments(bundle);
		}
		return mNoForceNoticeDialogFragment;
	}
	public void showDialog() {
		FragmentTransaction transaction=((FragmentActivity)mActivity).getSupportFragmentManager().beginTransaction();
//		show(transaction, Contants.DialogFragmentTag.UPDATE_NO_FORCE_NOTICE);
		transaction.add(this, Contants.DialogFragmentTag.UPDATE_NO_FORCE_NOTICE);
		transaction.commitAllowingStateLoss();
		CMBILogManager.printEventLog(mActivity, "0", "130009", "");
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(UpdateResource.getInstance(mActivity).mgp_sdk_2_0_dialog_update_not_force_notice, container,false);
		initView(view);
		initData();
		initEvent();
		return view;
	}
	
	@Override  
	protected void initView(View view) {
		mTvNoForceUpdateTitle = (TextView) view.findViewById(UpdateResource.getInstance(mActivity).mgp_update_not_force_title_tv);
		mTvNoForceUpdateDesc = (TextView) view.findViewById(UpdateResource.getInstance(mActivity).mgp_update_not_force_desc_tv);
		mLlNoForceUpdateNexttimeBtn = (LinearLayout) view.findViewById(UpdateResource.getInstance(mActivity).mgp_update_no_force_nexttime_btn_ll);
		mLlNoForceUpdateImmediatelyBtn = (LinearLayout) view.findViewById(UpdateResource.getInstance(mActivity).mgp_update_no_force_immediately_btn_ll);
	}

	@Override
	protected void initData() {
		//获取到下载参数
		Bundle bundle = getArguments();
		mAppInfo = (DownloadAppInfo) bundle.getSerializable("DownloadAppInfo");
		int packageSize = bundle.getInt("package_size");
		//绑定界面控件
//		mTvNoForceUpdateTitle.setText(mAppInfo.getUpdate_title());
		String formatSize = DownloadUtil.bytesFormat(packageSize * 1024);
		mTvNoForceUpdateDesc.setText(mAppInfo.getUpdate_description()+"("+formatSize+")");
	}

	@Override
	protected void initEvent() {
		mLlNoForceUpdateImmediatelyBtn.setOnClickListener(this);
		mLlNoForceUpdateNexttimeBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		if (view == mLlNoForceUpdateImmediatelyBtn) {
			CMBILogManager.printEventLog(mActivity, "0", "139001", "");
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
			
		}else if(view == mLlNoForceUpdateNexttimeBtn){
			CMBILogManager.printEventLog(mActivity, "0", "139002", "");
			//销毁Dialog
			((CYMGSplashActivity)mActivity).setDownloadCancled(true);
			dismiss();
		}
	}

}
