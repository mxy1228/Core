package com.changyou.mgp.sdk.mbi.pay.handler;

import com.changyou.mgp.sdk.mbi.entity.OrderList;

public interface IOrderHandler {

	/**
	 * 开始请求订单列表
	 */
	public void showWaitingDialog();

	/**
	 * 成功请求订单列表
	 * @param list
	 */
	public void onSuccessRequestOrderList(OrderList list,boolean refresh);

	/**
	 * 请求订单列表失败
	 * @param statusCode
	 * @param content
	 */
	public void onFailedRequestOrderList(int statusCode,String content,boolean refresh);
	
	/**
	 * 显示空数据页面
	 */
	public void showEmptyView(int size);
}
