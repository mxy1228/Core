package com.changyou.mgp.sdk.mbi.account.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.changyou.mgp.sdk.mbi.account.id.AccResource;
import com.changyou.mgp.sdk.mbi.account.utils.CYMGLoginResultUtil;
import com.changyou.mgp.sdk.mbi.config.Contants;
import com.changyou.mgp.sdk.mbi.config.Params;
import com.changyou.mgp.sdk.mbi.log.CYLog;
import com.changyou.mgp.sdk.mbi.mbi.manager.CMBILogManager;

public class CYMGAutoLoginDialogFragment extends BaseDialogFragment implements OnClickListener{
	
	private static CYLog log = CYLog.getInstance();
	
	private static Activity mActivity;
	
	private TextView mAccountTv;
	private TextView mSwtichTv;
	private ImageView mLoadingImg;
	
	private String mUid,mToken,mAcc,mType;
	
	private Timer mTimer;
	private int mCount = 0;
	private int isOK = 0;
	
	private Bundle mBundle;
	
	private AnimationDrawable mAnim;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(AccResource.getInstance(mActivity).mgp_sdk_2_0_dialog_auto_login, container,false);
		initView(view);
		initData();
		initEvent();
		return view;
	}

	@Override
	protected void initView(View view) {
		mAccountTv=(TextView) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_auto_login_account_TextView);
		mSwtichTv=(TextView) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_auto_login_switch_TextView);
		mLoadingImg=(ImageView) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_auto_login_loading_ImageView);
	}

	@Override
	protected void initData() {
		try {
			mBundle=getArguments();
			mUid=mBundle.getString(Params.UID);
			mToken=mBundle.getString(Params.TOKEN);
			mAcc=mBundle.getString(Params.ACCOUNT);
			mType=mBundle.getString(Params.TYPE);
			mAccountTv.setText(mAcc.substring(6, mAcc.length()));
			startTimer();
			startAnim();
		} catch (Exception e) {
			log.e(e);
		}
	}

	@Override
	protected void initEvent() {
		mSwtichTv.setOnClickListener(this);
	}
	
	public static CYMGAutoLoginDialogFragment newInewInstance(Activity activity,Bundle bundle){
		mActivity=activity;
		CYMGAutoLoginDialogFragment cymgAutoLoginDialogFragment=new CYMGAutoLoginDialogFragment();
		cymgAutoLoginDialogFragment.setArguments(bundle);
		cymgAutoLoginDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, AccResource.getInstance(mActivity).mgp_sdk_2_0_mian_dialog);
		return cymgAutoLoginDialogFragment;
	}
	
	@Override
	public void dismiss() {
		super.dismiss();
		if(mAnim.isRunning()){
			mAnim.stop();
		}
	}
	
	@Override
	public void onCancel(DialogInterface dialog) {
		mActivity.finish();
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==AccResource.getInstance(mActivity).mgp_sdk_2_0_auto_login_switch_TextView){
			CMBILogManager.printEventLog(mActivity, "0", "137001", "");
			((CYMGMainDialogFragmentActivity)mActivity).changeDialogFragment(Contants.DialogFragmentTag.SWITCH_ACCOUNT, null);
			mTimer.cancel();
			dismiss();
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	private void startAnim(){
		mLoadingImg.setBackgroundResource(AccResource.getInstance(mActivity).mgp_sdk_2_0_dialog_loading_anim);  
		mAnim = (AnimationDrawable) mLoadingImg.getBackground();  
		mAnim.start();  
	}
	
	private void startTimer(){
		TimerTask task = new TimerTask(){

			@Override
			public void run() {
				if(mCount==3){
					CYMGLoginResultUtil.loginCallback(mActivity, mAcc,mUid,mToken,mType);
				}
				mCount++;
			}
		};
		mTimer=new Timer();
		mTimer.schedule(task,0,1000);
	}
	
	public void showDialog() {
		FragmentTransaction transaction=((FragmentActivity)mActivity).getSupportFragmentManager().beginTransaction();
        transaction.add(this, Contants.DialogFragmentTag.AUTO_LOGIN);
        transaction.commitAllowingStateLoss(); 
	}
}
