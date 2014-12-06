package com.changyou.mgp.sdk.mbi.pay.ui;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.http.Header;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.changyou.mgp.sdk.mbi.account.id.AccResource;
import com.changyou.mgp.sdk.mbi.channel.CYMGChannel;
import com.changyou.mgp.sdk.mbi.channel.CYMGChannelEntity;
import com.changyou.mgp.sdk.mbi.config.CYMGProtocolConfig;
import com.changyou.mgp.sdk.mbi.config.CYMGProtocolKeys;
import com.changyou.mgp.sdk.mbi.config.Contants;
import com.changyou.mgp.sdk.mbi.config.HttpContants;
import com.changyou.mgp.sdk.mbi.config.Params;
import com.changyou.mgp.sdk.mbi.config.SDKConfig;
import com.changyou.mgp.sdk.mbi.entity.ErrorInfo;
import com.changyou.mgp.sdk.mbi.entity.GoodsItem;
import com.changyou.mgp.sdk.mbi.entity.GoodsList;
import com.changyou.mgp.sdk.mbi.entity.UPayResultEvent;
import com.changyou.mgp.sdk.mbi.http.MyAsyncResponseHandler;
import com.changyou.mgp.sdk.mbi.http.MyHttpClient;
import com.changyou.mgp.sdk.mbi.log.CYLog;
import com.changyou.mgp.sdk.mbi.mbi.manager.CMBILogManager;
import com.changyou.mgp.sdk.mbi.pay.adapter.GoodsListAdapter;
import com.changyou.mgp.sdk.mbi.pay.id.PayResource;
import com.changyou.mgp.sdk.mbi.platform.CYMGChannelHelper;
import com.changyou.mgp.sdk.mbi.platform.CYMGPayHelper;
import com.changyou.mgp.sdk.mbi.ui.base.BaseFragment;
import com.changyou.mgp.sdk.mbi.ui.widget.WaitingDialog;
import com.changyou.mgp.sdk.mbi.ui.widget.WaitingDialog.MyDialogDismissListener;
import com.changyou.mgp.sdk.mbi.utils.JSONUtil;
import com.changyou.mgp.sdk.mbi.utils.MetaDataValueUtils;
import com.changyou.mgp.sdk.mbi.utils.MyToast;
import com.changyou.mgp.sdk.mbi.utils.NetWorkUtils;
import com.changyou.mgp.sdk.mbi.utils.SettingSPUtil;
import com.changyou.mgp.sdk.mbi.utils.SignUtils;
import com.changyou.mgp.sdk.mbi.utils.StringUtils;
import com.changyou.mgp.sdk.mbi.utils.SystemUtils;
import com.changyou.mgp.sdk.mbi.utils.UserInfoSPUtil;
import com.loopj.android.http.RequestParams;

import de.greenrobot.event.EventBus;
/**
 * 
 * 功能描述：商品列表界
 *
 * @author 严峥(yanzheng)
 * @date 2014-1-20 上午10:27:52
 * 修改历史�?修改人，修改时间，修改原�?内容)
 */
public class CYMGPaymentFragment extends BaseFragment implements OnClickListener{
	
	private CYLog log = CYLog.getInstance();
	
	private Activity mContext;
	
//	private NetErrorView mNetErrorView;
//	private LinearLayout mNullDataView;
//	private Button mRefreshBtn;
//	private ListView mListView;
	private WaitingDialog mWaitingDialog;
	private GridView mGV;
	private RelativeLayout mTopRL;
//	private ImageButton mADIBtn;
	private ImageButton mCloseIBtn;
	private ImageButton mRecoredIBtn;
	private ViewStub mNetErrorView;
	private Button mNetErrorRefreshBtn;
	private ImageView mTopIV;
	private LinearLayout mContentLL;//该LL只有在横版显示时才用到
//	private View mTitle;
//	private ImageButton mServiceImgBtn;
//	private TextView mTitleTv;
//	private ImageButton mBackIBtn;
//	private Button mServiceBtn;
	
	private List<GoodsItem> mGoodsList;
	private GoodsListAdapter mAdapter;
	private MyHttpClient mGoodsRequestHttp;
	private MyHttpClient mCreateOrderHttp;
	
