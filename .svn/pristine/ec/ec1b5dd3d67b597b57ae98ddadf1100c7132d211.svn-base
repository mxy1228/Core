package com.changyou.mgp.sdk.mbi.cts.id;

import android.content.Context;

import com.changyou.mgp.sdk.mbi.pay.id.PayResource;
import com.changyou.mgp.sdk.mbi.utils.ResourceUtil;

public class CtsResource {

	private static CtsResource instance = null;
	private boolean mHadInit;//标记是否已经初始化
	private ResourceUtil mResourceUtil; 

	public static CtsResource getInstance(Context context) {
		if (instance == null) {
			synchronized (PayResource.class) {
				if (instance == null) {
					instance = new CtsResource(context);
				}
			}
		}
		return instance;
	}
	
	private CtsResource(Context context) {
		mResourceUtil = ResourceUtil.getInstance(context);
		if(mHadInit){
			return;
		}else{
			init(context);
		}
	}
	
	// CYMGCustomerServiceActivity.R
	public int mgp_onlineserver_root_1_03;
	public int mgp_onlineserver_root_layout;

	// CYMGCustomerServiceFeedbackFragment.R
	public int mgp_onlineserver_feedback_1_03;
	public int mgp_onlineserver_question_dialog;
	public int mgp_onlineserver_custom_dialog;
	public int mgp_onlineserver_feedback_im;

	public int mgp_onlineserver_feedback_1_03_title;
	public int mgp_onlineserver_feedback_1_03_question_TextView;
	public int mgp_onlineserver_feedback_1_03_ScrollView;
	public int mgp_onlineserver_feedback_1_03_ScrollEditLayout;
	public int mgp_onlineserver_feedback_1_03_content_EditText;
	public int mgp_onlineserver_feedback_1_03_phone_EditText;
	public int mgp_onlineserver_feedback_1_03_feedback_button;
	public int mgp_onlineserver_feedback_1_03_onlineIM_LinearLayout;
	public int mgp_onlineserver_feedback_1_03_serverTEL_LinearLayout;
	public int mgp_onlineserver_feedback_1_03_LinearLayout;
	public int mgp_onlineserver_feedback_1_03_error_ll;
	public int mgp_onlineserver_feedback_im_error_ll;
	public int mgp_onlineserver_question_dialog_RadioGroup;
	public int mgp_onlineserver_dialog_screen_LinearLayout;
	public int mgp_onlineserver_dialog_root_LinearLayout;
	public int mgp_onlineserver_dialog_close_ImageView;
	public int mgp_onlineserver_dialog_notdata_LinearLayout;
	public int mgp_onlineserver_dialog_notdata_Button;
	public int mgp_onlineserver_dialog_error_LinearLayout;
	public int mgp_onlineserver_dialog_list_LinearLayout;
	public int mgp_onlineserver_dialog_listView;
	public int mgp_net_error_refresh_btn_id;

	public int mgp_title_tv_onlineserver;
	public int mgp_title_btn_onlineserver;
	public int mgp_login_dialog_title;
	public int mgp_onlineserver_feedback_dialog_commit_error;
	public int mgp_login_dialog_retry;
	public int mgp_login_dialog_cancel;
	public int mgp_onlineserver_feedback_toast_im_and_bbs_error;
	public int mgp_drop_down_list_footer_default_text;
	public int mgp_net_error_hint;
	public int mgp_comitting;
	public int mgp_onlineserver_feedback_toast_commit;
	public int mgp_onlineserver_feedback_toast_all_error;
	public int mgp_onlineserver_feedback_toast_question_error;
	public int mgp_onlineserver_feedback_toast_content_error;
	public int mgp_onlineserver_feedback_toast_phone_null;
	public int mgp_onlineserver_feedback_toast_phone_error;
	public int mgp_onlineserver_feedback_server_tel;
	public int mgp_onlineserver_feedback_server_tel_error;

	public int mgp_onlineserver_custom_dialog_style;

	public int mgp_black;

	// CYMGWordOrderQueryFragment.R
	public int mgp_feedback_query;
	public int mgp_feedback_query_popup_dialog;
	public int mgp_feedback_query_popup_item_text;

