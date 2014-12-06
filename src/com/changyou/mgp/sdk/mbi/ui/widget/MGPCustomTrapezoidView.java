package com.changyou.mgp.sdk.mbi.ui.widget;

import com.changyou.mgp.sdk.mbi.pay.id.PayResource;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * 功能描述: 自定义背景为梯形的View
 * @author 欧阳海冰(OuyangHaibing) 
 * @date 2014-2-19 下午2:53:36 
 *
 */
public class MGPCustomTrapezoidView extends View{
	
	/**View默认宽度*/
	private int mWidth;
//	private static final int VIEW_WIDTH = 250;
	/**View默认高度*/
	private int mHeight;
//	private static final int VIEW_HEIGHT = 50;
	/**画梯形时起始点偏离x原点的位置*/
//	private static final int START_X = VIEW_HEIGHT;
	/**用于画梯形背景的画笔*/
	private Context mContext;
	/**用于画梯形背景的画笔*/
	private Paint mPaint;
	/**用于画文字的画笔*/
	private Paint mTextPaint;
	/**梯形背景的颜色*/
	private String mBgColor;
	/**梯形默认背景颜色值*/
	private static final String DEFAULT_BG_COLOR = "#F08030";
	/**文字*/
	private String mText;
	
	public MGPCustomTrapezoidView(Context context) {
		super(context);
		this.mContext = context;
		initPaint();
	}
	public MGPCustomTrapezoidView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		initPaint();
	}
	
	public MGPCustomTrapezoidView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		initPaint();
	}
	
	/**
	 * 功能描述: 对外公开的设置梯形背景颜色的方法
	 * @author 欧阳海冰(OuyangHaibing)
	 * @param @param mBgColor 
	 * @return void
	 * @throws
	 */
	public void setBgColor(String mBgColor) {
		this.mBgColor = mBgColor;
		initPaint();
		//刷新
		this.invalidate();
	}
	
	/**
	 * 功能描述: 对外公开的设置文字的方法
	 * @author 欧阳海冰(OuyangHaibing)
	 * @param @param mText 
	 * @return void
	 * @throws
	 */
	public void setText(String mText) {
		if (mText!= null && !"".equals(mText)) {
			this.mText = mText;
			initPaint();
			this.invalidate();
		}
	}
	/**
	 * 功能描述: 初始化画笔及设置相关属性
	 * @author 欧阳海冰(OuyangHaibing)
	 * @param  
	 * @return void
	 * @throws
	 */
	private void initPaint() {
		mWidth = mContext.getResources().getDimensionPixelSize(PayResource.getInstance(mContext).mgp_payment_order_state_width);
		mHeight = mContext.getResources().getDimensionPixelSize(PayResource.getInstance(mContext).mgp_payment_order_state_height);
		mPaint = new Paint();
		mPaint.setColor(mBgColor==null?Color.parseColor(DEFAULT_BG_COLOR):Color.parseColor(mBgColor));
		//置paint的 style 为FILL：实心
		mPaint.setStyle(Paint.Style.FILL);
		//去锯齿
		mPaint.setAntiAlias(true);
		
		if (mText!= null && !"".equals(mText)) {
			mTextPaint = new Paint();
			mTextPaint.setColor(Color.WHITE);
			//设置常规字体类型
			mTextPaint.setTypeface(Typeface.DEFAULT);
			//设置字体大小
			mTextPaint.setTextSize(mContext.getResources().getDimensionPixelSize(PayResource.getInstance(mContext).mgp_text_small));
		}
		
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(resolveSize(mWidth, widthMeasureSpec), resolveSize(mHeight, widthMeasureSpec));
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Path p = new Path();
		p.moveTo(mHeight, 0);
		p.lineTo(mWidth, 0);
		p.lineTo(mWidth,mHeight);
		p.lineTo(0,mHeight);
		p.close();
		canvas.drawPath(p, mPaint);
		
		//在mText不为空或空串的情况下才去画文字
		if (mTextPaint != null && mText!=null && !"".equals(mText)) {
			canvas.drawText(mText, mHeight + 35, mHeight * 3/4, mTextPaint);
		}
	}

}
