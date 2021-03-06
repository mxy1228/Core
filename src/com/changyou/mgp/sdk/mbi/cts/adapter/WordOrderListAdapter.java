package com.changyou.mgp.sdk.mbi.cts.adapter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.changyou.mgp.sdk.mbi.cts.id.CtsResource;
import com.changyou.mgp.sdk.mbi.entity.CustomerServiceItem;
import com.changyou.mgp.sdk.mbi.entity.OrderReplyEntity;
import com.changyou.mgp.sdk.mbi.log.CYLog;
import com.changyou.mgp.sdk.mbi.ui.widget.MGPChargeRecordDotIV;
import com.changyou.mgp.sdk.mbi.ui.widget.MGPCustomTrapezoidView;

public class WordOrderListAdapter extends BaseAdapter {

	private static final int MAX_TEXT_COUNT = 50;

	private static final int USER_REPLY = 0;
	private static final int CS_REPLY = 1;

	private static final int ARROW_UP = 1;
	private static final int ARROW_DOWN = -1;

	private static final String TYPE_OF_WEEK = "week";

	private static final String TYPE_OF_DATE = "date";

	private static final String TYPE_OF_TIME = "time";

	private Context mContext;
	private CtsResource mResource;
	private List<CustomerServiceItem> mData;
	private Map<Integer, Integer> quesTagMap = new HashMap<Integer, Integer>(); 
	private Map<Integer, Integer> replyTagMap = new HashMap<Integer, Integer>(); 

//	public void setData(List<CustomerServiceItem> mData) {
//		this.mData = mData;
//	}

	private LayoutInflater mInflater;
	private CYLog mLog;

	public WordOrderListAdapter(Context context,List<CustomerServiceItem> list) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
		this.mLog = CYLog.getInstance();
		this.mData = list;
		this.mResource=CtsResource.getInstance(mContext);
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		for (int i = 0; i < mData.size(); i++) {
			quesTagMap.put(i, ARROW_DOWN);
			replyTagMap.put(i, ARROW_DOWN);
		}
	}
	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	
