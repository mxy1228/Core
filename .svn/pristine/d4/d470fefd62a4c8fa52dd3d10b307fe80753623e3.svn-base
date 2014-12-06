package com.changyou.mgp.sdk.mbi.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class KeyboardShowHideListenerLayout extends LinearLayout {
	public KeyboardShowHideListenerLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public KeyboardShowHideListenerLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public interface KeyboardListener {
		public void onSoftKeyboardShown(boolean isShowing);
	}

	private KeyboardListener listener;

	public void setKeyboardListener(KeyboardListener listener) {
		this.listener = listener;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int height = MeasureSpec.getSize(heightMeasureSpec);// 得到布局的高度
		Activity activity = (Activity) getContext();
		Rect rect = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);// 得到窗体矩形
		int diff = (height - rect.bottom);// 布局高度 减去 窗体矩形的底线 之差
		if (listener != null) {
			listener.onSoftKeyboardShown(diff > 128); // 目前所有软键盘最低的高度为128，之差如果大于128，证明底部弹出软键盘
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
