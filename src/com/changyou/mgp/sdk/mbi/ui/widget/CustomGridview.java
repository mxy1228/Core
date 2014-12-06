package com.changyou.mgp.sdk.mbi.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.GridView;

public class CustomGridview extends GridView {
	
	boolean expanded = false;
	
	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public CustomGridview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomGridview(Context context) {
		super(context);
	}

	public CustomGridview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	// 该自定义控件只是重写了GridView的onMeasure方法，使其不会出现滚动条，ScrollView嵌套ListView也是同样的道理，不再赘述。
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
//				MeasureSpec.AT_MOST);
//		super.onMeasure(widthMeasureSpec, expandSpec);
		if (isExpanded()){
	        // Calculate entire height by providing a very large height hint.
	        // View.MEASURED_SIZE_MASK represents the largest height possible.
	        int expandSpec = MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK,
	                MeasureSpec.AT_MOST);
	        super.onMeasure(widthMeasureSpec, expandSpec);

	        ViewGroup.LayoutParams params = getLayoutParams();
	        params.height = getMeasuredHeight();
	    }
	    else{
	        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	    }
	}
	
}