	public int mgp_feedback_query_lv;
	public int mgp_feedback_query_driver_tv;
	public int mgp_feedback_query_empty_ll;
	public int mgp_feedback_query_empty_tv;
	public int mgp_feedback_query_error_layout;
	public int mgp_onlineserver_feedback_query_title;
	public int mgp_title_left_ibtn;
	public int mgp_title_tv;
	public int mgp_title_right_btn;
	public int mgp_feedback_query_lv_dialog;
	public int tv_text;

	public int mgp_title_tv_feedback;
	public int mgp_title_btn_feedback;
	public int mgp_loading;
	public int mgp_no_feedback_text;
	public int mgp_feedback_replyied_empty_text;
	public int mgp_feedback_noreply_empty_text;
	public int mgp_feedback_refresh_nodata_text;

	public int mgp_feedback_query_filter;

	// AbnormalOrderListAdapter.R
	public int mgp_onlineserver_custom_dialog_list_item;

	public int mgp_onlineserver_feedback_dialog_order_status_1;
	public int mgp_onlineserver_feedback_dialog_order_status_2;
	public int mgp_onlineserver_feedback_dialog_order_status_3;
	public int mgp_onlineserver_feedback_dialog_order_status_4;
	public int mgp_onlineserver_feedback_dialog_order_status_5;

	// AbnormalOrderListViewCache.R
	public int mgp_onlineserver_custom_dialog_list_item_date_TextView;
	public int mgp_onlineserver_custom_dialog_list_item_amount_TextView;
	public int mgp_onlineserver_custom_dialog_list_item_state_TextView;

	// WordOrderListAdapter.R
	public int mgp_feedback_query_item;

	public int mgp_feedback_qurey_item_time_tv;
	public int mgp_feedback_qurey_item_dot_iv;
	public int mgp_feedback_qurey_item_date_tv;
	public int mgp_feedback_qurey_item_classification_tv;
	public int mgp_feedback_qurey_item_question_tv;
	public int mgp_feedback_qurey_item_state_tv;
	public int mgp_feedback_qurey_item_operation;
	public int mgp_feedback_operation_tv;
	public int mgp_feedback_qurey_item_arrow_iv;
	public int mgp_feedback_item_viewstub_ll;
	public int mgp_feedback_item_reply_tv;
	public int mgp_feedback_qurey_item_operation_reply;
	public int mgp_feedback_operation_reply_tv;
	public int mgp_feedback_qurey_item_arrow_reply_iv;
	public int mgp_onlineserver_feedback_im_webview;
	public int mgp_onlineserver_feedback_im_title;
	public int mgp_title_tv_onlineserver_im;

	public int mgp_list_line_margin_left;
	public int mgp_feedback_list_item_triangle_margin_bottom;

	public int mgp_feedback_query_status_noreply;
	public int mgp_feedback_query_detail;
	public int mgp_feedback_query_close;
	public int mgp_feedback_query_status_reply;
	public int mgp_feedback_query_date;

	public int mgp_feedback_arrow_down;
	public int mgp_feedback_arrow_up;

	public int mgp_order_state_color;
	public int mgp_feedback_question_type_value;
	public int mgp_feedback_question_type_color;

