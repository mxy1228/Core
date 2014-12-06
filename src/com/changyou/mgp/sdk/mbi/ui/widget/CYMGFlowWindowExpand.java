package com.changyou.mgp.sdk.mbi.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.changyou.mgp.sdk.mbi.account.id.AccResource;
import com.changyou.mgp.sdk.mbi.account.ui.CYMGContainerActivity;
import com.changyou.mgp.sdk.mbi.entity.FlowWinCloseAnimEvent;
import com.changyou.mgp.sdk.mbi.entity.FlowWinExpandAnimEvent;
import com.changyou.mgp.sdk.mbi.platform.CYMGCaptureHandler;
import com.changyou.mgp.sdk.mbi.platform.CYMGCaptureLogicListener;
import com.changyou.mgp.sdk.mbi.platform.CYMGGameParams;
import com.changyou.mgp.sdk.mbi.platform.CYMGGameParamsHandler;

import de.greenrobot.event.EventBus;

public class CYMGFlowWindowExpand extends LinearLayout implements OnClickListener{

	private Context mContext;
	private View mView;
	private int mScreenWidth;
	private int mScreenHeight;
	private AccResource mRes;
	private boolean mIsRightBigger;
	private boolean mIsShowCapture;
	
	private boolean mAnimating;
	
	public CYMGFlowWindowExpand(Context context,boolean showCapture) {
		super(context);
		this.mContext = context;
		this.mIsShowCapture = showCapture;
		init();
	}
	
	public CYMGFlowWindowExpand(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		init();
	}

	private void init(){
		this.mRes = AccResource.getInstance(mContext);
		//计算屏幕的尺寸
		DisplayMetrics metrics = new DisplayMetrics();
		getWM().getDefaultDisplay().getMetrics(metrics);
		this.mScreenWidth = metrics.widthPixels;
		this.mScreenHeight = metrics.heightPixels;
		int left = CYMGFlowWinManager.mParams.x;
		int right = mScreenWidth - left;
		mIsRightBigger = left < right;
		mView = mIsRightBigger ?  inflate(mContext, mRes.cymg_flow_win_right, null) : inflate(mContext, mRes.cymg_flow_win_left, null);
		addView(mView);
	}
	
	public void exeExpandOrClose(){
		LinearLayout container = null;
		container = (LinearLayout)mView.findViewById(mRes.cymg_flow_win_container);
		ImageButton iBtn = (ImageButton)mView.findViewById(mRes.cymg_flow_win_ibtn);
		iBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mAnimating){
					return;
				}
				ScaleAnimation closeAnim = null;
				if(mIsRightBigger){
					closeAnim = new ScaleAnimation(1f, 0f, 1f, 1f, 1f, 1f);
				}else{
					closeAnim = new ScaleAnimation(1f, 0f, 1f, 1f, Animation.RELATIVE_TO_SELF,1f,Animation.RELATIVE_TO_SELF, 1f);
				}
				closeAnim.setDuration(300);
				closeAnim.setInterpolator(new AccelerateInterpolator());
				closeAnim.setFillAfter(true);
				final View iv = mView.findViewById(mRes.cymg_flow_win_container);
				closeAnim.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
						mAnimating = true;
						FlowWinCloseAnimEvent e = new FlowWinCloseAnimEvent();
						e.setState(FlowWinCloseAnimEvent.START);
						EventBus.getDefault().post(e);
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						FlowWinCloseAnimEvent e = new FlowWinCloseAnimEvent();
						e.setState(FlowWinCloseAnimEvent.END);
						EventBus.getDefault().post(e);
					}
				});
				iv.startAnimation(closeAnim);
			}
		});
		Button pBtn = (Button)mView.findViewById(mRes.cymg_flow_win_personal_btn);
		Button forumBtn = (Button)mView.findViewById(mRes.cymg_flow_win_forum_btn);
		Button captureBtn = (Button)mView.findViewById(mRes.cymg_flow_win_capture_btn);
		Button gameCircleBtn = (Button)mView.findViewById(mRes.cymg_flow_win_game_circle_btn);
		captureBtn.setVisibility(mIsShowCapture ? View.VISIBLE : View.GONE);
		captureBtn.setOnClickListener(this);
		pBtn.setOnClickListener(this);
		forumBtn.setOnClickListener(this);
		gameCircleBtn.setOnClickListener(this);
		container.setVisibility(View.VISIBLE);
		ScaleAnimation expandAnim = null;
		if(mIsRightBigger){
			expandAnim = new ScaleAnimation(0, 1.0f, 1f, 1f, 1f, 1f);
		}else{
			expandAnim = new ScaleAnimation(0f, 1f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 1f);
		}
		expandAnim.setDuration(300);
		expandAnim.setInterpolator(new DecelerateInterpolator());
		expandAnim.setFillAfter(true);
		expandAnim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				mAnimating = true;
				FlowWinExpandAnimEvent e = new FlowWinExpandAnimEvent();
				e.setState(FlowWinExpandAnimEvent.START);
				EventBus.getDefault().post(e);
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				mAnimating = false;
				FlowWinExpandAnimEvent e = new FlowWinExpandAnimEvent();
				e.setState(FlowWinExpandAnimEvent.END);
				EventBus.getDefault().post(e);
			}
		});
		container.startAnimation(expandAnim);
	}
	
	private WindowManager getWM(){
		return (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		mView.layout(l, t, r, b);
	}

	@Override
	public void onClick(View v) {
		final Intent intent = new Intent(mContext,CYMGContainerActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		CYMGFlowWinManager.getInstance().showCloseWin();
		if(v.getId() == mRes.cymg_flow_win_personal_btn){
			intent.putExtra("type", CYMGContainerActivity.PERSONAL);
		}else if(v.getId() == mRes.cymg_flow_win_forum_btn){
			intent.putExtra("type", CYMGContainerActivity.FORUM);
		}else if(v.getId() == mRes.cymg_flow_win_capture_btn){
			CYMGCaptureHandler handler = CYMGFlowWinManager.getInstance().getCaptureHandler();
			if(handler == null){
				return;
			}
			CYMGCaptureLogicListener listener = new CYMGCaptureLogicListener() {
				
				@Override
				public void setCapturePath(String path) {
					intent.putExtra("type",CYMGContainerActivity.CAPTURE);
					intent.putExtra("path", path);
					mContext.startActivity(intent);
				}
			};
			handler.capture(listener);
			return;
		}else if(v.getId() == mRes.cymg_flow_win_game_circle_btn){
			intent.putExtra("type",CYMGContainerActivity.GAME_CIRCLE);
		}
		mContext.startActivity(intent);
	}
	
	public void animEnd(){
		mAnimating = false;
	}
}
