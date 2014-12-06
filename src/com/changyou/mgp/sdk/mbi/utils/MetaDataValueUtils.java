package com.changyou.mgp.sdk.mbi.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.changyou.mgp.sdk.mbi.config.Contants;
import com.changyou.mgp.sdk.mbi.log.CYLog;

public class MetaDataValueUtils {  
	
	private static CYLog log = CYLog.getInstance();
	
	private static final String KEY_APP_ID_WEIXIN="APP_ID_WEIXIN";
	private static final String KEY_APP_KEY_WEIXIN="APP_KEY_WEIXIN";
	private static final String KEY_SECRET_WEIXIN="SECRET_WEIXIN";
	
	public static String getMetaDataValue(Context context, String key, String defValue) {
		String value = getMetaDataValue(context,key);
		return (value == null) ? defValue : value;
	}

	private static String getMetaDataValue(Context context,String key) {
		Object value = null;
		PackageManager packageManager = context.getPackageManager();
		ApplicationInfo applicationInfo;
		try {
			applicationInfo = packageManager.getApplicationInfo(
					context.getPackageName(), PackageManager.GET_META_DATA);
			if (applicationInfo != null && applicationInfo.metaData != null) {
				value = applicationInfo.metaData.get(key);
				log.d("key = "+key+",value = "+value);
			}
		} catch (NameNotFoundException e) {
		}
		if (value == null) {
			value="";
		}
		return String.valueOf(value);
	}
	
	/**
	 * 
	 * 功能描述：获取渠道ID
	 * 
	 * @author 徐萌阳(xumengyang)
	 * @param @return
	 * @return String
	 * @date 2014-2-19 下午2:20:49
	 * 
	 */
	public static String getChannelID(Context context) {
		return getMetaDataValue(context,Contants.KEY_CHANNLE_ID,"");
	}

	/**
	 * 
	 * 功能描述：获取APP_KEY
	 * 
	 * @author 徐萌阳(xumengyang)
	 * @param @return
	 * @return String
	 * @date 2014-2-19 下午2:22:20
	 * 
	 */
	public static String getAppKey(Context context) {
		return getMetaDataValue(context,Contants.KEY_APPKEY,"");
	}

