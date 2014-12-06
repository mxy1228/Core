package com.changyou.mgp.sdk.mbi.utils;

import android.content.Context;

public class CYMGResourceUtil {

	public static int getLayoutID(Context context,String key){
		return context.getResources().getIdentifier(key, "layout", context.getPackageName());
	}
	
	public static int getStringID(Context context,String key){
		return context.getResources().getIdentifier(key, "string", context.getPackageName());
	}
	
	public static int getDrawableID(Context context,String key){
		return context.getResources().getIdentifier(key, "drawable", context.getPackageName());
	}
	
	public static int getStyleID(Context context,String key){
		return context.getResources().getIdentifier(key, "style", context.getPackageName());
	}
	
	public static int getID(Context context,String key){
		return context.getResources().getIdentifier(key, "id", context.getPackageName());
	}
	
	public static int getColorID(Context context,String key){
		return context.getResources().getIdentifier(key, "color", context.getPackageName());
	}
	
	public static int getAttrID(Context context,String key){
		return context.getResources().getIdentifier(key, "attr", context.getPackageName());
	}
	
}
