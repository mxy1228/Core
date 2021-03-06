package com.changyou.mgp.sdk.mbi.pay.adapter;

import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;

import com.changyou.mgp.sdk.mbi.config.CYMGProtocolConfig;
import com.changyou.mgp.sdk.mbi.config.SDKConfig;
import com.changyou.mgp.sdk.mbi.entity.GoodsItem;
import com.changyou.mgp.sdk.mbi.image.MGPImageLoader;
import com.changyou.mgp.sdk.mbi.log.CYLog;
import com.changyou.mgp.sdk.mbi.pay.id.PayResource;
import com.changyou.mgp.sdk.mbi.pay.viewcache.GoodsListViewCache;
import com.changyou.mgp.sdk.mbi.utils.SettingSPUtil;
import com.changyou.mgp.sdk.mbi.utils.SystemUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
/**
 * 
 * 功能描述：商品列表Adapter
 *
 * @author 严峥(yanzheng)
 * @date 2014-1-20 上午10:16:44
 * 修改历史：(修改人，修改时间，修改原因/内容)
 */
public class GoodsListAdapter extends BaseAdapter {
	
	private CYLog log = CYLog.getInstance();
	
	private Context mContext;
	private List<GoodsItem> mList;
	private DisplayImageOptions options;
	private boolean mIsLandScape;//是否横向显示
	private int a=1;
	private static final int SUI_XIN_GOU=3;
	private int mImgWidth;
	private int mImgHeight;
	private int mGrayLLHeightLand;

	private PayResource mPayResource;
	