	/**
	 * 
	 * 功能描述：获取APP_SECRET
	 *
	 * @author 严峥(yanzheng)
	 * @param @return
	 * @return String
	 * @date 2014-3-10 下午3:16:45
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static String getAppSecret(Context context) {
		return getMetaDataValue(context,Contants.KEY_APPSECRET,"");
	}
	
	/**
	 * 
	 * 功能描述：获取QQ_APP_ID
	 *
	 * @author 严峥(yanzheng)
	 * @param @return
	 * @return String
	 * @date 2014-3-24 上午11:53:01
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static String getQQAppId(Context context){
		return getMetaDataValue(context,Contants.KEY_QQ_APPID,"");
	}
	
	/**
	 * 
	 * 功能描述：获取SINA_APP_KEY
	 *
	 * @author 严峥(yanzheng)
	 * @param @return
	 * @return String
	 * @date 2014-3-24 上午11:53:01
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static String getSinaAppKey(Context context){
		return getMetaDataValue(context,Contants.KEY_SINA_APPKEY,"");
	}
	
	/**
	 * 
	 * 功能描述：获取SINA_APP_KEY
	 *
	 * @author 严峥(yanzheng)
	 * @param @return
	 * @return String
	 * @date 2014-3-24 上午11:53:01
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static String getSinaAppSecret(Context context){
		return getMetaDataValue(context,Contants.KEY_SINA_APPSECRET,"");
	}
	
	/**
	 * 
	 * 功能描述：获取91_APP_ID
	 *
	 * @author 严峥(yanzheng)
	 * @param @return
	 * @return String
	 * @date 2014-3-24 上午11:53:01
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static String get91AppId(Context context){
		return getMetaDataValue(context,Contants.KEY_APP_ID_91,"");
	}
	
	/**
	 * 
	 * 功能描述：获取91_APP_KEY
	 *
	 * @author 严峥(yanzheng)
	 * @param @return
	 * @return String
	 * @date 2014-3-24 上午11:53:01
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static String get91AppKey(Context context){
		return getMetaDataValue(context,Contants.KEY_APP_KEY_91,"");
	}
	
	/**
	 * 
	 * 功能描述：获取UC_CP_ID
	 *
	 * @author 严峥(yanzheng)
	 * @param @return
	 * @return String
	 * @date 2014-3-24 上午11:53:01
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static String getUcCPID(Context context){
		return getMetaDataValue(context,Contants.KEY_APP_CPID_UC,"");
	}
	
	/**
	 * 
	 * 功能描述：获取UC_APP_APIKEY
	 *
	 * @author 严峥(yanzheng)
	 * @param @return
	 * @return String
	 * @date 2014-3-24 上午11:53:01
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static String getUcAPIKEY(Context context){
		return getMetaDataValue(context,Contants.KEY_APP_APIKEY_UC,"");
	}
	
	/**
	 * 
	 * 功能描述：获取UC_APP_GAMEID
	 *
	 * @author 严峥(yanzheng)
	 * @param @return
	 * @return String
	 * @date 2014-3-24 上午11:53:01
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static String getUcGAMEID(Context context){
		return getMetaDataValue(context,Contants.KEY_APP_GAMEID_UC,"");
	}
	
	/**
	 * 
	 * 功能描述：获取UC_APP_SERVERID
	 *
	 * @author 严峥(yanzheng)
	 * @param @return
	 * @return String
	 * @date 2014-3-24 上午11:53:01
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static String getUcSERVERID(Context context){
		return getMetaDataValue(context,Contants.KEY_APP_SERVERID_UC,"");
	}
	
	/**
	 * 
	 * 功能描述：获取WDJ_APP_KEY
	 *
	 * @author 严峥(yanzheng)
	 * @param @return
	 * @return String
	 * @date 2014-3-24 上午11:53:01
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static String getWdjAppKey(Context context){
		return getMetaDataValue(context,Contants.KEY_APP_KEY_WDJ,"");
	}
	
	/**
	 * 
	 * 功能描述：获取WDJ_APP_SECRET
	 *
	 * @author 严峥(yanzheng)
	 * @param @return
	 * @return String
	 * @date 2014-3-24 上午11:53:01
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static String getWdjAppSecret(Context context){
		return getMetaDataValue(context,Contants.KEY_APP_SECRET_WDJ,"");
	}
	
	/**
	 * 功能描述: 获取多酷配置的appId
	 * @param @param context
	 * @param @return  
	 * @return String    返回类型 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @date 2014-3-25 下午4:01:17
	 */
	public static String getDuokuAppId(Context context){
		return getMetaDataValue(context,Contants.KEY_APP_ID_DUOKU,"");
	}
	/**
	 * 
	 * 功能描述: 获取多酷配置的appkey
	 * @param @param context
	 * @param @return  
	 * @return String    返回类型 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @date 2014-3-25 下午4:02:03
	 */
	public static String getDuokuAppKey(Context context){
		return getMetaDataValue(context,Contants.KEY_APP_KEY_DUOKU,"");
	}
	
	/**
	 * 
	 * 功能描述: 获取多酷配置的appSceret
	 * @param @param context
	 * @param @return  
	 * @return String    返回类型 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @date 2014-4-2 下午12:10:43
	 */
	public static String getDuokuAppSceret(Context context){
		return getMetaDataValue(context,Contants.KEY_APP_SCERET_DUOKU,"");
	}
	
