package com.changyou.mgp.sdk.mbi.config;

/**
 * 
 * 功能描述：存储接口URL地址
 *
 * @author 徐萌阳(xumengyang)
 * @date 2013-12-20下午6:03:29
 */
public class HttpContants {
	
	public static class IP{
		public static final String CYOU_WRAP_IP = "http://member.changyou.com/";//WrapIP
	}
	
	private static String getIP(){
		return SDKConfig.DEBUG ? 
				"http://tgateway.changyou.com" 
				: "http://gateway.changyou.com";
//				"http://180.149.154.73"
//				: "http://180.149.154.73";
	}
	
	/**
	 * 获取接口地址，该方法会根据SDKConfig.DEBUG判断返回正式服还是测试服地址
	 * @param key
	 * @return
	 */
	public static String getURL(String key){
		return getIP()+key;
	}
	
	//畅游用户登录
	public static String CHANGYOU_LOGIN =  "/gateway/cyou/user/login.json";
	//
	public static String CHANGYOU_LOGIN_NEW =  "/gateway/cyou/orangelo/account/changyouLogin.json";
	//橙柚用户登录
	public static String CHENGYOU_LOGIN =  "/gateway/cyou/orangelo/account/login.json";
	//橙柚自动获取账号
	public static String CHENGYOU_AUTO_GET_CN =  "/gateway/cyou/orangelo/account/autoCreateCn.json";
	//橙柚手机号注册
	public static String CHENGYOU_PHONE_REGISTER =  "/gateway/cyou/orangelo/account/cellphone/register.json";
	//橙柚获取验证码
	public static String CHENGYOU_AUTH_CODE =  "/gateway/cyou/orangelo/account/cellphone/verify.json";
	//橙柚用户名注册
	public static String CHENGYOU_USER_REGISTER =  "/gateway/cyou/orangelo/account/userDefinedRegister.json";
	//登录token验证
	public static String TOKEN_VERIFY =  "/gateway/cyou/orangelo/account/tokenVerify.json";
	//激活码验证
	public static String ACTIVATE_CODE_USE =  "/gateway/cyou/user/activate/use.json";
	//是否需要激活
	public static String ACTIVATE_CODE_VERIFY =  "/gateway/cyou/user/activate/verify.json";
	//畅游用户协议
	public static String CNANGYOU_PROTOCOL = "http://member.changyou.com/inc/useragreement.html";
	//橙柚用户协议
	public static String CNENGYOU_PROTOCOL = "http://i.cy.com/useragreement.html";
	//畅游忘记密码
	public static String CNANGYOU_FORGET_PASSWORD = "http://member.changyou.com/common/codeAdmin.do";
	//橙柚忘记密码
	public static String CNENGYOU_FORGET_PASSWORD = "http://i.cy.com/retrievepwd";
	//橙柚没有激活码
	public static String CNENGYOU_ACTIVATE_CODE = "http://i.cy.com/sdk/bbs";

	//商品列表
	public static String PAYMENT_GOODS =  "/gateway/cyou/goods/goodslist.json";
	//订单核对
	public static String ORDER_CHECK =  "/gateway/cyou/order/verify.json";
	//订单生成
	public static String CREATE_ORDER =  "/gateway/cyou/order/create.json";
	//服务器订单生成
	public static String CREATE_ORDER_FROM_SERVER =  "/gateway/cyou/order/serverCreate.json";
	//获取订单列表
	public static String ORDER_LIST =  "/gateway/cyou/order/query.json";
	//订单验证
	public static String VERIFY_ORDER =  "/gateway/cyou/order/verify.json";
	//工单类型
	public static String WORK_ORDER_TYPE =  "/gateway/cyou/customers/workordertype/query.json";
	//工单查询
	public static String WORK_ORDER_LIST =  "/gateway/cyou/customers/workorder/query.json";
	//工单录入
	public static String WORK_ORDER_IMPORT =  "/gateway/cyou/customers/workorder/create.json";
	//手机注册
	public static String TEL_REGIST = "/gateway/cyou/account/cellphone/register.json";
	//邮箱注册
	public static String MAIL_REGIST = "/gateway/cyou/account/email/register.json";
	//支付宝wrap支付
	public static String ALIPAY_WRAP = "/pay/alipayapi.jsp";
	//一卡通支付
	public static String ONE_CARD_PAY = "/pay/sdkOneCardPayAction";
	//易宝支付
	public static String YEE_PAY = "/pay/sdkYeePayAction";
	//MO9支付
	public static String MO9_PAY = "/pay/GetTargetUrlServlet";
	//骏网支付
	public static String JUN_PAY = "/pay/SdkHeepayServlet";
	//获取手机验证码
	public static String GET_VALIDATE_NUM =  "/gateway/cyou/account/cellphone/verify.json";
	//MDO
	public static String MDO =  "/gateway/cyou/mdo/msgcontent/msgquery.json";
	//全局参数
	public static String GLOBAL_PARAMS =  "/gateway/cyou/sys/maintainmap/query.json";
	//银联获取tn流水号
	public static String UPPAY_TN =  "/gateway/cyou/order/upmp/tn.json";
	//财付通支付地址
	public static String TENPAY = "/pay/tenpay/payRequest.jsp";
	//游戏更新地址
	public static String GAME_UPDATE_URL = "/gateway/cyou/version/getNewVersion.json";
	//橙柚图片上传
	public static String UPLOAD_IMG_URL = "http://file.cy.com/upload";
	//橙柚游戏圈分享发表
	public static String GAME_CIRCLE_SHARE = "http://i.cy.com/sdk/gameshare/release";
	//在线IM地址
	public static String CTS_IM_URL = "http://im.changyou.com/live800/chatClient/chatbox.jsp?companyID=8937&configID=11&skillId=2&enterurl=skd";
	//在线IM断开连接地址
	public static String CTS_IM_DISCONNECT_URL = "http://im.changyou.com/live800/ChaterServer?cmd=202&visitorIDInSession=8937chater";
	//360登录验证
	public static String QIHOO_LOGIN = 
			"http://10.12.16.240:8080"
			//IP.HOST_IP 
			+ "/gateway/cyou/sdk/qihoo/login.json";
	//OPPO渠道充值回调URL
	public static String getOppoPayNotifyUrl(){
		return SDKConfig.DEBUG ? 
				"http://servicebilling.changyou.com/service/sdkOppoPaylist"
				:"http://servicebilling.changyou.com/service/sdkOppoPaylist";
	}
	
