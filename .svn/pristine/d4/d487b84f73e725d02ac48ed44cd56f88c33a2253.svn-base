package com.changyou.mgp.sdk.mbi.pay.adapter;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.changyou.mgp.sdk.mbi.log.CYLog;
import com.changyou.mgp.sdk.mbi.pay.id.PayResource;

public class PaymentAdapter extends BaseAdapter {

	private CYLog log = CYLog.getInstance();
	private PayResource mPayResource;
	
	private List<Integer> mPics = null;
	
	private Context mContext;
	private LayoutInflater mInflater;
	private int colnum = 3;
	public PaymentAdapter(Context context,List<Integer> picArr){
		this.mContext = context;
		this.mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mPayResource=PayResource.getInstance(mContext);
		this.mPics = picArr;

	}
	@Override
	public int getCount() {
		return mPics.size();
	}

	@Override
	public Object getItem(int position) {
		return mPics.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(mPayResource.mgp_sdk_2_0_pay_cashier_payway_item, null);
			holder = new ViewHolder();
			holder.pay_way_iv = (ImageView) convertView.findViewById(mPayResource.mgp_pay_cashier_payway_iv);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.pay_way_iv.setBackgroundResource(mPics.get(position));
//		int pHight = parent.getHeight();
//		int pWidth = parent.getWidth();
//		GridView.LayoutParams params = new GridView.LayoutParams(
//		LayoutParams.MATCH_PARENT, (pHight) / 3);
//		        convertView.setLayoutParams(params);
		
	// Calculate the item width by the column number to let total width fill the screen width
      // 根据列数计算项目宽度，以使总宽度尽量填充屏幕
      // Calculate the height by your scale rate, I just use itemWidth here
      // 下面根据比例计算您的item的高度，此处只是使用itemWidth
//      int itemHeight = parent.getHeight()/3 -20;
//      int itemWidth = itemHeight;
//      log.d("//////////////itemWidth:"+itemWidth);
//      AbsListView.LayoutParams param = new AbsListView.LayoutParams(
//              itemWidth,
//              itemHeight);
//      convertView.setLayoutParams(param);
		
		return convertView;
	}
	
	
	static class ViewHolder{
		ImageView pay_way_iv;
	}

}
