package com.changyou.mgp.sdk.mbi.channel;

import android.content.Context;
import android.os.Bundle;



public class CYMGChannelEntity{

	private Bundle bundle;
	private Context mContext;
	private int mScreenOrientation;

	public Bundle getBundle() {
		return bundle;
	}

	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}

	public Context getmContext() {
		return mContext;
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}

	public int getmScreenOrientation() {
		return mScreenOrientation;
	}

	public void setmScreenOrientation(int mScreenOrientation) {
		this.mScreenOrientation = mScreenOrientation;
	}
	
}
