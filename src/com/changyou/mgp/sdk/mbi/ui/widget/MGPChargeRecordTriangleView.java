package com.changyou.mgp.sdk.mbi.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.TextView;

public class MGPChargeRecordTriangleView extends TextView {

	private static final int VIEW_WIDTH = 10;//View默认宽度
	private static final int VIEW_HEIGHT = 23;//View默认高度
	private static final String DEFAULT_BG_COLOR = "#FFFFFF";
	
	public MGPChargeRecordTriangleView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public MGPChargeRecordTriangleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public MGPChargeRecordTriangleView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}


	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.parseColor(DEFAULT_BG_COLOR));
		Path p = new Path();
		p.moveTo(0, VIEW_HEIGHT/2);
		p.lineTo(VIEW_WIDTH, VIEW_HEIGHT);
		p.lineTo(VIEW_WIDTH, 0);
		p.close();
		canvas.drawPath(p, paint);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(resolveSize(VIEW_WIDTH, widthMeasureSpec), resolveSize(VIEW_HEIGHT, heightMeasureSpec));
	}
}
