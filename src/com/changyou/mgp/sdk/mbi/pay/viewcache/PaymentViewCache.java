package com.changyou.mgp.sdk.mbi.pay.viewcache;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.changyou.mgp.sdk.mbi.pay.id.PayResource;

public class PaymentViewCache {

	private View mView;
	private TextView mTitleTV;
	private ImageView mLogoIV;
	private TextView mNameTV;
	private TextView mDescribeTV;
	private LinearLayout mTitleLL;
	private Context mContext;
	
	public PaymentViewCache(View view,Context context){
		this.mView = view;
		this.mContext = context;
	}


	public ImageView getmLogoIV() {
		if(mLogoIV == null){
			mLogoIV = (ImageView)mView.findViewById(PayResource.getInstance(mContext).mgp_payment_item_way_logo_iv);
		}
		return mLogoIV;
	}

	public TextView getmNameTV() {
		if(mNameTV == null){
			mNameTV = (TextView)mView.findViewById(PayResource.getInstance(mContext).mgp_payment_item_way_name_tv);
		}
		return mNameTV;
	}
	
	public TextView getmDescribeTV() {
		if(mDescribeTV == null){
			mDescribeTV = (TextView)mView.findViewById(PayResource.getInstance(mContext).mgp_payment_item_way_describe_tv);
		}
		return mDescribeTV;
	}

	
}
