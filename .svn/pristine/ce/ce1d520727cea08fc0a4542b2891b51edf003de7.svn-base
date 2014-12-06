package com.changyou.mgp.sdk.mbi.utils;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.changyou.mgp.sdk.mbi.config.Contants;

/**
 * 功能描述: 工具类，对外提供保存和获取用户登录信息的静态方法
 * @author 欧阳海冰(OuyangHaibing) 
 * @date 2014-1-24 下午6:45:24 
 *
 */
public class UserInfoSPUtil {
	/**
	 * 功能描述: 从SharedPerference中获取用户的名称
	 * @author 欧阳海冰(OuyangHaibing)
	 * @param @param context
	 * @param @return 
	 * @return String
	 * @throws
	 */
	public static String getUsername(Context context) {
		return (String) SharedPreferenceUtils.getSharedPreference(context,
				Contants.LoginParams.SP_USER_INFO).get(
				Contants.LoginParams.USERNAME);
	}

	/**
	 * 功能描述: 将用户的姓名保存到SharedPerference中
	 * @author 欧阳海冰(OuyangHaibing)
	 * @param @param context
	 * @param @param username 
	 * @return void
	 * @throws
	 */
	public static void setUsername(Context context, String username) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Contants.LoginParams.USERNAME, username);
		SharedPreferenceUtils.saveSharedPreference(context,
				Contants.LoginParams.SP_USER_INFO, map);
	}
	
	/**
	 * 
	 * 功能描述：从SharedPerference中获取uid
	 *
	 * @author 严峥(yanzheng)
	 * @param @param context
	 * @param @return
	 * @return String
	 * @date 2014-4-23 下午3:13:12
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static String getUid(Context context) {
		return (String) SharedPreferenceUtils.getSharedPreference(context,
				Contants.LoginParams.SP_USER_INFO).get(
				Contants.LoginParams.UID);
	}

	/**
	 * 
	 * 功能描述：从SharedPerference中存储uid
	 *
	 * @author 严峥(yanzheng)
	 * @param @param context
	 * @param @param uid
	 * @return void
	 * @date 2014-4-23 下午3:13:25
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static void setUid(Context context, String uid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Contants.LoginParams.UID, uid);
		SharedPreferenceUtils.saveSharedPreference(context,
				Contants.LoginParams.SP_USER_INFO, map);
	}
	
	/**
	 * 
	 * 功能描述：从SharedPerference中获取token
	 *
	 * @author 严峥(yanzheng)
	 * @param @param context
	 * @param @return
	 * @return String
	 * @date 2014-4-23 下午3:13:12
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static String getToken(Context context) {
		return (String) SharedPreferenceUtils.getSharedPreference(context,
				Contants.LoginParams.SP_USER_INFO).get(
				Contants.LoginParams.TOKEN);
	}

	/**
	 * 
	 * 功能描述：从SharedPerference中存储token
	 *
	 * @author 严峥(yanzheng)
	 * @param @param context
	 * @param @param uid
	 * @return void
	 * @date 2014-4-23 下午3:13:25
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static void setToken(Context context, String token) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Contants.LoginParams.TOKEN, token);
		SharedPreferenceUtils.saveSharedPreference(context,
				Contants.LoginParams.SP_USER_INFO, map);
	}
	
	/**
	 * 
	 * 功能描述：从SharedPerference中获取token
	 *
	 * @author 严峥(yanzheng)
	 * @param @param context
	 * @param @return
	 * @return String
	 * @date 2014-4-23 下午3:13:12
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static String getType(Context context) {
		return (String) SharedPreferenceUtils.getSharedPreference(context,
				Contants.LoginParams.SP_USER_INFO).get(
				Contants.LoginParams.TYPE);
	}

	/**
	 * 
	 * 功能描述：从SharedPerference中存储token
	 *
	 * @author 严峥(yanzheng)
	 * @param @param context
	 * @param @param uid
	 * @return void
	 * @date 2014-4-23 下午3:13:25
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static void setType(Context context, String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Contants.LoginParams.TYPE, type);
		SharedPreferenceUtils.saveSharedPreference(context,
				Contants.LoginParams.SP_USER_INFO, map);
	}
}