	public void init(Context context) {
		// CYMGCustomerServiceActivity.R
		mgp_onlineserver_root_1_03 = getResourceIDByLayout("mgp_onlineserver_root_1_03");
		mgp_onlineserver_root_layout = getResourceIDById("mgp_onlineserver_root_layout");

		// CYMGCustomerServiceFeedbackFragment.R
		mgp_onlineserver_feedback_1_03 = getResourceIDByLayout("mgp_onlineserver_feedback_1_03");
		mgp_onlineserver_question_dialog = getResourceIDByLayout("mgp_onlineserver_question_dialog");
		mgp_onlineserver_custom_dialog = getResourceIDByLayout("mgp_onlineserver_custom_dialog");
		mgp_onlineserver_feedback_im = getResourceIDByLayout("mgp_onlineserver_feedback_im");

		mgp_onlineserver_feedback_1_03_title = getResourceIDById("mgp_onlineserver_feedback_1_03_title");
		mgp_onlineserver_feedback_1_03_question_TextView = getResourceIDById("mgp_onlineserver_feedback_1_03_question_TextView");
		mgp_onlineserver_feedback_1_03_ScrollView = getResourceIDById("mgp_onlineserver_feedback_1_03_ScrollView");
		mgp_onlineserver_feedback_1_03_ScrollEditLayout = getResourceIDById("mgp_onlineserver_feedback_1_03_ScrollEditLayout");
		mgp_onlineserver_feedback_1_03_content_EditText = getResourceIDById("mgp_onlineserver_feedback_1_03_content_EditText");
		mgp_onlineserver_feedback_1_03_phone_EditText = getResourceIDById("mgp_onlineserver_feedback_1_03_phone_EditText");
		mgp_onlineserver_feedback_1_03_feedback_button = getResourceIDById("mgp_onlineserver_feedback_1_03_feedback_button");
		mgp_onlineserver_feedback_1_03_onlineIM_LinearLayout = getResourceIDById("mgp_onlineserver_feedback_1_03_onlineIM_LinearLayout");
		mgp_onlineserver_feedback_1_03_serverTEL_LinearLayout = getResourceIDById("mgp_onlineserver_feedback_1_03_serverTEL_LinearLayout");
		mgp_onlineserver_feedback_1_03_LinearLayout = getResourceIDById("mgp_onlineserver_feedback_1_03_LinearLayout");
		mgp_onlineserver_feedback_1_03_error_ll = getResourceIDById("mgp_onlineserver_feedback_1_03_error_ll");
		mgp_onlineserver_feedback_im_error_ll = getResourceIDById("mgp_onlineserver_feedback_im_error_ll");
		mgp_onlineserver_question_dialog_RadioGroup = getResourceIDById("mgp_onlineserver_question_dialog_RadioGroup");
		mgp_onlineserver_dialog_screen_LinearLayout = getResourceIDById("mgp_onlineserver_dialog_screen_LinearLayout");
		mgp_onlineserver_dialog_root_LinearLayout = getResourceIDById("mgp_onlineserver_dialog_root_LinearLayout");
		mgp_onlineserver_dialog_close_ImageView = getResourceIDById("mgp_onlineserver_dialog_close_ImageView");
		mgp_onlineserver_dialog_notdata_LinearLayout = getResourceIDById("mgp_onlineserver_dialog_notdata_LinearLayout");
		mgp_onlineserver_dialog_notdata_Button = getResourceIDById("mgp_onlineserver_dialog_notdata_Button");
		mgp_onlineserver_dialog_error_LinearLayout = getResourceIDById("mgp_onlineserver_dialog_error_LinearLayout");
		mgp_onlineserver_dialog_list_LinearLayout = getResourceIDById("mgp_onlineserver_dialog_list_LinearLayout");
		mgp_onlineserver_dialog_listView = getResourceIDById("mgp_onlineserver_dialog_listView");
		mgp_net_error_refresh_btn_id = getResourceIDById("mgp_net_error_refresh_btn_id");
		mgp_onlineserver_feedback_im_title = getResourceIDById("mgp_onlineserver_feedback_im_title");

		mgp_title_tv_onlineserver = getResourceIDByString("mgp_title_tv_onlineserver");
		mgp_title_tv_onlineserver_im = getResourceIDByString("mgp_title_tv_onlineserver_im");
		mgp_title_btn_onlineserver = getResourceIDByString("mgp_title_btn_onlineserver");
		mgp_login_dialog_title = getResourceIDByString("mgp_login_dialog_title");
		mgp_onlineserver_feedback_dialog_commit_error = getResourceIDByString("mgp_onlineserver_feedback_dialog_commit_error");
		mgp_login_dialog_retry = getResourceIDByString("mgp_login_dialog_retry");
		mgp_login_dialog_cancel = getResourceIDByString("mgp_login_dialog_cancel");
		mgp_onlineserver_feedback_toast_im_and_bbs_error = getResourceIDByString("mgp_onlineserver_feedback_toast_im_and_bbs_error");
		mgp_drop_down_list_footer_default_text = getResourceIDByString("mgp_drop_down_list_footer_default_text");
		mgp_net_error_hint = getResourceIDByString("mgp_net_error_hint");
		mgp_comitting = getResourceIDByString("mgp_comitting");
		mgp_onlineserver_feedback_toast_commit = getResourceIDByString("mgp_onlineserver_feedback_toast_commit");
		mgp_onlineserver_feedback_toast_all_error = getResourceIDByString("mgp_onlineserver_feedback_toast_all_error");
		mgp_onlineserver_feedback_toast_question_error = getResourceIDByString("mgp_onlineserver_feedback_toast_question_error");
		mgp_onlineserver_feedback_toast_content_error = getResourceIDByString("mgp_onlineserver_feedback_toast_content_error");
		mgp_onlineserver_feedback_toast_phone_null = getResourceIDByString("mgp_onlineserver_feedback_toast_phone_null");
		mgp_onlineserver_feedback_toast_phone_error = getResourceIDByString("mgp_onlineserver_feedback_toast_phone_error");
		mgp_onlineserver_feedback_server_tel = getResourceIDByString("mgp_onlineserver_feedback_server_tel");
		mgp_onlineserver_feedback_server_tel_error = getResourceIDByString("mgp_onlineserver_feedback_server_tel_error");

		mgp_onlineserver_custom_dialog_style = getResourceIDByStyle("mgp_onlineserver_custom_dialog_style");

		mgp_black = getResourceIDByColor("mgp_black");

		// CYMGWordOrderQueryFragment.R
		mgp_feedback_query = getResourceIDByLayout("mgp_feedback_query");
		mgp_feedback_query_popup_dialog = getResourceIDByLayout("mgp_feedback_query_popup_dialog");
		mgp_feedback_query_popup_item_text = getResourceIDByLayout("mgp_feedback_query_popup_item_text");

		mgp_feedback_query_lv = getResourceIDById("mgp_feedback_query_lv");
		mgp_feedback_query_driver_tv = getResourceIDById("mgp_feedback_query_driver_tv");
		mgp_feedback_query_empty_ll = getResourceIDById("mgp_feedback_query_empty_ll");
		mgp_feedback_query_empty_tv = getResourceIDById("mgp_feedback_query_empty_tv");
		mgp_feedback_query_error_layout = getResourceIDById("mgp_feedback_query_error_layout");
		mgp_onlineserver_feedback_query_title = getResourceIDById("mgp_onlineserver_feedback_query_title");
		mgp_title_left_ibtn = getResourceIDById("mgp_title_left_ibtn");
		mgp_title_tv = getResourceIDById("mgp_title_tv");
		mgp_title_right_btn = getResourceIDById("mgp_title_right_btn");
		mgp_feedback_query_lv_dialog = getResourceIDById("mgp_feedback_query_lv_dialog");
		tv_text = getResourceIDById("tv_text");

		mgp_title_tv_feedback = getResourceIDByString("mgp_title_tv_feedback");
		mgp_title_btn_feedback = getResourceIDByString("mgp_title_btn_feedback");
		mgp_loading = getResourceIDByString("mgp_loading");
		mgp_no_feedback_text = getResourceIDByString("mgp_no_feedback_text");
		mgp_feedback_replyied_empty_text = getResourceIDByString("mgp_feedback_replyied_empty_text");
		mgp_feedback_noreply_empty_text = getResourceIDByString("mgp_feedback_noreply_empty_text");
		mgp_feedback_refresh_nodata_text = getResourceIDByString("mgp_feedback_refresh_nodata_text");

		mgp_feedback_query_filter = getResourceIDByArray("mgp_feedback_query_filter");

		// AbnormalOrderListAdapter.R
		mgp_onlineserver_custom_dialog_list_item = getResourceIDByLayout("mgp_onlineserver_custom_dialog_list_item");

		mgp_onlineserver_feedback_dialog_order_status_1 = getResourceIDByString("mgp_onlineserver_feedback_dialog_order_status_1");
		mgp_onlineserver_feedback_dialog_order_status_2 = getResourceIDByString("mgp_onlineserver_feedback_dialog_order_status_2");
		mgp_onlineserver_feedback_dialog_order_status_3 = getResourceIDByString("mgp_onlineserver_feedback_dialog_order_status_3");
		mgp_onlineserver_feedback_dialog_order_status_4 = getResourceIDByString("mgp_onlineserver_feedback_dialog_order_status_4");
		mgp_onlineserver_feedback_dialog_order_status_5 = getResourceIDByString("mgp_onlineserver_feedback_dialog_order_status_5");

		// AbnormalOrderListViewCache.R
		mgp_onlineserver_custom_dialog_list_item_date_TextView = getResourceIDById("mgp_onlineserver_custom_dialog_list_item_date_TextView");
		mgp_onlineserver_custom_dialog_list_item_amount_TextView = getResourceIDById("mgp_onlineserver_custom_dialog_list_item_amount_TextView");
		mgp_onlineserver_custom_dialog_list_item_state_TextView = getResourceIDById("mgp_onlineserver_custom_dialog_list_item_state_TextView");

		// WordOrderListAdapter.R
		mgp_feedback_query_item = getResourceIDByLayout("mgp_feedback_query_item");

		mgp_feedback_qurey_item_time_tv = getResourceIDById("mgp_feedback_qurey_item_time_tv");
		mgp_feedback_qurey_item_dot_iv = getResourceIDById("mgp_feedback_qurey_item_dot_iv");
		mgp_feedback_qurey_item_date_tv = getResourceIDById("mgp_feedback_qurey_item_date_tv");
		mgp_feedback_qurey_item_classification_tv = getResourceIDById("mgp_feedback_qurey_item_classification_tv");
		mgp_feedback_qurey_item_question_tv = getResourceIDById("mgp_feedback_qurey_item_question_tv");
		mgp_feedback_qurey_item_state_tv = getResourceIDById("mgp_feedback_qurey_item_state_tv");
		mgp_feedback_qurey_item_operation = getResourceIDById("mgp_feedback_qurey_item_operation");
		mgp_feedback_operation_tv = getResourceIDById("mgp_feedback_operation_tv");
		mgp_feedback_qurey_item_arrow_iv = getResourceIDById("mgp_feedback_qurey_item_arrow_iv");
		mgp_feedback_item_viewstub_ll = getResourceIDById("mgp_feedback_item_viewstub_ll");
		mgp_feedback_item_reply_tv = getResourceIDById("mgp_feedback_item_reply_tv");
		mgp_feedback_qurey_item_operation_reply = getResourceIDById("mgp_feedback_qurey_item_operation_reply");
		mgp_feedback_operation_reply_tv = getResourceIDById("mgp_feedback_operation_reply_tv");
		mgp_feedback_qurey_item_arrow_reply_iv = getResourceIDById("mgp_feedback_qurey_item_arrow_reply_iv");
		mgp_onlineserver_feedback_im_webview = getResourceIDById("mgp_onlineserver_feedback_im_webview");

		mgp_list_line_margin_left = getResourceIDByDimen("mgp_list_line_margin_left");
		mgp_feedback_list_item_triangle_margin_bottom = getResourceIDByDimen("mgp_feedback_list_item_triangle_margin_bottom");

		mgp_feedback_query_status_noreply = getResourceIDByString("mgp_feedback_query_status_noreply");
		mgp_feedback_query_detail = getResourceIDByString("mgp_feedback_query_detail");
		mgp_feedback_query_close = getResourceIDByString("mgp_feedback_query_close");
		mgp_feedback_query_status_reply = getResourceIDByString("mgp_feedback_query_status_reply");
		mgp_feedback_query_date = getResourceIDByString("mgp_feedback_query_date");

		mgp_feedback_arrow_down = getResourceIDByDrawable("mgp_feedback_arrow_down");
		mgp_feedback_arrow_up = getResourceIDByDrawable("mgp_feedback_arrow_up");

		mgp_order_state_color = getResourceIDByArray("mgp_order_state_color");
		mgp_feedback_question_type_value = getResourceIDByArray("mgp_feedback_question_type_value");
		mgp_feedback_question_type_color = getResourceIDByArray("mgp_feedback_question_type_color");
	}
	
	private int getResourceIDByLayout(String id){
		return mResourceUtil.getLayoutId(id);
	}
	private int getResourceIDById(String id){
		return mResourceUtil.getId(id);
	}
	private int getResourceIDByString(String id){
		return mResourceUtil.getStringId(id);
	}
	private int getResourceIDByDrawable(String id){
		return mResourceUtil.getDrawableId(id);
	}
	private int getResourceIDByArray(String id){
		return mResourceUtil.getArrayId(id);
	}
	private int getResourceIDByStyle(String id){
		return mResourceUtil.getStyleId(id);
	}
	private int getResourceIDByColor(String id){
		return mResourceUtil.getColorId(id);
	}
	private int getResourceIDByDimen(String id){
		return mResourceUtil.getDimenId(id);
	}

}
