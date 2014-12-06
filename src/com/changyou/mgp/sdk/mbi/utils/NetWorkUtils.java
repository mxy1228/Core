package com.changyou.mgp.sdk.mbi.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.changyou.mgp.sdk.mbi.log.CYLog;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;

/**
 * 
 * 功能描述：获取网络信息工具
 * 
 * @author 严峥(yanzheng)
 * @date 2013-12-24 下午4:35:52 修改历史：(修改人，修改时间，修改原因/内容)
 */
public class NetWorkUtils {
	
	private static CYLog log = CYLog.getInstance();

	/**
	 * 
	 * 功能描述：获取外网ip
	 * 
	 * @author 严峥(yanzheng)
	 * @param @return
	 * @return String
	 * @date 2014-1-3 上午11:18:20
	 * 
	 *       修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static String getNetIpAddress() {
		URL infoUrl = null;
		InputStream inStream = null;
		try {
			infoUrl = new URL("http://iframe.ip138.com/ic.asp");
			URLConnection connection = infoUrl.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				inStream = httpConnection.getInputStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inStream, "utf-8"));
				StringBuilder strber = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null)
					strber.append(line + "\n");
				inStream.close();
				// 从反馈的结果中提取出IP地址
				int start = strber.indexOf("[");
				int end = strber.indexOf("]", start + 1);
				line = strber.substring(start + 1, end);
				return line;
			}
		} catch (MalformedURLException e) {
			log.e(e);
		} catch (IOException e) {
			log.e(e);
		}
		return null;
	}

	/**
	 * 
	 * 功能描述：获取内网ip地址
	 *
	 * @author 严峥(yanzheng)
	 * @param @param ctx
	 * @param @return
	 * @return String
	 * @date 2014-3-6 上午10:52:18
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static String getLocalIpAddress(Context ctx) {
		String ip = getIpAddress1();
		if (ip != null) {
			return ip;
		}
		ip = getIpAddress2(ctx);
		if (ip != null) {
			return ip;
		}
		ip = getIpAddress3(ctx);
		if (ip != null) {
			return ip;
		}
		return null;
	}

	/**
	 * 
	 * 功能描述：获取内网ip地址:方法一
	 *
	 * @author 严峥(yanzheng)
	 * @param @return
	 * @return String
	 * @date 2014-3-6 上午10:52:59
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	private static String getIpAddress1() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()
							&& !inetAddress.isLinkLocalAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException e) {
			log.e(e);
		}
		return null;
	}

	/**
	 * 
	 * 功能描述：获取内网ip地址:方法二
	 *
	 * @author 严峥(yanzheng)
	 * @param @return
	 * @return String
	 * @date 2014-3-6 上午10:52:59
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	private static String getIpAddress2(Context ctx) {
		try {
			WifiManager wifiManager = (WifiManager) ctx
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			int ipAddress = wifiInfo.getIpAddress();
			int[] ipAddr = new int[4];
			ipAddr[0] = ipAddress & 0xFF;
			ipAddr[1] = (ipAddress >> 8) & 0xFF;
			ipAddr[2] = (ipAddress >> 16) & 0xFF;
			ipAddr[3] = (ipAddress >> 24) & 0xFF;
			return new StringBuilder().append(ipAddr[0]).append(".")
					.append(ipAddr[1]).append(".").append(ipAddr[2])
					.append(".").append(ipAddr[3]).append(".").toString();
		} catch (Exception e) {
			log.e(e);
		}
		return null;
	}

	/**
	 * 
	 * 功能描述：获取内网ip地址:方法三
	 *
	 * @author 严峥(yanzheng)
	 * @param @return
	 * @return String
	 * @date 2014-3-6 上午10:52:59
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	private static String getIpAddress3(Context ctx) {
		try {
			WifiManager wifi_service = (WifiManager) ctx
					.getSystemService(Context.WIFI_SERVICE);
			DhcpInfo dhcpInfo = wifi_service.getDhcpInfo();
			return Formatter.formatIpAddress(dhcpInfo.ipAddress);
		} catch (Exception e) {
			log.e(e);
		}
		return null;
	}

	/**
	 * 
	 * 功能描述：获取运营商
	 *
	 * @author 严峥(yanzheng)
	 * @param @param ctx
	 * @param @return
	 * @return String
	 * @date 2014-3-6 上午10:53:38
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static String getOperator(Context ctx) {
		TelephonyManager tel = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);
		String simOperator = tel.getSimOperator();
		if (simOperator != null) {
			if (simOperator.startsWith("46000")
					|| simOperator.startsWith("46002")
					|| simOperator.startsWith("46007")) {
				simOperator = "中国移动";
			}
			if (simOperator.startsWith("46001")) {
				simOperator = "中国联通";
			}
			if (simOperator.startsWith("46003")) {
				simOperator = "中国电信";
			}
		}
		return simOperator;
	}

	/**
	 * 
	 * 功能描述：判断是否有网络连接
	 *
	 * @author 严峥(yanzheng)
	 * @param @param context
	 * @param @return
	 * @return boolean
	 * @date 2014-3-6 上午10:53:54
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 
	 * 功能描述：判断WIFI网络是否可用
	 *
	 * @author 严峥(yanzheng)
	 * @param @param context
	 * @param @return
	 * @return boolean
	 * @date 2014-3-6 上午10:54:00
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 
	 * 功能描述：判断MOBILE网络是否可用
	 *
	 * @author 严峥(yanzheng)
	 * @param @param context
	 * @param @return
	 * @return boolean
	 * @date 2014-3-6 上午10:54:07
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mMobileNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mMobileNetworkInfo != null) {
				return mMobileNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 
	 * 功能描述：联网方式
	 *
	 * @author 严峥(yanzheng)
	 * @param @param ctx
	 * @param @return
	 * @return String
	 * @date 2014-3-6 上午10:54:14
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static String getNetType(Context ctx) {
		ConnectivityManager cm = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info != null) {
			String typeName = info.getTypeName();
			return typeName;
		}
		return null;
	}
	
	/**
	 * 
	 * 功能描述：验证手机号码号段
	 * 
	 * @author 严峥(yanzheng)
	 * @param @param mobiles
	 * @param @return
	 * @return boolean
	 * @date 2014-2-20 下午2:33:00
	 * 
	 *       修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
}
