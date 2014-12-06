package com.changyou.mgp.sdk.mbi.account.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.changyou.mgp.sdk.mbi.account.id.AccResource;

public class CYMGLoginToast {

	private static Toast mToast;
	private static View mView;
	private static TextView mAccountTv;

	private CYMGLoginToast() {
	}

	private static void getToast(Context context) {
		if (mToast == null) {
			mToast = new Toast(context);
		}
		if (mView == null) {
			mView = LayoutInflater.from(context).inflate(AccResource.getInstance(context).mgp_sdk_2_0_dialog_login_toast, null);
			mAccountTv = (TextView) mView.findViewById(AccResource.getInstance(context).mgp_sdk_2_0_dialog_login_toast_account_TextView);
		}
		mToast.setView(mView);
	}

	public static void showLoginToast(final Context context, final String account) {
		try {
			new Handler(Looper.getMainLooper()).post(new Runnable() {
				@Override
				public void run() {
					getToast(context.getApplicationContext());
					if (!TextUtils.isEmpty(account)) {
						mAccountTv.setText(account);
					}
					mToast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 30);
					mToast.setDuration(Toast.LENGTH_SHORT);
					mToast.show();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
