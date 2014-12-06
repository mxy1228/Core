package com.changyou.mgp.sdk.mbi.update.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.changyou.mgp.sdk.downloader.CYMGDownloader;
import com.changyou.mgp.sdk.downloader.bean.DownloadAppInfo;
import com.changyou.mgp.sdk.downloader.bean.ProgressInfo;
import com.changyou.mgp.sdk.downloader.constants.DownloadConstants;
import com.changyou.mgp.sdk.downloader.constants.DownloadConstants.DownloadState;
import com.changyou.mgp.sdk.downloader.listener.DownloadInitListener;
import com.changyou.mgp.sdk.downloader.listener.DownloadListener;
import com.changyou.mgp.sdk.downloader.utils.DownloadUtil;
import com.changyou.mgp.sdk.mbi.account.ui.BaseDialogFragment;
import com.changyou.mgp.sdk.mbi.config.Contants;
import com.changyou.mgp.sdk.mbi.log.CYLog;
import com.changyou.mgp.sdk.mbi.mbi.manager.CMBILogManager;
import com.changyou.mgp.sdk.mbi.update.id.UpdateResource;
import com.changyou.mgp.sdk.mbi.update.listener.NetworkStateChangeListener;
import com.changyou.mgp.sdk.mbi.update.receiver.NetworkStateReceiver;
import com.changyou.mgp.sdk.mbi.utils.NetWorkUtils;
import com.changyou.mgp.sdk.mbi.utils.UpdateSpUtil;
/**
 * 
 * @ClassName: CYMGUpdateDownloadDialogFragment 
 * @Description: 下载Dialog
 * @author J.Beyond 
 * @date 2014年8月6日 上午10:33:42 
 *
 */
public class CYMGUpdateDownloadDialogFragment extends BaseDialogFragment implements DownloadListener{
	protected static final int ON_INIT_FAIL = 10;
	CYLog mLog = CYLog.getInstance();
	private static TextView mTvDownloadTitleTxt; 	//下载标题
	private static TextView mTvDownloadDescTxt; 	//更新文案提示
	private static TextView mTvDownloadPercentTxt;	//下载进度提示
	private static Button mDownloadOperatiosBtn;	//下载相关按钮
	private CYMGDownloader mCYMGDownloader;
	private static Activity mActivity;
	private DownloadAppInfo mAppInfo;
	private static ProgressBar mProgressBar;
	private static int sDownloadState = 0;
	private NetworkStateReceiver mNetworkStateReceiver;
	public static boolean isDownloadCompleted = false;
	private ProgressInfo mProgressInfo;
	private boolean isNetConnected;
	
	public int getDownloadState() {
		return sDownloadState;
	}
	
