package com.changyou.mgp.sdk.mbi.pay.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.changyou.mgp.sdk.mbi.config.Contants;
import com.changyou.mgp.sdk.mbi.config.HttpContants;
import com.changyou.mgp.sdk.mbi.config.Params;
import com.changyou.mgp.sdk.mbi.entity.GoodsItem;
import com.changyou.mgp.sdk.mbi.log.CYLog;
import com.changyou.mgp.sdk.mbi.pay.id.PayResource;
import com.changyou.mgp.sdk.mbi.platform.CYMGPayHelper;
import com.changyou.mgp.sdk.mbi.ui.base.BaseFragment;
import com.changyou.mgp.sdk.mbi.ui.widget.NetErrorView;
import com.changyou.mgp.sdk.mbi.ui.widget.WaitingDialog;

public class CYMGMo9PayFragment extends BaseFragment implements OnClickListener{

	private CYLog log = CYLog.getInstance();
	private Activity mActivity;
	
	private WebView mWebView;
	private WaitingDialog mWaitingDialog;
	private NetErrorView mNetErrorView;
	private Button mRetryBtn;
	private View mTitle;
	private ImageButton mServiceImgBtn;
	private TextView mTitleTv;
	private ImageButton mBackIBtn;
	private Button mServiceBtn;
	
	private Bundle mBundle;
	
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
	}
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(PayResource.getInstance(mActivity).mgp_payment_mo9_wrap, container,false);
		initView(view);
		initTitle(view);
		initData();
		initEvent();
		return view;
	}

	private void initView(View view){
		this.mWebView = (WebView)view.findViewById(PayResource.getInstance(mActivity).mgp_payment_mo9_WebView);
		this.mWebView.setVerticalScrollBarEnabled(false);
		this.mWebView.getSettings().setSupportZoom(false);
		this.mWebView.getSettings().setSaveFormData(false);
		this.mWebView.getSettings().setSavePassword(false);
		this.mWebView.getSettings().setJavaScriptEnabled(true);
		this.mWebView.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				
			}
			
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				log.e("error is "+description);
				mWaitingDialog.dismiss();
				if(!mNetErrorView.isShown()){
					mNetErrorView.setVisibility(View.VISIBLE);
					mWebView.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				log.d("onPageFinished:url"+url);
				mWaitingDialog.dismiss();
			}
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if(HttpContants.getMo9ReturnUrl().equals(url)){
					paySuccess(mBundle);
				}else{
					mWaitingDialog.show();
				}
				return super.shouldOverrideUrlLoading(view, url);
			}
		});
		this.mWaitingDialog = new WaitingDialog(mActivity);
		this.mNetErrorView = (NetErrorView)view.findViewById(PayResource.getInstance(mActivity).mgp_payment_mo9_NetErrorView);
	}
	
	private void initTitle(View view){
		this.mTitle = view.findViewById(PayResource.getInstance(mActivity).mgp_payment_mo9_title);
		this.mBackIBtn = (ImageButton)mTitle.findViewById(PayResource.getInstance(mActivity).mgp_title_left_ibtn);
		this.mServiceBtn = (Button)mTitle.findViewById(PayResource.getInstance(mActivity).mgp_title_right_btn);
		this.mServiceBtn.setVisibility(View.GONE);
		this.mServiceImgBtn = (ImageButton)mTitle.findViewById(PayResource.getInstance(mActivity).mgp_title_right_imgbtn);
		this.mServiceImgBtn.setVisibility(View.GONE);
		this.mTitleTv = (TextView)mTitle.findViewById(PayResource.getInstance(mActivity).mgp_title_tv);
		this.mTitleTv.setText(PayResource.getInstance(mActivity).mgp_title_tv_payment_way);
		this.mRetryBtn = mNetErrorView.getRefreshBtn();
	}
	
	@Override
	protected void initData() {
		mWaitingDialog.show();
		this.mBundle=getArguments();
		String url=mBundle.getString("mo9_url");
		if(!TextUtils.isEmpty(url)){
			this.mWebView.loadUrl(url);
		}
	}

	@Override
	protected void initEvent() {
		this.mBackIBtn.setOnClickListener(this);
		this.mRetryBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == mNetErrorView.getRefreshBtnId()){
			if(mNetErrorView.isShown()){
				mNetErrorView.setVisibility(View.GONE);
				mWebView.setVisibility(View.VISIBLE);
			}
		}else if(v.getId() == PayResource.getInstance(mActivity).mgp_title_left_ibtn){
			((CYMGPaymentActivity)mActivity).goback();
		}
	}
	
	private void paySuccess(final Bundle data){
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			
			@Override
			public void run() {
				GoodsItem goodsItem = (GoodsItem)data.getSerializable(Params.GOODSITEM);
				CYMGPayHelper.getInstance().paySuccess(data.getString(Params.UID),goodsItem, data.getString(Params.ORDER_ID),mActivity);
				Bundle b = new Bundle();
				b.putInt("type", CYMGPaymentSuccessFragment.CARD_SUCCESS);
				((CYMGPaymentActivity)mActivity).changeFragment(Contants.FragmentTag.PAYMENT_SUCCESS_FRAGMENT_TAG, b);
			}
		});
	}
}
