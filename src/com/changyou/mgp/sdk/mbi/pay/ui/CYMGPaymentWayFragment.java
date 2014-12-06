package com.changyou.mgp.sdk.mbi.pay.ui;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.changyou.mgp.sdk.mbi.account.id.AccResource;
import com.changyou.mgp.sdk.mbi.config.CYMGProtocolConfig;
import com.changyou.mgp.sdk.mbi.config.CYMGProtocolKeys;
import com.changyou.mgp.sdk.mbi.config.Contants;
import com.changyou.mgp.sdk.mbi.config.HttpContants;
import com.changyou.mgp.sdk.mbi.config.Params;
import com.changyou.mgp.sdk.mbi.config.SDKConfig;
import com.changyou.mgp.sdk.mbi.entity.GoodsItem;
import com.changyou.mgp.sdk.mbi.entity.MDOEntity;
import com.changyou.mgp.sdk.mbi.entity.UPayResultEvent;
import com.changyou.mgp.sdk.mbi.http.MyAsyncResponseHandler;
import com.changyou.mgp.sdk.mbi.http.MyHttpClient;
import com.changyou.mgp.sdk.mbi.log.CYLog;
import com.changyou.mgp.sdk.mbi.pay.adapter.PaymentAdapter;
import com.changyou.mgp.sdk.mbi.pay.alipay.MGPAlipay;
import com.changyou.mgp.sdk.mbi.pay.id.PayResource;
import com.changyou.mgp.sdk.mbi.pay.weixin.WeixinPayModel;
import com.changyou.mgp.sdk.mbi.platform.CYMGPlatform;
import com.changyou.mgp.sdk.mbi.ui.base.BaseFragment;
import com.changyou.mgp.sdk.mbi.ui.widget.CustomGridview;
import com.changyou.mgp.sdk.mbi.ui.widget.WaitingDialog;
import com.changyou.mgp.sdk.mbi.ui.widget.WaitingDialog.MyDialogDismissListener;
import com.changyou.mgp.sdk.mbi.utils.JSONUtil;
import com.changyou.mgp.sdk.mbi.utils.MetaDataValueUtils;
import com.changyou.mgp.sdk.mbi.utils.MyToast;
import com.changyou.mgp.sdk.mbi.utils.NetWorkUtils;
import com.changyou.mgp.sdk.mbi.utils.SettingSPUtil;
import com.changyou.mgp.sdk.mbi.utils.SystemUtils;
import com.changyou.mgp.sdk.mbi.utils.UserInfoSPUtil;
import com.loopj.android.http.RequestParams;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;

import de.greenrobot.event.EventBus;
/**
 * 
 * 功能描述：支付方式页�?
 *
 * @author 徐萌�?xumengyang)
 *
 * @date 2014-1-15
 */
public class CYMGPaymentWayFragment extends BaseFragment implements OnItemClickListener,OnClickListener{

	private CYLog log = CYLog.getInstance();
	
	public static final int MOD_PRICE = 2;
	public static final int RESULT_FOR_ALIPAY_CODE = 3;//支付宝极简收银台回调code
	public static final int RESULT_FOR_MO9_CODE = 100;//MO9回调code

	protected static final int PAY_WAY_GET_ORDERID_SUCCESS = 0x01;
	protected static final int PAY_WAY_GET_ORDERID_FAIL = 0x02;
	
	private Activity mActivity;
	private GoodsItem mGoodsItem;
	private String mUID;
	private WaitingDialog mWaitingDialog;
	private String mCurrentOrderID;//本次生成的订单号
	private PaymentAdapter mAdapter;
	
	private MyHttpClient mHttpClient;
	private PayResource mPayResource;
	private Bundle mBundle;
	
	private boolean isDestroy = false;

	boolean isMBO=false;
	boolean hasWeixinPay=false;
	private boolean mCreating;

	private Button mCtsBtn;
	private CustomGridview mPaywayGv;
	private TextView mGoodsPriceTv;
	private TextView mGoodsNameTv;
	private TextView mGameAccTv;
	private TextView mGameNameTv;
	private TextView mCloseTv;
	