	private String mUID;
	private int mGoodsWidthLand;//横向显示时商品图片宽度
	private boolean mIsLandScape;//是否横线显示
	private Bundle mBundle;
	private int mGrayLLHeightLand;//横线显示时灰色半透明框的高度
	private boolean mCreating;//控制狂点商品频繁创建订单
	private Bitmap mTopBtm;
	private Bitmap mCloseBtm;
	private Bitmap mRecordBtm;
	private WaitingDialog mDialog;
//	private Bitmap mADBtm;
	
	private boolean isConfigurationChanged = false;
	
	@Override
	public void onAttach(Activity activity) {
		log.i("onAttach");
		super.onAttach(activity);
		this.mContext=activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		log.i("onCreateView");
		//读取屏幕显示方向，不同的显示方向在页面初始化时会有不同
		mIsLandScape = SettingSPUtil.getLandScape(mContext) == CYMGProtocolConfig.LANDSCAPE;
		View view = null;
		if(mIsLandScape){
			view = inflater.inflate(PayResource.getInstance(mContext).mgp_payment_1_08,container,false);
		}else{
			view = inflater.inflate(PayResource.getInstance(mContext).mgp_payment_1_08,container,false);
		}
		initView(view);
		initData();
		initEvent();
		return view;
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		log.i("onConfigurationChanged");
		ViewGroup parentView = (ViewGroup) getView();
		parentView.removeAllViews();
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(PayResource.getInstance(mContext).mgp_payment_1_08,parentView, false);
		initView(view);
		parentView.addView(view);
		initData();
		initEvent();
	}
	
	@Override
	public void onResume() {
		log.i("onResume");
		super.onResume();
	}
	
	@Override
	public void onDestroy() {
		log.i("onDestroy");
		super.onDestroy();
		mWaitingDialog.destory();
		if(mTopBtm != null){
			mTopBtm.recycle();
		}
		if(mCloseBtm != null){
			mCloseBtm.recycle();
		}
		if(mRecordBtm != null){
			mRecordBtm.recycle();
		}
		if(mGoodsRequestHttp != null){
			mGoodsRequestHttp.cancelRequests(mContext, true);
		}
		if(mCreateOrderHttp != null){
			mCreateOrderHttp.cancelRequests(mContext, true);
		}
//		if(mADBtm != null){
//			mADBtm.recycle();
//		}
	}
	
