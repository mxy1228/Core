package com.changyou.mgp.sdk.mbi.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;

/**
 * 
 * 功能描述：String判断类
 *
 * @author 严峥(yanzheng)
 * @date 2014-1-2 下午12:11:48
 * 修改历史：(修改人，修改时间，修改原因/内容)
 */
public class StringUtils {

	/**
	 * 
	 * 功能描述：非空判断
	 *
	 * @author 严峥(yanzheng)
	 * @param @param str
	 * @param @return
	 * @return boolean
	 * @date 2014-1-2 下午12:11:22
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static boolean isEmpty(String str) {
		if (null != str && !"".equals(str)) {
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * 功能描述：判断字符串是否由数字组成
	 *
	 * @author 严峥(yanzheng)
	 * @param @param str
	 * @param @return
	 * @return boolean
	 * @date 2014-1-2 下午12:11:17
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static boolean isNumeric(String str){
	    for (int i = str.length();--i>=0;){
	    	if (!Character.isDigit(str.charAt(i))){
	    		return false;
	    	}
	    }
	    return true;
	}
	
	/**
	 * 
	 * 功能描述：对“注册表示您同意《畅游用户协议》”进行html加工
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param @param context
	 * @param @return
	 * @return String
	 * @date 2014-3-6 下午8:32:24
	 *
	 */
	public static SpannableString getCYProtocol(String str,ClickableSpan cspan){
		SpannableString spstr = new SpannableString(str);
		spstr.setSpan(new AbsoluteSizeSpan(13, true), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		spstr.setSpan(new UnderlineSpan(), 8, str.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		spstr.setSpan(cspan, 8, str.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spstr;
	}

}
