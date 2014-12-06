package com.changyou.mgp.sdk.mbi.ui.widget;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.changyou.mgp.sdk.mbi.account.id.AccResource;
import com.changyou.mgp.sdk.mbi.config.SDKConfig;
import com.changyou.mgp.sdk.mbi.log.CYLog;

/**
 * 
 * 功能描述：自定义WaitingDialog
 *
 * @author 徐萌阳(xumengyang)
 *
 * <p>修改历史：(修改人，修改时间，修改原因/内容)</p>
 */
public class WaitingDialog implements OnKeyListener{

	private Context mContext;
	private int mT;
	private MyDialogDismissListener mListener;
	private AccResource mRes;
	private LayoutInflater mInfalter;
	private static int TIME_OUT = 12;
	private MyDialog mDialog;
	private MyCountDown mCountDown;
	private TextView mTV;
	private ImageView mIV;
	private View mView;
	
	public WaitingDialog(Context context) {
		this.mContext = context;
		init();
	}
	
	public WaitingDialog(Context context, int theme) {
		this.mContext = context;
		init();
	}

	private void init(){
		this.mRes = AccResource.getInstance(mContext);
		mInfalter = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = mInfalter.inflate(AccResource.getInstance(mContext).cymg_waiting_dialog,null);
		this.mTV = (TextView)mView.findViewById(mRes.cymg_waiting_dialog_tv);
		this.mIV = (ImageView)mView.findViewById(mRes.cymg_waiting_dialog_iv);
		mDialog = new MyDialog(mContext, mRes.mgp_sdk_2_0_loading_dialog);
	}
	
	public void setMessage(String msg){
		this.mTV.setText(msg);
	}
	
	private class MyDialog extends Dialog{

		
		public MyDialog(Context context, int theme) {
			super(context, theme);
			initDialog();
		}
		
		private void initDialog(){
			this.setCanceledOnTouchOutside(false);
			this.setOnKeyListener(WaitingDialog.this);
			if(mView == null){
				CYLog.getInstance().e("mVewi is null");
				return;
			}
			this.setContentView(mView);
		}
		
		
	}

	@Override
	public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
		
		if(keyCode == KeyEvent.KEYCODE_BACK){
			dismiss();
			Activity a = (Activity)mContext;
			if(a instanceof FragmentActivity){
				FragmentActivity fa = (FragmentActivity)a;
				FragmentManager fm = fa.getSupportFragmentManager();
				if(fm.getBackStackEntryCount() == 0){
					fa.finish();
					return true;
				}else{
					fm.popBackStack();
					return true;
				}
			}else{
				a.finish();
				return true;
			}
		}
		return false;
	}
	
//	public static void showWaitingDialog(WaitingDialog waitingDialog){
//		if(waitingDialog!=null){
//			waitingDialog.show();
//		}
//	}
	
//	public static void DismissWaitingDialog(WaitingDialog waitingDialog){
//		if(waitingDialog!=null&& mDialog.isShowing()){
//			waitingDialog.dismiss();
//		}
//	}
//	
//	public static void DestroyWaitingDialog(WaitingDialog waitingDialog){
//		DismissWaitingDialog(waitingDialog);
//		waitingDialog=null;
//	}
	
	public void show() {
		try {
			mDialog.show();
			AnimationDrawable ad = (AnimationDrawable)mIV.getBackground();
			ad.start();
			mCountDown = new MyCountDown(TIME_OUT, 1000);
			mCountDown.start();
//			mT = TIME_OUT;
//			mTimer = new Timer();
//			mTimer.schedule(new TimerTask() {
//				
//				@Override
//				public void run() {
//					if(mT-- == 0){
//						mHandler.sendEmptyMessage(1);
//					};
//					
//				}
//			}, 0,1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void dismiss() {
		try {
			mDialog.dismiss();
			if(mCountDown != null){
				mCountDown.cancel();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void cancel() {
		try {
			mDialog.cancel();
			if(mCountDown != null){
				mCountDown.cancel();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void destory(){
		try {
			mDialog.dismiss();
			mDialog = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public interface MyDialogDismissListener{
		public void onTimeOutDismiss();
	}
	
	public void setDismissListener(int timeout,MyDialogDismissListener listener){
		TIME_OUT = timeout + 2;
		this.mListener = listener;
	}
	
	public void setDismissListener(MyDialogDismissListener listener){
		//设置默认超时时间
		TIME_OUT = SDKConfig.TIME_OUT;
		this.mListener = listener;
	}
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(mListener != null){
				mListener.onTimeOutDismiss();
			}
			dismiss();
		};
	};
	
	
	private class MyCountDown extends CountDownTimer{

		public MyCountDown(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onFinish() {
			
			mHandler.sendEmptyMessage(1);
		}
	}
}