	/**
	 * 
	 * @param contetx
	 * @param list
	 * @param grayLLHeightLand 横向显示时灰色半透明框的高度，计算广告图片高度时使用
	 */
	public GoodsListAdapter(Context contetx,List<GoodsItem> list,int grayLLHeightLand){
		this.mContext=contetx;
		this.mList=list;
		this.mPayResource=PayResource.getInstance(mContext);
		this.options = new DisplayImageOptions.Builder().showImageOnLoading(mPayResource.cymg_payment_goods_default)
				.showImageForEmptyUri(mPayResource.cymg_payment_goods_default)
				.showImageOnFail(mPayResource.cymg_payment_goods_default)
				.cacheInMemory(true)
				.cacheOnDisc(true)
				.imageScaleType(ImageScaleType.NONE)
				.build();
		this.mIsLandScape = SettingSPUtil.getLandScape(mContext) == CYMGProtocolConfig.LANDSCAPE;
		this.mGrayLLHeightLand = grayLLHeightLand;
		calculateWidthAndHeight();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	/**
	 * 计算商品图片的高度和宽度
	 * 根据屏幕显示方向不同，计算出来的数值也不同
	 */
	private void calculateWidthAndHeight(){
		if(mIsLandScape){
			//横向显示
			//根据灰色半透明框的高度和设计图中商品图片在框内高度比例计算图片显示高度
			mImgHeight = (int)(mGrayLLHeightLand * SDKConfig.PAYMENT_GOODS_PIC_SCALE_IN_LAND);
			mImgWidth = (int)(mImgHeight * SDKConfig.PAYMENT_GOODS_PIC_SCALE);
		}else{
			//竖向显示
			//以屏幕宽度为基准，减去Item之间的padding和margin后/3即为每个商品的宽度，然后根据商品图片宽高比算出图片高度
			int sWidth = SystemUtils.getScreenWidthSize(mContext);
			mImgWidth = (sWidth 
					- (6 * mContext.getResources().getDimensionPixelOffset(PayResource.getInstance(mContext).mgp_payment_goods_item_margin))) / 3;
			mImgHeight = (int)(mImgWidth / SDKConfig.PAYMENT_GOODS_PIC_SCALE);
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		GoodsListViewCache holder = null;
		if(convertView==null){
			holder = new GoodsListViewCache(mContext);
			convertView=LayoutInflater.from(mContext).inflate(PayResource.getInstance(mContext).mgp_payment_item_1_08, null);
			holder.setView(convertView);
			convertView.setTag(holder); 
		}else{
			holder=(GoodsListViewCache) convertView.getTag();
		}
		final GoodsItem mGoodsItem=mList.get(position);
//		holder.getGoodsLl().setTag(a);
//		holder.getGoodsPriceLl().setTag(a);
//		if(mGoodsItem.getType()==3){
//			holder.getGoodsLl().setTag(b);
//			holder.getGoodsPriceLl().setTag(b);
//		}
//		ImageView mImageView=holder.getGoodsIconImg();
//		String url=mGoodsItem.getGoods_icon().toString();
//		MGPImageLoader.getInstance(mContext).displayImage(url, mImageView, options);
//		holder.getGoodsNameTv().setText(mGoodsItem.getGoods_name());
//		holder.getGoodsDescribeTv().setText(mGoodsItem.getGoods_describe());
		double price=Double.valueOf(mGoodsItem.getGoods_price());
//		int tag1 = (Integer) holder.getGoodsLl().getTag();
//		if(tag1==b){
//			holder.getBuyLl().setGravity(Gravity.CENTER);
//			holder.getGoodsLl().setBackgroundDrawable(mContext.getResources().getDrawable(PayResource.getInstance(mContext).mgp_payment_list_suixingou_item_xbg_1_03));
//		}else{
//			holder.getBuyLl().setGravity(Gravity.CENTER_HORIZONTAL);
//			holder.getGoodsLl().setBackgroundDrawable(mContext.getResources().getDrawable(PayResource.getInstance(mContext).mgp_payment_list_item_xbg_1_03));
//		}
//		int tag2 = (Integer) holder.getGoodsPriceLl().getTag();
//		if(tag2==b){
//			if(SettingSPUtil.getLandScape(mContext)==CYMGProtocolConfig.PORTRAIT){
//				holder.getGoodsPriceLl().setVisibility(View.GONE);
//			}else{
//				holder.getGoodsPriceLl().setVisibility(View.INVISIBLE);
//			}
//		}else{
//			holder.getGoodsPriceLl().setVisibility(View.VISIBLE);
//			holder.getGoodsPriceTv().setText(String.valueOf((int)price));
//		}
		
		//如果是竖向显示的话，需要动态调整Item之间的margin，原则是2|1|1|2
//		if(!mIsLandScape){
//			LinearLayout.LayoutParams vlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//			switch (position % 3) {
//			case 0:
//				vlp.leftMargin = 2 * PayResource.getInstance(mContext).mgp_payment_goods_item_margin;
//				vlp.rightMargin = PayResource.getInstance(mContext).mgp_payment_goods_item_margin;
//				vlp.bottomMargin = PayResource.getInstance(mContext).mgp_payment_goods_item_margin;
//				vlp.topMargin = PayResource.getInstance(mContext).mgp_payment_goods_item_margin;
//				break;
//			case 1:
//				vlp.leftMargin = PayResource.getInstance(mContext).mgp_payment_goods_item_margin;
//				vlp.rightMargin = PayResource.getInstance(mContext).mgp_payment_goods_item_margin;
//				vlp.bottomMargin = PayResource.getInstance(mContext).mgp_payment_goods_item_margin;
//				vlp.topMargin = PayResource.getInstance(mContext).mgp_payment_goods_item_margin;
//				break;
//			case 2:
//				vlp.leftMargin =  PayResource.getInstance(mContext).mgp_payment_goods_item_margin;
//				vlp.rightMargin = 2 * PayResource.getInstance(mContext).mgp_payment_goods_item_margin;
//				vlp.bottomMargin = PayResource.getInstance(mContext).mgp_payment_goods_item_margin;
//				vlp.topMargin = PayResource.getInstance(mContext).mgp_payment_goods_item_margin;
//				break;
//			default:
//				break;
//			}
//			holder.getmContentLL().setLayoutParams(vlp);
//		}
		android.widget.LinearLayout.LayoutParams ivParam = new android.widget.LinearLayout.LayoutParams(mImgWidth, mImgHeight);
		ivParam.gravity = Gravity.CENTER_HORIZONTAL;
		holder.getGoodsIconImg().setLayoutParams(ivParam);
		MGPImageLoader.getInstance(mContext).displayImage(mGoodsItem.getGoods_icon().toString(), holder.getGoodsIconImg(), options);
		holder.getmGoodsNameTV().setText(mGoodsItem.getGoods_name());
		holder.getGoodsPriceTv().setText(mGoodsItem.getType() == SUI_XIN_GOU ? mGoodsItem.getGoods_describe() : mContext.getString(PayResource.getInstance(mContext).mgp_goods_price, String.valueOf((int)price)));
//		holder.getGoodsPriceTv().setVisibility(mGoodsItem.getType() == SUI_XIN_GOU ? View.INVISIBLE : View.VISIBLE); 
		return convertView;
	}

}