	private void initView(View view){
		this.mDialog = new WaitingDialog(mContext);
		this.mWaitingDialog = new WaitingDialog(mContext);
		this.mWaitingDialog.setMessage(mContext.getString(PayResource.getInstance(mContext).mgp_loading));
		this.mGV = (GridView)view.findViewById(PayResource.getInstance(mContext).cymg_payment_gv);
		this.mGV.setSelector(new ColorDrawable(Color.TRANSPARENT));
		this.mTopRL = (RelativeLayout)view.findViewById(PayResource.getInstance(mContext).cymg_payment_top_rl);
//		this.mADIBtn = (ImageButton)view.findViewById(PayResource.getInstance(mContext).cymg_payment_adv_ibtn);
		this.mCloseIBtn = (ImageButton)view.findViewById(PayResource.getInstance(mContext).cymg_payment_close_ibtn);
		this.mRecoredIBtn = (ImageButton)view.findViewById(PayResource.getInstance(mContext).cymg_payment_record_ibtn);
		LinearLayout.LayoutParams netErrorlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		netErrorlp.gravity = Gravity.CENTER;
		this.mNetErrorView = (ViewStub)view.findViewById(PayResource.getInstance(mContext).cymg_payment_net_error_view);
		this.mNetErrorView.inflate();
		this.mNetErrorView.setVisibility(View.GONE);
		this.mNetErrorView.setLayoutParams(netErrorlp);
		this.mNetErrorRefreshBtn = (Button)view.findViewById(PayResource.getInstance(mContext).mgp_payment_net_error_refresh_btn);
		this.mTopIV = (ImageView)view.findViewById(PayResource.getInstance(mContext).cymg_payment_top_iv);
//		if(mIsLandScape){
		this.mContentLL = (LinearLayout)view.findViewById(PayResource.getInstance(mContext).cymg_payment_content_ll_land);
//		}
		int sWidth = SystemUtils.getScreenWidthSize(mContext);
		int closeIBtnWidth;
		int closeIBtnHeight;
		int recordIBtnWidth;
		int recordIBtnHeight;
		if(mIsLandScape){
			//横向显示
			//计算头部RL的高度
			int topIVHeight = (int)(sWidth / SDKConfig.PAYMENT_TOP_IV_SCALE_LAND);
			android.widget.LinearLayout.LayoutParams topParams = new android.widget.LinearLayout.LayoutParams(sWidth, topIVHeight);
			this.mTopRL.setLayoutParams(topParams);
			int sHeight = SystemUtils.getScreenHeightSize(mContext);
			//计算显示商品列表的GV及父控件（灰色半透明）的高度，将根据该高度计算商品图片的高度及广告条的高度
			mGrayLLHeightLand = sHeight - topIVHeight 
					- mContext.getResources().getDimensionPixelOffset(PayResource.getInstance(mContext).mgp_payment_gv_margin_land);
			android.widget.LinearLayout.LayoutParams contentParam = new android.widget.LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, mGrayLLHeightLand);
			contentParam.leftMargin = mContext.getResources().getDimensionPixelOffset(PayResource.getInstance(mContext).mgp_payment_gv_margin_land);
			contentParam.rightMargin = mContext.getResources().getDimensionPixelOffset(PayResource.getInstance(mContext).mgp_payment_gv_margin_land);
			mContentLL.setLayoutParams(contentParam);
			int goodsPicHeight = (int)(mGrayLLHeightLand * SDKConfig.PAYMENT_GOODS_PIC_SCALE_IN_LAND);
			mGoodsWidthLand = (int)(goodsPicHeight * SDKConfig.PAYMENT_GOODS_PIC_SCALE);
			closeIBtnWidth = (int)(sWidth * SDKConfig.PAYMENT_CLOSE_IN_SCREEN_SCALE_LAND);
			closeIBtnHeight = (int)(closeIBtnWidth / SDKConfig.PAYMENT_CLOSE_SCALE);
			recordIBtnWidth = (int)(sWidth * SDKConfig.PAYMENT_RECORD_IN_SCREEN_SCALE_LAND);
			recordIBtnHeight = (int)(recordIBtnWidth / SDKConfig.PAYMENT_RECORD_SCALE);
		}else{
			//竖向显示
			//计算头部的RL的高度
			int topIVHeight = sWidth / SDKConfig.PAYMENT_TOP_IV_SCALE;
			android.widget.LinearLayout.LayoutParams topParams = new android.widget.LinearLayout.LayoutParams(sWidth, topIVHeight);
			this.mTopRL.setLayoutParams(topParams);
			closeIBtnWidth = (int)(sWidth * SDKConfig.PAYMENT_CLOSE_IN_SCREEN_SCALE);
			closeIBtnHeight = (int)(closeIBtnWidth / SDKConfig.PAYMENT_CLOSE_SCALE);
			recordIBtnWidth = (int)(sWidth * SDKConfig.PAYMENT_RECORD_IN_SCREEN_SCALE);
			recordIBtnHeight = (int)(recordIBtnWidth / SDKConfig.PAYMENT_RECORD_SCALE);
		}
		//根据屏幕宽度计算关闭按钮高度和宽度
		android.widget.RelativeLayout.LayoutParams closeParam = new android.widget.RelativeLayout.LayoutParams(closeIBtnWidth, closeIBtnHeight);
		closeParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
		closeParam.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
		this.mCloseIBtn.setScaleType(ScaleType.FIT_XY);
		this.mCloseIBtn.setLayoutParams(closeParam);
		//根据屏幕宽度计算充值记录按钮的高度和宽度
		android.widget.RelativeLayout.LayoutParams recordParam = new android.widget.RelativeLayout.LayoutParams(recordIBtnWidth, recordIBtnHeight);
		recordParam.addRule(android.widget.RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		recordParam.addRule(android.widget.RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		this.mRecoredIBtn.setScaleType(ScaleType.FIT_XY);
		this.mRecoredIBtn.setLayoutParams(recordParam);
		initCtsImg();
	}

	@Override
	protected void initData() {
		this.mBundle= getArguments();
		this.mUID = mBundle.getString(CYMGProtocolKeys.UID);
		if(TextUtils.isEmpty(this.mUID) || this.mUID == null){
			log.e("uid is null");
			showNetErrorView();
			return;
		}
		
		if(mGoodsList==null){
			mGoodsList = new ArrayList<GoodsItem>();
		}
		if(mAdapter==null){
			this.mAdapter=new GoodsListAdapter(mContext,mGoodsList,mGrayLLHeightLand);
		}
//		this.mListView.setAdapter(mAdapter);
		this.mGV.setAdapter(mAdapter);
		if(mGoodsList.isEmpty()){
			getPaymentGoodsRequest();
		}else{
			setLandGV();
			mAdapter.notifyDataSetChanged();
			showGoodsListView();
		}
	}

	@Override
	protected void initEvent() {
		this.mCloseIBtn.setOnClickListener(this);
		this.mRecoredIBtn.setOnClickListener(this);
		this.mNetErrorRefreshBtn.setOnClickListener(this);
		this.mGV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				GoodsItem mGoodsItem = mGoodsList.get(arg2);
				if(mGoodsItem.getType()==3){
				}else{
				}
				if(checkGoodsItem(mGoodsItem)){
					createOrderFromServer(mBundle,mGoodsItem);
				}else{
					MyToast.showDebugToast(mContext, "goodsItem exception");
				}
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == PayResource.getInstance(mContext).mgp_payment_net_error_refresh_btn){
			getPaymentGoodsRequest();
		}else if(v.getId()==PayResource.getInstance(mContext).cymg_payment_record_ibtn){
			((CYMGPaymentActivity)mContext).changeFragment(Contants.FragmentTag.PAYMENT_ORDER_FRAGMENT_TAG , null);
		}else if(v.getId()==PayResource.getInstance(mContext).cymg_payment_close_ibtn){
			((CYMGPaymentActivity)mContext).goback();
		}
		
	}
	
