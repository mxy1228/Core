package com.changyou.mgp.sdk.mbi.ui.base;

import android.support.v4.app.Fragment;
import android.widget.Toast;
/**
 * 
 * 功能描述：Fragment基类，，提供基础复用方法，管理统一性Action
 *
 * @author 徐萌阳(xumengyang)
 * @date 2013-12-22下午2:53:08
 */
public abstract class BaseFragment extends Fragment {

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
		Toast.makeText(getActivity(), res, Toast.LENGTH_SHORT).show();
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
		Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
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
		Toast.makeText(getActivity(), res, Toast.LENGTH_LONG).show();
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
		Toast.makeText(getActivity(), str, Toast.LENGTH_LONG).show();
	}
}
