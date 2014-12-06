package com.changyou.mgp.sdk.mbi.pay.adapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;

import com.changyou.mgp.sdk.mbi.entity.OrderItem;
import com.changyou.mgp.sdk.mbi.log.CYLog;
import com.changyou.mgp.sdk.mbi.pay.id.PayResource;
import com.changyou.mgp.sdk.mbi.pay.viewcache.ChargeRecorderViewCache;
import com.changyou.mgp.sdk.mbi.utils.ResourceUtil;

public class OrderAdapter extends BaseAdapter {

	private CYLog log = CYLog.getInstance();
	
	private Context mContext;
	private List<OrderItem> mData;
	private LayoutInflater mInflater;
	private String mPackageName;
	
	public OrderAdapter(Context context,List<OrderItem> data){
		this.mContext = context;
		this.mData = data;
		this.mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mPackageName = mContext.getPackageName();
	}
	
	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ChargeRecorderViewCache viewCache = null;
		if(convertView == null){
			convertView = mInflater.inflate(PayResource.getInstance(mContext).mgp_charge_recorder_item, null);
			viewCache = new ChargeRecorderViewCache(convertView,mContext);
			convertView.setTag(viewCache);
		}else{
			viewCache = (ChargeRecorderViewCache)convertView.getTag();
		}
		OrderItem item = mData.get(position);
		if(item != null){
			if(item.getOrder_id() != null){
				viewCache.getmIDTV().setText(item.getOrder_id());
			}else{
				log.e("item.getOrderID is null");
			}
			if(item.getGoods_name() != null){
				viewCache.getmNameTV().setText(item.getGoods_name());
			}else{
				log.e("item.getGoodsName is null");
			}
			if(item.getGoods_price() != 0){
				viewCache.getmPriceTV().setVisibility(View.VISIBLE);
				String price = mContext.getString(PayResource.getInstance(mContext).mgp_order_price, item.getGoods_price());
				viewCache.getmPriceTV().setText(initSString(price, PayResource.getInstance(mContext).mgp_order_item_price));
			}else{
				log.e("item.getGoodsPrice is null");
				viewCache.getmPriceTV().setVisibility(View.GONE);
			}
			if(position != 0){
				if(isChargeInADay(item.getCreate_date(), mData.get(position - 1).getCreate_date())){
					viewCache.getmDateTV().setVisibility(View.GONE);
				}else{
					viewCache.getmDateTV().setVisibility(View.VISIBLE);
					viewCache.getmDateTV().setText(getDate(item.getCreate_date()));
				}
			}else{
				viewCache.getmDateTV().setVisibility(View.VISIBLE);
				viewCache.getmDateTV().setText(getDate(item.getCreate_date()));
			}
			LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			lp.leftMargin = mContext.getResources().getDimensionPixelSize(PayResource.getInstance(mContext).mgp_list_line_margin_left) - viewCache.getmStateDotView().getCircleRadius();
			lp.bottomMargin = mContext.getResources().getDimensionPixelSize(PayResource.getInstance(mContext).mgp_list_item_triangle_margin_bottom) + viewCache.getmStateDotView().getCircleRadius();
			lp.width = mContext.getResources().getDimensionPixelSize(PayResource.getInstance(mContext).mgp_order_item_state_dot_size);
			lp.height = mContext.getResources().getDimensionPixelSize(PayResource.getInstance(mContext).mgp_order_item_state_dot_size);
			lp.gravity = Gravity.BOTTOM;
			viewCache.getmStateDotView().setLayoutParams(lp);
			viewCache.getmTimeTV().setText(getTime(item.getCreate_date()));
			LinearLayout.LayoutParams triangleLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			triangleLP.leftMargin  = 3;
			triangleLP.bottomMargin = mContext.getResources().getDimensionPixelSize(PayResource.getInstance(mContext).mgp_list_item_triangle_margin_bottom) + viewCache.getmStateDotView().getCircleRadius();
			triangleLP.gravity = Gravity.BOTTOM;
			viewCache.getmTriangleIV().setLayoutParams(triangleLP);
			LayoutParams timeTVLP = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			timeTVLP.bottomMargin = mContext.getResources().getDimensionPixelSize(PayResource.getInstance(mContext).mgp_list_item_triangle_margin_bottom) + viewCache.getmStateDotView().getCircleRadius();
			timeTVLP.gravity = Gravity.BOTTOM;
			timeTVLP.leftMargin = mContext.getResources().getDimensionPixelSize(PayResource.getInstance(mContext).mgp_order_item_time_margin_left);
			viewCache.getmTimeTV().setLayoutParams(timeTVLP);
			setState(item.getOrder_status(), viewCache,item.getOrder_status_msg());
		}else{
			log.e("item is null");
		}
		return convertView;
	}
	
	/**
	 * 
	 * 功能描述：为订单编号/商品名称/充值金额初始化字符串样式，Str以'：'分割，前面保持原样式不变，后面根据传进来的colorID改变颜色
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param str 目标String
	 * @param colorID 目标颜色
	 * @param @return
	 * @return SpannableString
	 * @date 2014-2-18 下午2:40:53
	 *
	 */
	private SpannableString initSString(String str,int colorID){
		SpannableString sstr = new SpannableString(str);
		ForegroundColorSpan span = new ForegroundColorSpan(mContext.getResources().getColor(colorID));
		sstr.setSpan(span, str.indexOf("：")+1, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return sstr;
	}
	
	/**
	 * 
	 * 功能描述：将“yyyy-MM-dd hh:MM”时间转换为Date
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param @param createDate
	 * @param @return
	 * @return Date
	 * @date 2014-2-19 下午3:02:03
	 *
	 */
	private Date conversDate(String createDate){
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createDate);
		} catch (Exception e) {
			log.e(e);
		}
		return null;
	}

	/**
	 * 
	 * 功能描述: 判断俩次充值日期是否为同一天
	 * @author 欧阳海冰(OuyangHaibing)
	 * @param @param thisDate
	 * @param @param previousDate
	 * @param @return 
	 * @return boolean
	 * @throws
	 */
	private boolean isChargeInADay(String thisDate, String previousDate) {
		Date date = conversDate(thisDate);
		if(date == null){
			log.e("date is null");
			return false;
		}
		Date pDate = conversDate(previousDate);
		if(pDate == null){
			log.e("pDate is null");
			return false;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Calendar pCalendar = Calendar.getInstance();
		pCalendar.setTime(pDate);
		return calendar.get(Calendar.YEAR) == pCalendar.get(Calendar.YEAR)
				&& calendar.get(Calendar.MONTH) == pCalendar.get(Calendar.MONTH)
				&& calendar.get(Calendar.DAY_OF_MONTH) == pCalendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 
	 * 功能描述：根据订单的createDate计算出格式为hh:MM的时间
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param @param createTime
	 * @param @return
	 * @return String
	 * @date 2014-2-18 下午3:01:06
	 *
	 */
	private String getTime(String createTime){
		Date date = conversDate(createTime);
		if(date == null){
			log.e("date is null");
			return null;
		}
		return new SimpleDateFormat("HH:mm").format(date);
	}
	
	/**
	 * 
	 * 功能描述：根据订单的createDate计算出格式为“X年X月X日 周X”的时间
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param @param createTime
	 * @param @return
	 * @return String
	 * @date 2014-2-18 下午3:03:15
	 *
	 */
	private String getDate(String createDate){
		Date date = conversDate(createDate);
		if(date == null){
			log.e("date is null");
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return mContext.getString(PayResource.getInstance(mContext).mgp_order_date, calendar.get(Calendar.YEAR)
				,calendar.get(Calendar.MONTH)+1
				,calendar.get(Calendar.DAY_OF_MONTH)
				,getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK)));
	}
	
	/**
	 * 
	 * 功能描述：将calendar.get(Calendar.DAY_OF_WEEK)的值转换为周X
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param @param day
	 * @param @return
	 * @return String
	 * @date 2014-2-19 下午3:15:31
	 *
	 */
	private String getDayOfWeek(int day){
		return mContext.getResources().getStringArray(PayResource.getInstance(mContext).mgp_day_of_week)[day-1];
	}
	
	/**
	 * 
	 * 功能描述：根据不同订单状态设置不同颜色显示
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param @param state
	 * @return void
	 * @date 2014-2-19 下午3:52:36
	 *
	 */
	private void setState(int state,ChargeRecorderViewCache viewCache,String stateStr){
		String[] colors = mContext.getResources().getStringArray(PayResource.getInstance(mContext).mgp_order_state_color);
//		String[] strs = mContext.getResources().getStringArray(PayResource.getInstance(mContext).mgp_order_state_value);
		viewCache.getmStateDotView().setSmallCircleColor(colors[state-1]);
		viewCache.getmStateView().setBgColor(colors[state-1]);
		viewCache.getmStateView().setText(stateStr);
	}
	
	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}
	
	@Override
	public boolean isEnabled(int position) {
		return false;
	}
}
