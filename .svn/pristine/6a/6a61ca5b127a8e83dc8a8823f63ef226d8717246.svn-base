package com.changyou.mgp.sdk.mbi.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.changyou.mgp.sdk.mbi.account.id.AccResource;
import com.changyou.mgp.sdk.mbi.account.ui.CYMGContainerActivity;
import com.changyou.mgp.sdk.mbi.config.CYMGProtocolConfig;
import com.changyou.mgp.sdk.mbi.log.CYLog;
import com.changyou.mgp.sdk.mbi.utils.SettingSPUtil;

public class CYMGFlowWindowClose extends LinearLayout{

	private CYLog log = CYLog.getInstance();
	//悬浮窗为合拢状态
	private static final int CLOSE = 0;
	//悬浮窗为展开状态
	private static final int EXPAND = 1;
	//悬浮窗磁性吸附到屏幕最上和最下判断的默认距离
	private static final int DEFAULT_MAGNETIC = 300;
	//磁性吸附时每频率移动距离
	private static final int PER_MAGNETIC_DISTANCE = 10;
	//磁性移动速率
	private static final int PER_MAGNETIC_TIME = 5;
	//悬浮窗变透明消息
	private static final int TRANSPARENT = 1;
	//多少秒之后变透明
	private static final int TIME_TRANSPARENT = 5;
	
	private Context mContext;
	private AccResource mRes;
	private int mScreenWidth;
	private int mScreenHeight;
	private int mXInView;
	private int mYInView;
	private float mPressDownX;
	private float mPressDownY;
	private boolean mAttached;
	private CountDownTimer mTimer;
	//悬浮窗是否处于透明状态
	private boolean mIsTransparent;
	
	private ImageButton mIBtn;
	private ImageButton mIBtnTransparent;
	private View mLeftView;
	private View mRightView;
	private LinearLayout mLeftContainer;
	private LinearLayout mRightContainer;
	private FrameLayout mContainer;
	
	
	public CYMGFlowWindowClose(Context context) {
		super(context);
		this.mContext = context;
		init();
	}
	
	private CYMGFlowWindowClose(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		init();
	}
	
