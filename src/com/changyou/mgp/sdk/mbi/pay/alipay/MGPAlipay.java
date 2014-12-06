package com.changyou.mgp.sdk.mbi.pay.alipay;

import java.net.URLEncoder;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.changyou.mgp.sdk.mbi.config.CYMGPrompt;
import com.changyou.mgp.sdk.mbi.config.Contants;
import com.changyou.mgp.sdk.mbi.config.Params;
import com.changyou.mgp.sdk.mbi.entity.GoodsItem;
import com.changyou.mgp.sdk.mbi.log.CYLog;
import com.changyou.mgp.sdk.mbi.pay.ui.CYMGPaymentActivity;
import com.changyou.mgp.sdk.mbi.pay.ui.CYMGPaymentSuccessFragment;
import com.changyou.mgp.sdk.mbi.platform.CYMGPayHelper;

/**
 * 
 * 功能描述：支付宝极简收银台
 *
 * @author 徐萌阳(xumengyang)
 *
 * @date 2014-1-15
 */
public class MGPAlipay {

	private CYLog log = CYLog.getInstance();
	
	private static final String URL_ENCODER = "UTF-8";
	private static final String SN = "sn";
	private static final String OUT_TRADE_NO = "out_trade_no";//从支付宝解析订单号的key
	private static final String SUCCESS = "success";//从支付宝解析订单号的key
	
	private Activity mActivity;
//	private SimpleDateFormat mFormat;
	
	public MGPAlipay(Activity activity){
		this.mActivity = activity;
	}
	
//	public void setActivity(Activity activity){
//		this.mActivity = activity;
//	}
	