	/**
	 * 初始化/assets中CP配置的自定义图片资源，并给各个控件设置图片资源
	 */
	private void initCtsImg(){
		Properties pro = new Properties();
		AssetManager am = mContext.getAssets();
		try {
			InputStream is = am.open(CYMGPayHelper.IMG_PRO);
			pro.load(is);
			if(mIsLandScape){
				mTopBtm = BitmapFactory.decodeStream(am.open(pro.getProperty("payment_top_img_land")));
				mCloseBtm = BitmapFactory.decodeStream(am.open(pro.getProperty("payment_close_btn_img_land")));
				mRecordBtm = BitmapFactory.decodeStream(am.open(pro.getProperty("payment_record_btn_img_land")));
			}else{
				mTopBtm = BitmapFactory.decodeStream(am.open(pro.getProperty("payment_top_img")));
				mCloseBtm = BitmapFactory.decodeStream(am.open(pro.getProperty("payment_close_btn_img")));
				mRecordBtm = BitmapFactory.decodeStream(am.open(pro.getProperty("payment_record_btn_img")));
			}
		} catch (Exception e) {
			log.e(e);
		}
		this.mTopIV.setImageBitmap(mTopBtm);
		this.mCloseIBtn.setImageBitmap(mCloseBtm);
		this.mRecoredIBtn.setImageBitmap(mRecordBtm);
	}
	
