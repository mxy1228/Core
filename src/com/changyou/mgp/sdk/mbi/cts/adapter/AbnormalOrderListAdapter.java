package com.changyou.mgp.sdk.mbi.cts.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.changyou.mgp.sdk.mbi.config.Contants;
import com.changyou.mgp.sdk.mbi.cts.id.CtsResource;
import com.changyou.mgp.sdk.mbi.cts.viewcache.AbnormalOrderListViewCache;
import com.changyou.mgp.sdk.mbi.entity.OrderItem;
/**
 * 
 * 功能描述：反馈页面中异常订单列表适配器
 *
 * @author 严峥(yanzheng)
 * @date 2014-2-18 下午2:03:23
 * 修改历史：(修改人，修改时间，修改原因/内容)
 */
public class AbnormalOrderListAdapter extends BaseAdapter {
	private Context mContext;
	private List<OrderItem> mList;
	private CtsResource mResource;
	
	public List<OrderItem> getmList() {
		return mList;
	}

	public AbnormalOrderListAdapter(Context contetx,List<OrderItem> list){
		this.mContext=contetx;
		this.mList=list;
		mResource=CtsResource.getInstance(mContext);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AbnormalOrderListViewCache holder = null;
		if(convertView==null){
			holder = new AbnormalOrderListViewCache();
			convertView=LayoutInflater.from(mContext).inflate(mResource.mgp_onlineserver_custom_dialog_list_item, null);
			holder.setView(convertView);
			convertView.setTag(holder); 
		}else{
			holder=(AbnormalOrderListViewCache) convertView.getTag();
		}
		OrderItem mOrderItem=this.mList.get(position);
		//订单创建时间
		holder.getDateTV().setText(mOrderItem.getCreate_date());
		//订单商品价格
		holder.getAmountTV().setText(String.valueOf(mOrderItem.getGoods_price()));
		//根据订单状态码，显示相应订单状态
		switch (mOrderItem.getOrder_status()) {
		case 1:
		case 4:
		case 5:
		case 6:
			holder.getStateTV().setText(mOrderItem.getOrder_status_msg());
			break;
		}
		return convertView;
	}

}