	private Handler mHandler = new Handler(){
		
		@Override
		public void dispatchMessage(Message msg) {
			super.dispatchMessage(msg);
			switch (msg.what) {
			case ON_INIT_FAIL:
				((CYMGSplashActivity)mActivity).changeDialogFragment(Contants.DialogFragmentTag.UPDATE_NETERROR_NOTICE);
				break;
			case DownloadConstants.DownloadState.NOTSTART:
				if (mProgressInfo != null) {
					mTvDownloadPercentTxt.setText(mProgressInfo.getPercent());
					mProgressBar.setProgress(mProgressInfo.getProgress());
				}
				mDownloadOperatiosBtn.setText("下载");
				break;
			case DownloadConstants.DownloadState.DOWNLOADING:
				ProgressInfo progressInfo = (ProgressInfo) msg.obj;
				mDownloadOperatiosBtn.setText("暂停");//下载中
				if (progressInfo != null) {
					mTvDownloadPercentTxt.setText(progressInfo.getPercent());
					mProgressBar.setProgress(progressInfo.getProgress());
				}
				break;
			case DownloadConstants.DownloadState.PAUSE:
				if (mProgressInfo != null) {
					mTvDownloadPercentTxt.setText(mProgressInfo.getPercent());
					mProgressBar.setProgress(mProgressInfo.getProgress());
				}
				mDownloadOperatiosBtn.setText("继续");
				
				break;
			case DownloadConstants.DownloadState.RESUME:
				mDownloadOperatiosBtn.setText("暂停");
				break;
			case DownloadConstants.DownloadState.FINISHED:
				CMBILogManager.printEventLog(mActivity, "0", "138002", "");
				mDownloadOperatiosBtn.setText("安装");
				mProgressBar.setProgress(100);
				mTvDownloadPercentTxt.setText("100%");
				break;
			case DownloadConstants.DownloadState.FAILED:
				((CYMGSplashActivity)mActivity).changeDialogFragment(Contants.DialogFragmentTag.UPDATE_NETERROR_NOTICE);
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(UpdateResource.getInstance(mActivity).mgp_sdk_2_0_dialog_update_download, container,false);
		initView(view);
		initData();
		initEvent();
		return view;
	}
	@Override
	public void onPause() {
		super.onPause();
		mLog.d("CYMGUpdateDownloadDialogFragment#onPause");
		if (mActivity == null) {
			mLog.e("mActivity is null!!");
			return;
		}
		//如果未下载完退出了下载界面
		if (!isDownloadCompleted) {
			if (mAppInfo.getCompeleteSize()!=0) {
				UpdateSpUtil.setIsUpdateCanceled(mActivity, true);
				if (mCYMGDownloader != null) {
					mCYMGDownloader.doPause(mAppInfo);
				}
			}
		}else {
			UpdateSpUtil.setIsDownloadCompleted(mActivity, true);
		}
	}
	
	@Override
	protected void initView(View view) {
		mTvDownloadTitleTxt = (TextView) view.findViewById(UpdateResource.getInstance(mActivity).mgp_update_title_txt_tv);
		mTvDownloadDescTxt = (TextView) view.findViewById(UpdateResource.getInstance(mActivity).mgp_update_desc_txt_tv);
		mTvDownloadPercentTxt = (TextView) view.findViewById(UpdateResource.getInstance(mActivity).mgp_update_download_percent_txt_tv);
		mProgressBar = (ProgressBar) view.findViewById(UpdateResource.getInstance(mActivity).mgp_update_download_progress_bar);
		mDownloadOperatiosBtn = (Button) view.findViewById(UpdateResource.getInstance(mActivity).mgp_update_download_operatios_btn);
	}

	@Override
	protected void initData() {
		//初始网络状态
		isNetConnected = NetWorkUtils.isNetworkConnected(mActivity);
		isWifiConnected = NetWorkUtils.isWifiConnected(mActivity);
		//获取到下载参数
		Bundle bundle = getArguments();
		mAppInfo = (DownloadAppInfo) bundle.getSerializable("DownloadAppInfo");
		int packageSize = bundle.getInt("package_size");
		isPreDownloadCanceled = bundle.getBoolean("isPreDownloadCanceled");
		isForceUpdate = mAppInfo.getUpdate_level() == 1 ? false :true;
		
		//绑定控件数据
//		mTvDownloadTitleTxt.setText(mAppInfo.getUpdate_title());
		String formatSize = DownloadUtil.bytesFormat(packageSize*1024);
		mTvDownloadDescTxt.setText(mAppInfo.getUpdate_description()+"("+formatSize+")");
		
		//初始化下载管理器
		if (mActivity == null) {
			mLog.e("mContext is null!");
			return;
		}
		mCYMGDownloader = CYMGDownloader.getInstance(mActivity);
		
		//初始化广播接收者
		mNetworkStateReceiver = new NetworkStateReceiver(mActivity, new NetworkStateChangeListener() {
			
			@Override
			public void onWifiDisconnected() {
				mLog.d("onWifiDisconnected()");
				if (isWifiConnected && !((CYMGSplashActivity)mActivity).isWillingDownloadWithoutWifi()) {
					((CYMGSplashActivity)mActivity).changeDialogFragment(Contants.DialogFragmentTag.UPDATE_WIFI_NOTICE);
				}
				if (sDownloadState != DownloadConstants.DownloadState.FINISHED) {
					mCYMGDownloader.doPause(mAppInfo);
				}
			}
			
			@Override
			public void onNetworkDisconnected() {
				mLog.d("onNetworkDisconnected()");
				((CYMGSplashActivity)mActivity).changeDialogFragment(Contants.DialogFragmentTag.UPDATE_NETERROR_NOTICE);
			}
		});
		//注册广播
		mNetworkStateReceiver.registerReceiver();
		
		//初始化下载状态
		mCYMGDownloader.doInit(mAppInfo, new DownloadInitListener() {

			@Override
			public void onInitSuccess(DownloadAppInfo appInfo,ProgressInfo progressInfo) {
				mLog.i("初始化下载状态："+progressInfo.toString());
				mAppInfo = appInfo;
				mProgressInfo = progressInfo;
				sDownloadState = appInfo.getDownloadStatus();
				//更改按钮文字
				startHandler(mAppInfo);
				//判断之前是否下载完毕
				if (mAppInfo.getDownloadStatus() != DownloadState.FINISHED) {
					//初始化完毕后直接下载
					if (!isPreDownloadCanceled) {
						executeResume();
					}else {
						mTvDownloadDescTxt.setText(UpdateResource.getInstance(mActivity).mgp_game_pre_update_info_txt);
					}
				}else {
					//直接提示下载完毕
					mTvDownloadDescTxt.setText(UpdateResource.getInstance(mActivity).mgp_game_pre_update_complete_info_txt);
				}
			}

			@Override
			public void onInitFail(DownloadAppInfo appInfo, String errorInfo) {
				mLog.e(errorInfo);
				Message msg = Message.obtain();
				msg.what = ON_INIT_FAIL;
				mHandler.sendMessage(msg);
			}
		});
	}

	@Override
	protected void initEvent() {
		mDownloadOperatiosBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				mLog.e(sDownloadState);
				switch (sDownloadState) {
				case DownloadConstants.DownloadState.NOTSTART://0
					mCYMGDownloader.registListener(mAppInfo, CYMGUpdateDownloadDialogFragment.this);
					mCYMGDownloader.doDownload(mAppInfo);
					break;
				case DownloadConstants.DownloadState.DOWNLOADING://2
					mCYMGDownloader.doPause(mAppInfo);
					break;
				case DownloadConstants.DownloadState.PAUSE://3
					mCYMGDownloader.registListener(mAppInfo, CYMGUpdateDownloadDialogFragment.this);
					if (isWifiConnected) {
						mCYMGDownloader.doResume(mAppInfo);
					}else {
						if (((CYMGSplashActivity)mActivity).isWillingDownloadWithoutWifi()) {
							mCYMGDownloader.doResume(mAppInfo);
						}else {
							((CYMGSplashActivity)mActivity).changeDialogFragment(Contants.DialogFragmentTag.UPDATE_WIFI_NOTICE);
						}
					}
					break;
				case DownloadConstants.DownloadState.RESUME://4
					
					break;
				case DownloadConstants.DownloadState.FINISHED://5
					mCYMGDownloader.doInstall(mAppInfo);
					
					break;
				case DownloadConstants.DownloadState.FAILED://6

					break;
				}
			}
		});
	}
	
	public static CYMGUpdateDownloadDialogFragment newInstance(Activity activity,Bundle bundle){
		mActivity = activity;
//		if (mDownloadDialogFragment == null) {
			mDownloadDialogFragment = new CYMGUpdateDownloadDialogFragment();
			mDownloadDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, UpdateResource.getInstance(mActivity).mgp_sdk_2_0_update_mian_dialog);
			mDownloadDialogFragment.setCancelable(false);
			mDownloadDialogFragment.setArguments(bundle);
