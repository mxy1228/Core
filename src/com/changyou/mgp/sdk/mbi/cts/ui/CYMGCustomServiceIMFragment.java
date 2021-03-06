package com.changyou.mgp.sdk.mbi.cts.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.changyou.mgp.sdk.mbi.config.HttpContants;
import com.changyou.mgp.sdk.mbi.config.SDKConfig;
import com.changyou.mgp.sdk.mbi.cts.id.CtsResource;
import com.changyou.mgp.sdk.mbi.cts.ui.CYMGCustomerServiceActivity.CtsBackPressEvent;
import com.changyou.mgp.sdk.mbi.http.MyAsyncResponseHandler;
import com.changyou.mgp.sdk.mbi.log.CYLog;
import com.changyou.mgp.sdk.mbi.ui.base.BaseFragment;
import com.changyou.mgp.sdk.mbi.ui.widget.NetErrorView;
import com.changyou.mgp.sdk.mbi.ui.widget.WaitingDialog;
import com.changyou.mgp.sdk.mbi.ui.widget.WaitingDialog.MyDialogDismissListener;
import com.loopj.android.http.AsyncHttpClient;

import de.greenrobot.event.EventBus;

public class CYMGCustomServiceIMFragment extends BaseFragment implements OnClickListener{

	CYLog log = CYLog.getInstance();
	private static final int SHOW_NET_ERROR_VIEW = 1000;
	private static final int SHOW_NORMAL_VIEW = 1001;
	private static final int SHOW_EMPTY_VIEW = 1002;
	
	private Activity mContext;
	private FragmentManager mFragmentManager;
	private WebView mWebView;
	private View mTitle;
	private ImageButton mBackIBtn;
	private TextView mTitleTv;
	private Button mqueryBtn;
	private WaitingDialog mWaitingDialog;
	private NetErrorView mNetErrorView;
	private Button mRefreshBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		log.d("CYMGCustomServiceIMFragment,onCreate");
	 	this.mFragmentManager = getFragmentManager();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		log.d("CYMGCustomServiceIMFragment,onAttach");
		this.mContext = activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(CtsResource.getInstance(mContext).mgp_onlineserver_feedback_im,container, false);
		initPage(view);
		initWaitingDialog();
		initWebView(view);
		initData();
		initEvent();
		return view;
	}
	private void initWaitingDialog(){
		this.mWaitingDialog = new WaitingDialog(mContext);
		this.mWaitingDialog.setMessage(mContext.getString(CtsResource.getInstance(mContext).mgp_loading));
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	private void initWebView(View view) {
		this.mWebView = (WebView) view.findViewById(CtsResource.getInstance(mContext).mgp_onlineserver_feedback_im_webview);
		this.mWebView.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				log.d("onPageStarted="+url);
				mWaitingDialog.setDismissListener(SDKConfig.TIME_OUT, new MyDialogDismissListener() {
					
					@Override
					public void onTimeOutDismiss() {
						showNetErrorView();
					}

				});
				mWaitingDialog.show();
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				log.d("onPageFinished="+url);
				mWaitingDialog.dismiss();
			}
			
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				log.e("请求失败-->"+description);
				mWaitingDialog.dismiss();
			}
			
			@Override
			public void onFormResubmission(WebView view, Message dontResend,
					Message resend) {
				resend.sendToTarget();
			}
		});
		
		this.mWebView.setWebChromeClient(new WebChromeClient() {
			public boolean onJsAlert(WebView view, String url, String message,
					final JsResult result) {
				AlertDialog.Builder b = new AlertDialog.Builder(mContext);
				b.setTitle("提示");
				b.setIcon(android.R.drawable.ic_dialog_info);
				b.setMessage(message);
				b.setPositiveButton(android.R.string.ok,
						new AlertDialog.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								result.confirm();
								//关闭当前页面
								goBack();
							}
						});
				b.setCancelable(false);
				b.create();
				b.show();
				return true;
			};
		});
		//JS支持
		this.mWebView.getSettings().setJavaScriptEnabled(true);
		this.mWebView.getSettings().setLoadsImagesAutomatically(true);
	}

	private void initPage(View view) {
		this.mTitle = view.findViewById(CtsResource.getInstance(mContext).mgp_onlineserver_feedback_im_title);
		this.mBackIBtn = (ImageButton) mTitle.findViewById(CtsResource.getInstance(mContext).mgp_title_left_ibtn);
		this.mTitleTv = (TextView) mTitle.findViewById(CtsResource.getInstance(mContext).mgp_title_tv);
		this.mqueryBtn = (Button) mTitle.findViewById(CtsResource.getInstance(mContext).mgp_title_right_btn);
		this.mqueryBtn.setVisibility(View.GONE);
		this.mTitleTv.setText(CtsResource.getInstance(mContext).mgp_title_tv_onlineserver_im);
		this.mNetErrorView = (NetErrorView) view.findViewById(CtsResource.getInstance(mContext).mgp_onlineserver_feedback_im_error_ll);
		mNetErrorView.setVisibility(View.GONE);
		this.mRefreshBtn = mNetErrorView.getRefreshBtn();
	}

	@Override
	protected void initData() {
		this.mWebView.clearCache(true);
		this.mWebView.clearHistory();
		this.mWebView.loadUrl(HttpContants.CTS_IM_URL);
		EventBus.getDefault().register(this);
	}
	
	private void showNetErrorView() {
		this.mNetErrorView.setVisibility(View.VISIBLE);
	}
	
	private void goBack() {
		if (mWebView.canGoBack()) {
			mWebView.goBack();
			log.d("--------->>CYMGCustomServiceIMFragment#WebView.goBack");
		}else {
			this.mFragmentManager.popBackStack();
		}
	}

	@Override
	protected void initEvent() {
		this.mBackIBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		if (view == mBackIBtn) {
			if (!isExitDialogShown) {
				showExitDialog();
			}
		}else if (view == mRefreshBtn) {
			this.mWebView.loadUrl(HttpContants.CTS_IM_URL);
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		log.d("CYMGCustomerServiceIMFragment,onDestroy");
		CYMGCustomerServiceActivity.removeFragment(this);
		mWaitingDialog.destory();
		mWebView.destroy();
		isExitDialogShown = false;
	}
	
	public void onEventMainThread(CtsBackPressEvent event){
		//接收到CYMGCustomerServiceActivity的Back键按下事件
		log.d("get CtsBackPressEvent");
		if (!isExitDialogShown) {
			showExitDialog();
		}
	}
	
	static boolean isExitDialogShown = false;
	private synchronized void showExitDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle("提示");
		builder.setMessage("您确定真的需要结束当前会话吗？");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				disConnection();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				isExitDialogShown = false;
			}
		});
		builder.show();
		isExitDialogShown = true;
	}

	/**
	 * 
	 * @Title: disConnection 
	 * @Description: 断开与服务器的会话连接 
	 * @param  
	 * @return void
	 * @throws
	 */
	protected void disConnection() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(mContext, HttpContants.CTS_IM_DISCONNECT_URL, new MyAsyncResponseHandler(){
			@Override
			public void onStart() {
				super.onStart();
			}
			
			@Override
			public void onSuccess(int statusCode, String content) {
				log.d("------------------>>:disConnection#onSuccess::content"+content);
				goBack();
			}
			@Override
			public void onFailure(int statusCode, Throwable error,
					String content) {
				log.e("------------------>>:disConnection#onFailure::content:"+content);
			}
		});
	}
	
	

}