	private WindowManager getWM(){
		return (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
	}
	
	private void init(){
		log.d("init");
		this.mRes = AccResource.getInstance(mContext);
		
		this.mLeftView = inflate(mContext, mRes.cymg_flow_win_left, null);
		this.mRightView = inflate(mContext, mRes.cymg_flow_win_right, null);
		this.mIBtn = (ImageButton)inflate(mContext, mRes.cymg_flow_win, null);
		addView(mIBtn);
		this.mIBtnTransparent = (ImageButton)inflate(mContext, mRes.cymg_flow_win_transparent, null);
		//计算屏幕的尺寸
		DisplayMetrics metrics = new DisplayMetrics();
		getWM().getDefaultDisplay().getMetrics(metrics);
		this.mScreenWidth = metrics.widthPixels;
		this.mScreenHeight = metrics.heightPixels;
		
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		mIBtn.layout(l, t, r,b);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			setMeasuredDimension(mContext.getResources().getDimensionPixelSize(mRes.cymg_flow_win_size)
					, mContext.getResources().getDimensionPixelSize(mRes.cymg_flow_win_size));
		
	}
	
	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
		return super.drawChild(canvas, child, drawingTime);
	}
	

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_MOVE:
			//拦截ACTION_MOVE，交由Parent进行拖动
			return true;
		case MotionEvent.ACTION_UP:
			//不拦截ACTION_UP，由IBtn判断是否执行点击事件
			//一旦捕捉到ACTION_UP就开始透明倒计时
			return true;
		case MotionEvent.ACTION_DOWN:
			return true;
		default:
			break;
		}
		//其他ACTION默认为不拦截
		return false;
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//&ACTION_MASK是为了排除多点触控的干扰
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			if(mTimer != null){
				mTimer.cancel();
			}
			mIBtn.setImageResource(mRes.cymg_flow_win_btn);
			//不拦截ACTION_DOWN，将ACTION_DOWN转交给IBtn，否则IBtn无法接收ACTION_UP事件
			mXInView = (int)event.getX();
			mYInView = (int)event.getY();
			if(mPressDownX == 0){
				mPressDownX = event.getRawX();
			}
			if(mPressDownY == 0){
				mPressDownY = event.getRawY();
			}
			return false;
		case MotionEvent.ACTION_UP:
			log.v("MotionEvent.ACTION_UP");
			startCountDownTimer();
			float pressUpX = event.getRawX();
			float pressUpY = event.getRawY();
			log.v("mPressDownX="+mPressDownX+" pressUpX="+pressUpX+" mPressDownY="+mPressDownY+" pressUpY="+pressUpY);
			//设置两像素的容差
			if((pressUpX >= mPressDownX-2 && pressUpX <= mPressDownX+2) 
					&& (pressUpY >= mPressDownY-2 && pressUpY <= mPressDownY+2)){
				//如果ACTION_UP时的坐标和ACTION_DOWN的坐标一致，则表示此次交互为点击
				//下面处理点击事件的响应
				CYMGFlowWinManager.getInstance().showExpandWin();
				mPressDownX = 0;
				mPressDownY = 0;
				return true;
			}
			log.v("start auto move");
			mPressDownX = 0;
			mPressDownY = 0;
			//下面计算悬浮窗目前位置距离屏幕上下左右的相对位置
			log.v("mScreenHeight="+mScreenHeight+" and mScreentWidth="+mScreenWidth);
			int top = (int)(event.getRawY() - event.getY());
			int left = (int)(event.getRawX() - event.getX());
			int right = (int)(mScreenWidth - left);
			int bottom = (int)(mScreenHeight - top);
			if(top < mScreenHeight / 2){
				//如果悬浮窗在屏幕上半部分
				if(top > DEFAULT_MAGNETIC){
					//如果距离屏幕顶部的距离大于默认磁性距离，则直接判断左右磁性
					if(left < right){
						//如果距离屏幕左边的距离小于距离屏幕右边的距离，则左边的磁性强，此时悬浮窗吸附到屏幕左侧
						//y轴坐标不变，X轴坐标变为0
						new MagneticMoveTask(0, CYMGFlowWinManager.mParams.y).execute();
					}else{
						//否则移动到屏幕右边，Y轴坐标不变，X轴坐标变为屏幕宽度
						new MagneticMoveTask(mScreenWidth, CYMGFlowWinManager.mParams.y).execute();
					}
				}else{
					//否则悬浮窗吸附到屏幕顶端,X轴坐标不变，Y轴坐标变为0
					new MagneticMoveTask(CYMGFlowWinManager.mParams.x, 0).execute();
				}
			}else{
				//如果悬浮窗在屏幕下半部分
				if(bottom > DEFAULT_MAGNETIC){
					//如果距离屏幕底部的距离大于默认磁性距离，则直接判断左右磁性
					if(left < right){
						//吸附到屏幕左侧，X轴变0，Y轴不变
						new MagneticMoveTask(0, CYMGFlowWinManager.mParams.y).execute();
					}else{
						//吸附到屏幕右侧，X轴变mScreenWidth，Y轴不变
						new MagneticMoveTask(mScreenWidth, CYMGFlowWinManager.mParams.y).execute();
					}
				}else{
					//吸附到屏幕底部，Y轴不变，X轴变为mScreenHeight
					new MagneticMoveTask(CYMGFlowWinManager.mParams.x, mScreenHeight).execute();
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			updatePostion((int)event.getRawX(), (int)event.getRawY());
			break;
		default:
			break;
		}
		return super.onTouchEvent(event);
	}
	
	/**
	 * 更新悬浮窗坐标
	 * @param x 目的坐标的X轴
	 * @param y 目的坐标的Y轴
	 */
	private void updatePostion(int x,int y){
		CYMGFlowWinManager.mParams.x = (int)(x - mXInView);
		CYMGFlowWinManager.mParams.y = (int)(y - mYInView);
		getWM().updateViewLayout(this, CYMGFlowWinManager.mParams);
	}
	
	/**
	 * 异步任务用于更新悬浮窗坐标
	 * @author xumengyang
	 *
	 */
	private class MagneticMoveTask extends AsyncTask<Void, Void, Void>{
		
		//目的X轴坐标
		private int pX;
		//目的Y轴坐标
		private int pY;
		//现X轴坐标与目的X轴坐标差值
		private int deltaX;
		//先Y轴坐标与目的Y轴坐标差值
		private int deltaY;
		
		public MagneticMoveTask(int x,int y){
			pX = x;
			pY = y;
		}
		
		@Override
		protected void onPreExecute() {
			//在磁性移动之前首先计算坐标差值
			//在拖动存在惯性（暂且这么叫吧）的情况下，会出现CYMGFlowWinManager.mParams.x为负数的情况，所以在这里取CYMGFlowWinManager.mParams.x的绝对值，下面的CYMGFlowWinManager.mParams.y也一样
			this.deltaX = pX - Math.abs(CYMGFlowWinManager.mParams.x);
			this.deltaY = pY - Math.abs(CYMGFlowWinManager.mParams.y);
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			if(deltaX == 0){
				//若X轴的差值为0，则只需在Y轴移动即可
				if(deltaY > 0){
					//向下移动
					while(CYMGFlowWinManager.mParams.y <= mScreenHeight){
						CYMGFlowWinManager.mParams.y += PER_MAGNETIC_DISTANCE;
						publishProgress();
						try {
							Thread.sleep(PER_MAGNETIC_TIME);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}else{
					//向上移动
					while(CYMGFlowWinManager.mParams.y >= 0){
						CYMGFlowWinManager.mParams.y -= PER_MAGNETIC_DISTANCE;
						publishProgress();
						try {
							Thread.sleep(PER_MAGNETIC_TIME);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}else{
				//若X轴差值不为0，则需要在X轴移动
				if(deltaX < 0){
					//向左移动
					while(CYMGFlowWinManager.mParams.x >= 0){
						CYMGFlowWinManager.mParams.x -= PER_MAGNETIC_DISTANCE;
						publishProgress();
						try {
							Thread.sleep(PER_MAGNETIC_TIME);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}else{
					//向右移动
					while(CYMGFlowWinManager.mParams.x <= mScreenWidth){
						CYMGFlowWinManager.mParams.x += PER_MAGNETIC_DISTANCE;
						publishProgress();
						try {
							Thread.sleep(PER_MAGNETIC_TIME);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			return null;
		}
		
		
		@Override
		protected void onProgressUpdate(Void... values) {
			//更新悬浮窗位置
			try {
				getWM().updateViewLayout(CYMGFlowWindowClose.this, CYMGFlowWinManager.mParams);
			} catch (Exception e) {
				log.e(e);
			}
		}
		
	}
	
	/**
	 * 倒计时器，倒计时五秒会将悬浮窗变透明
	 * @author xumengyang
	 *
	 */
	private class MyCountDownTimer extends CountDownTimer{
		
		public MyCountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			mIBtn.setImageResource(mRes.cymg_flow_win_btn);
		}
		

		@Override
		public void onTick(long millisUntilFinished) {
		}

		@Override
		public void onFinish() {
			if(!mIsTransparent){
				log.i("MyCountDownTimer:onFinish:");
				mIBtn.setImageResource(mRes.cymg_flow_win_btn_transparent);
			}
		}
		
	}
	
	/**
	 * 开始倒计时
	 */
	public void startCountDownTimer(){
		if(mTimer != null){
			mTimer.cancel();
		}
		mTimer = new MyCountDownTimer(TIME_TRANSPARENT * 1000, 1000);
		mTimer.start();
	}
	
}
