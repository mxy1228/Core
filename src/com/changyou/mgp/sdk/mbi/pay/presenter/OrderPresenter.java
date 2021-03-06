package com.changyou.mgp.sdk.mbi.pay.presenter;

import android.content.Context;

import com.changyou.mgp.sdk.mbi.entity.OrderList;
import com.changyou.mgp.sdk.mbi.http.CYMGHttpRequest;
import com.changyou.mgp.sdk.mbi.http.CYMGHttpRequest.OnRequestListener;
import com.changyou.mgp.sdk.mbi.pay.handler.IOrderHandler;

public class OrderPresenter {

	private IOrderHandler mHandler;
	
	public OrderPresenter(IOrderHandler handler){
		this.mHandler = handler;
	}
	
	/**
	 * 请求订单列表
	 * @param ctx
	 * @param uid
	 * @param pageNo
	 */
	public void requestOrderList(Context ctx,String uid,final int pageNo,final boolean refresh){
		new CYMGHttpRequest(ctx).getOrderList(ctx, uid, pageNo+"", new OnRequestListener<OrderList>() {
			
			@Override
			public void onSuccess(int statusCode, OrderList result) {
				if(result.getData().isEmpty() && pageNo == 1){
					//用户还没有充值记录，这时通知页面显示空数据项
					mHandler.showEmptyView(result.getData().size());
				}else{
					mHandler.onSuccessRequestOrderList(result,refresh);
				}
			}
			
			@Override
			public void onStart() {
				mHandler.showWaitingDialog();
			}
			
			@Override
			public void onFailed(int statusCode, String content) {
				mHandler.onFailedRequestOrderList(statusCode, content,refresh);
			}
		});
	}
}