//	public void setState(int state) {
//		this.notifyDataSetChanged();
//	}
	/**
	 * 将单击时的高亮给禁用掉
	 */
	@Override
	public boolean areAllItemsEnabled() {
	    return false;
	}
	@Override
	public boolean isEnabled(int position) {
	    return false;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(mResource.mgp_feedback_query_item,null);
			holder.tvTime = (TextView) convertView.
					findViewById(mResource.mgp_feedback_qurey_item_time_tv);
			holder.ivDot = (MGPChargeRecordDotIV) convertView
					.findViewById(mResource.mgp_feedback_qurey_item_dot_iv);
			holder.tvDateStamp = (TextView) convertView
					.findViewById(mResource.mgp_feedback_qurey_item_date_tv);
			holder.tvQuesClassification = (MGPCustomTrapezoidView) convertView
					.findViewById(mResource.mgp_feedback_qurey_item_classification_tv);
			holder.tvQuestion = (TextView) convertView
					.findViewById(mResource.mgp_feedback_qurey_item_question_tv);
			holder.tvQuestionState = (TextView) convertView
					.findViewById(mResource.mgp_feedback_qurey_item_state_tv);
			holder.tvOperation = (LinearLayout) convertView
					.findViewById(mResource.mgp_feedback_qurey_item_operation);
			holder.tvQuesDetail = (TextView) convertView
					.findViewById(mResource.mgp_feedback_operation_tv);
			holder.ivArrow = (ImageView) convertView
					.findViewById(mResource.mgp_feedback_qurey_item_arrow_iv);
			holder.llViewStub = (LinearLayout) convertView
					.findViewById(mResource.mgp_feedback_item_viewstub_ll);
			holder.tvReply = (TextView) convertView
					.findViewById(mResource.mgp_feedback_item_reply_tv);
			holder.tvOperationReply = (LinearLayout) convertView
					.findViewById(mResource.mgp_feedback_qurey_item_operation_reply);
			holder.tvReplyDetail = (TextView) convertView
					.findViewById(mResource.mgp_feedback_operation_reply_tv);
			holder.ivArrowReply = (ImageView) convertView
					.findViewById(mResource.mgp_feedback_qurey_item_arrow_reply_iv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		//获取数据
		CustomerServiceItem item = mData.get(position);
		Timestamp timestamp = Timestamp.valueOf(item.getCreate_date());
		if (item == null) {
			mLog.e("item is null");
			return null;
		}
		
		//动态设置“圆点”的布局参数
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params.leftMargin = mContext.getResources().getDimensionPixelSize(mResource.mgp_list_line_margin_left) - 
				holder.ivDot.getCircleRadius();
		params.bottomMargin = mContext.getResources().getDimensionPixelSize(mResource.mgp_feedback_list_item_triangle_margin_bottom);
		params.gravity = Gravity.BOTTOM;
		holder.ivDot.setLayoutParams(params);
		
		// 设置时间
		String time = getDateInfo(timestamp, TYPE_OF_TIME);
		holder.tvTime.setText(time);

		holder.tvQuesClassification.setText(item.getOrder_type_name());
		// // 设置DOT和问题归类
		setState(Integer.parseInt(item.getOrder_type()), holder);

		// 设置日期戳
		if (position != 0) {
			String thisChargeDate = item.getCreate_date();
			String previousChargeDate = mData.get(position - 1)
					.getCreate_date();
			// 如果是同一天充值的，将日期戳隐藏
			if (isChargeInADay(thisChargeDate, previousChargeDate)) {
				holder.tvDateStamp.setVisibility(View.GONE);
			} else {// 否则显示
				holder.tvDateStamp.setVisibility(View.VISIBLE);
				String week = getDateInfo(timestamp, TYPE_OF_WEEK);
				String date = getDateInfo(timestamp, TYPE_OF_DATE);
				// 设置日期戳的文本
				holder.tvDateStamp.setText(date + " " + week);
			}
		} else {
			holder.tvDateStamp.setVisibility(View.VISIBLE);
			String week = getDateInfo(timestamp, TYPE_OF_WEEK);
			String date = getDateInfo(timestamp, TYPE_OF_DATE);
			// 设置日期戳的文本
			holder.tvDateStamp.setText(date + " " + week);
		}
		
		//设置问题详细按钮TAG的初始值
		holder.tvOperation.setTag(quesTagMap.get(position));
		//设置客服回复详细按钮TAG的初始值
		holder.tvOperationReply.setTag(replyTagMap.get(position));

		// 设置问题和回复相关
		List<OrderReplyEntity> orderReplyList = item.getWork_order_replys();
		for (int i = 0; i < orderReplyList.size(); i++) {
			OrderReplyEntity replyEntity = orderReplyList.get(i);
			int replyType = replyEntity.getReply_type();
			if (replyType == USER_REPLY) {// 用户答复
//				holder.tvQuestionState
//						.setText(Html.fromHtml(mContext
//								.getString(mResource.mgp_feedback_query_status_noreply)));
//				holder.llViewStub.setVisibility(View.GONE);
				//如果问题的字数大于50
				if (replyEntity.getReply_content().length() > MAX_TEXT_COUNT) {
					// 控制详细按钮显示
					holder.tvOperation.setVisibility(View.VISIBLE);
					//if-else 解决convertView复用问题
					int tag = (Integer) holder.tvOperation.getTag();
					if (tag == ARROW_DOWN) {
						//设置截取后的字段
						String subContent = replyEntity.getReply_content()
								.substring(0, MAX_TEXT_COUNT) + "...";
						holder.tvQuestion.setText(subContent);
						holder.tvQuesDetail.setText(mContext.getString(mResource.mgp_feedback_query_detail));
						holder.ivArrow.setBackgroundResource(mResource.mgp_feedback_arrow_down);
					}else {
						holder.tvQuestion.setText(replyEntity.getReply_content());
						holder.tvQuesDetail.setText(mContext.getString(mResource.mgp_feedback_query_close));
						holder.ivArrow.setBackgroundResource(mResource.mgp_feedback_arrow_up);
					}
					// 设置详细的点击事件监听
					holder.tvOperation.setOnClickListener(new DetailClick(holder,
							replyEntity.getReply_content(),position));
				} else {
					//直接设置全部字段
					holder.tvQuestion.setText(replyEntity.getReply_content());
					// 控制详细按钮隐藏
					holder.tvOperation.setVisibility(View.GONE);
				}

			} else if (replyType == CS_REPLY) {// 客服答复
				holder.tvQuestionState.setText(Html.fromHtml(mContext
						.getString(mResource.mgp_feedback_query_status_reply)));
//				holder.tvSubmitChannel.setVisibility(View.VISIBLE);
				// 已跟PM确认，提交渠道暂时写死
//				holder.tvSubmitChannel.setText(mContext
//						.getString(R.string.mgp_feedback_query_submit_channel));
				// 客服答复显示
				holder.llViewStub.setVisibility(View.VISIBLE);
				
				if (replyEntity.getReply_content().length() > MAX_TEXT_COUNT) {
					//设置客服回复详细按钮为显示
					holder.tvOperationReply.setVisibility(View.VISIBLE);
					//if-else 解决convertView复用问题
					int tag = (Integer) holder.tvOperationReply.getTag();
					if (tag == ARROW_DOWN) {
						String subContent = replyEntity.getReply_content()
								.substring(0, MAX_TEXT_COUNT) + "...";
						holder.tvReply.setText(subContent);
						holder.tvReplyDetail.setText(mContext.getString(mResource.mgp_feedback_query_detail));
						holder.ivArrowReply.setBackgroundResource(mResource.mgp_feedback_arrow_down);
					}else {
						holder.tvReply.setText(replyEntity.getReply_content());
						holder.tvReplyDetail.setText(mContext.getString(mResource.mgp_feedback_query_close));
						holder.ivArrowReply.setBackgroundResource(mResource.mgp_feedback_arrow_up);
					}
					//  设置详细的点击事件监听
					holder.tvOperationReply.setOnClickListener(new DetailClick(
							holder, replyEntity.getReply_content(),position));
				} else {
					holder.tvReply.setText(replyEntity.getReply_content());
					holder.tvOperationReply.setVisibility(View.GONE);
				}

			}
		}

		return convertView;
	}

	/**
	 * 功能描述: 详细按钮的点击事件
	 * 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @date 2014-2-18 上午11:02:41
	 * 
	 */
	class DetailClick implements OnClickListener {

		private ViewHolder holder;
		private String content;
		private int position;

		public DetailClick(ViewHolder holder, String content,int position) {
			this.holder = holder;
			this.content = content;
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			if (v == holder.tvOperation) {
				int tag = (Integer) holder.tvOperation.getTag();
				if (tag == ARROW_DOWN) {
					holder.tvQuestion.setText(content);
					holder.tvQuesDetail.setText(mContext.getString(mResource.mgp_feedback_query_close));
					holder.ivArrow.setBackgroundResource(mResource.mgp_feedback_arrow_up);
					tag = ARROW_UP;
					quesTagMap.put(position, tag);
					holder.tvOperation.setTag(tag);
				} else {
					holder.tvQuestion
							.setText(content.substring(0, MAX_TEXT_COUNT)+ "...");
					holder.tvQuesDetail.setText(mContext.getString(mResource.mgp_feedback_query_detail));
					holder.ivArrow.setBackgroundResource(mResource.mgp_feedback_arrow_down);
					tag = ARROW_DOWN;
					quesTagMap.put(position, tag);
					holder.tvOperation.setTag(tag);
				}
			} else if (v == holder.tvOperationReply) {
				int tag = (Integer) holder.tvOperationReply.getTag();
				if (tag == ARROW_DOWN) {
					holder.tvReply.setText(content);
					holder.tvReplyDetail.setText(mContext.getString(mResource.mgp_feedback_query_close));
					holder.ivArrowReply.setBackgroundResource(mResource.mgp_feedback_arrow_up);
					tag = ARROW_UP;
					replyTagMap.put(position, tag);
					holder.tvOperationReply.setTag(tag);
				} else {
					holder.tvReply
							.setText(content.substring(0, MAX_TEXT_COUNT)+ "...");
					holder.tvReplyDetail.setText(mContext.getString(mResource.mgp_feedback_query_detail));
					holder.ivArrowReply.setBackgroundResource(mResource.mgp_feedback_arrow_down);
					tag = ARROW_DOWN;
					replyTagMap.put(position, tag);
					holder.tvOperationReply.setTag(tag);
				}
			}
		}
	}

	/**
	 * 功能描述: 判断俩次充值日期是否为同一天
	 * 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @param @param thisDate
	 * @param @param previousDate
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	private boolean isChargeInADay(String thisDate, String previousDate) {
		Timestamp thisStamp = null;
		Timestamp previousStamp = null;
		try {
			// 将String类型的时间转换为Timestamp
			thisStamp = Timestamp.valueOf(thisDate);
			previousStamp = Timestamp.valueOf(previousDate);

			// 得到实际日期：年+月+日
			String thisDay = getDateInfo(thisStamp, TYPE_OF_DATE);
			String previousDay = getDateInfo(previousStamp, TYPE_OF_DATE);

			// 判断两个日期是否一致
			if (thisDay != null && previousDay != null
					&& thisDay.equals(previousDay)) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 功能描述: 根据传递过来的Timestamp和type，返回String类型的时间（日期、时间、星期）
	 * 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @param @param timestamp
	 * @param @param type
	 * @param @return
	 * @return String
	 * @throws
	 */
	private String getDateInfo(Timestamp timestamp, String type) {
		Date date = new Date();
		date = timestamp;
		if (TYPE_OF_DATE.equals(type)) {
			SimpleDateFormat sy = new SimpleDateFormat("yyyy");
			SimpleDateFormat sm = new SimpleDateFormat("MM");
			SimpleDateFormat sd = new SimpleDateFormat("dd");

			String sYear = sy.format(date);
			String sMon = sm.format(date);
			String sDate = sd.format(date);

			return mContext.getString(mResource.mgp_feedback_query_date, sYear,
					sMon, sDate);

		} else if (TYPE_OF_TIME.equals(type)) {// HH:mm:ss
			SimpleDateFormat sh = new SimpleDateFormat("HH");
			SimpleDateFormat sf = new SimpleDateFormat("mm");

			String sHour = sh.format(date);
			String sMinute = sf.format(date);
			return sHour + ":" + sMinute;
		} else if (TYPE_OF_WEEK.equals(type)) {
			SimpleDateFormat sw = new SimpleDateFormat("E");
			String sWeek = sw.format(date);
			return sWeek;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * 功能描述: 根据不同问题分类设置不同颜色显示
	 * 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @param @param state
	 * @param @param holder
	 * @return void
	 * @throws
	 */
	private void setState(int state, ViewHolder holder) {
		String[] colors = mContext.getResources().getStringArray(
				mResource.mgp_feedback_question_type_color);
		String[] strs = mContext.getResources().getStringArray(
				mResource.mgp_feedback_question_type_value);
		holder.ivDot.setSmallCircleColor(colors[state - 1]);
		holder.tvQuesClassification.setBgColor(colors[state - 1]);
		holder.tvQuesClassification.setText(strs[state - 1]);
	}

	static class ViewHolder {
		TextView tvTime;// 时间
		MGPChargeRecordDotIV ivDot;// 点
		TextView tvDateStamp;// 日期戳
		MGPCustomTrapezoidView tvQuesClassification;// 问题分类
		TextView tvQuestion;// 问题
		TextView tvQuestionState;// 问题状态
//		TextView tvSubmitChannel;// 提交渠道
		LinearLayout tvOperation;// 详情
		TextView tvQuesDetail;
		ImageView ivArrow;// 箭头图标
		LinearLayout llViewStub;// 回复的ViewGroup
		TextView tvReply;// 回复
		LinearLayout tvOperationReply;// 详情
		TextView tvReplyDetail;
		ImageView ivArrowReply;// 客服回复箭头图标
	}

}
