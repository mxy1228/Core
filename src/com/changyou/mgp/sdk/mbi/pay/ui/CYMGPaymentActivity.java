package com.changyou.mgp.sdk.mbi.pay.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.changyou.mgp.sdk.mbi.config.CYMGPrompt;
import com.changyou.mgp.sdk.mbi.config.CYMGProtocolConfig;
import com.changyou.mgp.sdk.mbi.config.CYMGProtocolKeys;
import com.changyou.mgp.sdk.mbi.config.Contants;
import com.changyou.mgp.sdk.mbi.config.Params;
import com.changyou.mgp.sdk.mbi.config.SDKConfig;
import com.changyou.mgp.sdk.mbi.entity.GoodsItem;
import com.changyou.mgp.sdk.mbi.entity.LoginOutEvent;
import com.changyou.mgp.sdk.mbi.entity.UPayResultEvent;
import com.changyou.mgp.sdk.mbi.log.CYLog;
import com.changyou.mgp.sdk.mbi.pay.id.PayResource;
import com.changyou.mgp.sdk.mbi.platform.CYMGPayHelper;
import com.changyou.mgp.sdk.mbi.ui.base.BaseActivity;
import com.changyou.mgp.sdk.mbi.utils.Blur;

import de.greenrobot.event.EventBus;
/**
 * 
 * 功能描述：支付Activity，承�?个Fragment：MGPPaymentFragment、MGPPaymentWayFragment、MGPCyouProtocolFragment、MGPChargeRecorderFragment
 *
 * @author 严峥(yanzheng)
 * @date 2014-1-20 上午10:25:38
 * 修改历史�?修改人，修改时间，修改原�?内容)
 */
public class CYMGPaymentActivity extends BaseActivity implements OnClickListener{
	
	private CYLog log = CYLog.getInstance();
	private static final int BLUR_FINISHED = 1;//对截图进行毛玻璃处理完成
	
	private String mUID;
	private int mMenuClickCount;//记录物理Menu键按下次数，若等于十次则Toast显示版本号，该功能非产品经理设计，为隐藏的查看版本号的方法
	private float alpha = (float)0.98;
	
	private Bundle mBundle;
	private Bundle mData;
	private Bitmap mCaptureBtm;
	private GoodsItem mGoodsItem;
	private String mOrderId;
	
