package com.changyou.mgp.sdk.mbi.ui.widget;

import com.changyou.mgp.sdk.mbi.entity.CaptureIVAttachEvent;

import de.greenrobot.event.EventBus;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CaptureImageView extends ImageView {

	public CaptureImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public CaptureImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public CaptureImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		CaptureIVAttachEvent e = new CaptureIVAttachEvent();
		e.setHeight(getHeight());
		e.setWidth(getWidth());
		EventBus.getDefault().post(e);
	}
}