	/**
	 * 
	 * 功能描述：获取商品列�?
	 *
	 * @author 徐萌�?xumengyang)
	 * @param @param context
	 * @return void
	 * @date 2014-2-25 下午3:22:40
	 *
	 */
	private void getPaymentGoodsRequest(){
		if(NetWorkUtils.isNetworkConnected(mContext)){
			mGoodsRequestHttp = new MyHttpClient(mContext);
			mGoodsRequestHttp.cancelRequests(mContext, true);
			RequestParams map = new RequestParams();
			mGoodsRequestHttp.get(
					HttpContants.getURL(HttpContants.PAYMENT_GOODS)
					, map, new MyAsyncResponseHandler(){
				@Override
				public void onStart() {
					CMBILogManager.printEventLog(mContext, "0", "110050", "");
					if(isDetached()){
						return;
					}
					try {
						mDialog.setDismissListener(mGoodsRequestHttp.getTimeout(),new MyDialogDismissListener() {
							
							@Override
							public void onTimeOutDismiss() {
								showNetErrorView();
							}
						});
						mDialog.show();
					} catch (Exception e) {
						log.e(e);
					}
					
				}
		
				@Override
				public void onSuccess(int statusCode, String content) {
					CMBILogManager.printEventLog(mContext, "0", "120050", "");
					log.d("getPaymentGoodsRequest-----onSuccess,content:"+content);
					if(isDetached()){
						return;
					}
					try {
						mDialog.dismiss();
						if(!StringUtils.isEmpty(content)){
							GoodsList mList=JSONUtil.getMapper().readValue(JSONUtil.initJsonArrayStr(content), new TypeReference<GoodsList>() {
							});
							mGoodsList.clear();
							mGoodsList.addAll(mList.getData());
							setLandGV();
							mAdapter.notifyDataSetChanged();
							if(!mGoodsList.isEmpty()){
								showGoodsListView();
							}
						}else{
							showNetErrorView();
						}
					} catch (Exception e) {
						log.e(e);
					}
				}
				
				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
					CMBILogManager.printEventLog(mContext, "0", "120050", "");
					if(isDetached()){
						return;
					}
					try {
						log.e("onFailure,content:"+arg3.getMessage());
						mDialog.dismiss();
						showNetErrorView();
					} catch (Exception e) {
						log.e(e);
					}
				}
			});
		}else{
			showNetErrorView();
		}
	}
	
	/**
	 * 
	 * 功能描述：检测GoodsItem数据的合法�?
	 *
	 * @author 徐萌�?xumengyang)
	 * @param item
	 * @return boolean
	 * @date 2014-2-10 下午7:59:28
	 *
	 */
	private boolean checkGoodsItem(GoodsItem item){
		if(item == null){
			log.e("item is null");
			return false;
		}else if(TextUtils.isEmpty(item.getGoods_name()) || item.getGoods_name() == null){
			log.e("goodsDescribe is null");
			return false;
		}else if(TextUtils.isEmpty(item.getGoods_price()) || item.getGoods_price() == null){
			log.e("price is null");
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 从服务器获取订单号
	 * @param item
	 */
	private void createOrderFromServer(final Bundle bundle,final GoodsItem item){
		if(mCreating){
			return;
		}
		mCreating = true;
		final String mUID = bundle.getString(CYMGProtocolKeys.UID);
		final String channel_id = MetaDataValueUtils.getChannelID(mContext);
		if(TextUtils.isEmpty(mUID) || mUID == null){
			throw new IllegalAccessError("uid is null");
		}
		if(NetWorkUtils.isNetworkConnected(mContext)){
			mCreateOrderHttp = new MyHttpClient(mContext);
			Map<String,String> params = new HashMap<String, String>();
			String type = UserInfoSPUtil.getType(mContext);
			String account_id = UserInfoSPUtil.getUsername(mContext);
			if(Contants.LoginParams.TYPE_CYOU.equals(type) 
					&& MetaDataValueUtils.getChannelID(mContext) == mContext.getString(AccResource.getInstance(mContext).mgp_channel_cy)){
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
			params.put("push_info", bundle.getString(CYMGProtocolKeys.PUSH_INFO));
			params.put("game_channel", MetaDataValueUtils.getCMBIChannelID(mContext));
			if(bundle.getString(CYMGProtocolKeys.ROLE_ID) != null){
				params.put("role_id", bundle.getString(CYMGProtocolKeys.ROLE_ID));
			}
			if(bundle.getString(CYMGProtocolKeys.GROUP_ID) != null){
				params.put("group_id", bundle.getString(CYMGProtocolKeys.GROUP_ID));
			}
			mCreateOrderHttp.post(HttpContants.getURL(HttpContants.CREATE_ORDER_FROM_SERVER), params, new MyAsyncResponseHandler(){
				@Override
				public void onStart() {
					CMBILogManager.printEventLog(mContext, "0", "110051", "");
					mWaitingDialog.show();
				}
				
				@Override
				public void onSuccess(int statusCode, String content) {
					try {
						CMBILogManager.printEventLog(mContext, "0", "120051", "");
						log.d("onSuccess:content = "+content);
						mWaitingDialog.dismiss();
						if(TextUtils.isEmpty(content)){
							mCreating = false;
							return;
						}
						JSONObject jsonObject = new JSONObject(content);
						String order_id=jsonObject.getString("order_id");
						String orderSign=jsonObject.getString("orderSign");
						String md5_one=SignUtils.stringToMD5(mUID+"|"+order_id+"|"+channel_id);
						String md5_two="";
						if(!TextUtils.isEmpty(md5_one)){
							md5_two=SignUtils.stringToMD5(md5_one.substring(0, 10));
						}
						if(orderSign.equals(md5_two)){
							bundle.putSerializable(Params.GOODSITEM, item);
							bundle.putString(Params.ORDER_ID, order_id);
							bundle.putString(Params.UID, mUID);
							CYMGChannelEntity entity = new CYMGChannelEntity();
							entity.setBundle(bundle);
							entity.setmContext(getActivity());
							entity.setmScreenOrientation(SettingSPUtil.getLandScape(mContext));
							CYMGChannel channelDo = CYMGChannelHelper.getChannel(mContext,MetaDataValueUtils.getChannelID(mContext));
							channelDo.doPay(entity);
							UPayResultEvent e = new UPayResultEvent();
							e.setmItem(item);
							e.setmOrderID(order_id);
							EventBus.getDefault().post(e);
							CMBILogManager.printEventLog(mContext, "0", "110052", "");
						}else{
							MyToast.showToast(mContext, mContext.getString(PayResource.getInstance(mContext).mgp_payment_net_error_toast_txt));
						}
						mCreating = false;
					} catch (Exception e) {
						log.e(e);
					}
				}
				
				@Override
				public void onFailure(Throwable error, String content) {
					try {
					CMBILogManager.printEventLog(mContext, "0", "120051", "");
					log.e("onFailure:content = "+content);
					mCreating = false;
					mWaitingDialog.dismiss();
					ErrorInfo info=JSONUtil.getMapper().readValue(content, new TypeReference<ErrorInfo>() {});
						if(info!=null){
							String msg=info.getClient_message();
							MyToast.showToast(mContext, msg);
							if(info.getError_code()==24007){
								getPaymentGoodsRequest();
							}
						}else{
							MyToast.showToast(mContext, mContext.getString(PayResource.getInstance(mContext).mgp_payment_net_error_toast_txt));
						}
					}  catch (Exception e) {
						log.e(e);
					}
				}
			});
		}else{
			mCreating = false;
			MyToast.showToast(mContext, mContext.getString(PayResource.getInstance(mContext).mgp_payment_net_error_toast_txt));
		}
	}
	
	private void showGoodsListView(){
		this.mNetErrorView.setVisibility(View.GONE);
		this.mGV.setVisibility(View.VISIBLE);
		if(mContentLL != null){
			this.mContentLL.setVisibility(View.VISIBLE);
		}
	}
	
	private void showNetErrorView(){
		this.mNetErrorView.setVisibility(View.VISIBLE);
		this.mGV.setVisibility(View.GONE);
		if(mContentLL != null){
			this.mContentLL.setVisibility(View.GONE);
		}
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	
	private void setLandGV(){
		//只有页面横线显示时才会执行下面的执行逻辑
		if(mIsLandScape){
			//首先计算每个商品Item的宽度
			int goodsItemWidthLand = mGoodsWidthLand 
					+ mContext.getResources().getDimensionPixelOffset(PayResource.getInstance(mContext).mgp_payment_goods_item_margin)*2;
			//然后根据数据长度计算GridView的宽度
			int mGVWidth = goodsItemWidthLand * mGoodsList.size();
			android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(mGVWidth, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
			mGV.setLayoutParams(params);
			mGV.setNumColumns(mGoodsList.size());
		}
	}
	
}
