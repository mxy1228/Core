package com.changyou.mgp.sdk.mbi.pay.ui;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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

import com.changyou.mgp.sdk.mbi.config.CYMGPrompt;
import com.changyou.mgp.sdk.mbi.config.Contants;
import com.changyou.mgp.sdk.mbi.config.HttpContants;
import com.changyou.mgp.sdk.mbi.config.Params;
import com.changyou.mgp.sdk.mbi.entity.GoodsItem;
import com.changyou.mgp.sdk.mbi.http.MyAsyncResponseHandler;
import com.changyou.mgp.sdk.mbi.http.MyHttpClient;
import com.changyou.mgp.sdk.mbi.log.CYLog;
import com.changyou.mgp.sdk.mbi.pay.alipay.AlipayConfig;
import com.changyou.mgp.sdk.mbi.pay.alipay.MGPAlipay;
import com.changyou.mgp.sdk.mbi.pay.id.PayResource;
import com.changyou.mgp.sdk.mbi.platform.CYMGPayHelper;
import com.changyou.mgp.sdk.mbi.ui.base.BaseFragment;
import com.changyou.mgp.sdk.mbi.ui.widget.NetErrorView;
import com.changyou.mgp.sdk.mbi.ui.widget.WaitingDialog;
import com.changyou.mgp.sdk.mbi.utils.MyToast;
import com.changyou.mgp.sdk.mbi.utils.NetWorkUtils;
import com.loopj.android.http.RequestParams;

public class CYMGAlipayWrapFragment extends BaseFragment implements OnClickListener{

	private static final int LOAD_HTML = 1;
	private static final int PAY_SUCCESS = 2;
	
	private CYLog log = CYLog.getInstance();
	private Activity mActivity;
	private GoodsItem mGoodsItem;
	private String mUID;
	private String mOrderID;
	
