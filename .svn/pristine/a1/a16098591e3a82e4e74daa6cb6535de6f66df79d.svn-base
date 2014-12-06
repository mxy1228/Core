package com.changyou.mgp.sdk.mbi.pay.alipay;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088011401787324";
	
	// 交易安全检验码，由数字和字母组成的32位字符串
	// 如果签名方式设置为“MD5”时，请设置该参数
	public static String key = "la8hpx268fqvkdifjml34ijrvk7sh6fu";
	
    // 商户的私钥
    // 如果签名方式设置为“0001”时，请设置该参数
	public static String private_key = "";
			//"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJXul4W2307v2EuDtrZsEqcQrFSXsKVPes/X7IOSupADnoWDyIutRG0+PGkt526oHk3e2g0XJcqMTd81ocoRz5ohkM1nqgtSb7g1mdv02OwevQThH5ERDpXOz8O0C7LdZXroynFIEzMNSDwG9OEXGGKBktyOKCNmj6SnGZ6C22GFAgMBAAECgYBmOAFz92atwZWcflMuceYRoqtrDPQw8EwRseudFIc/Mkh8TImIuPuC40B6kQJNDi+C9aGB9TsdqaRZvo3sX1JCftzcpWhImdvuyRsKYXj1570wO/J9tcepr7BMdW6M3hJ1KWHX6/GQ5+BkiW6bgIWk4oKEaNXG8Z+QXgx97/gvAQJBAMcegPCL6wKSq+FThE5XP8RylvmT7xgVfwRmj2L0GqkNRJ54D2+MKg2pIQuG+AHRUuM4QTHBfaC0wDoD2sHEQMUCQQDAww2a+Su906jXhAveeeJ7Jh8g9OchwOQw/3FNm8uhAi0ua0lgVjzh469pZRfYujLk7XuymyR9csmItqer2ynBAkEAqpfDuFLnoWivwXigHc46X8AdAO5xJZ0lGUwVBJ5GSReI9ou+Db90OAfyu8GCsWv5K5qCoHI5g3nYRtwOi3vsUQJBALg8NU6/hE0kdaW2sVlsCUlGwE3RhO+/2tnx66sPkupKmEBbjoAjLIGCCm+jjc04+dfQG4AzxAaapYCjg1JAYIECQF3Kbfy6ia733g8FmS/iERhUkefqRNiqUvccCXIYaaLcj1ErUzf4w9t7JPLkNTRIDjRbpFHe1L6EPKiO7HCsb+E=";

    // 支付宝的公钥
    // 如果签名方式设置为“0001”时，请设置该参数
	public static String ali_public_key = "";
			//"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";

	// 字符编码格式 目前支持  utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式，选择项：0001(RSA)、MD5
	public static String sign_type = "MD5";
	// 无线的产品中，签名方式为rsa时，sign_type需赋值为0001而不是RSA
	
	//公钥
	public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCV7peFtt9O79hLg7a2bBKnEKxUl7ClT3rP1+yDkrqQA56Fg8iLrURtPjxpLeduqB5N3toNFyXKjE3fNaHKEc+aIZDNZ6oLUm+4NZnb9NjsHr0E4R+REQ6Vzs/DtAuy3WV66MpxSBMzDUg8BvThFxhigZLcjigjZo+kpxmegtthhQIDAQAB";
	//私钥
	public static final String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJXul4W2307v2EuDtrZsEqcQrFSXsKVPes/X7IOSupADnoWDyIutRG0+PGkt526oHk3e2g0XJcqMTd81ocoRz5ohkM1nqgtSb7g1mdv02OwevQThH5ERDpXOz8O0C7LdZXroynFIEzMNSDwG9OEXGGKBktyOKCNmj6SnGZ6C22GFAgMBAAECgYBmOAFz92atwZWcflMuceYRoqtrDPQw8EwRseudFIc/Mkh8TImIuPuC40B6kQJNDi+C9aGB9TsdqaRZvo3sX1JCftzcpWhImdvuyRsKYXj1570wO/J9tcepr7BMdW6M3hJ1KWHX6/GQ5+BkiW6bgIWk4oKEaNXG8Z+QXgx97/gvAQJBAMcegPCL6wKSq+FThE5XP8RylvmT7xgVfwRmj2L0GqkNRJ54D2+MKg2pIQuG+AHRUuM4QTHBfaC0wDoD2sHEQMUCQQDAww2a+Su906jXhAveeeJ7Jh8g9OchwOQw/3FNm8uhAi0ua0lgVjzh469pZRfYujLk7XuymyR9csmItqer2ynBAkEAqpfDuFLnoWivwXigHc46X8AdAO5xJZ0lGUwVBJ5GSReI9ou+Db90OAfyu8GCsWv5K5qCoHI5g3nYRtwOi3vsUQJBALg8NU6/hE0kdaW2sVlsCUlGwE3RhO+/2tnx66sPkupKmEBbjoAjLIGCCm+jjc04+dfQG4AzxAaapYCjg1JAYIECQF3Kbfy6ia733g8FmS/iERhUkefqRNiqUvccCXIYaaLcj1ErUzf4w9t7JPLkNTRIDjRbpFHe1L6EPKiO7HCsb+E=";
	//合作者身份ID
	public static final String PARTNER = "partner";
	//畅游在支付宝的合作者ID
	public static final String PARTNER_VALUE = "2088011401787324";
	//接口名称
	public static final String SERVICE = "service";
	//接口名称Value
	public static final String SERVICE_VALUE = "mobile.securitypay.pay";
	//参数编码字符集
	public static final String INPUT_CHARSET = "_input_charset";
	//参数编码字符集Value
	public static final String INPUT_CHARSET_VALUE = "utf-8";
	//签名方式
	public static final String SIGN_TYPE = "sign_type";
	//签名方式Value
	public static final String SIGN_TYPE_VALUE = "RSA";
	//服务器异步通知页面路径
	public static final String NOTIFY_URL = "notify_url";
