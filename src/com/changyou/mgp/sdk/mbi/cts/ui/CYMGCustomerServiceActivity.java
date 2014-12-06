package com.changyou.mgp.sdk.mbi.cts.ui;

import java.util.Stack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.changyou.mgp.sdk.mbi.config.Contants;
import com.changyou.mgp.sdk.mbi.cts.id.CtsResource;
import com.changyou.mgp.sdk.mbi.log.CYLog;
import com.changyou.mgp.sdk.mbi.ui.base.BaseActivity;

import de.greenrobot.event.EventBus;
/**
 * 
 * 功能描述：在线客服Activity 包含：客服反馈、工单查询功能
 *
 * @author 严峥(yanzheng)
 * @date 2014-3-26 下午1:54:27
 * 修改历史：(修改人，修改时间，修改原因/内容)
 */
public class CYMGCustomerServiceActivity extends BaseActivity{
	private static CYLog log = CYLog.getInstance();
	private FragmentManager mFragmentManager;//fragment管理器
	private CYMGCustomerServiceFeedbackFragment mMGPOlineServerFeedbackFragment;
	private Bundle mBundle;
	private CtsResource mResource;
	private static Stack<Fragment> sFragmentsStack;//存放该Activity所托管的所有Fragment
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mResource=CtsResource.getInstance(this);
		setContentView(mResource.mgp_onlineserver_root_1_03);
		initView();
		initData();
		initEvent();
	}
	
	@Override
	protected void initView() {
		intoMGPOlineServerFeedbackFragment();
	}

	@Override
	protected void initData() {
		
	}
	/**
	 * 
	 * @Title: addFragment 
	 * @Description: 添加Fragment到Stack（）
	 * @param @param fragment 
	 * @return void
	 * @throws
	 */
	public static void addFragment(Fragment fragment) {
		if (sFragmentsStack == null) {
			sFragmentsStack = new Stack<Fragment>();
		}
		if (sFragmentsStack.isEmpty()) {
			sFragmentsStack.add(fragment);
		}else {
			String clsName = fragment.getClass().getSimpleName();
			boolean isSameFragment = false;
			int pos = 0;
			for (int i = 0; i < sFragmentsStack.size(); i++) {
				if (clsName.equals(sFragmentsStack.get(i).getClass().getSimpleName())) {
					isSameFragment = true;
					pos = i;
				}
			}
			if (isSameFragment) {
				sFragmentsStack.remove(pos);
			}
			sFragmentsStack.add(fragment);
		}
	}
	/**
	 * 
	 * @Title: removeFragment 
	 * @Description: 从Stack中移除指定的Fragment
	 * @param @param fragment
	 * @param @return 
	 * @return boolean
	 * @throws
	 */
	public static boolean removeFragment(Fragment fragment) {
		if (fragment != null) {
			return sFragmentsStack.remove(fragment);
		}
		return false;
	}
	/**
	 * 
	 * @Title: getCurrentFragment 
	 * @Description: 获取到当前的Fragment
	 * @param @return 
	 * @return Fragment
	 * @throws
	 */
	private Fragment getCurrentFragment(){
		return sFragmentsStack.lastElement();
	}

	@Override
	protected void initEvent() {
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){//判断是否是后退键
			if (getCurrentFragment() instanceof CYMGCustomServiceIMFragment) {
				EventBus.getDefault().post(new CtsBackPressEvent());
				log.e("******************EventBus#post::CtsBackPressEvent");
				return true;
			}else {
				if(this.mFragmentManager.getBackStackEntryCount()==0){
					this.finish();
				}else{
					this.mFragmentManager.popBackStack();
				}
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * 
	 * 功能描述：进入客服反馈页面
	 *
	 * @author 严峥(yanzheng)
	 * @param @param content
	 * @return void
	 * @date 2014-3-26 下午1:56:09
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	private void intoMGPOlineServerFeedbackFragment(){
		mBundle=getIntent().getExtras();
		mFragmentManager=getSupportFragmentManager();
		FragmentTransaction mFT=mFragmentManager.beginTransaction();
		mMGPOlineServerFeedbackFragment=new CYMGCustomerServiceFeedbackFragment();
		mMGPOlineServerFeedbackFragment.setArguments(mBundle);
		mFT.replace(mResource.mgp_onlineserver_root_layout, mMGPOlineServerFeedbackFragment,Contants.FragmentTag.ONLINESERVER_FEEDBACK_FRAGMENT);
		mFT.commit();
		addFragment(mMGPOlineServerFeedbackFragment);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		sFragmentsStack.clear();
		
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		
	}
	
	
	class CtsBackPressEvent{
		
	}

}
