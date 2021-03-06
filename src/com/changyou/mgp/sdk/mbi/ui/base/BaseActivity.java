package com.changyou.mgp.sdk.mbi.ui.base;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.changyou.mgp.sdk.mbi.R;
import com.changyou.mgp.sdk.mbi.config.CYMGProtocolConfig;
import com.changyou.mgp.sdk.mbi.log.CYLog;
import com.changyou.mgp.sdk.mbi.utils.SettingSPUtil;
/**
 * 
 * 功能描述：Activity基类，提供基础复用方法，管理统一性Action
 *
 * @author 徐萌阳(xumengyang)
 * @date 2013-12-22下午2:52:13
 */
public abstract class BaseActivity extends FragmentActivity {

	protected int mScreenWidth;
	protected int mScreenHeight;
	protected float mDensity;
	protected LayoutInflater mInflater;
	protected CYLog log;
	protected ActionBar mActionBar;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		//获取屏幕属性
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		mScreenWidth = metric.widthPixels;
		mScreenHeight = metric.heightPixels;
		mDensity = metric.density;
		
		mInflater = LayoutInflater.from(this);
		log = CYLog.getInstance();
		
		//设置屏幕方向
		if(SettingSPUtil.getLandScape(this)==CYMGProtocolConfig.PORTRAIT){
			setRequestedOrientation(CYMGProtocolConfig.PORTRAIT);
		}else if(SettingSPUtil.getLandScape(this)==CYMGProtocolConfig.LANDSCAPE){
			setRequestedOrientation(CYMGProtocolConfig.LANDSCAPE);
		}
		
	}
	
	/**
	 * 
	 * 功能描述：初始化页面
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param 
	 * @return void
	 * @date 2013-12-22 下午2:48:31
	 *
	 */
	protected abstract void initView();
	
	/**
	 * 
	 * 功能描述：初始化数据
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param 
	 * @return void
	 * @date 2013-12-22 下午2:48:53
	 *
	 */
	protected abstract void initData();
	
	/**
	 * 
	 * 功能描述：初始化各个响应事件
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param 
	 * @return void
	 * @date 2013-12-22 下午2:49:20
	 *
	 */
	protected abstract void initEvent();
	
	/**
	 * 
	 * 功能描述：短Toast，参数为资源id
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param @param res
	 * @return void
	 * @date 2013-12-22 下午2:50:02
	 *
	 */
	protected void showShortToast(int res) {
		Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 
	 * 功能描述：短Toast，参数为String
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param @param str
	 * @return void
	 * @date 2013-12-22 下午2:50:23
	 *
	 */
	protected void showShortToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 
	 * 功能描述：长Toast，参数为资源id
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param @param res
	 * @return void
	 * @date 2013-12-22 下午2:50:50
	 *
	 */
	protected void showLongToast(int res) {
		Toast.makeText(this, res, Toast.LENGTH_LONG).show();
	}

	/**
	 * 
	 * 功能描述：长Toast，参数为String
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param @param str
	 * @return void
	 * @date 2013-12-22 下午2:51:08
	 *
	 */
	protected void showLongToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_LONG).show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}
