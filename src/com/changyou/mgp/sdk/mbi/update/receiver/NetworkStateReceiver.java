package com.changyou.mgp.sdk.mbi.update.receiver;

import com.changyou.mgp.sdk.mbi.update.listener.NetworkStateChangeListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 
 * @ClassName: NetworkStateReceiver 
 * @Description: 网络状态广播接收者
 * @author J.Beyond 
 * @date 2014年8月6日 下午8:36:33 
 *
 */
public class NetworkStateReceiver extends BroadcastReceiver {

	private NetworkStateChangeListener mNetStateChangeListener;
	private Context mContext;
	
	private boolean isUnRegist = false;
	
	public boolean isUnRegist() {
		return isUnRegist;
	}

	public void setUnRegist(boolean isUnRegist) {
		this.isUnRegist = isUnRegist;
	}

	public NetworkStateReceiver(Context context,NetworkStateChangeListener netStateChangeListener) {
		this.mContext = context;
		this.mNetStateChangeListener = netStateChangeListener;
	}
	
	/**
	 * 
	 * @Title: registerReceiver 
	 * @Description: 注册广播
	 * @param  
	 * @return void
	 * @throws
	 */
	public void registerReceiver() {
		IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		mContext.registerReceiver(this, filter);
	}
	
	/**
	 * 
	 * @Title: unRegisterReceiver 
	 * @Description: 取消注册
	 * @param  
	 * @return void
	 * @throws
	 */
	public void unRegisterReceiver() {
		mContext.unregisterReceiver(this);
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		//网络不可用
		if (!wifiInfo.isConnected() && !mobileInfo.isConnected()) {
			mNetStateChangeListener.onNetworkDisconnected();
		}else if (!wifiInfo.isConnected() && mobileInfo.isConnected()) {
			mNetStateChangeListener.onWifiDisconnected();
		}
	}
	
	

}
