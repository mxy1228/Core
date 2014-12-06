package com.changyou.mgp.sdk.mbi.utils;

import com.changyou.mgp.sdk.mbi.config.SDKConfig;

import android.content.Context;
import android.widget.Toast;

public class MyToast {

	public static void showDebugToast(Context context,String str){
		if(SDKConfig.DEBUG){
			Toast.makeText(context, "Debug:"+str, Toast.LENGTH_LONG).show();
		}
	}
	
	public static void showDebugToast(Context context,int res){
		if(SDKConfig.DEBUG){
			Toast.makeText(context, "Debug:"+res, Toast.LENGTH_LONG).show();
		}
	}
	
	public static void showToast(Context context,int res){
		Toast.makeText(context, res, Toast.LENGTH_LONG).show();
	}
	
	public static void showToast(Context context,String str){
		Toast.makeText(context, str, Toast.LENGTH_LONG).show();
	}
}
