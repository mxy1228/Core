package com.changyou.mgp.sdk.mbi.pay.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.changyou.mgp.sdk.mbi.entity.RechargeCenterItem;
import com.changyou.mgp.sdk.mbi.pay.id.PayResource;

/**
 * 
 * 功能描述: 充值中心的列表适配器
 * @author 欧阳海冰(OuyangHaibing)
 * @date 2014-5-5 上午11:43:44
 */
public class RechargeCenterAdapter extends BaseAdapter {
	
	
	private Context mContext;
	private PayResource mPayResource;
	private List<RechargeCenterItem> mList;
	private LayoutInflater mInflater;
	
	public RechargeCenterAdapter(Context context,List<RechargeCenterItem> list) {
		this.mContext = context;
		this.mPayResource = PayResource.getInstance(mContext);
		this.mList = list;
		this.mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(mPayResource.mgp_pament_recharge_center_listitem, null);
			holder = new ViewHolder();
			holder.charge_item_iv = (ImageView) convertView.findViewById(mPayResource.mgp_charge_item_iv);
			holder.charge_item_type_name_tv = (TextView) convertView.findViewById(mPayResource.mgp_charge_item_type_name);
			holder.charge_item_type_info_tv = (TextView) convertView.findViewById(mPayResource.mgp_charge_item_type_info);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		//获取某个item的数据
		RechargeCenterItem item = mList.get(position);
		
		//绑定控件
		holder.charge_item_iv.setBackgroundResource(item.getImageId());
		holder.charge_item_type_name_tv.setText(item.getChagrgeType());
		holder.charge_item_type_info_tv.setText(item.getChargeInfo());
		
		return convertView;
	}
	
	static class ViewHolder{
		ImageView charge_item_iv;
		TextView charge_item_type_name_tv;
		TextView charge_item_type_info_tv;
	}
	

}
