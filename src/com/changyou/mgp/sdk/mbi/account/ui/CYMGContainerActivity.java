package com.changyou.mgp.sdk.mbi.account.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.changyou.mgp.sdk.mbi.account.id.AccResource;
import com.changyou.mgp.sdk.mbi.entity.CYMGContainerBackPressEvent;
import com.changyou.mgp.sdk.mbi.log.CYLog;
import com.changyou.mgp.sdk.mbi.platform.CYMGCaptureHandler;
import com.changyou.mgp.sdk.mbi.platform.CYMGCaptureLogicListener;
import com.changyou.mgp.sdk.mbi.ui.base.BaseActivity;
import com.changyou.mgp.sdk.mbi.ui.widget.CYMGFlowWinManager;
import com.changyou.mgp.sdk.mbi.utils.UserInfoSPUtil;

import de.greenrobot.event.EventBus;

public class CYMGContainerActivity extends BaseActivity{

	//打开个人中心
	public static final int PERSONAL = 1;
	private static final String PERSONAL_TAG = "personal";
	//打开论坛
	public static final int FORUM = 2;
	private static final String FORUM_TAG = "forum";
	//截图
	public static final int CAPTURE = 3;
	private static final String CAPTURE_TAG = "capture";
	//游戏圈
	public static final int GAME_CIRCLE = 4;
	private static final String GAME_CIRCLE_TAG = "game_circle";
	
	private static final String WEB_TAG = "web";
	
	
	private AccResource mRes;
	private int mType;
	private String mToken;
	private CYLog log = CYLog.getInstance();
	private boolean mReceiveError;
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mRes = AccResource.getInstance(this);
		setContentView(mRes.cymg_flow_win_view);
		initData();
		initView();
		initEvent();
	}
	
	@SuppressLint("JavascriptInterface")
	@Override
	protected void initView() {
		
	}
	
	

	@Override
	protected void initData() {
		try {
			mRes = AccResource.getInstance(this);
			Intent intent = getIntent();
			mType = intent.getIntExtra("type", 0);
			mToken = UserInfoSPUtil.getToken(this);
			if(mType == 0){
				log.e("mType = 0");
			}
			final Bundle bundle = new Bundle();
			bundle.putString("token", mToken);
			bundle.putInt("type", mType);
			switch (mType) {
			case CAPTURE:
				String path = intent.getStringExtra("path");
				bundle.putString("capture_path", path);
				Intent iCapture = new Intent(CYMGContainerActivity.this, CYMGFlowWinCaptureActivity.class);
				iCapture.putExtras(bundle);
				startActivity(iCapture);
				CYMGContainerActivity.this.finish();
				break;
			default:
				//打开WebView
				CYMGFlowWinWebFragemnt webF = new CYMGFlowWinWebFragemnt();
				webF.setArguments(bundle);
				getSupportFragmentManager().beginTransaction().replace(mRes.cymg_flow_win_view_container_ll, webF, WEB_TAG).commit();
				break;
			}
		} catch (Exception e) {
			log.e(e);
		}
		
	}

	@Override
	protected void initEvent() {
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			//发送Back键按下Action给CYMGFlowWinWebFragment，然后交由CYMGFlowWinWebFragment判断返回操作
			EventBus.getDefault().post(new CYMGContainerBackPressEvent());
		}
		return true;
	}
	
}
