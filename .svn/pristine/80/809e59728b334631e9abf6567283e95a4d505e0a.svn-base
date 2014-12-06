package com.changyou.mgp.sdk.mbi.update.ui;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.changyou.mgp.sdk.mbi.R;
import com.changyou.mgp.sdk.mbi.channel.CYMGChannel;
import com.changyou.mgp.sdk.mbi.config.CYMGProtocolConfig;
import com.changyou.mgp.sdk.mbi.log.CYLog;
import com.changyou.mgp.sdk.mbi.platform.CYMGChannelHelper;
import com.changyou.mgp.sdk.mbi.platform.CYMGPlatformConfiguration;
import com.changyou.mgp.sdk.mbi.ui.base.BaseFragment;
import com.changyou.mgp.sdk.mbi.update.id.UpdateResource;
import com.changyou.mgp.sdk.mbi.utils.MetaDataValueUtils;

public class CYMGSplashFragment extends BaseFragment {
	CYLog mLog = CYLog.getInstance();
	protected static final int GET_NEW_PACKAGE_INFO_SUCCESS = 0;
	protected static final int GET_NEW_PACKAGE_INFO_FAILURE = 1;
	public static final int ON_ANIMATION_END = 2;
	//闪屏图片的名称前缀
	private static final String SPLASH_HORIZONTAL_DRAWABLE_PREFIX_NAME ="mgp_sdk_splash_horizontal_0";
	private static final String SPLASH_VERTICAL_DRAWABLE_PREFIX_NAME ="mgp_sdk_splash_vertical_0";
	private Activity mContext;
	private UpdateResource mRes;
	private ImageView mIvSplash;
	private ArrayList<Integer> mDrawableIds;
	private int mScreenOrientation;
	private boolean isNewVersionAvailable = false;
	private int i = 0;
	private List<AlphaAnimation> mAnimationList;
	public static boolean sIsSplashing = false;
	
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mContext = activity;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRes = UpdateResource.getInstance(mContext);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(mRes.mgp_sdk_2_0_init_splash, container,false);
		initScreenOrientation();
		initView(view);
		return view;
	}

	private void initScreenOrientation() {
		Bundle bundle = getArguments();
		mScreenOrientation = bundle.getInt("ScreenOrientation");
	}

	private void initAnimation() {
		//初始化闪屏资源id
		mDrawableIds = new ArrayList<Integer>();
		mDrawableIds.clear();
		Resources res = getResources();
		//闪屏图片（0~3张）
		for (int i = 1; i <= 3; i++) {
			int splash_drawable_id;
			if (mScreenOrientation == CYMGProtocolConfig.LANDSCAPE) {
				splash_drawable_id = res.getIdentifier(SPLASH_HORIZONTAL_DRAWABLE_PREFIX_NAME+i, "drawable", mContext.getPackageName());
			}else {
				splash_drawable_id = res.getIdentifier(SPLASH_VERTICAL_DRAWABLE_PREFIX_NAME+i, "drawable", mContext.getPackageName());
			}
			
			if (splash_drawable_id != 0) {
				mDrawableIds.add(splash_drawable_id);
			}
		}
		if (mDrawableIds.size() == 0) {
			//没有闪屏图片，直接初始化SDK
			((CYMGSplashActivity)mContext).getNewVersionInfo();
			return;
		}
		mIvSplash.setBackgroundResource(mDrawableIds.get(0));
		mAnimationList = new ArrayList<AlphaAnimation>();
		for (int i = 0; i < mDrawableIds.size(); i++) {
			AlphaAnimation alphaAnimation = null;
			if (i == 0) {
				alphaAnimation=new AlphaAnimation(1.0f, 0.0f);
				alphaAnimation.setDuration(2000);
			}else {
				alphaAnimation=new AlphaAnimation(0.0f, 1.0f);
				alphaAnimation.setRepeatMode(Animation.REVERSE);
				alphaAnimation.setRepeatCount(1);
				alphaAnimation.setDuration(1000);
			}
			alphaAnimation.setAnimationListener(new changeSplashAnimationListener());
			mAnimationList.add(alphaAnimation);
		}
		mIvSplash.setAnimation(mAnimationList.get(0));
		mAnimationList.get(0).start();
	}

	private void initView(View view) {
		mIvSplash = (ImageView) view.findViewById(mRes.mgp_splash_iv);
		initAnimation();
	}

	@Override
	protected void initData() {
		//SDK初始化
//		CYMGPlatformConfiguration config = new CYMGPlatformConfiguration
//				.Builder(mContext)
//				.setScreenOrientation(mScreenOrientation)
//				.build();
//		String channelID=MetaDataValueUtils.getChannelID(mContext);
//		CYMGChannel channel = CYMGChannelHelper.getChannel(mContext, channelID);
//		channel.init(config);
	}

	@Override
	protected void initEvent() {
		
	}
	
	/**
	 * 
	 * @ClassName: changeSplashAnimationListener 
	 * @Description: 动画事件监听
	 * @author J.Beyond 
	 * @date 2014年8月8日 下午2:38:13 
	 *
	 */
	private class changeSplashAnimationListener implements AnimationListener{

		@SuppressLint("ResourceAsColor")
		@Override
		public void onAnimationEnd(Animation animation) {
			i++;
//			animation.cancel();
			//设置消失时背景
			mIvSplash.setBackgroundColor(mRes.mgp_sdk_white);
			if (i == mDrawableIds.size()) {
//				initData();
//				getNewVersionInfo();
				((CYMGSplashActivity)mContext).getNewVersionInfo();
//				CYMGSplashActivity.this.finish();
				mContext.overridePendingTransition( mRes.mgp_game_update_zoom_out,0);
				sIsSplashing = false;
				return;
			}
			
			if (!isNewVersionAvailable) {
				mIvSplash.setBackgroundResource(mDrawableIds.get(i));
				mIvSplash.setAnimation(mAnimationList.get(i));
				mAnimationList.get(i).start();
			}
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			
		}

		@Override
		public void onAnimationStart(Animation animation) {
			sIsSplashing = true;
		}
	}
	
}
