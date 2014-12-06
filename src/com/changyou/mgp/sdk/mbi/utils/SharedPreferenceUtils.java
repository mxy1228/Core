package com.changyou.mgp.sdk.mbi.utils;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
/**
 * 
 * 功能描述：SharedPreference工具
 *
 * @author 严峥(yanzheng)
 * @date 2013-12-24 下午4:36:23
 * 修改历史：(修改人，修改时间，修改原因/内容)
 */
public class SharedPreferenceUtils {	
	/**
	 * 保存配置文件信息
	 */
	public static boolean saveSharedPreference(Context context,String fileName,Map<String, Object> map) {
		boolean flag = false;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			Object value = entry.getValue();
			if (value instanceof Integer) {
				editor.putInt(entry.getKey(), (Integer) value);
			} else if (value instanceof Float) {
				editor.putFloat(entry.getKey(), (Float) value);
			} else if (value instanceof Long) {
				editor.putLong(entry.getKey(), (Long) value);
			} else if (value instanceof Boolean) {
				editor.putBoolean(entry.getKey(), (Boolean) value);
			} else if (value instanceof String) {
				editor.putString(entry.getKey(), (String) value);
			}
		}
		flag = editor.commit();
		return flag;
	}

	/**
	 * 获取配置文件信息
	 */
	public static Map<String, Object> getSharedPreference(Context context,String fileName) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Context.MODE_PRIVATE);
		Map<String, Object> result = (Map<String, Object>) sharedPreferences.getAll();
		return result;
	}

}