	/**
	 * 
	 * 功能描述：获取MI_APP_ID
	 *
	 * @author 严峥(yanzheng)
	 * @param @return
	 * @return String
	 * @date 2014-3-24 上午11:53:01
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static String getMiAppId(Context context){
		return getMetaDataValue(context,Contants.KEY_APP_ID_MI,"");
	}
	
	/**
	 * 
	 * 功能描述：获取MI_APP_KEY
	 *
	 * @author 严峥(yanzheng)
	 * @param @return
	 * @return String
	 * @date 2014-3-24 上午11:53:01
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static String getMiAppKey(Context context){
		return getMetaDataValue(context,Contants.KEY_APP_KEY_MI,"");
	}
	
	/**
	 * 
	 * 功能描述：获取ANZHI_APP_KEY
	 *
	 * @author 严峥(yanzheng)
	 * @param @return
	 * @return String
	 * @date 2014-3-24 上午11:53:01
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static String getAnZhiAppKey(Context context){
		return getMetaDataValue(context,Contants.KEY_APP_KEY_ANZHI,"");
	}
	
	/**
	 * 
	 * 功能描述：获取ANZHI_APP_SECRET
	 *
	 * @author 严峥(yanzheng)
	 * @param @return
	 * @return String
	 * @date 2014-3-24 上午11:53:01
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static String getAnZhiAppSecret(Context context){
		return getMetaDataValue(context,Contants.KEY_APP_SECRET_ANZHI,"");
	}
	
	/**
	 * 
	 * 功能描述：获取OPPO_APP_KEY
	 *
	 * @author 严峥(yanzheng)
	 * @param @return
	 * @return String
	 * @date 2014-3-24 上午11:53:01
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static String getOppoAppKey(Context context){
		return getMetaDataValue(context,Contants.KEY_APP_KEY_OPPO,"");
	}
	
	/**
	 * 
	 * 功能描述：获取OPPO_APP_SECRET
	 *
	 * @author 严峥(yanzheng)
	 * @param @return
	 * @return String
	 * @date 2014-3-24 上午11:53:01
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static String getOppoAppSecret(Context context){
		return getMetaDataValue(context,Contants.KEY_APP_SECRET_OPPO,"");
	}
	

	
	
	/**
	 * 
	 * 功能描述: 获取qihoo appid
	 * @param @param context
	 * @param @return  
	 * @return String    返回类型 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @date 2014-4-28 下午6:01:57
	 */
	public static String getQihooClientId(Context context){
		return getMetaDataValue(context,Contants.KEY_APP_ID_QIHOO,"");
	}
	
	/**
	 * 
	 * 功能描述: 获取qihoo appSceret
	 * @param @param context
	 * @param @return  
	 * @return String    返回类型 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @date 2014-4-28 下午6:03:44
	 */
	public static String getQihooClientSceret(Context context){
		return getMetaDataValue(context,Contants.KEY_APP_SCERET_QIHOO,"");
	}
	
	
	/**
	 * 获取CMBI_CHANNEL_ID，该ID即会作为CMBI统计的ChannelID，又会作为创建订单时的推广渠道ID
	 * @param context
	 * @return
	 */
	public static String getCMBIChannelID(Context context){
		return getMetaDataValue(context, Contants.KEY_CMBI_CHANNEL_ID,"");
	}
	
	public static String getWeixinAppKey(Context context) {
		return getMetaDataValue(context,KEY_APP_KEY_WEIXIN,"");
	}
	public static String getWeixinAppSecret(Context context) {
		return getMetaDataValue(context,KEY_SECRET_WEIXIN,"");
	}
	public static String getWeixinAppId(Context context){
		return getMetaDataValue(context,KEY_APP_ID_WEIXIN,"");
	}
	
	
	/**
	 * 
	 * @Description: 获取版本号
	 * @param @param context
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws 
	 * @author 欧阳海冰（OuyangHaibing）
	 * @date 2014年8月25日 下午2:26:23
	 */
	public static String getVersion(Context context){
		try {
			PackageInfo pkg = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			String versionName = pkg.versionName; 
			log.d("SDK Version:"+versionName);
			return versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
}
