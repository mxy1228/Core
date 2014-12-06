package com.changyou.mgp.sdk.mbi.ui.widget;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ListView;

import com.changyou.mgp.sdk.mbi.R;
import com.changyou.mgp.sdk.mbi.config.Contants;
import com.changyou.mgp.sdk.mbi.pay.id.PayResource;

public class MGPDropDownListView extends ListView implements OnScrollListener,Runnable,OnClickListener{

	private static final int STATE_NORMAL = 0;
	private static final int STATE_READY = 1;
	private static final int STATE_PULL = 2;
	private static final int STATE_UPDATING = 3;
	private static final int MAX_Y_OVERSCROLL = 50;
	
	private MGPDropDownListHeaderView mHeaderView;
	private View mFootView;
	private Button mFooterClickBtn;
//	private LinearLayout mFootViewLL;
	
	private int mState;
	private int mTouchSlop;//允许用户滑动的最小距离
	private int mActivePointerID;//用户触摸点ID
//	private int mDistance;
//	private int mStep;
	private boolean mPositive;
	private float mLastY;
	private boolean mHadNoData;
	private boolean mLoadingMore = false;
	private boolean mLoadComplete = false;
	private boolean mOpenPullRefresh = false;
	private boolean mOverScrolled = false;
	private OnDropDownListener mRefreshListener;
	private OnBottomListener mBottomListener;
	private AbsListView.OnScrollListener mScrollListener;
	private int mFirstItemIndex;
	private int mLastVisibleItem;
	private int mTotalItemCount;
	private long mStartUpdateTimeLine = 0;
	private int mMaxYOverscrollDistance;
	private int mFootViewHeight;
	private Context mContext;
	
	public MGPDropDownListView(Context context) {
		super(context);
		init(context);
		this.mContext = context;
	}
	
	public MGPDropDownListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray array = context.obtainStyledAttributes(attrs,PayResource.getInstance(mContext).mgp_drop_down_list_attr);
		mOpenPullRefresh = array.getBoolean(PayResource.getInstance(mContext).mgp_drop_down_list_attr_isOpenDropDown, false);
		array.recycle();
		init(context);
	}
	
	public MGPDropDownListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context){
		this.mHeaderView = new MGPDropDownListHeaderView(getContext(), this);
		View view = LayoutInflater.from(getContext()).inflate(PayResource.getInstance(mContext).mgp_pull_fresh_head, this.mHeaderView, false);
		this.mHeaderView.addView(view);
		if(mOpenPullRefresh){
			addHeaderView(mHeaderView, null, false);
		}
		this.mState = STATE_NORMAL;
		ViewConfiguration config = ViewConfiguration.get(getContext());
		this.mTouchSlop = config.getScaledTouchSlop();
		this.mFootView = LayoutInflater.from(getContext()).inflate(PayResource.getInstance(mContext).mgp_drop_down_list_footer, this,false);
		this.mFooterClickBtn = (Button)mFootView.findViewById(PayResource.getInstance(mContext).drop_down_list_footer_button);
		this.mFooterClickBtn.setOnClickListener(this);
//		this.mFootViewLL = (LinearLayout)mFootView.findViewById(PayResource.getInstance(mContext).pull_refresh_footview_ll);
		addFooterView(mFootView);
		this.mFootViewHeight = mFootView.getMeasuredHeight();
		super.setOnScrollListener(this);
//		initLayoutAnimation(context);
		initBounce(context);
		setHeaderDividersEnabled(false);
		setFooterDividersEnabled(false);
		setFadingEdgeLength(0);
	}
	
	private void initBounce(Context context){
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		float density = metrics.density;
		mMaxYOverscrollDistance = (int)(density * MAX_Y_OVERSCROLL);
	}
	
