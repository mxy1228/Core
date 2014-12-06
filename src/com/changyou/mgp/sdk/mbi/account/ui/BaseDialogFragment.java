package com.changyou.mgp.sdk.mbi.account.ui;

import android.support.v4.app.DialogFragment;
import android.view.View;

public abstract class BaseDialogFragment extends DialogFragment {
	
	protected abstract void initView(View view);

	protected abstract void initData();
	
	protected abstract void initEvent();

}
