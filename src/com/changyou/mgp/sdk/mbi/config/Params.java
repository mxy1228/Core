package com.changyou.mgp.sdk.mbi.config;

/**
 * 
 * 功能描述：用来存放跨类传递的参数常量
 *
 * @author 徐萌阳(xumengyang)
 *
 * @date 2014-1-20
 */
public class Params {
	
	public static final String GOODSNAME = "goodsName";
	public static final String GOODSDESCRIBE = "goodsDescribe";
	public static final String GOODSPRICE = "goodsPrice";
	public static final String GOODSICON = "goodsIcon";
	public static final String GOODSID = "goodsId";
	public static final String GOODSNUMBER= "goodsNumber";
	public static final String GOODSREGISTERID= "goodsRegisterId";
	public static final String GOODSTYPE= "type";
	public static final String GOODSITEM = "goods_item";
	public static final String ORDER_ID = "order_id";
	
	public static final String UID = "uid";
	public static final String TOKEN = "token";
	public static final String ACCOUNT = "account";
	public static final String PASSWORD = "password";
	public static final String TYPE = "type";
	public static final String PHONENUM = "phonenum";
	public static final String CONTENT = "content";
	public static final String BUNDLE = "bundle";
	public static final String REGISTER_MODE = "register_mode";
	
	public static final String ISAUTOLOGIN = "isAutoLogin";
	
	public static final String CHARGERECORDER = "ChargeRecorder";
	
	public static class MYHTTPCLIENT_HEADER_PARAMS{
		public static final String DEBUG = "debug";
		public static final String APP_KEY = "app_key";
		public static final String CHANNEL_ID = "channel_id";
		public static final String TAG = "tag";
		public static final String SIGN = "sign";
		public static final String VERSION = "clientVersion";
		public static final String MEDIA_CHANNEL_ID = "media_channel_id";
	}
	
	/**
	 * 
	 * 功能描述：支付宝wrap支付相关参数
	 *
	 * @author 徐萌阳(xumengyang)
	 *
	 * @date 2014-1-21
	 */
	public static class ALIPAY_WRAP{
		public static final String TRADE_NO = "WIDout_trade_no";//订单号
		public static final String SUBJECT = "WIDsubject";//商品名称
		public static final String FEE = "WIDtotal_fee";//商品价格
		public static final String UID = "uid";
		public static final String WRAP_WAY = "wrap_way";//wrap支付方式 1：支付宝
	}
	
	/**
	 * 功能描述: 豌豆荚渠道登录结果回调接口对外暴露的参数
	 * @author 欧阳海冰(OuyangHaibing) 
	 * @date 2014-2-25 下午6:59:45 
	 *
	 */
	public static class Wdj_LOGIN_RESULT_PARAMS{
		public static final String UID = "uid";
		public static final String TOKEN = "token";
	}
	
	public static class GOLOBAL_PARAMS{
		public static final String KEY_ALIPAY_OVERLOAD_CONFIRM_LOGIN_URL = "KEY_ALIPAY_OVERLOAD_CONFIRM_LOGIN_URL";
		public static final String KEY_ALIPAY_OVERLOAD_LOGIN_URL = "KEY_ALIPAY_OVERLOAD_LOGIN_URL";
		public static final String KEY_ALIPAY_CREDIT_CARD_URL = "KEY_ALIPAY_CREDIT_CARD_URL";
		public static final String KEY_ALIPAY_SAVING_CARD_URL = "KEY_ALIPAY_SAVING_CARD_URL";
		public static final String SDK_ALI_WAP_PAY = "SDK_ALI_WAP_PAY";
		public static final String KEY_QUICK_ALIPAY_NOTIFY_URL = "SDK_ALI_KJ_PAY";
	}
}