	//UC渠道充值回调URL
	public static String getUCNotifyUrl() {
		return SDKConfig.DEBUG?"http://tservicebilling.changyou.com/service/sdkUcPaylist"
				:"http://servicebilling.changyou.com/service/sdkUcPaylist";
	}
	//联想渠道充值回调URL
	public static String getLenovoNotifyUrl() {
		return SDKConfig.DEBUG?"" +
				"http://tservicebilling.changyou.com/service/sdkLenovoPaylist"
				//"http://t.changyou.com/service/sdkLenovoPaylist"
				:"http://servicebilling.changyou.com/service/sdkLenovoPaylist";
	}
	
	
	//当乐渠道充值回调URL
	public static String getDownJoyNotifyUrl() {
		return SDKConfig.DEBUG?"" +
				"http://tservicebilling.changyou.com/service/sdkDanglePaylist"
				//"http://t.changyou.com/service/sdkDanglelist"
				:"http://servicebilling.changyou.com/service/sdkDanglePaylist";
	}
	
	//华为渠道充值回调URL
	public static String getHuaweiNotifyUrl() {
		return SDKConfig.DEBUG?"http://tservicebilling.changyou.com/service/sdkHwPaylist":
			"http://servicebilling.changyou.com/service/sdkHwPaylist";
	}
	//一卡通充值回调URL
	public static String getOneCardPayNotifyUrl() {
		return SDKConfig.DEBUG?"http://tservicebilling.changyou.com/service/sdkOneCardPayCallBackAction":
			"http://servicebilling.changyou.com/service/sdkOneCardPayCallBackAction";
	}
	//财付通回调
	public static String getTenpayNotifyUrl(){
		return SDKConfig.DEBUG?"http://tservicebilling.changyou.com/service/SdkTenpayNotifyServlet":
			"http://servicebilling.changyou.com/service/SdkTenpayNotifyServlet";
	}
	//财付通支付完成回调
	public static String getTenpayCallBackUrl(){
		return SDKConfig.DEBUG?"http://tgateway.changyou.com/pay/tenpay/callback_url.jsp":
			"http://gateway.changyou.com/pay/tenpay/callback_url.jsp";
	}
	//财付通支付完成回调
	public static String getMo9ReturnUrl(){
		return SDKConfig.DEBUG?"http://tgateway.changyou.com/pay/mo9/callback_url.jsp":
			"http://gateway.changyou.com/pay/mo9/callback_url.jsp";
	}
	
	
		//微信支付完成回调的地址
	public static String getWeixinPayReturnUrl(){
		return  SDKConfig.DEBUG? "http://tservicebilling.changyou.com/service/sdkWeChatPaylist":
			  "http://tservicebilling.changyou.com/service/sdkWeChatPaylist";
	}
	//微信支付从billing先获取token的请求地址
	public static String getWeixinPayTokenUrl(){
		return  SDKConfig.DEBUG?"http://tgateway.changyou.com/pay/WeChatPayServlet":
			"http://gateway.changyou.com/pay/WeChatPayServlet";
	}
	
	//微信支付从billing先获取sign的请求地址
	public static String getWeixinPaySignUrl(){
		return  SDKConfig.DEBUG?"http://tgateway.changyou.com/pay/WeChatSignServlet":
			"http://gateway.changyou.com/pay/WeChatSignServlet";
	}
}