	private WebView mWebView;
	private WaitingDialog mWaitingDialog;
	private NetErrorView mNetErrorView;
	private Button mRetryBtn;
	private View mTitle;
	private ImageButton mServiceImgBtn;
	private TextView mTitleTv;
	private ImageButton mBackIBtn;
	private Button mServiceBtn;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(PayResource.getInstance(mActivity).mgp_alipay_wrap, container,false);
		this.mWebView = (WebView)view.findViewById(PayResource.getInstance(mActivity).mgp_alipay_wrap_wv);
		this.mWaitingDialog = new WaitingDialog(mActivity);
		this.mNetErrorView = (NetErrorView)view.findViewById(PayResource.getInstance(mActivity).mgp_alipay_wrap_error_ll);
		this.mTitle = view.findViewById(PayResource.getInstance(mActivity).payment_title);
		this.mBackIBtn = (ImageButton)mTitle.findViewById(PayResource.getInstance(mActivity).mgp_title_left_ibtn);
		this.mServiceBtn = (Button)mTitle.findViewById(PayResource.getInstance(mActivity).mgp_title_right_btn);
		this.mServiceBtn.setVisibility(View.GONE);
		this.mServiceImgBtn = (ImageButton)mTitle.findViewById(PayResource.getInstance(mActivity).mgp_title_right_imgbtn);
		this.mServiceImgBtn.setVisibility(View.GONE);
		this.mTitleTv = (TextView)mTitle.findViewById(PayResource.getInstance(mActivity).mgp_title_tv);
		this.mTitleTv.setText(PayResource.getInstance(mActivity).mgp_title_tv_payment_way);
		this.mRetryBtn = mNetErrorView.getRefreshBtn();
		this.mBackIBtn.setOnClickListener(this);
		mRetryBtn.setOnClickListener(this);
		initData();
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
//		mWaitingDialog.destory();
//		mWaitingDialog=null;
	}
	
	@Override
	protected void initData() {
		//不开启对js的支持将无法提交表单
		this.mWebView.getSettings().setJavaScriptEnabled(true);
		this.mWebView.getSettings().setLoadsImagesAutomatically(true);
		mWebView.setWebViewClient(new WebViewClient(){
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
				if(url.startsWith(AlipayConfig.MERCHANT_URL)){
					//用户放弃支付
					CYMGPayHelper.getInstance().payException(CYMGPrompt.CODE_PAY_CANCEL);
//					((CYMGPaymentActivity)mActivity).goback();
					Bundle b = new Bundle();
					b.putInt("type", CYMGPaymentSuccessFragment.FAILED);
					((CYMGPaymentActivity)mActivity).changeFragment(Contants.FragmentTag.PAYMENT_SUCCESS_FRAGMENT_TAG, b);
				}
			}
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				mWaitingDialog.show();
				log.d("shouldOverrideUrlLoading and url="+url);
				if(url.startsWith(new MGPAlipay(mActivity).getParam(Params.GOLOBAL_PARAMS.SDK_ALI_WAP_PAY))){
					saveOrderInfoIntoDB(url);
				}else if(url.startsWith(AlipayConfig.MERCHANT_URL)){
					//用户放弃支付
					CYMGPayHelper.getInstance().payException(CYMGPrompt.CODE_PAY_CANCEL);
//					((CYMGPaymentActivity)mActivity).goback();
					Bundle b = new Bundle();
					b.putInt("type", CYMGPaymentSuccessFragment.FAILED);
					((CYMGPaymentActivity)mActivity).changeFragment(Contants.FragmentTag.PAYMENT_SUCCESS_FRAGMENT_TAG, b);
				}
				return super.shouldOverrideUrlLoading(view, url);
			}
		});
		Bundle b = getArguments();
		if(b == null){
			log.e("Bundle is null");
			return;
		}
		mOrderID = b.getString(Params.ALIPAY_WRAP.TRADE_NO);
		mUID = b.getString(Params.ALIPAY_WRAP.UID);
		if(mUID == null){
			throw new IllegalAccessError("uid is null");
		}
		mGoodsItem = (GoodsItem)b.getSerializable(Params.GOODSITEM);
		if(mOrderID == null){
			log.e("mOrderID is null");
			return;
		}
		if(mGoodsItem == null){
			log.e("mGoodsItem is null");
			return;
		}
		getOrderInfoAndShow();
	}

	@Override
	protected void initEvent() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 
	 * 功能描述：根据mGoodsItem中的信息生成订单信息，并生成表单给WebView显示
	 *
	 * @author 徐萌�?xumengyang)
	 * @param 
	 * @return void
	 * @date 2014-1-23 下午2:38:39
	 *
	 */
	private void getOrderInfoAndShow(){
		if(!checkPayParams(mGoodsItem)){
			return;
		}
		if(NetWorkUtils.isNetworkConnected(mActivity)){
			RequestParams params = new RequestParams();
			params.put("WIDout_trade_no", mOrderID);
			params.put("WIDsubject", mGoodsItem.getGoods_name());
			params.put("WIDtotal_fee", mGoodsItem.getGoods_price());
			new MyHttpClient(mActivity).get(HttpContants.getURL(HttpContants.ALIPAY_WRAP), params, new MyAsyncResponseHandler(){
				@Override
				public void onStart() {
					mWaitingDialog.show();
				}
				
				@Override
				public void onSuccess(int statusCode, String content) {
					mWaitingDialog.dismiss();
					log.d("getAlipay wrap content = "+content);
					Message msg = mHandler.obtainMessage();
					msg.what = LOAD_HTML;
					msg.obj = content;
					mHandler.sendMessage(msg);
				}
				
				@Override
				public void onFailure(Throwable error, String content) {
					mWaitingDialog.dismiss();
					log.e("getAlipay wrap error = "+content);
				}
			});
//			String basicInfo = MGPAlipay.getInstance(mActivity).getBasicWrapOrderInfo(mUID,mGoodsItem.getGoodsName(), mGoodsItem.getGoodsPrice(),mOrderID);
//			log.d("basicInfo = "+basicInfo);
//			final Map<String, String> sParaTempToken = new HashMap<String, String>();
//			sParaTempToken.put("service", AlipayConfig.WRAP_SERVICE);
//			sParaTempToken.put("partner", AlipayConfig.PARTNER_VALUE);
//			sParaTempToken.put("_input_charset", "utf-8");
//			sParaTempToken.put("sec_id", "MD5");
//			sParaTempToken.put("format", "xml");
//			sParaTempToken.put("v", "2.0");
//			sParaTempToken.put("req_id", UtilDate.getOrderNum());
//			sParaTempToken.put("req_data", basicInfo);
//			new AlipayWrapThread(sParaTempToken).start();
		}else{
			if(!mNetErrorView.isShown()){
				mNetErrorView.setVisibility(View.VISIBLE);
				mWebView.setVisibility(View.GONE);
			}
		}
		
	}

	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case LOAD_HTML:
				String html = msg.obj.toString();
				log.d("html="+html);
				mWebView.loadData(html, "text/html", "UTF-8");
				break;
			case PAY_SUCCESS:
				mWaitingDialog.dismiss();
				if(mActivity != null){
//					((CYMGPaymentActivity)mActivity).back2PaymentFragment();;
					Bundle b = new Bundle();
					b.putInt("type", CYMGPaymentSuccessFragment.SUCCESS);
					((CYMGPaymentActivity)mActivity).changeFragment(Contants.FragmentTag.PAYMENT_SUCCESS_FRAGMENT_TAG, b);
					CYMGPayHelper.getInstance().paySuccess(mUID
							, mGoodsItem
							, mOrderID
							,mActivity);
				}else{
					log.e("mActivity is null");
				}
				break;
			default:
				break;
			}
			
		};
	};
	
	/**
	 * 
	 * 功能描述：使用线程加载支付宝wrap。若在UI主线程会报错
	 *
	 * @author 徐萌�?xumengyang)
	 *
	 * @date 2014-1-21
	 */
