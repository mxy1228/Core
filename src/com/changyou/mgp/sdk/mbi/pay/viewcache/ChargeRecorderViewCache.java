package com.changyou.mgp.sdk.mbi.pay.viewcache;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.changyou.mgp.sdk.mbi.pay.id.PayResource;
import com.changyou.mgp.sdk.mbi.ui.widget.MGPChargeRecordDotIV;
import com.changyou.mgp.sdk.mbi.ui.widget.MGPCustomTrapezoidView;

public class ChargeRecorderViewCache {
	
	private View mView;
	private TextView mTimeTV;
	private TextView mDateTV;
	private MGPCustomTrapezoidView mStateView;
	private MGPChargeRecordDotIV mStateDotView;
	private TextView mIDTV;
	private TextView mNameTV;
	private TextView mPriceTV;
	private ImageView mTriangleIV;
	private Context mContext;
	
	public ChargeRecorderViewCache(View view,Context context){
		this.mView = view;
	}
	
	public TextView getmTimeTV() {
		if(mTimeTV == null){
			mTimeTV = (TextView)mView.findViewById(PayResource.getInstance(mContext).mgp_charge_recorder_item_time_tv);
		}
		return mTimeTV;
	}
	public TextView getmDateTV() {
		if(mDateTV == null){
			mDateTV = (TextView)mView.findViewById(PayResource.getInstance(mContext).mgp_charge_recorder_item_date_tv);
		}
		return mDateTV;
	}
	public TextView getmIDTV() {
		if(mIDTV == null){
			mIDTV = (TextView)mView.findViewById(PayResource.getInstance(mContext).mgp_charge_recorder_item_id_tv2);
		}
		return mIDTV;
	}
	public TextView getmNameTV() {
		if(mNameTV == null){
			mNameTV = (TextView)mView.findViewById(PayResource.getInstance(mContext).mgp_charge_recorder_item_name_tv);
		}
		return mNameTV;
	}
	public TextView getmPriceTV() {
		if(mPriceTV == null){
			mPriceTV = (TextView)mView.findViewById(PayResource.getInstance(mContext).mgp_charge_recorder_item_price_tv);
		}
		return mPriceTV;
	}

	public MGPCustomTrapezoidView getmStateView() {
		if(mStateView == null){
			mStateView = (MGPCustomTrapezoidView)mView.findViewById(PayResource.getInstance(mContext).mgp_charge_recorder_state_view);
		}
		return mStateView;
	}

	public MGPChargeRecordDotIV getmStateDotView() {
		if(mStateDotView == null){
			mStateDotView = (MGPChargeRecordDotIV)mView.findViewById(PayResource.getInstance(mContext).mgp_charge_recorder_item_state_dot_view);
		}
		return mStateDotView;
	}

	public ImageView getmTriangleIV() {
		if(mTriangleIV == null){
			this.mTriangleIV = (ImageView)mView.findViewById(PayResource.getInstance(mContext).mgp_charge_recorder_item_triangle_iv);
		}
		return mTriangleIV;
	}

	public void setmTriangleIV(ImageView mTriangleIV) {
		this.mTriangleIV = mTriangleIV;
	}
	
}
