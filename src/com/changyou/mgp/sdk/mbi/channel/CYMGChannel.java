package com.changyou.mgp.sdk.mbi.channel;

import com.changyou.mgp.sdk.mbi.platform.CYMGPlatformConfiguration;

public interface CYMGChannel {

	/**
	 * 
	 * 功能描述：第三方渠道初始化
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param @param entity
	 * @return void
	 * @date 2014-2-20 下午1:32:10
	 *
	 */
	public void init(CYMGPlatformConfiguration config);
	
	/**
	 * 
	 * 功能描述：第三方渠道登录
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param @param entity
	 * @return void
	 * @date 2014-2-20 下午1:32:10
	 *
	 */
	public void doLogin(CYMGChannelEntity entity);
	
	/**
	 * 
	 * 功能描述：第三方渠道支付
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param @param entity
	 * @return void
	 * @date 2014-2-20 下午5:04:09
	 *
	 */
	public void doPay(CYMGChannelEntity entity);
	
	/**
	 * 
	 * 功能描述：第三方渠道注销
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param @param entity
	 * @return void
	 * @date 2014-2-20 下午1:32:10
	 *
	 */
	public void doLogout(CYMGChannelEntity entity);
	
	/**
	 * 
	 * 功能描述：第三方渠道重启
	 *
	 * @author 严峥(yanzheng)
	 * @param @param entity
	 * @return void
	 * @date 2014-2-25 上午9:41:28
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public void doResume(CYMGChannelEntity entity);
	
	/**
	 * 
	 * 功能描述：第三方渠暂停
	 *
	 * @author 严峥(yanzheng)
	 * @param @param entity
	 * @return void
	 * @date 2014-2-25 上午9:41:28
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public void doPause(CYMGChannelEntity entity);
	
	/**
	 * 
	 * 功能描述：第三方渠停止
	 *
	 * @author 严峥(yanzheng)
	 * @param @param entity
	 * @return void
	 * @date 2014-2-25 上午9:41:28
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public void doStop(CYMGChannelEntity entity);
	
	/**
	 * 
	 * 功能描述：第三方渠道销毁
	 *
	 * @author 严峥(yanzheng)
	 * @param @param entity
	 * @return void
	 * @date 2014-2-25 上午9:41:28
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public void doDestroy(CYMGChannelEntity entity);
	
	/**
	 * 
	 * 功能描述：打开渠道客服功能
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param @param entity
	 * @return void
	 * @date 2014-3-25 下午3:21:05
	 *
	 */
	public void doCustomerService(CYMGChannelEntity entity);
	
	/**
	 * 
	 * 功能描述: 显示各个渠道的用户中心
	 * @param @param entity  
	 * @return void    返回类型 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @date 2014-4-28 下午8:16:05
	 */
	public void doShowUserCenter(CYMGChannelEntity entity);
	
	/**
	 * 显示/隐藏悬浮窗
	 * @param entity
	 */
	public void doFloatWindow(CYMGChannelEntity entity);
	
	/**
	 * 渠道通用接口
	 * @param entity
	 */
	public void doExtCommomAPI(CYMGChannelEntity entity);
	
	/**
	 * 获取sdk版本号
	 */
	public String getSdkVersion();
}
