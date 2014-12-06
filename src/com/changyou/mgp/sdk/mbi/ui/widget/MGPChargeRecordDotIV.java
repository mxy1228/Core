package com.changyou.mgp.sdk.mbi.ui.widget;

import com.changyou.mgp.sdk.mbi.R;
import com.changyou.mgp.sdk.mbi.pay.id.PayResource;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MGPChargeRecordDotIV extends View {
	
//	private static final int DEFAULT_BIG_CIRCLE_RADIUS = 12;//底层圆圈默认半径
//	private static final int DEFAULT_SMALL_CIRCLE_RADIUS = 8;//上层圆圈默认半径
//	private static final int VIEW_SIZE = 24;//整个View默认尺寸
	private static final String DEFAULT_BIT_CIRCLE_COLOR = "#FFFFFF";
	private static final String DEFAULT_SMALL_CIRCLE_COLOR = "#E83060";
	
	private Context mContext;
	private Paint mBigPaint;
	private Paint mSmallPaint;
	private String mBigCircleColorRGB;//底层大圆颜色
	private String mSmallCircleColorRGB;//上层小圆颜色
	private int mBigCircleRadius;//底层大圆半径
	private int mSmallCircleRadius;//上层小圆半径
	private int mSize;

	public MGPChargeRecordDotIV(Context context) {
		super(context);
		this.mContext = context;
		init();
	}
	
	public MGPChargeRecordDotIV(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		init();
	}
	
	public MGPChargeRecordDotIV(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		init();
	}
	
	private void init(){
		mBigPaint = new Paint();
		mBigPaint.setColor(mBigCircleColorRGB == null ? Color.parseColor(DEFAULT_BIT_CIRCLE_COLOR) : Color.parseColor(mBigCircleColorRGB));
		mBigPaint.setStyle(Paint.Style.FILL);
		mBigPaint.setAntiAlias(true);//打开抗锯齿
		mSmallPaint = new Paint();
		mSmallPaint.setColor(mSmallCircleColorRGB == null ? Color.parseColor(DEFAULT_SMALL_CIRCLE_COLOR) : Color.parseColor(mSmallCircleColorRGB));
		mSmallPaint.setStyle(Paint.Style.FILL);
		mSmallPaint.setAntiAlias(true);
		mBigCircleRadius = mContext.getResources().getDimensionPixelSize(PayResource.getInstance(mContext).mgp_order_item_state_dot_big_radius);
		mSmallCircleRadius = mContext.getResources().getDimensionPixelSize(PayResource.getInstance(mContext).mgp_order_item_state_dot_small_radius);
		mSize = mContext.getResources().getDimensionPixelSize(PayResource.getInstance(mContext).mgp_order_item_state_dot_size);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawCircle(mSize/2, mSize/2, mBigCircleRadius, mBigPaint);
		canvas.drawCircle(mSize/2, mSize/2, mSmallCircleRadius, mSmallPaint);
	}
	
	/**
	 * 
	 * 功能描述：获取园半径
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param @return
	 * @return int
	 * @date 2014-3-9 下午3:11:35
	 *
	 */
	public int getCircleRadius(){
		return mBigCircleRadius;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(resolveSize(mSize, widthMeasureSpec), resolveSize(mSize, widthMeasureSpec));
	}

	/**
	 * 
	 * 功能描述：设置底层大圆的颜色
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param colorRGB
	 * @return void
	 * @date 2014-2-17 下午3:36:09
	 *
	 */
	public void setBigCircleColor(String colorRGB){
		this.mBigCircleColorRGB = colorRGB;
		init();
		this.invalidate();
	}
	
	/**
	 * 
	 * 功能描述：设置上层小圆的颜色
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param colorRGB
	 * @return void
	 * @date 2014-2-17 下午3:36:58
	 *
	 */
	public void setSmallCircleColor(String colorRGB){
		this.mSmallCircleColorRGB = colorRGB;
		init();
		this.invalidate();
	}
}