	private Handler mHandler = new Handler(){
		@Override
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case PAY_WAY_GET_ORDERID_SUCCESS:
				
				break;
			case PAY_WAY_GET_ORDERID_FAIL:
				mWaitingDialog.dismiss();
				Toast.makeText(mActivity, PayResource.getInstance(mActivity).mgp_net_error_hint, Toast.LENGTH_LONG).show();
				((CYMGPaymentActivity)mActivity).goback();
				break;
			}
		}
	};

	private View mHorizonScrollview;

	private int screenOrientation;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
		screenOrientation = SettingSPUtil.getLandScape(mActivity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = null;
		if (screenOrientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT) {
			view = inflater.inflate(PayResource.getInstance(mActivity).mgp_sdk_2_0_pay_cashier, container,false);
		}else {
			view = inflater.inflate(PayResource.getInstance(mActivity).mgp_sdk_2_0_pay_cashier_land, container,false);
		}
		initView(view);
		initWaitingDialog();
		initData();
		initEvent();
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		//将scrollView滚动到最顶端
		if (mHorizonScrollview != null) {
			mHorizonScrollview.post(new Runnable() {
				
				@Override
				public void run() {
					mHorizonScrollview.scrollTo(0, 0);
				}
			});
		}
	}
	
	private void initView(View view){
		this.mCloseTv = (TextView)view.findViewById(PayResource.getInstance(mActivity).mgp_pay_cashier_close_tv);
		this.mGameNameTv = (TextView)view.findViewById(PayResource.getInstance(mActivity).mgp_pay_cashier_game_name);
		this.mGameAccTv = (TextView)view.findViewById(PayResource.getInstance(mActivity).mgp_pay_cashier_game_acc);
		this.mGoodsNameTv = (TextView)view.findViewById(PayResource.getInstance(mActivity).mgp_pay_cashier_goods_name);
		this.mGoodsPriceTv = (TextView)view.findViewById(PayResource.getInstance(mActivity).mgp_pay_cashier_goods_money);
		this.mCtsBtn = (Button)view.findViewById(PayResource.getInstance(mActivity).mgp_sdk_2_0_pay_cashier_cts_entrance_btn);
		this.mPaywayGv = (CustomGridview)view.findViewById(PayResource.getInstance(mActivity).mgp_pay_cashier_pay_way_gv);
		this.mHorizonScrollview = view.findViewById(PayResource.getInstance(mActivity).mgp_pay_cashier_horizon_scrollview);
		screenOrientation = SettingSPUtil.getLandScape(mActivity);
		if (screenOrientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT) {
			this.mPaywayGv.setExpanded(false);
			//动态设置列间距
			int spacing = (SystemUtils.getScreenWidthSize(mActivity) - SystemUtils.dip2px(mActivity, 30)*2 - SystemUtils.dip2px(mActivity, 69)*3)/2;
			mPaywayGv.setHorizontalSpacing(spacing);
		}else {
			this.mPaywayGv.setExpanded(true);
			//动态设置列间距
			int spacing = (SystemUtils.getScreenWidthSize(mActivity) - SystemUtils.dip2px(mActivity, 30)*2 - SystemUtils.dip2px(mActivity, 69)*4)/3;
			mPaywayGv.setHorizontalSpacing(spacing);
		}
		
	}
	
	private void initWaitingDialog(){
		this.mWaitingDialog = new WaitingDialog(mActivity);
		this.mWaitingDialog.setMessage(mActivity.getString(PayResource.getInstance(mActivity).mgp_loading));
	}
	
	@Override
	public void onDestroy() {
		this.isDestroy=true;
		super.onDestroy();
	}
	
	@Override
	protected void initData() {
		mBundle = getArguments();
		int opentype = mBundle.getInt(CYMGProtocolKeys.PAYMENT_METHOD);
		if(opentype == CYMGProtocolConfig.PAY_WAY){
			//如果直接打开支付方式页，需首先弹出dialog创建订单
			String goods_id = mBundle.getString(CYMGProtocolKeys.GOODS_ID);
			String goods_register_id = mBundle.getString(CYMGProtocolKeys.GOODS_REGIST_ID);
			String goods_number = mBundle.getString(CYMGProtocolKeys.GOODS_NUMBER);
			String goods_price = mBundle.getString(CYMGProtocolKeys.GOODS_PRICE);
			String goods_name = mBundle.getString(CYMGProtocolKeys.GOODS_NAME); 
			this.mGoodsItem = new GoodsItem();
			this.mGoodsItem.setGoods_id(goods_id);
			this.mGoodsItem.setGoods_register_id(goods_register_id);
			this.mGoodsItem.setGoods_number(goods_number);
			this.mGoodsItem.setGoods_price(Double.valueOf(goods_price));
			this.mGoodsItem.setGoods_name(goods_name);
			mBundle.putSerializable(Params.GOODSITEM, mGoodsItem);
			if(mCurrentOrderID == null){
				createOrderFromServer(mGoodsItem);
			}
		}else{
			this.mGoodsItem = (GoodsItem)mBundle.getSerializable(Params.GOODSITEM);
			this.mCurrentOrderID = mBundle.getString(Params.ORDER_ID);
			if(this.mCurrentOrderID == null){
				throw new IllegalAccessError("order_id is null");
			}
		}
		
		this.mUID = mBundle.getString(Params.UID);
		if (this.mGoodsItem == null) {
			log.e("mGoodsItem is null");
			return;
		}
		if(this.mUID == null){
			throw new IllegalAccessError("uid is null");
		}
		
		WidgetBindData();
	}
	
	private void WidgetBindData(){
		//设置支付信息
		//游戏名称
		if (TextUtils.isEmpty(SystemUtils.getApplicationName(mActivity))) {
			this.mGameNameTv.setVisibility(View.GONE);
		}else {
			String gameName = mActivity.getString(PayResource.getInstance(mActivity)
					.mgp_sdk_2_0_pay_cashier_game_name_txt, SystemUtils.getApplicationName(mActivity));
			this.mGameNameTv.setText(gameName);
		}
		//游戏账号
		String gameAcc = UserInfoSPUtil.getUsername(mActivity);
		if (TextUtils.isEmpty(gameAcc)) {
			this.mGameAccTv.setVisibility(View.GONE);
		}else {
			String gameAccSub = gameAcc.substring(Contants.ACC_TYPE_CHANGYOU.length());
			String gameAccF = mActivity.getString(PayResource.getInstance(mActivity)
						.mgp_sdk_2_0_pay_cashier_game_acc_txt, gameAccSub);
			this.mGameAccTv.setText(gameAccF);
		}
		//商品名称
		if (TextUtils.isEmpty(mGoodsItem.getGoods_name())) {
			this.mGoodsNameTv.setVisibility(View.GONE);
		}else {
			String goodsNameF = mActivity.getString(PayResource.getInstance(mActivity)
					.mgp_sdk_2_0_pay_cashier_goods_name_txt, mGoodsItem.getGoods_name());
			this.mGoodsNameTv.setText(goodsNameF);
		}
		
		//充值金额
		if (TextUtils.isEmpty(mGoodsItem.getGoods_price())) {
			this.mGoodsPriceTv.setVisibility(View.GONE);
		}else {
			String goodsPriceF = mActivity.getString(PayResource.getInstance(mActivity)
					.mgp_sdk_2_0_pay_cashier_charge_price_txt, mGoodsItem.getGoods_price());
			this.mGoodsPriceTv.setText(initSString(goodsPriceF, PayResource.getInstance(mActivity).mgp_sdk_2_0_c3));
		}
		
		this.mPayResource=PayResource.getInstance(mActivity);
		ArrayList<Integer> imgArr=new ArrayList<Integer>();
		imgArr.add(mPayResource.mgp_pay_cashier_alipay_cashier);
		imgArr.add(mPayResource.mgp_pay_cashier_alipay_web);
		String weixinAppId=MetaDataValueUtils.getWeixinAppId(mActivity);
		String weixinAppKey=MetaDataValueUtils.getWeixinAppKey(mActivity);
		String weixinAppSecret=MetaDataValueUtils.getWeixinAppSecret(mActivity);
		if(checkWeixin()){
			imgArr.add(mPayResource.mgp_pay_cashier_weixin);
			hasWeixinPay=true;
		}else{
			hasWeixinPay=false;
		}
		imgArr.add(mPayResource.mgp_pay_cashier_unionpay);
		imgArr.add(mPayResource.mgp_pay_cashier_cft);
		imgArr.add(mPayResource.mgp_pay_cashier_mo9);
		imgArr.add(mPayResource.mgp_pay_cashier_cellphone_recharge_card);
		imgArr.add(mPayResource.mgp_pay_cashier_game_card);
		//若有两元商品则显示手机短信支�?
		mAdapter = new PaymentAdapter(mActivity,imgArr);
		mPaywayGv.setAdapter(mAdapter);
	}

	@Override
	protected void initEvent() {
		this.mCloseTv.setOnClickListener(this);
		this.mPaywayGv.setOnItemClickListener(this);
		this.mCtsBtn.setOnClickListener(this);
	}
	
	/**
	 * 
	 * @Title: initSString 
	 * @Description: Str以'：'分割，前面保持原样式不变，后面根据传进来的colorID改变颜色
	 * @param @param str
	 * @param @param colorID
	 * @param @return 
	 * @return SpannableString
	 * @throws
	 */
	private SpannableString initSString(String str,int colorID){
		SpannableString sstr = new SpannableString(str);
		ForegroundColorSpan span = new ForegroundColorSpan(mActivity.getResources().getColor(colorID));
		sstr.setSpan(span, str.indexOf("：")+1, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		sstr.setSpan(new AbsoluteSizeSpan(20,true), str.indexOf("：")+1, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return sstr;
	}

	private void doAlipayWap(){
		if(NetWorkUtils.isNetworkConnected(mActivity)){
			CYMGAlipayWrapFragment f = new CYMGAlipayWrapFragment();
			Bundle b = new Bundle();
			b.putSerializable(Params.GOODSITEM, mGoodsItem);
			b.putString(Params.ALIPAY_WRAP.TRADE_NO, mCurrentOrderID);
			b.putString(Params.ALIPAY_WRAP.UID, mUID);
			f.setArguments(b);
			getFragmentManager().beginTransaction().replace(PayResource.getInstance(mActivity).payment_root_layout, f, Contants.FragmentTag.ALIPAY_WRAP_FRAGMENT)
			.addToBackStack(Contants.FragmentTag.ALIPAY_WRAP_FRAGMENT).commit();
		}else{
			showNetErrorToast();
		}
		
	}
	
	private void doTenPay(){
		if(NetWorkUtils.isNetworkConnected(mActivity)){
			CYMGTenPayFragment tenpayF = new CYMGTenPayFragment();
			Bundle tenpayB = new Bundle();
			tenpayB.putSerializable(Params.GOODSITEM, mGoodsItem);
			tenpayB.putString(Params.ALIPAY_WRAP.TRADE_NO, mCurrentOrderID);
			tenpayB.putString(Params.ALIPAY_WRAP.UID, mUID);
			tenpayF.setArguments(tenpayB);
			getFragmentManager().beginTransaction().replace(PayResource.getInstance(mActivity).payment_root_layout, tenpayF, Contants.FragmentTag.ALIPAY_WRAP_FRAGMENT)
			.addToBackStack(Contants.FragmentTag.ALIPAY_WRAP_FRAGMENT).commit();
		}else{
			showNetErrorToast();
		}
	}
	
	private void doAlipay(){
		if(NetWorkUtils.isNetworkConnected(mActivity)){
			new MGPAlipay(mActivity).pay(mUID,mGoodsItem,mCurrentOrderID);
		}else{
			showNetErrorToast();
		}
	}
	
	private void doPhoneCardPay(){
		if(NetWorkUtils.isNetworkConnected(mActivity)){
			((CYMGPaymentActivity)mActivity).changeFragment(Contants.FragmentTag.PAYMENT_WAY_PHONE_CARD_FRAGMENT_TAG, mBundle);
		}else{
			showNetErrorToast();
		}
	}
	
	private void doGameCardPay(){
		if(NetWorkUtils.isNetworkConnected(mActivity)){
			((CYMGPaymentActivity)mActivity).changeFragment(Contants.FragmentTag.PAYMENT_WAY_GAME_CARD_FRAGMENT_TAG, mBundle);
		}else{
			showNetErrorToast();
		}
	}
	
	private void doWeixinPay(){
		if(NetWorkUtils.isNetworkConnected(mActivity)){
			//微信支付
			WeixinPayModel weixinPay=WeixinPayModel.getInstance();
			weixinPay.setActivity(this.getActivity());
			weixinPay.startPay(mUID,mGoodsItem,mCurrentOrderID);
		}else{
			showNetErrorToast();
		}
	}
	
	private boolean checkWeixin(){
		String weixinAppId=MetaDataValueUtils.getWeixinAppId(mActivity);
		String weixinAppKey=MetaDataValueUtils.getWeixinAppKey(mActivity);
		String weixinAppSecret=MetaDataValueUtils.getWeixinAppSecret(mActivity);
		if(TextUtils.isEmpty(weixinAppId)||TextUtils.isEmpty(weixinAppKey)||TextUtils.isEmpty(weixinAppSecret)){
			return false;
		}
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(hasWeixinPay){
			switch (position) {
			case 0://支付宝收银台
				doAlipay();
				break;
			case 1://支付宝网页支付
				doAlipayWap();
				break;
			case 2://微信支付
				doWeixinPay();
				break;
			case 3://银联支付
				UPPay();
				break;
			case 4://财付通支付
				doTenPay();
				break;
			case 5://mo9支付
				MO9();
				break;
			case 6://手机充值卡
				doPhoneCardPay();
				break;
			case 7://游戏卡
				doGameCardPay();
				break;
			}
		}else{
			switch (position) {
			case 0://支付宝收银台
				doAlipay();
				break;
			case 1://支付宝网页支付
				doAlipayWap();
				break;
			case 2://银联支付
				UPPay();
				break;
			case 3://财付通支付
				doTenPay();
				break;
			case 4://mo9支付
				MO9();
				break;
			case 5://手机充值卡
				doPhoneCardPay();
				break;
			case 6://游戏卡
				doGameCardPay();
				break;
			}
		}
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==PayResource.getInstance(mActivity).mgp_pay_cashier_close_tv){
			((CYMGPaymentActivity)mActivity).goback();
		}else if (v.getId() == PayResource.getInstance(mActivity).mgp_sdk_2_0_pay_cashier_cts_entrance_btn) {
			//客服入口
			Bundle b = new Bundle();
			b.putString(CYMGProtocolKeys.UID, mUID);
			b.putString(CYMGProtocolKeys.ROLE_ID, mBundle.getString(CYMGProtocolKeys.ROLE_ID));
			b.putString(CYMGProtocolKeys.GAME_NAME, mBundle.getString(CYMGProtocolKeys.GAME_NAME));
			b.putString(CYMGProtocolKeys.SERVER_NAME, mBundle.getString(CYMGProtocolKeys.SERVER_NAME));
			CYMGPlatform.getInstance().customerService(mActivity, b);
		}
	}
	
	/**
	 * toast方式提示网络错误
	 */
	private void showNetErrorToast(){
		MyToast.showToast(mActivity,mActivity.getString(PayResource.getInstance(mActivity).mgp_payment_net_error_toast_txt));
	}
	
	
	/**
	 * 
	 * 功能描述：向服务器请求手机短信支付数�?
	 *
	 * @author 徐萌�?xumengyang)
	 * @param 
	 * @return void
	 * @date 2014-3-15 下午3:12:32
	 *
	 */
	private void mdo(String orderID){
		RequestParams params = new RequestParams();
		params.put("order_id", orderID);
		mHttpClient = new MyHttpClient(mActivity);
		mHttpClient.get(HttpContants.getURL(HttpContants.MDO), params, new MyAsyncResponseHandler(){
			
			@Override
			public void onStart() {
				mWaitingDialog.setDismissListener(mHttpClient.getTimeout(), new MyDialogDismissListener() {
					
					@Override
					public void onTimeOutDismiss() {
						showNetErrorToast();
					}
				});
				mWaitingDialog.show();
			}
			
			@Override
			public void onSuccess(int statusCode, String content) {
				log.d("mdo result = "+content);
				mWaitingDialog.dismiss();
				try {
					MDOEntity e = JSONUtil.getMapper().readValue(content, new TypeReference<MDOEntity>() {
					});
					if(e != null){
						if(isDestroy){
							log.d("CYMGPaymentWayFragment isDestroy");
							return;
						}
						Uri uri = Uri.parse("smsto:"+e.getMsgphone());
						Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
						intent.putExtra("sms_body", e.getMsgcontent());
						mActivity.startActivity(intent);
					}else{
						log.e("e is null");
					}
				} catch (Exception e) {
					log.e(e);
				}
			}
			
			@Override
			public void onFailure(Throwable error, String content) {
				log.e("mdo result = "+content);
				mWaitingDialog.dismiss();
				try {
					MDOEntity e = JSONUtil.getMapper().readValue(content, new TypeReference<MDOEntity>() {
					});
					Toast.makeText(mActivity, e.getClient_message(), Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					log.e(e);
				}
			}
			
		});
	}
	
	/**
	 * 
	 * 功能描述：银联支付
	 *
	 * @author 严峥(yanzheng)
	 * @param @param b
	 * @return void
	 * @date 2014-4-28 下午3:05:32
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public void UPPay(){
		if(NetWorkUtils.isNetworkConnected(mActivity)){
			log.d("UPPay");
//			GoodsItem goodsItem=(GoodsItem) mBundle.getSerializable(Params.GOODSITEM);
			mHttpClient = new MyHttpClient(mActivity);
			RequestParams map = new RequestParams();
			map.put("order_id", mCurrentOrderID);
			map.put("goods_price", mGoodsItem.getGoods_price());
			mHttpClient.get(HttpContants.getURL(HttpContants.UPPAY_TN), map, new MyAsyncResponseHandler(){
				
				@Override
				public void onStart() {
					mWaitingDialog.setDismissListener(mHttpClient.getTimeout(), new MyDialogDismissListener() {
						
						@Override
						public void onTimeOutDismiss() {
							showNetErrorToast();
						}
					});
					mWaitingDialog.show();
				}
				
				@Override
				public void onSuccess(int statusCode, String content) {
					log.d("onSuccess,content:"+content);
					mWaitingDialog.dismiss();
					if(!TextUtils.isEmpty(content)){
						try {
							JSONObject jsonObject=new JSONObject(content);
							int status=jsonObject.getInt("status");
							switch (status) {
							case 1:
								String tn=jsonObject.getString("tn");
								String mode="00";//正式
								if(SDKConfig.DEBUG){
//									mode="01";//测试
								}
								if(isDestroy){
									log.d("CYMGPaymentWayFragment isDestroy");
									return;
								}
								UPPayAssistEx.startPayByJAR(mActivity, PayActivity.class, null, null, tn, mode);
								break;
							default:
								log.d("UPPay获取TN流水号失败，status："+status);
								showNetErrorToast();
								break;
							}
						} catch (JSONException e) {
							log.e(e);
						}
					}
				}
				
				@Override
				public void onFailure(int statusCode, Throwable error,
						String content) {
					log.d("onFailure,content:"+content);
					mWaitingDialog.dismiss();
					showNetErrorToast();
				}
			});
		}else{
			showNetErrorToast();
		}
	}
	
	/**
	 * 
	 * 功能描述：MO9现玩后付
	 *
	 * @author 严峥(yanzheng)
	 * @param 
	 * @return void
	 * @date 2014-5-9 上午9:56:29
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	private void MO9(){
		if(NetWorkUtils.isNetworkConnected(mActivity)){
			log.d("MO9Pay");
//			GoodsItem goodsItem=(GoodsItem) mBundle.getSerializable(Params.GOODSITEM);
			String goodsName=mGoodsItem.getGoods_name();
			String item_name=null;
			try {
				item_name=new String(goodsName.getBytes(), "UTF-8");
			} catch (Exception e) {
				log.e(e);
			} 
			mHttpClient = new MyHttpClient(mActivity);
			RequestParams map = new RequestParams();
			map.put("lc", "CN");
			map.put("amount", mGoodsItem.getGoods_price());
			map.put("currency", "CNY");
			map.put("item_name", item_name);
			map.put("return_url", HttpContants.getMo9ReturnUrl());
			map.put("invoice", mCurrentOrderID == null ? mBundle.getString(Params.ORDER_ID) : mCurrentOrderID);
			mHttpClient.post(HttpContants.getURL(HttpContants.MO9_PAY), map, new MyAsyncResponseHandler(){
				
				@Override
				public void onStart() {
					mWaitingDialog.setDismissListener(mHttpClient.getTimeout(), new MyDialogDismissListener() {
						
						@Override
						public void onTimeOutDismiss() {
							showNetErrorToast();
						}
					});
					mWaitingDialog.show();
				}
				
				@Override
				public void onSuccess(int statusCode, String content) {
					log.d("onSuccess,content:"+content);
					mWaitingDialog.dismiss();
					if(!TextUtils.isEmpty(content)){
						try {
							JSONObject jsonObject=new JSONObject(content);
							String url=jsonObject.getString("targetUrl");
							if(isDestroy){
								log.d("CYMGPaymentWayFragment isDestroy");
								return;
							}
							Bundle bundle=new Bundle();
							bundle.putString("mo9_url", url);
							bundle.putString(Params.UID, mUID);
							bundle.putString(Params.ORDER_ID, mCurrentOrderID);
							bundle.putSerializable(Params.GOODSITEM, mGoodsItem);
							((CYMGPaymentActivity)mActivity).changeFragment(Contants.FragmentTag.PAYMENT_MO9_FRAGMENT_TAG, bundle);
						} catch (JSONException e) {
							log.e(e);
						}
					}else{
						showNetErrorToast();
					}
				}
				
				@Override
				public void onFailure(int statusCode, Throwable error,
						String content) {
					log.d("onFailure,content:"+content);
					mWaitingDialog.dismiss();
					showNetErrorToast();
				}
			});
		}else{
			showNetErrorToast();
		}
	}
	
	private void createOrderFromServer(final GoodsItem item){
		if(mCreating){
			return;
		}
		mCreating = true;
		final String mUID = mBundle.getString(CYMGProtocolKeys.UID);
		if(TextUtils.isEmpty(mUID) || mUID == null){
			throw new IllegalAccessError("uid is null");
		}
		if(NetWorkUtils.isNetworkConnected(mActivity)){
			MyHttpClient mCreateOrderHttp = new MyHttpClient(mActivity);
			Map<String,String> params = new HashMap<String, String>();
			String type = UserInfoSPUtil.getType(mActivity);
			String account_id = UserInfoSPUtil.getUsername(mActivity);
			if(Contants.LoginParams.TYPE_CYOU.equals(type) 
					&& MetaDataValueUtils.getChannelID(mActivity) == mActivity.getString(AccResource.getInstance(mActivity).mgp_channel_cy)){
				if(!TextUtils.isEmpty(account_id)&&account_id!=null){
					params.put("account_id", account_id);
				}else{
					params.put("account_id", mUID);
				}
			}else{
				params.put("account_id", mUID);
			}
			params.put("user_id", mUID);
			params.put("goods_id", item.getGoods_id());
			params.put("goods_register_id",item.getGoods_register_id());
			params.put("goods_number", item.getGoods_number());
			params.put("goods_price", String.valueOf(item.getGoods_price()));
			params.put("push_info", mBundle.getString(CYMGProtocolKeys.PUSH_INFO));
			params.put("game_channel", MetaDataValueUtils.getCMBIChannelID(mActivity));
			if(mBundle.getString(CYMGProtocolKeys.ROLE_ID) != null){
				params.put("role_id", mBundle.getString(CYMGProtocolKeys.ROLE_ID));
			}
			if(mBundle.getString(CYMGProtocolKeys.GROUP_ID) != null){
				params.put("group_id", mBundle.getString(CYMGProtocolKeys.GROUP_ID));
			}
			mCreateOrderHttp.post(HttpContants.getURL(HttpContants.CREATE_ORDER_FROM_SERVER), params, new MyAsyncResponseHandler(){
				@Override
				public void onStart() {
					mWaitingDialog.setMessage(mActivity.getString(PayResource.getInstance(mActivity).mgp_payment_creating_order));
					mWaitingDialog.show();
				}
				
				@Override
				public void onSuccess(int statusCode, String content) {
					try {
						log.d("onSuccess:content = "+content);
						mWaitingDialog.dismiss();
						if(TextUtils.isEmpty(content)){
							mCreating = false;
							return;
						}
						JSONObject jsonObject = new JSONObject(content);
						mCurrentOrderID = jsonObject.getString("order_id");
						mBundle.putString(Params.ORDER_ID, mCurrentOrderID);
						UPayResultEvent e = new UPayResultEvent();
						e.setmItem(item);
						e.setmOrderID(mCurrentOrderID);
						EventBus.getDefault().post(e);
//						mHandler.sendEmptyMessage(PAY_WAY_GET_ORDERID_SUCCESS);
						mCreating = false;
					} catch (Exception e) {
						log.e(e);
					}
				}
				@Override
				public void onFailure(int statusCode, Throwable error,
						String content) {
					log.e("onFailure:content="+content);
					mCreating = false;
					mHandler.sendEmptyMessage(PAY_WAY_GET_ORDERID_FAIL);
				}
			});
		}else{
			mCreating = false;
			MyToast.showToast(mActivity, mActivity.getString(PayResource.getInstance(mActivity).mgp_payment_net_error_toast_txt));
		}
	}
	
}
