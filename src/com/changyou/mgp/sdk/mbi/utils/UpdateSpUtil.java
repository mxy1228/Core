package com.changyou.mgp.sdk.mbi.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.changyou.mgp.sdk.mbi.config.Contants;

/**
 * 
 * @ClassName: UpdateSpUtil 
 * @Description: 存储游戏更新信息
 * @author J.Beyond 
 * @date 2014年8月8日 下午8:08:49 
 *
 */
public class UpdateSpUtil {

	private static final String UPDATE_CONFIG = "update_config";
	public static void setIsUpdateCanceled(Context context,boolean value){
		SharedPreferences sp = context.getSharedPreferences(UPDATE_CONFIG, Context.MODE_PRIVATE);
		Editor e = sp.edit();
		e.putBoolean(Contants.UpdateParams.IS_UPDATE_CANCELED, value);
		e.commit();
	}
	
	public static boolean isUpdateCanceled(Context context){
		SharedPreferences sp = context.getSharedPreferences(UPDATE_CONFIG, Context.MODE_PRIVATE);
		return sp.getBoolean(Contants.UpdateParams.IS_UPDATE_CANCELED, false);
	}
	
	public static void setIsDownloadCompleted(Context context,boolean value){
		SharedPreferences sp = context.getSharedPreferences(UPDATE_CONFIG, Context.MODE_PRIVATE);
		Editor e = sp.edit();
		e.putBoolean(Contants.UpdateParams.IS_NEWVERSION_INSTALLED, value);
		e.commit();
	}
	
	public static boolean isDownloadCompleted(Context context){
		SharedPreferences sp = context.getSharedPreferences(UPDATE_CONFIG, Context.MODE_PRIVATE);
		return sp.getBoolean(Contants.UpdateParams.IS_NEWVERSION_INSTALLED, false);
	}
	
}
