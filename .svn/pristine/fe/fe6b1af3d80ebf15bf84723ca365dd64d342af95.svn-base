package com.changyou.mgp.sdk.mbi.account.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.changyou.mgp.sdk.mbi.account.id.AccResource;
import com.changyou.mgp.sdk.mbi.config.Contants;
import com.changyou.mgp.sdk.mbi.config.HttpContants;
import com.changyou.mgp.sdk.mbi.config.Params;
import com.changyou.mgp.sdk.mbi.http.MyAsyncResponseHandler;
import com.changyou.mgp.sdk.mbi.http.MyHttpClient;
import com.changyou.mgp.sdk.mbi.log.CYLog;
import com.changyou.mgp.sdk.mbi.mbi.manager.CMBILogManager;
import com.changyou.mgp.sdk.mbi.utils.DesUtil;
import com.changyou.mgp.sdk.mbi.utils.SystemUtils;

public class CYMGRegisterContainerDialogFragment extends BaseDialogFragment
		implements OnClickListener {
	
	private CYLog log = CYLog.getInstance();

	private static Activity mActivity;
	
	private CYMGResgisrForPhoneFragment mCYMGResgisrForPhoneFragment;
	private CYMGResgisrForUserFragment mCYMGResgisrForUserFragment;

	private ImageButton mBackBtn;
	private ImageButton mCloseBtn;

	private ViewPager mPager;// 页卡内容
	private ImageView cursor;// 动画图片
	private TextView mPhoneTv, mUserTv;// 页卡头标
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private int one;//
	
	private CYMGLoadingDialogFragment mLoadingDialog;
	
	private boolean getCN = false;
	
	private Bundle mBundle;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(AccResource.getInstance(mActivity).mgp_sdk_2_0_dialog_register_container, container,false);
		initView(view);
		initViewPager(view);
		initImageView(view);
		initData();
		initEvent();
		return view;
	}

	@Override
	protected void initView(View view) {
		mBackBtn = (ImageButton) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_register_container_back_ImageButton);
		mCloseBtn = (ImageButton) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_register_container_close_ImageButton);
		mPhoneTv = (TextView) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_register_container_phone_TextView);
		mUserTv = (TextView) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_register_container_user_TextView);
		mLoadingDialog=CYMGLoadingDialogFragment.newInewInstance(mActivity);
		mCYMGResgisrForPhoneFragment=new CYMGResgisrForPhoneFragment();
		mCYMGResgisrForUserFragment=new CYMGResgisrForUserFragment();
		CMBILogManager.printEventLog(mActivity, "0", "130005", "");
		CMBILogManager.printEventLog(mActivity, "0", "135001", "");
	}

	@Override
	protected void initData() {
		mBundle=getArguments();
		if("phone".equals(mBundle.getString(Params.REGISTER_MODE))){
			mPager.setCurrentItem(0);
		}else if("user".equals(mBundle.getString(Params.REGISTER_MODE))){
			mPager.setCurrentItem(1);
		}
	}

	@Override
	protected void initEvent() {
		mBackBtn.setOnClickListener(this);
		mCloseBtn.setOnClickListener(this);
		mPhoneTv.setOnClickListener(this);
		mUserTv.setOnClickListener(this);
	}

	public static CYMGRegisterContainerDialogFragment newInewInstance(Activity activity,Bundle bundle) {
		mActivity = activity;
		CYMGRegisterContainerDialogFragment cymgRegisterContainerDialogFragment = new CYMGRegisterContainerDialogFragment();
		cymgRegisterContainerDialogFragment.setArguments(bundle);
		cymgRegisterContainerDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, AccResource.getInstance(mActivity).mgp_sdk_2_0_mian_dialog);
		return cymgRegisterContainerDialogFragment;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == AccResource.getInstance(mActivity).mgp_sdk_2_0_register_container_back_ImageButton) {
			CMBILogManager.printEventLog(mActivity, "0", "135008", "");
			dismiss();
		}else  if (v.getId() == AccResource.getInstance(mActivity).mgp_sdk_2_0_register_container_close_ImageButton) {
			CMBILogManager.printEventLog(mActivity, "0", "135009", "");
			mActivity.finish();
		} else if (v.getId() == AccResource.getInstance(mActivity).mgp_sdk_2_0_register_container_phone_TextView) {
			mPager.setCurrentItem(0);
		} else if (v.getId() == AccResource.getInstance(mActivity).mgp_sdk_2_0_register_container_user_TextView) {
			mPager.setCurrentItem(1);
		}
	}

	private void initViewPager(View view) {
		mPager = (ViewPager) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_register_container_ViewPager);
		mPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	private void initImageView(View view) {
		cursor = (ImageView) view.findViewById(AccResource.getInstance(mActivity).mgp_sdk_2_0_register_container_cursor_ImageView);
		bmpW = BitmapFactory.decodeResource(getResources(),AccResource.getInstance(mActivity).mgp_sdk_2_0_dialog_moving_cursor).getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 2 - bmpW) / 2;// 计算偏移量
		one = offset + bmpW - screenW / 8;
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);// 设置动画初始位置
	}

	class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			Animation animation = new TranslateAnimation(one * currIndex, one * arg0, 0, 0);// 显然这个比较简洁，只有一行代码。
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			cursor.startAnimation(animation);
			switch (arg0) {
			case 0:
				CMBILogManager.printEventLog(mActivity, "0", "135001", "");
				mPhoneTv.setTextColor(Color.parseColor("#000000"));
				mUserTv.setTextColor(Color.parseColor("#bcbcbc"));
				break;
			case 1:
				if(getCN==false){
					getCNFromServer();
					getCN=true;
				}
				CMBILogManager.printEventLog(mActivity, "0", "135002", "");
				mPhoneTv.setTextColor(Color.parseColor("#bcbcbc"));
				mUserTv.setTextColor(Color.parseColor("#000000"));
				break;
			}
		}
	}
	
	public void getCNFromServer(){
		String deviceid = SystemUtils.getDeviceId(mActivity);
		MyHttpClient myHttpClient=new MyHttpClient(mActivity);
		Map map=new HashMap();
		map.put("device_id", deviceid);
		myHttpClient.post(HttpContants.getURL(HttpContants.CHENGYOU_AUTO_GET_CN), map, new MyAsyncResponseHandler("getCNFromServer"){
			@Override
			public void onStart() {
				mLoadingDialog.setMessage(mActivity.getString(AccResource.getInstance(mActivity).mgp_sdk_2_0_loading_loading));
				mLoadingDialog.showDialog();
			}
			
			@Override
			public void onSuccess(int statusCode, String content) {
				try {
					log.d("statusCode:"+statusCode+",content:"+content);
					mLoadingDialog.dismissDialog();
					JSONObject jsonObject=new JSONObject(content);
					String cn=jsonObject.getString("cn");
					String password=jsonObject.getString("password");
					mCYMGResgisrForUserFragment.getmAccountET().setText(cn);
					mCYMGResgisrForUserFragment.getmPasswordET().setText(decodePassword(password));
				} catch (Exception e) {
					log.e(e);
				}
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable error, String content) {
				try {
					log.d("statusCode:"+statusCode+",Header:"+headers+",content:"+content);
					mLoadingDialog.dismissDialog();
				} catch (Exception e) {
					log.e(e);
				}
			}
		});
	}
	
	private String decodePassword(String password) {
		String result = null;
		try {
			result = new DesUtil().decrypt(password);
		} catch (Exception e) {
			log.e(e);
		}
		return result;
	}

	class MyPagerAdapter extends FragmentStatePagerAdapter {

		public ArrayList<Fragment> mFragmentList = new ArrayList<Fragment>();

		public MyPagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
			mFragmentList.add(mCYMGResgisrForPhoneFragment);
			mFragmentList.add(mCYMGResgisrForUserFragment);
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			super.destroyItem(container, position, object);
		}

		@Override
		public Fragment getItem(int arg0) {
			return mFragmentList.get(arg0);
		}

		@Override
		public int getCount() {
			return mFragmentList.size();
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}
	}
	
	public void showDialog() {
		FragmentTransaction transaction=((FragmentActivity)mActivity).getSupportFragmentManager().beginTransaction();
        transaction.add(this, Contants.DialogFragmentTag.REGISTER_CONTAINER);
        transaction.commitAllowingStateLoss(); 
	}

}
