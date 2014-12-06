package com.changyou.mgp.sdk.mbi.utils;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	//预定格式
	public static final String FORMAT_NORMAL_TIME = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_DATE_YEAR = "yyyyMMdd";
	public static final String FORMAT_TIME_MI = "yyyyMMddHHmm";
	public static final String FORMAT_CMBI_TIME = "yyyyMMddHHmmss";
	public static final String FORMAT_INFO_TIME = "yyyyMMddHHmmssSSS";
	public static final String FORMAT_LOG_TIME = "yyyy-MM-dd_HH:mm:ss_SSS";
	
	/**
	 * 解析日期
	 * @param time				时间
	 * @throws ParseException	解析异常
	 */
	public static Date parseDate(String time) throws ParseException{
         Format f = new SimpleDateFormat(FORMAT_NORMAL_TIME);
         Date d = (Date) f.parseObject(time);
         return d;
	}
	
	/**
	 * 格式化日期
	 * @param date		日期
	 * @param format	格式
	 * @return
	 */
	public static String formatDate(Date date,String format){
		SimpleDateFormat sdf= new SimpleDateFormat(format);
		String dateStr = sdf.format(date);
		return dateStr;
	}
	
	/**
	 * 获取系统当前时间，单位【秒】
	 */
	public static long getCurrentTimeToSecond(){
		return new Date(System.currentTimeMillis()).getTime()/1000;
	}
	
	/**
	 * 获取系统当前时间，单位为毫秒
	 */
	public static long getCurrentTimeToMS(){
		return new Date(System.currentTimeMillis()).getTime();
	}
}