	private FrameLayout mFL;
	private ImageView mIV;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		log.i("MGPPaymentActivity:onCreate");
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
	        savedInstanceState.remove("android:support:fragments");
	    }
		setContentView(PayResource.getInstance(this).mgp_payment_root);
		initView();
		initData();
		initEvent();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		log.i("MGPPaymentActivity:onDestroy");
		//将截图释放，以防止OOM
		if(mCaptureBtm != null){
			mCaptureBtm.recycle();
		}
	}
	
	@Override
	protected void initView() {
		log.i("initView");
		this.mFL = (FrameLayout)findViewById(PayResource.getInstance(CYMGPaymentActivity.this).payment_root_layout);
		this.mIV = (ImageView)findViewById(PayResource.getInstance(CYMGPaymentActivity.this).payment_root_iv);
		this.mUID = getIntent().getStringExtra(Params.UID);
		if(TextUtils.isEmpty(this.mUID) || this.mUID == null){
			throw new IllegalAccessError("uid is null");
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	protected void initData() {
		log.i("initData");
		CYMGPayHelper.getInstance().setContext(this);
		this.mBundle=getIntent().getExtras();
		if(this.mBundle == null){
			log.e("mBundle is null");
			return;
		}
		if(mBundle.getInt(CYMGProtocolKeys.PAYMENT_METHOD)==CYMGProtocolConfig.PAY_RECORD){
			CYMGChargeRecorderFragment f =new CYMGChargeRecorderFragment();
			f.setArguments(mBundle);
			getSupportFragmentManager().beginTransaction().replace(PayResource.getInstance(CYMGPaymentActivity.this).payment_root_layout, f,Contants.FragmentTag.PAYMENT_ORDER_FRAGMENT_TAG).commitAllowingStateLoss();
			return;
		}else if(mBundle.getInt(CYMGProtocolKeys.PAYMENT_METHOD) == CYMGProtocolConfig.PAY_WAY){
			CYMGPaymentWayFragment f =new CYMGPaymentWayFragment();
			f.setArguments(mBundle);
			getSupportFragmentManager().beginTransaction().replace(PayResource.getInstance(CYMGPaymentActivity.this).payment_root_layout, f,Contants.FragmentTag.PAYMENT_WAY_FRAGMENT_TAG).commitAllowingStateLoss();
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
			return;
		}
		byte[] bArray = mBundle.getByteArray("callerCapture");
		if(bArray != null){
			//将流重新组装成Bitmap
			mCaptureBtm = BitmapFactory.decodeByteArray(bArray, 0, bArray.length);
			mIV.setImageBitmap(mCaptureBtm);
			bluredImg();
		}else{
			log.e("Payment capture is null");
		}
		CYMGPaymentFragment f =new CYMGPaymentFragment();
		f.setArguments(mBundle);
		getSupportFragmentManager().beginTransaction().replace(PayResource.getInstance(CYMGPaymentActivity.this).payment_root_layout, f,Contants.FragmentTag.PAYMENT_FRAGMENT_TAG).commitAllowingStateLoss();
	}

	@Override
	protected void initEvent() {
		EventBus.getDefault().register(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==PayResource.getInstance(CYMGPaymentActivity.this).mgp_title_left_ibtn){
			goback();
		} 
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		//复写onSaveInstanceState，阻止自动调用super.onSaveInstanceState,这么做是为了解决某机型上快捷支付后奇葩的崩溃问题
		//解决方案见http://stackoverflow.com/questions/7469082/getting-exception-illegalstateexception-can-not-perform-this-action-after-onsa/10261438#10261438
	}
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case BLUR_FINISHED:
				mIV.setImageBitmap((Bitmap)msg.obj);
				break;

			default:
				break;
			}
		};
	};
	
	public void goback(){
		if(getSupportFragmentManager().getBackStackEntryCount() == 0){
			this.finish();
		}else{
			getSupportFragmentManager().popBackStack();
		}
	}
	
	@Override
	public void onBackPressed() {
		log.d("onBackPressed");
		goback();
	}
	
	public void changeFragment(String target,Bundle bundle){
		if(target.equals(Contants.FragmentTag.PAYMENT_WAY_FRAGMENT_TAG)){
			this.mData=bundle;
			CYMGPaymentWayFragment  paymentWayFragment = new CYMGPaymentWayFragment ();
			paymentWayFragment.setArguments(bundle);
			getSupportFragmentManager().beginTransaction().replace(PayResource.getInstance(CYMGPaymentActivity.this).payment_root_layout, paymentWayFragment
					,Contants.FragmentTag.PAYMENT_WAY_FRAGMENT_TAG).addToBackStack(Contants.FragmentTag.PAYMENT_WAY_FRAGMENT_TAG).commitAllowingStateLoss();
		}else if(target.endsWith(Contants.FragmentTag.PAYMENT_ORDER_FRAGMENT_TAG)){
			CYMGChargeRecorderFragment  chargeRecorderFragment = new CYMGChargeRecorderFragment();
			Bundle b = new Bundle();
			b.putString(Params.UID, mUID);
			chargeRecorderFragment.setArguments(b);
			getSupportFragmentManager().beginTransaction().replace(PayResource.getInstance(CYMGPaymentActivity.this).payment_root_layout, chargeRecorderFragment,
					Contants.FragmentTag.PAYMENT_ORDER_FRAGMENT_TAG).addToBackStack(Contants.FragmentTag.PAYMENT_ORDER_FRAGMENT_TAG).commitAllowingStateLoss();
		}else if(target.equals(Contants.FragmentTag.PAYMENT_WAY_PHONE_CARD_FRAGMENT_TAG)){
			CYMGPaymentWayPhoneCardFragment cymgPaymentWayPhoneCardFragment=new CYMGPaymentWayPhoneCardFragment();
			cymgPaymentWayPhoneCardFragment.setArguments(bundle);
			getSupportFragmentManager().beginTransaction().replace(PayResource.getInstance(CYMGPaymentActivity.this).payment_root_layout, cymgPaymentWayPhoneCardFragment
					,Contants.FragmentTag.PAYMENT_WAY_PHONE_CARD_FRAGMENT_TAG).addToBackStack(Contants.FragmentTag.PAYMENT_WAY_PHONE_CARD_FRAGMENT_TAG).commitAllowingStateLoss();
		}else if(target.equals(Contants.FragmentTag.PAYMENT_WAY_GAME_CARD_FRAGMENT_TAG)){
			CYMGPaymentWayGameCardFragment cymgPaymentWayGameCardFragment=new CYMGPaymentWayGameCardFragment();
			cymgPaymentWayGameCardFragment.setArguments(bundle);
			getSupportFragmentManager().beginTransaction().replace(PayResource.getInstance(CYMGPaymentActivity.this).payment_root_layout, cymgPaymentWayGameCardFragment
					,Contants.FragmentTag.PAYMENT_WAY_GAME_CARD_FRAGMENT_TAG).addToBackStack(Contants.FragmentTag.PAYMENT_WAY_GAME_CARD_FRAGMENT_TAG).commitAllowingStateLoss();
		}else if(target.equals(Contants.FragmentTag.PAYMENT_SUCCESS_FRAGMENT_TAG)){
//			int stackCount = getSupportFragmentManager().getBackStackEntryCount();
//			while(stackCount > 0){
//				log.d("stackCount = "+stackCount);
//				getSupportFragmentManager().popBackStack();
//				stackCount--;
//			}
			CYMGPaymentSuccessFragment cymgPaymentWayCardPromptFragment=new CYMGPaymentSuccessFragment();
			cymgPaymentWayCardPromptFragment.setArguments(bundle);
			getSupportFragmentManager().beginTransaction().replace(PayResource.getInstance(CYMGPaymentActivity.this).payment_root_layout, cymgPaymentWayCardPromptFragment
					,Contants.FragmentTag.PAYMENT_SUCCESS_FRAGMENT_TAG).addToBackStack(Contants.FragmentTag.PAYMENT_SUCCESS_FRAGMENT_TAG).commitAllowingStateLoss();
		}else if(target.equals(Contants.FragmentTag.PAYMENT_MO9_FRAGMENT_TAG)){
			CYMGMo9PayFragment cymgMo9PayFragment=new CYMGMo9PayFragment();
			cymgMo9PayFragment.setArguments(bundle);
			getSupportFragmentManager().beginTransaction().replace(PayResource.getInstance(CYMGPaymentActivity.this).payment_root_layout, cymgMo9PayFragment
					,Contants.FragmentTag.PAYMENT_MO9_FRAGMENT_TAG).addToBackStack(Contants.FragmentTag.PAYMENT_MO9_FRAGMENT_TAG).commitAllowingStateLoss();
		}
	}
	
	/**
	 * 返回到商品列表页面，然后PayHelper会在该页面进行订单验证
	 */
	public void back2PaymentFragment(){
		int stackCount = getSupportFragmentManager().getBackStackEntryCount();
		while(stackCount > 0){
			log.d("stackCount = "+stackCount);
			getSupportFragmentManager().popBackStack();
			stackCount--;
		}
		Fragment f = getSupportFragmentManager().findFragmentByTag(Contants.FragmentTag.PAYMENT_FRAGMENT_TAG);
		if(f == null){
			CYMGPaymentActivity.this.finish();
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_MENU){
			mMenuClickCount++;
			if(mMenuClickCount == 9){
				Toast.makeText(CYMGPaymentActivity.this, SDKConfig.VERSION, Toast.LENGTH_LONG).show();
				mMenuClickCount=0;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case CYMGPaymentWayFragment.RESULT_FOR_ALIPAY_CODE:
			
			break;
//		case CYMGPaymentWayFragment.RESULT_FOR_MO9_CODE:
//			log.d("RESULT_FOR_MO9_CODE,resultCode:"+resultCode);
//			if(resultCode==10){
//				log.d("onActivityResult,MO9支付成功");
//				paySuccess(mData);
//			}
//			break;
		default:
	        if (data == null) {
	        	payException();
	            return;
	        }
	        Bundle b = data.getExtras();
	        String str = b.getString("pay_result");
	        if (str.equalsIgnoreCase("success")) {
	        	log.d("onActivityResult,银联支付成功");
	        	paySuccess();
	        } else if (str.equalsIgnoreCase("fail")) {
	        	log.d("onActivityResult,银联支付失败");
	        	payException();
	        } else if (str.equalsIgnoreCase("cancel")) {
	        	log.d("onActivityResult,银联支付取消");
	        	payException();
	        }
			break;
		}
	}
	
	private void paySuccess(){
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			
			@Override
			public void run() {
				CYMGPayHelper.getInstance().paySuccess(mUID,mGoodsItem, mOrderId,CYMGPaymentActivity.this);
				Bundle b = new Bundle();
				b.putInt("type", CYMGPaymentSuccessFragment.SUCCESS);
				changeFragment(Contants.FragmentTag.PAYMENT_SUCCESS_FRAGMENT_TAG, b);
			}
		});
	}
	
	private void payException(){
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			
			@Override
			public void run() {
				CYMGPayHelper.getInstance().payException(CYMGPrompt.CODE_PAY_FAILED);
				Bundle b = new Bundle();
				b.putInt("type", CYMGPaymentSuccessFragment.FAILED);
				changeFragment(Contants.FragmentTag.PAYMENT_SUCCESS_FRAGMENT_TAG, b);
			}
		});

	}
	
	/**
	 * 对截图进行磨玻璃效果处理，然后重新设置给IV
	 */
	private void bluredImg(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				if(mCaptureBtm != null){
					//进行毛玻璃效果处理，Blur是逻辑处理的核心类，这里面对Bitmap进行了一系列的我看不懂的逻辑处理-_-|||
					//因为该处理比较耗时，所以放到子线程中
					mCaptureBtm = Blur.fastblur(CYMGPaymentActivity.this, mCaptureBtm, 18);
					Message msg = mHandler.obtainMessage();
					msg.what = BLUR_FINISHED;
					msg.obj = mCaptureBtm;
					mHandler.sendMessage(msg);
				}
			}
		}).start();
	}

	public void onEventMainThread(UPayResultEvent e){
		this.mGoodsItem = e.getmItem();
		this.mOrderId = e.getmOrderID();
	}
	
	public void  onEventMainThread(LoginOutEvent event){
		log.d("loginout channel id = "+event.getmChannelID());
		CYMGPaymentActivity.this.finish();
	}
}