//	private class AlipayWrapThread extends Thread{
//		private Map<String,String> params = new HashMap<String, String>();
//		
//		public AlipayWrapThread(Map<String,String> p){
//			this.params = p;
//		}
//		
//		@Override
//		public void run() {
//			try {
//				String request = AlipaySubmit.buildRequest(AlipayConfig.ALIPAY_GATEWAY_NEW,"","", params);
//				String decodeRequest = URLDecoder.decode(request,"utf-8");
//				String requestToken = AlipaySubmit.getRequestToken(decodeRequest);
//				String req_data = "<auth_and_execute_req><request_token>" + requestToken + "</request_token></auth_and_execute_req>";
//				Map<String, String> sParaTemp = new HashMap<String, String>();
//				sParaTemp.put("service", "alipay.wap.auth.authAndExecute");
//				sParaTemp.put("partner", AlipayConfig.PARTNER_VALUE);
//				sParaTemp.put("_input_charset", "utf-8");
//				sParaTemp.put("sec_id", "MD5");
//				sParaTemp.put("format", "xml");
//				sParaTemp.put("v", "2.0");
//				sParaTemp.put("req_data", req_data);
//				String html = AlipaySubmit.buildRequest(AlipayConfig.ALIPAY_GATEWAY_NEW, sParaTemp, "get", mActivity.getString(PayResource.getInstance(mActivity)"mgp_confirm));
//				Message msg = mHandler.obtainMessage();
//				msg.what = LOAD_HTML;
//				msg.obj = html;
//				mHandler.sendMessage(msg);
//			} catch (Exception e) {
//				log.e(e);
//			}
//		}
//	}
	
	/**
	 * 
	 * 功能描述：从回调信息中解析订单信息并存储到DB�?
	 *
	 * @author 徐萌�?xumengyang)
	 * @param @param callbackUrl
	 * @return void
	 * @date 2014-1-21 下午8:37:56
	 *
	 */
	private void saveOrderInfoIntoDB(String callbackUrl){
		if(callbackUrl == null || TextUtils.isEmpty(callbackUrl)){
			log.e("callbackUrl is null");
			return;
		}
		//剔除"AlipayConfig.NOTIFY_URL_VALU?"
		String tmp = callbackUrl.replace(AlipayConfig.NOTIFY_URL_VALUE+"?", "");
		log.d("tmp = "+tmp);
		//�?分割字符�?
		String[]  strs = tmp.split("&");
		Map<String,String> params = new HashMap<String, String>();
		for(int i=0;i<strs.length;i++){
			//获得第一�?字符的位�?
        	int nPos = strs[i].indexOf("=");
        	//获得字符串长�?
    		int nLen = strs[i].length();
    		//获得变量�?
    		String strKey = strs[i].substring(0, nPos);
    		//获得数�?
    		String strValue = strs[i].substring(nPos+1,nLen);
    		//放入MAP类中
    		params.put(strKey, strValue);
		}
		final String result = params.get("result");
		//Test
//		Toast.makeText(mActivity, "支付宝回调结"+result, Toast.LENGTH_SHORT).show();
		//Test
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			
			@Override
			public void run() {
				mWaitingDialog.dismiss();
				if(mActivity != null){
//					((CYMGPaymentActivity)mActivity).back2PaymentFragment();;
					Bundle b = new Bundle();
					b.putInt("type", CYMGPaymentSuccessFragment.SUCCESS);
					((CYMGPaymentActivity)mActivity).changeFragment(Contants.FragmentTag.PAYMENT_SUCCESS_FRAGMENT_TAG, b);
					CYMGPayHelper.getInstance().paySuccess(mUID
							, mGoodsItem
							, mOrderID
							, mActivity);
				}else{
					log.e("mActivity is null");
				}
			}
		});
//		new Thread(){
//			@Override
//			public void run() {
//				Message msg = mHandler.obtainMessage();
//				msg.what = PAY_SUCCESS;
//				mHandler.sendMessage(msg);
//			}
//		}.start();
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == mNetErrorView.getRefreshBtnId()){
			if(mNetErrorView.isShown()){
				mNetErrorView.setVisibility(View.GONE);
				mWebView.setVisibility(View.VISIBLE);
			}
			getOrderInfoAndShow();
		}else if(v.getId() == PayResource.getInstance(mActivity).mgp_title_left_ibtn){
			((CYMGPaymentActivity)mActivity).goback();
		}
	}
	
	
	/**
	 * 
	 * 功能描述：检测支付参数合法�?
	 *
	 * @author 徐萌�?xumengyang)
	 * @param @param item
	 * @param @return
	 * @return boolean
	 * @date 2014-3-17 上午9:53:03
	 *
	 */
	private boolean checkPayParams(GoodsItem item){
		if(item == null){
			MyToast.showDebugToast(mActivity, "GoodsItem is null");
			return false;
		}else if(item.getGoods_price() == null){
			MyToast.showDebugToast(mActivity, "goods price is null");
			return false;
		}else if(item.getGoods_name() == null){
			MyToast.showDebugToast(mActivity, "GoodsName is null");
			return false;
		}
		return true;
	}
}
