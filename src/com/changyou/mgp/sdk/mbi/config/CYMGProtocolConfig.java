package com.changyou.mgp.sdk.mbi.config;

import android.content.pm.ActivityInfo;

/**
 * 
 * 功能描述：对外开放接口参数值
 *
 * @author 徐萌阳(xumengyang)
 *
 * @date 2014-2-24
 */
public class CYMGProtocolConfig {

	public static final int PORTRAIT = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;//竖屏显示
	public static final int LANDSCAPE = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;//横屏显示
	
	public static final int COMMON_QIHOO_ADDICTION_QUERY = 100;//360渠道的防沉迷查询
	public static final int COMMON_QIHOO_REAL_NAME_REGISTER = 101;//360渠道的实名注册
	
	public static final int COMMON_91_PAUSE_PAGE = 200;//91暂停页
	public static final int COMMON_91_EXIT_PAGE = 201;//91退出页
	
	//功能描述：打开支付相关不同页面
	public static final int PAY_NORMAL = 301;//打开商品列表
	public static final int PAY_WAY = 302;//支付方式
	public static final int PAY_RECORD = 303;//充值记录
}
