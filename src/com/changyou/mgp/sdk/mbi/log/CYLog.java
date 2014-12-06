package com.changyou.mgp.sdk.mbi.log;

import android.util.Log;

import com.changyou.mgp.sdk.mbi.config.SDKConfig;

public class CYLog{
	private static String TAG = "com.changyou.mgp.sdk.mbi";
	private static CYLog log;
	
	private CYLog(){
		
	}
	
	/**
	 * 
	 * @return
	 */
	public static CYLog getInstance(){
		if(log == null){
			log = new CYLog();
		}
//		TAG=getCurrentInfo();
		return log;
	}

	private void debug(Object obj) {
		String funName = getFunName();
		String info = (funName == null ? obj.toString() : (funName + ":" + obj.toString()));
		Log.d(TAG, "CYMG:"+info);
	}
	/**
	 * 当前日志行号
	 * @return
	 */
//	private static String getCurrentInfo() {
//
//		StackTraceElement[] eles = Thread.currentThread().getStackTrace();
//		StackTraceElement targetEle = eles[5];
//		String info = "(" + targetEle.getClassName() + "."
//				+ targetEle.getMethodName() + ":" + targetEle.getLineNumber()
//				+ ")";
//		return info;
//	}

	private void error(Exception exception) {
		try {
			Log.e(TAG, "CYMG:", exception);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void error(Object obj) {
		try {
			if (obj != null) {
				String funName = getFunName();
				String info = (funName == null ? obj.toString() : (funName+ ":" + obj.toString()));
				Log.e(TAG, "CYMG:"+info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private String getFunName() {
		StackTraceElement[] sts = Thread.currentThread().getStackTrace();
		if (sts == null) {
			return null;
		}
		for (StackTraceElement st : sts) {
			if (st.isNativeMethod()) {
				continue;
			}
			if (st.getClassName().equals(Thread.class.getName())) {
				continue;
			}
			if (st.getClassName().equals(this.getClass().getName())) {
				continue;
			}
			return "[" + Thread.currentThread().getId() + ":"
					+ st.getFileName() + ":" + st.getLineNumber() + "]";
		}
		return null;
	}

//	private String getTimerFileName() {
//		SimpleDateFormat simpledateformat = new SimpleDateFormat(FORMAT);
//		StringBuffer stringbuffer = new StringBuffer();
//		stringbuffer.append(simpledateformat.format(new Date())).append(
//				"Timer.log");
//		return stringbuffer.toString();
//	}

	private synchronized void verbose(Object obj) {
		String funName = getFunName();
		String info = (funName == null ? obj.toString() : (funName + ":" + obj
				.toString()));
		Log.v(TAG, "CYMG:"+info);
	}

	private synchronized void warn(Object obj) {
		String funName = getFunName();
		String info = (funName == null ? obj.toString() : (funName + ":" + obj
				.toString()));
		Log.w(TAG, "CYMG:"+info);
	}

	public synchronized void d(Object obj) {
//		if (SDKConfig.DEBUG){
			debug(obj);
//		}
	}

	public synchronized void e(Exception exception) {
//		if (SDKConfig.DEBUG){
			error(exception);
//		}
	}

	public synchronized void e(Object obj) {
//		if (SDKConfig.DEBUG){
			error(obj);
//		}
	}

	public synchronized void i(Object obj) {
//		if(SDKConfig.DEBUG){
			String funName = getFunName();
			String info = (funName == null ? obj.toString() : (funName + ":" + obj
					.toString()));
			Log.i(TAG,"CYMG:"+info);
//		}
	}

	public synchronized void v(Object obj) {
//		if (SDKConfig.DEBUG){
			verbose(obj);
//		}
	}

	public synchronized void w(Object obj) {
//		if (SDKConfig.DEBUG){
			warn(obj);
//		}
	}

}