//		}
		return mDownloadDialogFragment;
	}
	
	public void showDialog() {
		FragmentTransaction transaction=((FragmentActivity)mActivity).getSupportFragmentManager().beginTransaction();
//		show(transaction, Contants.DialogFragmentTag.UPDATE_DOWNLOAD);
		transaction.add(this, Contants.DialogFragmentTag.UPDATE_DOWNLOAD);
		transaction.commitAllowingStateLoss();
	}
	
	@Override
	public void onDismiss(DialogInterface dialog) {
		mLog.d("CYMGUpdateDownloadDialogFragment#onDismiss");
		if (sDownloadState != DownloadConstants.DownloadState.FINISHED) {
			mCYMGDownloader.doPause(mAppInfo);
		}
		//判断广播接收者是否取消注册
		if (mNetworkStateReceiver != null && !mNetworkStateReceiver.isUnRegist()) {
			mNetworkStateReceiver.unRegisterReceiver();
			mNetworkStateReceiver.setUnRegist(true);
		}
		((CYMGSplashActivity)mActivity).finishActivity();
		super.onDismiss(dialog);
	}

	/**
	 * 
	 * @Title: executeDownload 
	 * @Description: 公开的执行下载的方法
	 * @param  
	 * @return void
	 * @throws
	 */
	public void executeDownload(){
		if (mCYMGDownloader != null) {
			mCYMGDownloader.registListener(mAppInfo, this); 
			mCYMGDownloader.doDownload(mAppInfo);
		}
	}
	
	/**
	 * 
	 * @Title: executeResume 
	 * @Description: 公开的执行继续下载的方法
	 * @param  
	 * @return void
	 * @throws
	 */
	public void executeResume() {
		if (mCYMGDownloader != null) {
			mCYMGDownloader.registListener(mAppInfo, this); 
			if (sDownloadState != DownloadConstants.DownloadState.NOTSTART) {
				mCYMGDownloader.doResume(mAppInfo);
			}else {
				mCYMGDownloader.doDownload(mAppInfo);
			}
		}
	}
	
	private boolean isForceUpdate;
	private boolean isPreDownloadCanceled;
	private static CYMGUpdateDownloadDialogFragment mDownloadDialogFragment;
	private boolean isWifiConnected;
	
	
	private void startHandler(DownloadAppInfo appInfo){
		this.mAppInfo = appInfo;
		Message msg = Message.obtain();
		msg.what = appInfo.getDownloadStatus();
		mHandler.sendMessage(msg);
	}
	
	private void startHandler(DownloadAppInfo appInfo,ProgressInfo progressInfo){
		this.mAppInfo = appInfo;
		this.mProgressInfo = progressInfo;
		Message msg = Message.obtain();
		msg.what = appInfo.getDownloadStatus();
		msg.obj = progressInfo;
		mHandler.sendMessage(msg);
	}
	
	private void startHandler(DownloadAppInfo appInfo,String errorInfo){
		this.mAppInfo = appInfo;
		Message msg = Message.obtain();
		msg.what = appInfo.getDownloadStatus();
		msg.obj = errorInfo;
		mHandler.sendMessage(msg);
	}

	@Override
	public void onDownloadFailed(DownloadAppInfo appInfo, String errorInfo) {
		mLog.e("下载失败，原因："+errorInfo);
		sDownloadState = DownloadConstants.DownloadState.FAILED;
		startHandler(appInfo,errorInfo);
	}

	@Override
	public void onDownloadPaused(DownloadAppInfo appInfo) {
		sDownloadState = DownloadConstants.DownloadState.PAUSE;
		startHandler(appInfo);
	}

	@Override
	public boolean onDownloadResume(DownloadAppInfo appInfo) {
		sDownloadState = DownloadConstants.DownloadState.RESUME;
		startHandler(appInfo);
		return false;
	}

	@Override
	public void onDownloadSuccess(DownloadAppInfo appInfo) {
		isDownloadCompleted = true;
		sDownloadState = DownloadConstants.DownloadState.FINISHED;
		startHandler(appInfo);
		//下载完毕后自动执行安装
		if (mCYMGDownloader != null) {
			mCYMGDownloader.doInstall(mAppInfo);
		}
		//判断广播接收者是否取消注册
		if (mNetworkStateReceiver != null && !mNetworkStateReceiver.isUnRegist()) {
			mNetworkStateReceiver.unRegisterReceiver();
			mNetworkStateReceiver.setUnRegist(true);
		}
	}

	@Override
	public void onDownloadWaiting(DownloadAppInfo appInfo) {
		sDownloadState = DownloadConstants.DownloadState.WAITING;
		startHandler(appInfo);
	}

	@Override
	public void onDownloading(DownloadAppInfo appInfo, ProgressInfo progressInfo) {
		mLog.i("下载中...进度信息："+progressInfo.toString());
		sDownloadState = DownloadConstants.DownloadState.DOWNLOADING;
		((CYMGSplashActivity)mActivity).setDownloaded(true);
		startHandler(appInfo,progressInfo);
	}


}