	/**
	 * 
	 * 功能描述：根据生成的支付宝私钥进行RSA加密
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param  content
	 * @param  加密后的内容
	 * @return String
	 * @date 2014-1-16 上午10:25:16
	 *
	 */
	private String rsaSign(String content){
		log.d("unsigned alipay params = "+content);
		String sign = null;
		try {
			String rsa = Rsa.sign(content, AlipayConfig.PRIVATE_KEY);
			if(rsa == null || TextUtils.isEmpty(rsa)){
				log.e("rsa is null");
				return null;
			}
			log.d("rsa = "+rsa);
			sign = URLEncoder.encode(rsa, URL_ENCODER);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.d("signed aplipay params = "+sign);
		return sign;
	}
	
	/**
	 * 
	 * 功能描述：调用支付宝极简收银台方式进行支付
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param activity 上下文
	 * @param orderInfo 订单信息
	 * @param totalFee 商品总金额
	 * @param listener 支付结果回调
	 * @return void
	 * @date 2014-1-16 上午11:08:48
	 *
	 */
	public void pay(final String uid,final GoodsItem goodsItem,final String orderID){
//		PayTask task = new PayTask(mActivity, new OnPayListener() {
//			
//			@Override
//			public void onPaySuccess(Context arg0, String resultStatus, String memo, String result) {
//				log.d("alipay success and resultStatus = "+resultStatus+" memo = "+memo+" result = "+result);
//				String trade_no = getOutTradeNo(result);
//				if(trade_no == null || TextUtils.isEmpty(trade_no)){
//					log.e("trade_no is null");
//					return;
//				}
//				log.d("trade_no="+trade_no);
//				Bundle b = new Bundle();
//				b.putInt("type", CYMGPaymentSuccessFragment.SUCCESS);
//				b.putString("cst_info", "test");
//				((CYMGPaymentActivity)mActivity).changeFragment(Contants.FragmentTag.PAYMENT_SUCCESS_FRAGMENT_TAG, b);
//				CYMGPayHelper.getInstance().paySuccess(uid, goodsItem, trade_no,mActivity);
//			}
//			
//			@Override
//			public void onPayFailed(Context arg0, String resultStatus, String memo, String result) {
//				log.e("alipay failed and resultStatus = "+resultStatus+" memo = "+memo+" result = "+result);
//				String trade_no = getOutTradeNo(result);
//				if(trade_no == null || TextUtils.isEmpty(trade_no)){
//					log.e("trade_no is null");
//					return;
//				}
//				log.d("trade_no="+trade_no);
//				MyToast.showDebugToast(mActivity, "支付宝回调结果=failed"+result);
//				Bundle b = new Bundle();
//				b.putInt("type", CYMGPaymentSuccessFragment.FAILED);
//				b.putString("cst_info", "test");
//				((CYMGPaymentActivity)mActivity).changeFragment(Contants.FragmentTag.PAYMENT_SUCCESS_FRAGMENT_TAG, b);
//				CYMGPayHelper.getInstance().payException(CYMGPrompt.CODE_PAY_FAILED);
//			}
//		});
		//更新至支付宝移动支付sdk2.0标准版
		new Thread() {
			public void run() {

				String orderInfo = getOrderInfo(uid,goodsItem,orderID);
				if(orderInfo == null){
					log.e("orderInfo is null");
					return;
				}
				PayTask alipay = new PayTask(mActivity);
				String result = alipay.pay(orderInfo);
				log.d("alipay result = "+result);
				String success = getSuccessValue(result);
				if(!TextUtils.isEmpty(success)){
					if("true".equals(success)){
						String trade_no = getOutTradeNo(result);
						if(trade_no == null || TextUtils.isEmpty(trade_no)){
							log.e("trade_no is null");
							return;
						}
						intoCYMGPaymentSuccessFragment(true, uid, goodsItem, trade_no);
					}else if("false".equals(success)){
						intoCYMGPaymentSuccessFragment(false, null, null, null);
					}
				}else{
					intoCYMGPaymentSuccessFragment(false, null, null, null);
				}
			}
		}.start();
	}
	
	private void intoCYMGPaymentSuccessFragment(final boolean b,final String uid,final GoodsItem goodsItem,final String trade_no){
		new Handler(Looper.getMainLooper()).post(new Runnable() {

			@Override
			public void run() {
				if(b){
					Bundle b = new Bundle();
					b.putInt("type", CYMGPaymentSuccessFragment.SUCCESS);
					b.putString("cst_info", "test");
					((CYMGPaymentActivity)mActivity).changeFragment(Contants.FragmentTag.PAYMENT_SUCCESS_FRAGMENT_TAG, b);
					CYMGPayHelper.getInstance().paySuccess(uid, goodsItem, trade_no,mActivity);
				}else{
					Bundle b = new Bundle();
					b.putInt("type", CYMGPaymentSuccessFragment.FAILED);
					b.putString("cst_info", "test");
					((CYMGPaymentActivity)mActivity).changeFragment(Contants.FragmentTag.PAYMENT_SUCCESS_FRAGMENT_TAG, b);
					CYMGPayHelper.getInstance().payException(CYMGPrompt.CODE_PAY_FAILED);
				}
			}
		});
	}
	
	/**
	 * 
	 * 功能描述：获取订单信息
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param @param outTradeNo
	 * @param @param subject
	 * @param @param body
	 * @param @param totalFee
	 * @param @return
	 * @return String
	 * @date 2014-1-16 下午2:04:57
	 *
	 */
	private String getOrderInfo(String uid,GoodsItem goodsItem,String orderID){
		String basicInfo = getBasicOrderInfo(uid 
				,goodsItem.getGoods_name()
				,goodsItem.getGoods_name()
				,goodsItem.getGoods_price()
				,orderID);
		if(basicInfo == null || TextUtils.isEmpty(basicInfo.toString())){
			return null;
		}
		String sign = rsaSign(basicInfo.toString());
		if(sign == null){
			return null;
		}
		String info = basicInfo+"&sign=" + "\"" + sign + "\"" + "&"
				+ "sign_type=\"RSA\"";
		log.d("orderInfo = "+info);
		return info;
	}
	
	/**
	 * 
	 * 功能描述：获取极简收银台支付基本信息
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param @param outTradeNo 订单号
	 * @param @param subject 商品名称
	 * @param @param body
	 * @param @param totalFee
	 * @param @return
	 * @return String
	 * @date 2014-1-16 下午1:31:11
	 *
	 */
	private String getBasicOrderInfo(String uid,String subject,String body,String totalFee,String orderId){
		if(orderId == null || TextUtils.isEmpty(orderId)){
			log.e("orderId is null");
			return null;
		}
//			StringBuilder sb = new StringBuilder();
//			sb.append("partner").append("=\"").append("2088011401787324").append("\"");
//			sb.append("&");
//			sb.append("seller_id").append("=\"").append("shouyou_pc@sohu.com").append("\"");
//			sb.append("&");
//			sb.append("out_trade_no").append("=\"").append("f323df238ceb4a4d8bdc05d758123b70").append("\"");
//			sb.append("&");
//			sb.append("subject").append("=\"").append(subject).append("\"");
//			sb.append("&");
//			sb.append("body").append("=\"").append(body).append("\"");
//			sb.append("&");
//			sb.append("total_fee").append("=\"").append(totalFee).append("\"");
//			sb.append("&");
//			sb.append("notify_url").append("=\"").append("http://notify.msp.hk/notify.htm").append("\"");
//			sb.append("&");
//			sb.append("service").append("=\"").append("mobile.securitypay.pay").append("\"");
//			sb.append("&");
//			sb.append("payment_type").append("=\"").append("1").append("\"");
//			sb.append("&");
//			sb.append("_input_charset").append("=\"").append("utf-8").append("\"");
//			return sb.toString();
		StringBuilder sb = new StringBuilder();
		sb.append(AlipayConfig.PARTNER).append("=\"").append(AlipayConfig.PARTNER_VALUE).append("\"");
		sb.append("&");
		sb.append(AlipayConfig.SELLER_ID).append("=\"").append(AlipayConfig.SELLER_ID_VALUE).append("\"");
		sb.append("&");
		sb.append(AlipayConfig.OUT_TRADE_NO).append("=\"").append(orderId).append("\"");
		sb.append("&");
		sb.append(AlipayConfig.SUBJECT).append("=\"").append(subject).append("\"");
		sb.append("&");
		sb.append(AlipayConfig.BODY).append("=\"").append(body).append("\"");
		sb.append("&");
		sb.append(AlipayConfig.TOTAL_FEE).append("=\"").append(totalFee).append("\"");
		sb.append("&");
		sb.append(AlipayConfig.NOTIFY_URL).append("=\"").append(getParam(Params.GOLOBAL_PARAMS.KEY_QUICK_ALIPAY_NOTIFY_URL)).append("\"");
		sb.append("&");
		sb.append(AlipayConfig.SERVICE).append("=\"").append(AlipayConfig.SERVICE_VALUE).append("\"");
		sb.append("&");
		sb.append(AlipayConfig.PAYMENT_TYPE).append("=\"").append(AlipayConfig.PAYMENT_TYPE_VALUE).append("\"");
		sb.append("&");
		sb.append(AlipayConfig.INPUT_CHARSET).append("=\"").append(AlipayConfig.INPUT_CHARSET_VALUE).append("\"");
		sb.append("&");
		sb.append(AlipayConfig.IT_B_PAY).append("=\"").append(AlipayConfig.IT_B_PAY_VALUE).append("\"");
//		log.d("快捷支付回调url="+getParam(Params.GOLOBAL_PARAMS.KEY_QUICK_ALIPAY_NOTIFY_URL));
		return sb.toString();
	}
	
	/**
	 * 
	 * 功能描述：获取wrap支付基本业务信息
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param @param subject
	 * @param @param body
	 * @param @param totalFee
	 * @param @return
	 * @return String
	 * @date 2014-1-21 下午5:05:33
	 *
	 */
	public String getBasicWrapOrderInfo(String uid,String subject,String totalFee,String orderId){
		if(orderId == null || TextUtils.isEmpty(orderId)){
			log.e("orderId is null");
			return null;
		}
		if(subject == null){
			log.e("subject is null");
			return null;
		}
		if(totalFee == null){
			log.e("totalFee is null");
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<direct_trade_create_req><notify_url>").append(AlipayConfig.NOTIFY_URL_VALUE).append("</notify_url><call_back_url>")
		.append(AlipayConfig.NOTIFY_URL_VALUE)
		.append("</call_back_url><seller_account_name>")
		.append(AlipayConfig.SELLER_ID_VALUE).append("</seller_account_name><out_trade_no>")
		.append(orderId).append("</out_trade_no><subject>")
		.append(subject).append("</subject><total_fee>")
		.append(totalFee).append("</total_fee><merchant_url>")
		.append(AlipayConfig.MERCHANT_URL).append("</merchant_url></direct_trade_create_req>");
		return sb.toString();
	}
	
	/**
	 * 
	 * 功能描述：获取订单号
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param activity
	 * @return String
	 * @date 2014-1-16 下午4:22:06
	 *
	 */
//	public String getOrderID(String uid){
//		return UUID.randomUUID().toString().replace("-", "");
//	}
	
	/**
	 * 
	 * 功能描述：根据支付宝的支付返回结果解析出订单号
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param result 支付宝的支付返回结果
	 * @return 订单号
	 * @return String
	 * @date 2014-1-20 下午4:31:52
	 *
	 */
	private String getOutTradeNo(String result){
		try {
			String[] values = result.split("&");
			int valueLength = values.length;
			for(int i=0;i<valueLength;i++){
				if(values[i].startsWith(OUT_TRADE_NO)){
					log.d("values[i]="+values[i]);
					return values[i].substring((OUT_TRADE_NO+"=\"").length(), values[i].length()-1);
				}
			}
		} catch (Exception e) {
			log.e(e);
		}
		return null;
	}
	
	private String getSuccessValue(String result){
		try {
			String[] values = result.split("&");
			int valueLength = values.length;
			for(int i=0;i<valueLength;i++){
				if(values[i].startsWith(SUCCESS)){
					log.d("values[i]="+values[i]);
					return values[i].substring((SUCCESS+"=\"").length(), values[i].length()-1);
				}
			}
		} catch (Exception e) {
			log.e(e);
		}
		return null;
	}
	
	/**
	 * 
	 * 功能描述：从SP
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param @param key
	 * @param @return
	 * @return String
	 * @date 2014-3-18 下午1:13:00
	 *
	 */
	public String getParam(String key){
		SharedPreferences sp = mActivity.getSharedPreferences(Contants.SP_NAME.SETTING, Context.MODE_PRIVATE);
		if(key.equals(Params.GOLOBAL_PARAMS.KEY_ALIPAY_CREDIT_CARD_URL)){
			return sp.getString(key, AlipayConfig.CREDIT_CARD_URL);
		}else if(key.equals(Params.GOLOBAL_PARAMS.KEY_ALIPAY_SAVING_CARD_URL)){
			return sp.getString(key, AlipayConfig.SAVING_CARD_URL);
		}else if(key.equals(Params.GOLOBAL_PARAMS.KEY_ALIPAY_OVERLOAD_CONFIRM_LOGIN_URL)){
			return sp.getString(key, AlipayConfig.OVERLOAD_CONFIRM_LOGIN_URL);
		}else if(key.equals(Params.GOLOBAL_PARAMS.KEY_ALIPAY_OVERLOAD_LOGIN_URL)){
			return sp.getString(key, AlipayConfig.OVERLOAD_LOGIN_URL);
		}else if(key.equals(Params.GOLOBAL_PARAMS.SDK_ALI_WAP_PAY)){
			return sp.getString(key, AlipayConfig.SDK_ALI_WAP_PAY);
		}else if(key.equals(Params.GOLOBAL_PARAMS.KEY_QUICK_ALIPAY_NOTIFY_URL)){
			return sp.getString(key, AlipayConfig.QUICK_ALIPAY_NOTIFY_URL);
		}
		return null;
	}
}
