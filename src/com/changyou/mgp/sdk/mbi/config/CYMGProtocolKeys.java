package com.changyou.mgp.sdk.mbi.config;

/**
 * 
 * 功能描述：对外开放接口参数
 *
 * @author 徐萌阳(xumengyang)
 *
 * @date 2014-2-24
 */
public class CYMGProtocolKeys {

	public static final String UID = "uid";
	public static final String PID = "pid";//只有360支付时使用
	public static final String TOKEN = "token";
	public static final String OID = "oid";//360支付时使用
	public static final String USERNAME = "username";//目前只有机锋登录返回该字段
	public static final String TOKEN_SECRET = "token_secret";//只有oppo使用
	public static final String APP_ID = "app_id";
	public static final String APP_KEY = "app_key";
	public static final String APP_SECRET = "app_secret";
	public static final String CHANNEL_ID = "channel_id";
	public static final String CHANNEL_NAME = "channel_name";
	public static final String TYPE = "type";
	public static final String ROLE_NAME = "role_name";//游戏角色名称
	public static final String GAME_NAME = "game_name";//游戏名称
	public static final String SERVER_NAME = "server_name";//服务器名称
	public static final String STATE_CODE = "state_code";
	public static final String MSG = "message";
	public static final String ORDER_ID = "order_id";//支付功能回传的订单号
	public static final String PRICE = "price";//支付功能回传的价格
	public static final String ISDEBUG = "isdebug";//支付功能回传的价格
	
	
	public static final String APP_NAME = "app_name";
	public static final String PUSH_INFO = "pushInfo";//调用创建订单接口时使用，由游戏从外部传进来
	public static final String OPCODE = "opcode";
	public static final String ROLE_ID = "role_id";//角色id，游戏调用支付功能时当做一个必须参数传进来
	public static final String GROUP_ID = "group_id";//服务器id，游戏调用支付功能时当做一个必须参数传进来
	public static final String SHOW_FLOAT_WINDOW = "show_float_window";//是否显示悬浮窗
	public static final String FLOAT_X = "float_x";//悬浮窗显示时的X坐标值
	public static final String FLOAT_Y = "float_y";//悬浮窗显示时的Y坐标值
	public static final String COMMON_API_CODE = "common_api_code";//渠道个性接口标识
	public static final String PAYMENT_METHOD = "pay_method_type";//根据该值判断打开哪个页面。1-包含商品列表；2-直接打开支付方式；3-充值记录
	//直接调用支付方式所需
	public static final String GOODS_ID = "goods_id";//支付功能回传的商品id
	public static final String GOODS_REGIST_ID = "goods_regist_id";
	public static final String GOODS_NUMBER = "goods_num";
	public static final String GOODS_PRICE = "goods_price";
	public static final String GOODS_NAME = "goods_name";//支付功能回传的商品名称
	public static final String GOODS_DESC = "goods_describe";//支付功能回传的商品描述
	//直接调用支付方式所需
	
	//小米
	public static final String BALANCE = "balance";//余额
	public static final String VIP = "vip";//vip等级
	public static final String LV = "level";//等级
	public static final String PARTY_NAME = "party_name";//公会名称
	public static final String DATA = "data";
	
	
	public static final String QIHOO_AUTHORIZATION_CODE = "authorization_code";//360登录返回的authorization code
	
	//联想
	public static final String LPSUST="lpsust";
	public static final String REALM="realm";
	public static final String APPKEY_LENOVO="appkey_lenovo";
	
	//当乐
	public static final String APPKEY_DOWNJOY="appkey_dangle";
	public static final String APP_ID_DOWNJOY="appId";
	public static final String UID_DOWNJOY="uid";
	public static final String  CHANNLEID_DOWNJOY="channelId";
	public static final String TAG_DOWNJOY="tag";
	public static final String SIGN_DOWNJOY="sign";
	
	
	//多酷
	public static final String APP_KEY_DUOKU="appkey_duoku";
	public static final String APP_ID_DUOKU="appid";
	public static final String APP_SCERET_DUOKU="appsecret_duoku";
	public static final String APP_SESSIONID_DUOKU="sessionId";
	//OPPO
	public static final String APP_KEY_OPPO="oppo_appkey";
	public static final String APP_SCERET_OPPO="oppo_appsecret";
	public static final String APP_ID_OPPO="appid";
	public static final String APP_TOKEN_OPPO="token_key";
	//MI
	public static final String APP_SESSION_MI="session";
	public static final String APP_ID_MI="miappid";
	public static final String APP_KEY_MI="miappkey";
	//Anzhi
	public static final String APP_ACCOUNT_ANZHI="account";
	public static final String APP_SID_ANZHI="sid";
	public static final String APP_KEY_ANZHI="anzhi_appkey";
	public static final String APP_SECRET_ANZHI="anzhi_appsecret";
	//UC
	public static final String UC_FLAG="flag";
	public static final String UC_CPID="cpId";
	public static final String UC_GAMEID="gameId";
	public static final String UC_SERVERID="serverId";
	public static final String UC_SID="sid";
	public static final String UC_APIKEY="apiKey";
	public static final String UC_CHANNELID="channelId";
	
	//WDJ
	public static final String WDJ_APPKEY_ID="appkey_id";
	public static final int WDJ_PAY_REQUEST_CODE = 2;
	//360
	public static final String QIHOO_CODE="code";
	public static final String QIHOO_CLIENT_ID="client_id";
	public static final String QIHOO_CLIENT_SECRET="client_secret";
	public static final String QIHOO_FLAG="flag";
	//调用360渠道的注销接口，需传该值以区分是直接注销还是切换账号，默认是false
	public static final String QIHOO_IS_SWITCH_ACCOUNT = "qihoo_is_switch_account";
	//360渠道支付扩展参数1
	public static final String QIHOO_PAY_EXT_1 = "qihoo_pay_ext_1";
	//360渠道支付扩展参数2
	public static final String QIHOO_PAY_EXT_2 = "qihoo_pay_ext_2";
	//91
	public static final String ND_UIN="uin";
	public static final String ND_SESSIONID="sessionId";
	public static final String ND_APPID="appId";
	public static final String ND_APPKEY="appkey_91";
	//cy
	public static final String CY_QQ_APPID="appid";
	
	
	public static final String USERIP="userip";
	public static final String DEVICEID="deviceid";
	
	
}