//			"http://www.mgpsdk.pay.success";
//					"http://servicebilling.changyou.com/service/alipaylist";//正式
	//签名
	public static final String SIGN = "sign";
	//订单号
	public static final String OUT_TRADE_NO = "out_trade_no";
	//商品名称‘
	public static final String SUBJECT = "subject";
	//支付类型
	public static final String PAYMENT_TYPE = "payment_type";
	//支付类型Value
	public static final String PAYMENT_TYPE_VALUE = "1";
	//卖家支付宝账号
	public static final String SELLER_ID = "seller_id";
	//畅游支付宝账号
	public static final String SELLER_ID_VALUE = "shouyou_pc@sohu.com";
	//总金额
	public static final String TOTAL_FEE = "total_fee";
	//商品详情
	public static final String BODY = "body";
	//未付款交易的超时时间
	public static final String IT_B_PAY = "it_b_pay";
	//未付款交易的超时时间Value
	public static final String IT_B_PAY_VALUE = "30m";
	//安全校验码
	public static final String S_KEY = "la8hpx268fqvkdifjml34ijrvk7sh6fu";
	//wrap支付支付宝默认访问地址
	public static final String ALIPAY_GATEWAY_NEW = "http://wappaygw.alipay.com/service/rest.htm?";
	//wrap支付Service
	public static final String WRAP_SERVICE = "alipay.wap.trade.create.direct";
	//信用卡支付URL
	public static String CREDIT_CARD_URL = "http://wappaygw.alipay.com/cashier/cashier_gateway_pay.htm?channelType=OPTIMIZED_MOTO&awid=";
////	//银行卡支付URL
	public static String SAVING_CARD_URL = "http://wappaygw.alipay.com/cashier/cashier_gateway_pay.htm?channelType=DEBIT_EXPRESS&awid=";
//	//信用卡/银行卡支付EndUrl
	public static final String CREDIT_SAVING_CARD_END_URL = "https://wapcashier.alipay.com/cashier/ex_gateway_cashier.htm";
//	//服务异步通知url
	public static final String NOTIFY_URL_VALUE = 
				"http://servicebilling.changyou.com/service/sdkAlipayPayList";
//	//信用卡支付页面&银行卡支付页面被拦截的登录url
	public static String OVERLOAD_LOGIN_URL = "https://wappaygw.alipay.com/cashier/wapcashier_login.htm";
//				"http://wappaygw.alipay.com/cashier/wapcashier_login.htm;jsessionid=";
//	//信用卡支付页面&银行卡支付页面被拦截的确认登录url
	public static String OVERLOAD_CONFIRM_LOGIN_URL = "https://wappaygw.alipay.com/cashier/wapcashier_confirm_login.htm";
//	//Wrap支付操作中断返回地址
	public static final String MERCHANT_URL = "http://www.mgpsdk.pay.cancel/";
//	//Wrap支付成功回调地址
	public static String SDK_ALI_WAP_PAY = "http://t.changyou.com/service/sdkAliWapPayList";
	//银行卡支付输入信息页面
	public static final String DEBIT_CARD_INPUT_INFO_URL = "https://wapcashier.alipay.com/gateway/gateway_debit_card.htm";
	//信用卡支付输入信息页面
	public static final String CREDIT_CARD_INPUT_INFO_URL = "http://wapcashier.alipay.com/home/common_check_code.htm";
	//输入短信验证码页面
	public static final String CARD_INPUT_VALIDATE_NUM_URL = "https://wapcashier.alipay.com/gateway/gateway_credit_card.htm";
	//快捷支付回调地址
	public static final String QUICK_ALIPAY_NOTIFY_URL = "http://servicebilling.changyou.com/service/sdkAliPayNotitylist";
	/**
	 * 
	 * 功能描述：支付方式标记
	 *
	 * @author 徐萌阳(xumengyang)
	 *
	 * @date 2014-2-18
	 */
	public static class WRAP_WAY{
		public static final int ALIPAY = 0;//wrap支付方式的支付宝支付
//		public static final int CREDIT_CARD = 0;//wrap支付方式的信用卡支付
//		public static final int SAVING_CARD = 1;//wrap支付方式的银行卡支付
	}
	
}