//	private void initLayoutAnimation(Context context){
//		AnimationSet set = new AnimationSet(false);
//		DisplayMetrics dm = new DisplayMetrics();
//		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
//		int screenW = dm.widthPixels;
//		Animation animation = new TranslateAnimation(screenW/2, 0, 0, 0);
//		animation.setDuration(200);
//		set.addAnimation(animation);
//		LayoutAnimationController c = new LayoutAnimationController(set);
//		this.setLayoutAnimation(c);
//	}
	
	public void setActivity(Activity activity){
		if(mHeaderView != null){
			this.mHeaderView.setActivity(activity);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
			if(mState == STATE_UPDATING && mOpenPullRefresh){
				return super.onTouchEvent(ev);
			}
			int action = ev.getAction() & MotionEventCompat.ACTION_MASK;
			switch (action) {
			case MotionEvent.ACTION_DOWN:
				mActivePointerID = MotionEventCompat.getPointerId(ev, 0);
				mLastY = ev.getY();
				if(mOpenPullRefresh){
					isFirstViewTop();
				}
				break;
			case MotionEvent.ACTION_MOVE:
				if(mActivePointerID == -1){
					break;
				}
				if(mLastY != 0f){
//					mDistance = (int)(mLastY - ev.getY());
//					if(mDistance < 0 && getFirstVisiblePosition() == 0 && getChildAt(0).getTop() == 0 && !mOpenPullRefresh){
//						mOverScrolled = true;
//						mDistance /= 2;
//						scrollTo(0, mDistance);
//						return true;
//					}else if(mDistance > 0 && getLastVisiblePosition() == getCount() - 1){
//						mOverScrolled = true;
//						mDistance /= 2;
//						scrollTo(0, mDistance);
//						return true;
//					}else{
//						mOverScrolled = false;
//					}
				}
				if(mState == STATE_NORMAL && mOpenPullRefresh){
					isFirstViewTop();
				}
				if(mState == STATE_READY && mOpenPullRefresh){
					int activePointerID = mActivePointerID;
					int activePointerIndex = MotionEventCompat.findPointerIndex(ev, activePointerID);
					float y = MotionEventCompat.getY(ev, activePointerIndex);
					int deltaY = (int)(y - mLastY);
					mLastY = y;
					//若用户是向上滑动或者用户滑动的距离小于允许滑动的最小距离，则把mState置为STATE_NORMAL
					if(deltaY <= 0 || Math.abs(y) < mTouchSlop){
						mState = STATE_NORMAL;
					}else{
						mState = STATE_PULL;
						ev.setAction(MotionEvent.ACTION_CANCEL);
						super.onTouchEvent(ev);
					}
				}
				if(mState == STATE_PULL && mOpenPullRefresh){
					int activePointerID = mActivePointerID;
					int activePointerIndex = MotionEventCompat.findPointerIndex(ev, activePointerID);
					final float y = MotionEventCompat.getY(ev, activePointerIndex);
					final int deltaY = (int)(y - mLastY);
					mLastY = y;
					int headerHeight = mHeaderView.getHeight();
					setHeaderHeight(headerHeight + deltaY * 5 / 9);
					return true;
				}
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				mActivePointerID = -1;
				if(mOpenPullRefresh && mState == STATE_PULL){
					update();
				}else{
//					if(mDistance != 0 && mOverScrolled){
//						mStep = 1;
//						mPositive = (mDistance > 0);
//						this.post(this);
//						return true;
//					}
				}
				mLastY = 0f;
//				mDistance = 0;
				break;
			case MotionEventCompat.ACTION_POINTER_DOWN:
				int index = MotionEventCompat.getActionIndex(ev);
				float y = MotionEventCompat.getY(ev, index);
				this.mLastY = y;
				this.mActivePointerID = MotionEventCompat.getPointerId(ev, index);
				break;
			case MotionEventCompat.ACTION_POINTER_UP:
				onSecondaryPointerUp(ev);
				break;
			}
		return super.onTouchEvent(ev);
	}
	
	private void update(){
		if(mHeaderView.isNeedUpdate()){
			if(this.mRefreshListener != null){
				this.mRefreshListener.onDropDown();
			}
			mStartUpdateTimeLine = System.currentTimeMillis();
			mHeaderView.startUpdate();
			mState = STATE_UPDATING;
		}else{
			mHeaderView.close(STATE_NORMAL);
		}
	}
	
	private void setHeaderHeight(int height){
		mHeaderView.setHeaderHeight(height);
	}
	
	/**
	 * 判断当前ListView的TopView是否为第一个Item
	 * 是的话就把mState置为STATE_READY
	 * @return
	 */
	private boolean isFirstViewTop(){
		if(getChildCount() == 0){
			return true;
		}
		int fistrVisiblePosition = this.getFirstVisiblePosition();
		View firstChildView = getChildAt(0);
		boolean is = firstChildView.getTop() == 0 && (fistrVisiblePosition == 0);
		if(is){
			mState = STATE_READY;
		}
		return is;
	}
	
	public void setState(int state){
		this.mState = state;
	}
	
	private void onSecondaryPointerUp(MotionEvent ev){
		int pointerIndex = MotionEventCompat.getActionIndex(ev);
		int pointerID = MotionEventCompat.getPointerId(ev, pointerIndex);
		if(pointerID == mActivePointerID){
			int newPointerIndex = pointerIndex == 0 ? 1 : 0;
			mLastY = MotionEventCompat.getY(ev, newPointerIndex);
			mActivePointerID = MotionEventCompat.getPointerId(ev, newPointerIndex);
		}
	}
	
	public void onDropDownComplete(){
		mHeaderView.finishUpdate(STATE_NORMAL,mStartUpdateTimeLine,this);
		if(getFooterViewsCount() == 0){
			this.mFooterClickBtn.setText(PayResource.getInstance(mContext).mgp_drop_down_list_footer_default_text);
			mFootView.setVisibility(View.VISIBLE);
			addFooterView(mFootView);
			this.mLoadComplete = false;
		}
	}
	
	public void reset(){
		if(getFooterViewsCount() == 0){
			this.mFooterClickBtn.setText(PayResource.getInstance(mContext).mgp_drop_down_list_footer_default_text);
			mFootView.setVisibility(View.VISIBLE);
			addFooterView(mFootView);
			this.mLoadComplete = false;
		}
	}
	
	public void setFooterBtnText(int id){
		this.mFooterClickBtn.setText(id);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		mFirstItemIndex = firstVisibleItem;
		mLastVisibleItem = firstVisibleItem + visibleItemCount;
		mTotalItemCount = totalItemCount;
		if(mScrollListener != null){
			mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if(mScrollListener != null){
			mScrollListener.onScrollStateChanged(view, scrollState);
		}
		if(getLastVisiblePosition() == (view.getCount()-1)){
			Log.d("mbi", "滑到底部");
			//如果用户滑动到了底部
			if(mBottomListener != null && !mLoadingMore && !mLoadComplete){
				Log.d("pengyou", "onLoad");
				mLoadingMore = true;
//				mFooterClickBtn.setText(PayResource.getInstance(mContext).mgp_drop_down_list_header_loading_text);
//				mBottomListener.onBottomLoad();
			}
		}
	}
	
	
	public static interface OnDropDownListener{
		public void onDropDown();
	}
	
	public interface OnBottomListener{
		public void onBottomLoad();
//		public void onScroll(AbsListView view, int firstVisibleItem,
//				int visibleItemCount, int totalItemCount);
//		public void onScrollStateChanged(AbsListView view, int scrollState);
	}
	
	@Override
    public void setOnScrollListener(AbsListView.OnScrollListener listener) {
		this.mScrollListener = listener;
    }
	
	public void setOnBottomListener(OnBottomListener listener){
		this.mBottomListener = listener;
	}
	
	public void setOnDropDownListener(OnDropDownListener listener){
		this.mRefreshListener = listener;
	}
	
	public void loadingFinish(){
		this.mLoadingMore = false;
	}
	
	public void onBottomComplete(){
		this.mFooterClickBtn.setText(PayResource.getInstance(mContext).mgp_drop_down_list_footer_default_text);
		mFootView.setVisibility(View.GONE);
		removeFooterView(mFootView);
		this.mLoadComplete = true;
	}
	
	public void onBottomComplete(List list){
		this.mFooterClickBtn.setText(PayResource.getInstance(mContext).mgp_drop_down_list_footer_default_text);
		if(list.size() < Contants.PAGE_SIZE){
			mFootView.setVisibility(View.GONE);
			removeFooterView(mFootView);
			this.mLoadComplete = true;
		}
	}
	
	public void onBottomComplete(int size){
		this.mFooterClickBtn.setText(PayResource.getInstance(mContext).mgp_drop_down_list_footer_default_text);
		if(size < Contants.PAGE_SIZE){
			mFootView.setVisibility(View.GONE);
			removeFooterView(mFootView);
			this.mLoadComplete = true;
		}
	}
	
	public void removeFootView(){
		mFootView.setVisibility(View.GONE);
		removeFooterView(mFootView);
		this.mLoadComplete = true;
	}
	
	@Override
	public void run() {
//		if(mDistance > 0 ){
//			mDistance -= mStep;
//			scrollTo(0, mDistance);
//			if(mDistance <= 0){
//				scrollTo(0, -0);
//				mLastY = 0f;
//				return;
//			}
//		}else{
//			mDistance += mStep;
//			scrollTo(0, mDistance);
//			if(mDistance >= 0){
//				scrollTo(0, 0);
//				mLastY = 0f;
//				return;
//			}
//		}
//		mStep += 1;
//		this.postDelayed(this, 5);
	}
	
	/**
	 * 更新某一行
	 * @param obj
	 */
	public void updateItem(Object obj){
		int start = this.getFirstVisiblePosition();
		for(int i = start,j = this.getLastVisiblePosition();i<j;i++){
			if(this.getItemAtPosition(i) == obj){
				View view = this.getChildAt(i);
				this.getAdapter().getView(i, view, this);
				return;
			}
		}
	}

	@Override
	public void onClick(View v) {
		if(mBottomListener != null){
			mFooterClickBtn.setText(PayResource.getInstance(mContext).mgp_drop_down_list_footer_loading_text);
			mBottomListener.onBottomLoad();
		}
	}
}
