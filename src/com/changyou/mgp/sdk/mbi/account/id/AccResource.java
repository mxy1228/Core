package com.changyou.mgp.sdk.mbi.account.id;

import android.content.Context;

import com.changyou.mgp.sdk.mbi.utils.ResourceUtil;

public class AccResource {

	private static AccResource mInstance;
	private ResourceUtil mResourceUtil;
	private boolean mHadInit;
	
	private AccResource(Context context){
		this.mResourceUtil = ResourceUtil.getInstance(context);
		if(mHadInit){
			return;
		}else{
			init(context);
		}
	}
	
	public static AccResource getInstance(Context context){
		if(mInstance == null){
			synchronized (AccResource.class) {
				if(mInstance == null){
					mInstance = new AccResource(context);
				}
			}
		}
		return mInstance;
	}
	//2.0
	//layout
	public int mgp_sdk_2_0_dialog_auto_login;
	public int mgp_sdk_2_0_dialog_loading_dialog;
	public int mgp_sdk_2_0_dialog_changyou_login;
	public int mgp_sdk_2_0_dialog_chengyou_login;
	public int mgp_sdk_2_0_dialog_login_toast;
	public int mgp_sdk_2_0_dialog_quick_into;
	public int mgp_sdk_2_0_dialog_register_container;
	public int mgp_sdk_2_0_dialog_register_phone_set_authcode;
	public int mgp_sdk_2_0_dialog_register_for_phone;
	public int mgp_sdk_2_0_dialog_register_for_user;
	public int mgp_sdk_2_0_dialog_switch_account;
	public int mgp_sdk_2_0_dialog_switch_account_drop_down_pop;
	public int mgp_sdk_2_0_dialog_switch_account_drop_down_pop_item;
	public int mgp_sdk_2_0_dialog_activate_code;
	public int cymg_flow_win;
	public int cymg_flow_win_left;
	public int cymg_flow_win_right;
	public int cymg_flow_win_transparent;
	public int cymg_flow_win_container_view;
	public int cymg_waiting_dialog;
	public int cymg_flow_win_view;
	public int cymg_flow_win_capture;
	
	//id
	public int mgp_sdk_2_0_auto_login_account_TextView;
	public int mgp_sdk_2_0_auto_login_switch_TextView;
	public int mgp_sdk_2_0_auto_login_loading_ImageView;
	public int mgp_sdk_2_0_dialog_loading_dialog_ImageView;
	public int mgp_sdk_2_0_dialog_loading_dialog_TextView;
	public int mgp_sdk_2_0_login_changyou_back_ImageButton;
	public int mgp_sdk_2_0_login_changyou_close_ImageButton;
	public int mgp_sdk_2_0_login_changyou_forget_ImageButton;
	public int mgp_sdk_2_0_login_changyou_account_EditText;
	public int mgp_sdk_2_0_login_changyou_password_EditText;
	public int mgp_sdk_2_0_login_changyou_login_Button;
	public int mgp_sdk_2_0_login_chengyou_back_ImageButton;
	public int mgp_sdk_2_0_login_chengyou_close_ImageButton;
	public int mgp_sdk_2_0_login_chengyou_forget_ImageButton;
	public int mgp_sdk_2_0_login_chengyou_account_EditText;
	public int mgp_sdk_2_0_login_chengyou_password_EditText;
	public int mgp_sdk_2_0_login_chengyou_register_Button;
	public int mgp_sdk_2_0_login_chengyou_login_Button;
	public int mgp_sdk_2_0_dialog_login_toast_account_TextView;
	public int mgp_sdk_2_0_quick_into_close_ImageButton;
	public int mgp_sdk_2_0_quick_into_kuaisu_Button;
	public int mgp_sdk_2_0_quick_into_chengyou_Button;
	public int mgp_sdk_2_0_quick_into_changyou_Button;
	public int mgp_sdk_2_0_register_container_back_ImageButton;
	public int mgp_sdk_2_0_register_container_close_ImageButton;
	public int mgp_sdk_2_0_register_container_phone_TextView;
	public int mgp_sdk_2_0_register_container_user_TextView;
	public int mgp_sdk_2_0_register_container_ViewPager;
	public int mgp_sdk_2_0_register_container_cursor_ImageView;
	public int mgp_sdk_2_0_register_phone_set_authcode_back_ImageButton;
	public int mgp_sdk_2_0_register_phone_set_authcode_close_ImageButton;
	public int mgp_sdk_2_0_register_phone_set_authcode_get_authcode_ImageButton;
	public int mgp_sdk_2_0_register_phone_set_authcode_num_TextView;
	public int mgp_sdk_2_0_register_phone_set_authcode_treaty_TextView;
	public int mgp_sdk_2_0_register_phone_set_authcode_authcode_EditText;
	public int mgp_sdk_2_0_register_phone_set_authcode_get_authcode_TextView;
	public int mgp_sdk_2_0_register_phone_set_authcode_commit_Button;
	public int mgp_sdk_2_0_register_for_phone_num_EditText;
	public int mgp_sdk_2_0_register_for_phone_commit_Button;
	public int mgp_sdk_2_0_register_for_user_account_EditText;
	public int mgp_sdk_2_0_register_for_user_password_EditText;
	public int mgp_sdk_2_0_register_for_user_treaty_TextView;
	public int mgp_sdk_2_0_register_for_user_commit_Button;
	public int mgp_sdk_2_0_switch_account_close_ImageButton;
	public int mgp_sdk_2_0_switch_account_commit_Button;
	public int mgp_sdk_2_0_switch_account_chengyou_Button;
	public int mgp_sdk_2_0_switch_account_changyou_Button;
	public int mgp_sdk_2_0_switch_account_select_TextView;
	public int mgp_sdk_2_0_dialog_switch_account_drop_down_pop_ListView;
	public int mgp_sdk_2_0_dialog_switch_account_drop_down_pop_item_account_TextView;
	public int mgp_sdk_2_0_dialog_switch_account_drop_down_pop_item_logo_ImageView;
	public int mgp_sdk_2_0_dialog_switch_account_drop_down_pop_item_del_ImageView;
	public int mgp_sdk_2_0_activate_code_back_ImageButton;
	public int mgp_sdk_2_0_activate_code_close_ImageButton;
	public int mgp_sdk_2_0_activate_code_EditText;
	public int mgp_sdk_2_0_activate_code_commit_Button;
	public int mgp_sdk_2_0_activate_code_notcode_TextView;
	public int cymg_flow_win_ibtn;
	public int cymg_flow_win_container;
	public int cymg_flow_win_container_title;
	public int cymg_flow_win_container_wv;
	public int mgp_title_right_imgbtn;
	public int cymg_flow_win_forum_btn;
	public int cymg_flow_win_personal_btn;
	public int cymg_waiting_dialog_tv;
	public int cymg_waiting_dialog_iv;
	public int cymg_flow_win_container_net_error;
	public int cymg_flow_win_capture_btn;
	public int cymg_flow_win_view_container_ll;
	public int cymg_flow_win_capture_iv;
	public int cymg_flow_win_capture_close_ibtn;
	public int cymg_flow_win_share_btn;
	public int cymg_flow_win_capture_et;
	public int cymg_flow_win_game_circle_btn;
	//string
	public int mgp_sdk_2_0_error_common_server;
	public int mgp_sdk_2_0_register_phone_set_authcode_str_2;
	public int mgp_sdk_2_0_find_password_set_password_str_2;
	public int mgp_sdk_2_0_loading_login;
	public int mgp_sdk_2_0_error_login_account;
	public int mgp_sdk_2_0_error_login_Password;
	public int mgp_sdk_2_0_error_login_sign;
	public int mgp_sdk_2_0_error_common_net;
	public int mgp_sdk_2_0_loading_verify;
	public int mgp_sdk_2_0_error_login_space;
	public int mgp_sdk_2_0_loading_quick_into;
	public int mgp_sdk_2_0_loading_register;
	public int mgp_sdk_2_0_error_register_auth_code;
	public int mgp_sdk_2_0_loading_commit;
	public int mgp_sdk_2_0_error_register_phone_num;
	public int mgp_sdk_2_0_error_register_account_1;
	public int mgp_sdk_2_0_error_register_account_2;
	public int mgp_sdk_2_0_error_register_Password_1;
	public int mgp_sdk_2_0_error_register_Password_2;
	public int mgp_sdk_2_0_error_register_space;
	public int mgp_sdk_2_0_loading_loading;
	public int mgp_sdk_2_0_switch_account_str_6;
	public int mgp_sdk_2_0_switch_account_str_7;
	public int mgp_sdk_2_0_switch_account_str_8;
	public int mgp_sdk_2_0_switch_account_str_9;
	public int mgp_sdk_2_0_error_activate_code;
	public int mgp_sdk_2_0_toast_register_success;
	public int cymg_shareing;
	//style
	public int mgp_sdk_2_0_mian_dialog;
	public int mgp_sdk_2_0_loading_dialog;
	//anim
	public int mgp_sdk_2_0_dialog_loading_anim;
	//color
	public int mgp_sdk_2_0_c1;
	//drawable
	public int mgp_sdk_2_0_dialog_moving_cursor;
	public int mgp_sdk_2_0_dialog_fall_sanjiao;
	public int mgp_sdk_2_0_dialog_chengyou_small_logo;
	public int mgp_sdk_2_0_dialog_changyou_small_logo;
	public int mgp_sdk_2_0_dialog_get_authcode_before;
	public int mgp_sdk_2_0_dialog_get_authcode_after;
	public int cymg_flow_win_btn;
	public int cymg_flow_win_bg_left;
	public int cymg_flow_win_bg_right;
	public int cymg_flow_win_btn_transparent;
	public int cymg_flow_win_container_back_btn_xbg;
	public int cymg_flow_win_container_close_btn_xbg;
	
	//2.0end
	
	
	//id
	public int mgp_title_tv;
	public int mgp_title_left_ibtn;
	public int mgp_title_right_btn;
	public int mgp_net_error_refresh_btn_id;
	
	//drawable
	public int mgp_common_net_error_pic;
	public int mgp_confirm_btn_xbg;
	
	//string
	public int mgp_protocol_title;
	public int mgp_loading;
	public int mgp_registering;
	public int mgp_login_dialog_title;
	public int mgp_login_dialog_retry;
	public int mgp_login_dialog_cancel;
	public int mgp_net_error_hint;
	public int mgp_login_toast_account_error;
	public int mgp_login_dialog_login_failure_21002;
	public int mgp_login_dialog_login_failure_21003;
	public int mgp_login_dialog_login_failure;
	public int mgp_login_toast_password_error;
	public int mgp_regist_password_error;
	public int mgp_regist_password_not_valide;
	public int mgp_username_cannot_the_same_with_passwd;
	public int mgp_regist_username_not_valide;
	public int mgp_regist_username_not_valide2;
	public int mgp_title_tv_regist;
	public int mgp_regist_mail_complete_hint1;
	public int mgp_regist_protocal;
	public int mgp_regist_mail_error;
	public int mgp_regist_password_error2;
	public int mgp_regist_registing;
	public int mgp_regist_tel_acc_error;
	public int mgp_validate_num_not_valide;
	public int mgp_regist_get_validate;
	public int mgp_regist_success;
	public int mgp_please_wait;
	public int mgp_count_down;
	public int mgp_regist_validate_had_send;
	public int mgp_login_dialog_register_failure_22004;
	public int mgp_login_dialog_register_failure_22005;
	public int mgp_login_dialog_register_failure;
	public int mgp_regist_please_enter_username;
	public int mgp_regist_check_username_length;
	public int mgp_regist_please_enter_password;
	public int mgp_regist_check_password_length;
	public int mgp_regist_net_state_error;
	public int mgp_channel_cy;
	public int mgp_login_dialog_logining;
	public int mgp_channel_360;
	public int mgp_channel_duoku;
	public int mgp_channel_huawei;
	public int mgp_channel_lenovo;
	public int mgp_channel_downjoy;
	public int mgp_channel_UC;
	public int mgp_channel_Wdj;
	public int mgp_not_login;
	public int mgp_net_error_hint1;
	public int mgp_net_error_hint2;
	public int mgp_refresh;
	public int mgp_login_success_info;
	
	//color
	public int mgp_refresh_btn_text_xbg;
	public int mgp_net_error_tv_color1;
	
	//dimen
	public int mgp_text_max;
	public int mgp_net_error_tv1_margin_top;
	public int mgp_net_error_tv2_margin_top;
	public int mgp_text_small;
	public int mgp_net_error_btn_margin_top;
	public int mgp_net_error_btn_padding_left_right;
	public int mgp_net_error_btn_padding_top_bottom;
	public int cymg_flow_win_size;
	
	//anim
	public int cymg_flow_win_expand_right;
	public int cymg_flow_win_close_right;
	
	private void init(Context context){
		//amin
		cymg_flow_win_expand_right = mResourceUtil.getAnimId("cymg_flow_win_expand_right");
		cymg_flow_win_close_right = mResourceUtil.getAnimId("cymg_flow_win_close_right");
		//2.0
		//layout
		mgp_sdk_2_0_dialog_auto_login=mResourceUtil.getLayoutId("mgp_sdk_2_0_dialog_auto_login");
		mgp_sdk_2_0_dialog_loading_dialog=mResourceUtil.getLayoutId("mgp_sdk_2_0_dialog_loading_dialog");
		mgp_sdk_2_0_dialog_changyou_login=mResourceUtil.getLayoutId("mgp_sdk_2_0_dialog_changyou_login");
		mgp_sdk_2_0_dialog_chengyou_login=mResourceUtil.getLayoutId("mgp_sdk_2_0_dialog_chengyou_login");
		mgp_sdk_2_0_dialog_login_toast=mResourceUtil.getLayoutId("mgp_sdk_2_0_dialog_login_toast");
		mgp_sdk_2_0_dialog_quick_into=mResourceUtil.getLayoutId("mgp_sdk_2_0_dialog_quick_into");
		mgp_sdk_2_0_dialog_register_container=mResourceUtil.getLayoutId("mgp_sdk_2_0_dialog_register_container");
		mgp_sdk_2_0_dialog_register_phone_set_authcode=mResourceUtil.getLayoutId("mgp_sdk_2_0_dialog_register_phone_set_authcode");
		mgp_sdk_2_0_dialog_register_for_phone=mResourceUtil.getLayoutId("mgp_sdk_2_0_dialog_register_for_phone");
		mgp_sdk_2_0_dialog_register_for_user=mResourceUtil.getLayoutId("mgp_sdk_2_0_dialog_register_for_user");
		mgp_sdk_2_0_dialog_switch_account=mResourceUtil.getLayoutId("mgp_sdk_2_0_dialog_switch_account");
		mgp_sdk_2_0_dialog_switch_account_drop_down_pop=mResourceUtil.getLayoutId("mgp_sdk_2_0_dialog_switch_account_drop_down_pop");
		mgp_sdk_2_0_dialog_switch_account_drop_down_pop_item=mResourceUtil.getLayoutId("mgp_sdk_2_0_dialog_switch_account_drop_down_pop_item");
		mgp_sdk_2_0_dialog_activate_code=mResourceUtil.getLayoutId("mgp_sdk_2_0_dialog_activate_code");
		cymg_flow_win = mResourceUtil.getLayoutId("cymg_flow_win");
		cymg_flow_win_left = mResourceUtil.getLayoutId("cymg_flow_win_left");
		cymg_flow_win_right = mResourceUtil.getLayoutId("cymg_flow_win_right");
		cymg_flow_win_transparent = mResourceUtil.getLayoutId("cymg_flow_win_transparent");
		cymg_flow_win_container_view = mResourceUtil.getLayoutId("cymg_flow_win_container_view");
		cymg_waiting_dialog = mResourceUtil.getLayoutId("cymg_waiting_dialog");
		cymg_flow_win_view = mResourceUtil.getLayoutId("cymg_flow_win_view");
		cymg_flow_win_capture = mResourceUtil.getLayoutId("cymg_flow_win_capture");
		//id
		cymg_flow_win_container_title = mResourceUtil.getId("cymg_flow_win_container_title");
		cymg_flow_win_ibtn = mResourceUtil.getId("cymg_flow_win_ibtn");
		cymg_flow_win_container = mResourceUtil.getId("cymg_flow_win_container");
		cymg_waiting_dialog_tv = mResourceUtil.getId("cymg_waiting_dialog_tv");
		mgp_title_right_imgbtn = mResourceUtil.getId("mgp_title_right_imgbtn");
		mgp_sdk_2_0_auto_login_account_TextView=mResourceUtil.getId("mgp_sdk_2_0_auto_login_account_TextView");
		mgp_sdk_2_0_auto_login_switch_TextView=mResourceUtil.getId("mgp_sdk_2_0_auto_login_switch_TextView");
		mgp_sdk_2_0_auto_login_loading_ImageView=mResourceUtil.getId("mgp_sdk_2_0_auto_login_loading_ImageView");
		mgp_sdk_2_0_dialog_loading_dialog_ImageView=mResourceUtil.getId("mgp_sdk_2_0_dialog_loading_dialog_ImageView");
		mgp_sdk_2_0_dialog_loading_dialog_TextView=mResourceUtil.getId("mgp_sdk_2_0_dialog_loading_dialog_TextView");
		mgp_sdk_2_0_login_changyou_back_ImageButton=mResourceUtil.getId("mgp_sdk_2_0_login_changyou_back_ImageButton");
		mgp_sdk_2_0_login_changyou_close_ImageButton=mResourceUtil.getId("mgp_sdk_2_0_login_changyou_close_ImageButton");
		mgp_sdk_2_0_login_changyou_forget_ImageButton=mResourceUtil.getId("mgp_sdk_2_0_login_changyou_forget_ImageButton");
		mgp_sdk_2_0_login_changyou_account_EditText=mResourceUtil.getId("mgp_sdk_2_0_login_changyou_account_EditText");
		mgp_sdk_2_0_login_changyou_password_EditText=mResourceUtil.getId("mgp_sdk_2_0_login_changyou_password_EditText");
		mgp_sdk_2_0_login_changyou_login_Button=mResourceUtil.getId("mgp_sdk_2_0_login_changyou_login_Button");
		mgp_sdk_2_0_login_chengyou_back_ImageButton=mResourceUtil.getId("mgp_sdk_2_0_login_chengyou_back_ImageButton");
		mgp_sdk_2_0_login_chengyou_close_ImageButton=mResourceUtil.getId("mgp_sdk_2_0_login_chengyou_close_ImageButton");
		mgp_sdk_2_0_login_chengyou_forget_ImageButton=mResourceUtil.getId("mgp_sdk_2_0_login_chengyou_forget_ImageButton");
		mgp_sdk_2_0_login_chengyou_account_EditText=mResourceUtil.getId("mgp_sdk_2_0_login_chengyou_account_EditText");
		mgp_sdk_2_0_login_chengyou_password_EditText=mResourceUtil.getId("mgp_sdk_2_0_login_chengyou_password_EditText");
		mgp_sdk_2_0_login_chengyou_register_Button=mResourceUtil.getId("mgp_sdk_2_0_login_chengyou_register_Button");
		mgp_sdk_2_0_login_chengyou_login_Button=mResourceUtil.getId("mgp_sdk_2_0_login_chengyou_login_Button");
		mgp_sdk_2_0_dialog_login_toast_account_TextView=mResourceUtil.getId("mgp_sdk_2_0_dialog_login_toast_account_TextView");
		mgp_sdk_2_0_quick_into_close_ImageButton=mResourceUtil.getId("mgp_sdk_2_0_quick_into_close_ImageButton");
		mgp_sdk_2_0_quick_into_kuaisu_Button=mResourceUtil.getId("mgp_sdk_2_0_quick_into_kuaisu_Button");
		mgp_sdk_2_0_quick_into_chengyou_Button=mResourceUtil.getId("mgp_sdk_2_0_quick_into_chengyou_Button");
		mgp_sdk_2_0_quick_into_changyou_Button=mResourceUtil.getId("mgp_sdk_2_0_quick_into_changyou_Button");
		mgp_sdk_2_0_register_container_back_ImageButton=mResourceUtil.getId("mgp_sdk_2_0_register_container_back_ImageButton");
		mgp_sdk_2_0_register_container_close_ImageButton=mResourceUtil.getId("mgp_sdk_2_0_register_container_close_ImageButton");
		mgp_sdk_2_0_register_container_phone_TextView=mResourceUtil.getId("mgp_sdk_2_0_register_container_phone_TextView");
		mgp_sdk_2_0_register_container_user_TextView=mResourceUtil.getId("mgp_sdk_2_0_register_container_user_TextView");
		mgp_sdk_2_0_register_container_ViewPager=mResourceUtil.getId("mgp_sdk_2_0_register_container_ViewPager");
		mgp_sdk_2_0_register_container_cursor_ImageView=mResourceUtil.getId("mgp_sdk_2_0_register_container_cursor_ImageView");
		mgp_sdk_2_0_register_phone_set_authcode_back_ImageButton=mResourceUtil.getId("mgp_sdk_2_0_register_phone_set_authcode_back_ImageButton");
		mgp_sdk_2_0_register_phone_set_authcode_close_ImageButton=mResourceUtil.getId("mgp_sdk_2_0_register_phone_set_authcode_close_ImageButton");
		mgp_sdk_2_0_register_phone_set_authcode_get_authcode_ImageButton=mResourceUtil.getId("mgp_sdk_2_0_register_phone_set_authcode_get_authcode_ImageButton");
		mgp_sdk_2_0_register_phone_set_authcode_num_TextView=mResourceUtil.getId("mgp_sdk_2_0_register_phone_set_authcode_num_TextView");
		mgp_sdk_2_0_register_phone_set_authcode_treaty_TextView=mResourceUtil.getId("mgp_sdk_2_0_register_phone_set_authcode_treaty_TextView");
		mgp_sdk_2_0_register_phone_set_authcode_authcode_EditText=mResourceUtil.getId("mgp_sdk_2_0_register_phone_set_authcode_authcode_EditText");
		mgp_sdk_2_0_register_phone_set_authcode_get_authcode_TextView=mResourceUtil.getId("mgp_sdk_2_0_register_phone_set_authcode_get_authcode_TextView");
		mgp_sdk_2_0_register_phone_set_authcode_commit_Button=mResourceUtil.getId("mgp_sdk_2_0_register_phone_set_authcode_commit_Button");
		mgp_sdk_2_0_register_for_phone_num_EditText=mResourceUtil.getId("mgp_sdk_2_0_register_for_phone_num_EditText");
		mgp_sdk_2_0_register_for_phone_commit_Button=mResourceUtil.getId("mgp_sdk_2_0_register_for_phone_commit_Button");
		mgp_sdk_2_0_register_for_user_account_EditText=mResourceUtil.getId("mgp_sdk_2_0_register_for_user_account_EditText");
		mgp_sdk_2_0_register_for_user_password_EditText=mResourceUtil.getId("mgp_sdk_2_0_register_for_user_password_EditText");
		mgp_sdk_2_0_register_for_user_treaty_TextView=mResourceUtil.getId("mgp_sdk_2_0_register_for_user_treaty_TextView");
		mgp_sdk_2_0_register_for_user_commit_Button=mResourceUtil.getId("mgp_sdk_2_0_register_for_user_commit_Button");
		mgp_sdk_2_0_switch_account_close_ImageButton=mResourceUtil.getId("mgp_sdk_2_0_switch_account_close_ImageButton");
		mgp_sdk_2_0_switch_account_commit_Button=mResourceUtil.getId("mgp_sdk_2_0_switch_account_commit_Button");
		mgp_sdk_2_0_switch_account_chengyou_Button=mResourceUtil.getId("mgp_sdk_2_0_switch_account_chengyou_Button");
		mgp_sdk_2_0_switch_account_changyou_Button=mResourceUtil.getId("mgp_sdk_2_0_switch_account_changyou_Button");
		mgp_sdk_2_0_switch_account_select_TextView=mResourceUtil.getId("mgp_sdk_2_0_switch_account_select_TextView");
		mgp_sdk_2_0_dialog_switch_account_drop_down_pop_ListView=mResourceUtil.getId("mgp_sdk_2_0_dialog_switch_account_drop_down_pop_ListView");
		mgp_sdk_2_0_dialog_switch_account_drop_down_pop_item_account_TextView=mResourceUtil.getId("mgp_sdk_2_0_dialog_switch_account_drop_down_pop_item_account_TextView");
		mgp_sdk_2_0_dialog_switch_account_drop_down_pop_item_logo_ImageView=mResourceUtil.getId("mgp_sdk_2_0_dialog_switch_account_drop_down_pop_item_logo_ImageView");
		mgp_sdk_2_0_dialog_switch_account_drop_down_pop_item_del_ImageView=mResourceUtil.getId("mgp_sdk_2_0_dialog_switch_account_drop_down_pop_item_del_ImageView");
		cymg_flow_win_container_wv = mResourceUtil.getId("cymg_flow_win_container_wv");
		mgp_sdk_2_0_activate_code_back_ImageButton=mResourceUtil.getId("mgp_sdk_2_0_activate_code_back_ImageButton");
		mgp_sdk_2_0_activate_code_close_ImageButton=mResourceUtil.getId("mgp_sdk_2_0_activate_code_close_ImageButton");
		mgp_sdk_2_0_activate_code_EditText=mResourceUtil.getId("mgp_sdk_2_0_activate_code_EditText");
		mgp_sdk_2_0_activate_code_commit_Button=mResourceUtil.getId("mgp_sdk_2_0_activate_code_commit_Button");
		mgp_sdk_2_0_activate_code_notcode_TextView=mResourceUtil.getId("mgp_sdk_2_0_activate_code_notcode_TextView");
		cymg_flow_win_container_net_error = mResourceUtil.getId("cymg_flow_win_container_net_error");
		cymg_flow_win_capture_btn = mResourceUtil.getId("cymg_flow_win_capture_btn");
		cymg_flow_win_view_container_ll = mResourceUtil.getId("cymg_flow_win_view_container_ll");
		cymg_flow_win_capture_iv = mResourceUtil.getId("cymg_flow_win_capture_iv");
		cymg_flow_win_capture_close_ibtn = mResourceUtil.getId("cymg_flow_win_capture_close_ibtn");
		cymg_flow_win_share_btn = mResourceUtil.getId("cymg_flow_win_share_btn");
		cymg_flow_win_capture_et = mResourceUtil.getId("cymg_flow_win_capture_et");
		//string
		mgp_sdk_2_0_error_common_server=mResourceUtil.getStringId("mgp_sdk_2_0_error_common_server");
		mgp_sdk_2_0_register_phone_set_authcode_str_2=mResourceUtil.getStringId("mgp_sdk_2_0_register_phone_set_authcode_str_2");
		mgp_sdk_2_0_find_password_set_password_str_2=mResourceUtil.getStringId("mgp_sdk_2_0_find_password_set_password_str_2");
		mgp_sdk_2_0_loading_login=mResourceUtil.getStringId("mgp_sdk_2_0_loading_login");
		mgp_sdk_2_0_error_login_account=mResourceUtil.getStringId("mgp_sdk_2_0_error_login_account");
		mgp_sdk_2_0_error_login_Password=mResourceUtil.getStringId("mgp_sdk_2_0_error_login_Password");
		mgp_sdk_2_0_error_login_sign=mResourceUtil.getStringId("mgp_sdk_2_0_error_login_sign");
		mgp_sdk_2_0_error_common_net=mResourceUtil.getStringId("mgp_sdk_2_0_error_common_net");
		mgp_sdk_2_0_loading_verify=mResourceUtil.getStringId("mgp_sdk_2_0_loading_verify");
		mgp_sdk_2_0_error_login_space=mResourceUtil.getStringId("mgp_sdk_2_0_error_login_space");
		mgp_sdk_2_0_loading_quick_into=mResourceUtil.getStringId("mgp_sdk_2_0_loading_quick_into");
		mgp_sdk_2_0_loading_register=mResourceUtil.getStringId("mgp_sdk_2_0_loading_register");
		mgp_sdk_2_0_error_register_auth_code=mResourceUtil.getStringId("mgp_sdk_2_0_error_register_auth_code");
		mgp_sdk_2_0_loading_commit=mResourceUtil.getStringId("mgp_sdk_2_0_loading_commit");
		mgp_sdk_2_0_error_register_phone_num=mResourceUtil.getStringId("mgp_sdk_2_0_error_register_phone_num");
		mgp_sdk_2_0_error_register_account_1=mResourceUtil.getStringId("mgp_sdk_2_0_error_register_account_1");
		mgp_sdk_2_0_error_register_account_2=mResourceUtil.getStringId("mgp_sdk_2_0_error_register_account_2");
		mgp_sdk_2_0_error_register_Password_1=mResourceUtil.getStringId("mgp_sdk_2_0_error_register_Password_1");
		mgp_sdk_2_0_error_register_Password_2=mResourceUtil.getStringId("mgp_sdk_2_0_error_register_Password_2");
		mgp_sdk_2_0_error_register_space=mResourceUtil.getStringId("mgp_sdk_2_0_error_register_space");
		mgp_sdk_2_0_loading_loading=mResourceUtil.getStringId("mgp_sdk_2_0_loading_loading");

		mgp_sdk_2_0_switch_account_str_6=mResourceUtil.getStringId("mgp_sdk_2_0_switch_account_str_6");
		mgp_sdk_2_0_switch_account_str_7=mResourceUtil.getStringId("mgp_sdk_2_0_switch_account_str_7");
		mgp_sdk_2_0_switch_account_str_8=mResourceUtil.getStringId("mgp_sdk_2_0_switch_account_str_8");
		mgp_sdk_2_0_switch_account_str_9=mResourceUtil.getStringId("mgp_sdk_2_0_switch_account_str_9");
		mgp_sdk_2_0_error_activate_code=mResourceUtil.getStringId("mgp_sdk_2_0_error_activate_code");
		mgp_sdk_2_0_toast_register_success=mResourceUtil.getStringId("mgp_sdk_2_0_toast_register_success");
		cymg_shareing = mResourceUtil.getStringId("cymg_shareing");
		//style
		mgp_sdk_2_0_mian_dialog=mResourceUtil.getStyleId("mgp_sdk_2_0_mian_dialog");
		mgp_sdk_2_0_loading_dialog=mResourceUtil.getStyleId("mgp_sdk_2_0_loading_dialog");
		//anim
		mgp_sdk_2_0_dialog_loading_anim=mResourceUtil.getAnimId("mgp_sdk_2_0_dialog_loading_anim");
		//color
		mgp_sdk_2_0_c1=mResourceUtil.getColorId("mgp_sdk_2_0_c1");
		//drawable
		mgp_sdk_2_0_dialog_moving_cursor=mResourceUtil.getDrawableId("mgp_sdk_2_0_dialog_moving_cursor");
		mgp_sdk_2_0_dialog_fall_sanjiao=mResourceUtil.getDrawableId("mgp_sdk_2_0_dialog_fall_sanjiao");
		mgp_sdk_2_0_dialog_chengyou_small_logo=mResourceUtil.getDrawableId("mgp_sdk_2_0_dialog_chengyou_small_logo");
		mgp_sdk_2_0_dialog_changyou_small_logo=mResourceUtil.getDrawableId("mgp_sdk_2_0_dialog_changyou_small_logo");
		mgp_sdk_2_0_dialog_get_authcode_before=mResourceUtil.getDrawableId("mgp_sdk_2_0_dialog_get_authcode_before");
		mgp_sdk_2_0_dialog_get_authcode_after=mResourceUtil.getDrawableId("mgp_sdk_2_0_dialog_get_authcode_after");
		cymg_flow_win_btn = mResourceUtil.getDrawableId("cymg_flow_win_btn");
		cymg_flow_win_bg_left = mResourceUtil.getDrawableId("cymg_flow_win_bg_left");
		cymg_flow_win_bg_right = mResourceUtil.getDrawableId("cymg_flow_win_bg_right");
		cymg_flow_win_btn_transparent = mResourceUtil.getDrawableId("cymg_flow_win_btn_transparent");
		cymg_flow_win_container_back_btn_xbg = mResourceUtil.getDrawableId("cymg_flow_win_container_back_btn_xbg");
		cymg_flow_win_container_close_btn_xbg = mResourceUtil.getDrawableId("cymg_flow_win_container_close_btn_xbg");
		//2.0end
		
		//id
		mgp_title_tv = mResourceUtil.getId("mgp_title_tv");
		mgp_title_left_ibtn = mResourceUtil.getId("mgp_title_left_ibtn");
		mgp_title_right_btn = mResourceUtil.getId("mgp_title_right_btn");
		mgp_net_error_refresh_btn_id = mResourceUtil.getId("mgp_net_error_refresh_btn_id");
		cymg_flow_win_forum_btn = mResourceUtil.getId("cymg_flow_win_forum_btn");
		cymg_flow_win_personal_btn = mResourceUtil.getId("cymg_flow_win_personal_btn");
		cymg_waiting_dialog_iv = mResourceUtil.getId("cymg_waiting_dialog_iv");
		cymg_flow_win_game_circle_btn = mResourceUtil.getId("cymg_flow_win_game_circle_btn");
		
		//String
		mgp_protocol_title = mResourceUtil.getStringId("mgp_protocol_title");
		mgp_loading = mResourceUtil.getStringId("mgp_loading");
		mgp_registering = mResourceUtil.getStringId("mgp_registering");
		mgp_login_dialog_title = mResourceUtil.getStringId("mgp_login_dialog_title");
		mgp_login_dialog_retry = mResourceUtil.getStringId("mgp_login_dialog_retry");
		mgp_login_dialog_cancel = mResourceUtil.getStringId("mgp_login_dialog_cancel");
		mgp_net_error_hint = mResourceUtil.getStringId("mgp_net_error_hint");
		mgp_login_toast_account_error = mResourceUtil.getStringId("mgp_login_toast_account_error");
		mgp_login_dialog_login_failure_21002 = mResourceUtil.getStringId("mgp_login_dialog_login_failure_21002");
		mgp_login_dialog_login_failure_21003 = mResourceUtil.getStringId("mgp_login_dialog_login_failure_21003");
		mgp_login_dialog_login_failure = mResourceUtil.getStringId("mgp_login_dialog_login_failure");
		mgp_login_toast_password_error = mResourceUtil.getStringId("mgp_login_toast_password_error");
		mgp_regist_password_error = mResourceUtil.getStringId("mgp_regist_password_error");
		mgp_regist_password_not_valide = mResourceUtil.getStringId("mgp_regist_password_not_valide");
		mgp_username_cannot_the_same_with_passwd = mResourceUtil.getStringId("mgp_username_cannot_the_same_with_passwd");
		mgp_regist_username_not_valide = mResourceUtil.getStringId("mgp_regist_username_not_valide");
		mgp_regist_username_not_valide2 = mResourceUtil.getStringId("mgp_regist_username_not_valide2");
		mgp_title_tv_regist = mResourceUtil.getStringId("mgp_title_tv_regist");
		mgp_regist_mail_complete_hint1 = mResourceUtil.getStringId("mgp_regist_mail_complete_hint1");
		mgp_regist_protocal = mResourceUtil.getStringId("mgp_regist_protocal");
		mgp_regist_mail_error = mResourceUtil.getStringId("mgp_regist_mail_error");
		mgp_regist_password_error2 = mResourceUtil.getStringId("mgp_regist_password_error2");
		mgp_regist_registing = mResourceUtil.getStringId("mgp_regist_registing");
		mgp_regist_tel_acc_error = mResourceUtil.getStringId("mgp_regist_tel_acc_error");
		mgp_validate_num_not_valide = mResourceUtil.getStringId("mgp_validate_num_not_valide");
		mgp_regist_get_validate = mResourceUtil.getStringId("mgp_regist_get_validate");
		mgp_regist_success = mResourceUtil.getStringId("mgp_regist_success");
		mgp_please_wait = mResourceUtil.getStringId("mgp_please_wait");
		mgp_count_down = mResourceUtil.getStringId("mgp_count_down");
		mgp_regist_validate_had_send = mResourceUtil.getStringId("mgp_regist_validate_had_send");
		mgp_login_dialog_register_failure_22004 = mResourceUtil.getStringId("mgp_login_dialog_register_failure_22004");
		mgp_login_dialog_register_failure_22005 = mResourceUtil.getStringId("mgp_login_dialog_register_failure_22005");
		mgp_login_dialog_register_failure = mResourceUtil.getStringId("mgp_login_dialog_register_failure");
		mgp_regist_please_enter_username = mResourceUtil.getStringId("mgp_regist_please_enter_username");
		mgp_regist_check_username_length = mResourceUtil.getStringId("mgp_regist_check_username_length");
		mgp_regist_please_enter_password = mResourceUtil.getStringId("mgp_regist_please_enter_password");
		mgp_regist_check_password_length = mResourceUtil.getStringId("mgp_regist_check_password_length");
		mgp_regist_net_state_error = mResourceUtil.getStringId("mgp_regist_net_state_error");
		mgp_channel_cy = mResourceUtil.getStringId("mgp_channel_cy");
		mgp_channel_360 = mResourceUtil.getStringId("mgp_channel_360");
		mgp_channel_UC = mResourceUtil.getStringId("mgp_channel_uc");
		mgp_channel_duoku = mResourceUtil.getStringId("mgp_channel_duoku");
		mgp_channel_huawei = mResourceUtil.getStringId("mgp_channel_huawei");
		mgp_channel_Wdj = mResourceUtil.getStringId("mgp_channel_wandoujia");
		mgp_channel_lenovo = mResourceUtil.getStringId("mgp_channel_lenovo");
		mgp_channel_downjoy=mResourceUtil.getStringId("mgp_channel_downjoy");
		mgp_not_login = mResourceUtil.getStringId("mgp_not_login");
		mgp_net_error_hint1 = mResourceUtil.getStringId("mgp_net_error_hint1");
		mgp_net_error_hint2 = mResourceUtil.getStringId("mgp_net_error_hint2");
		mgp_refresh = mResourceUtil.getStringId("mgp_refresh");
		mgp_login_success_info = mResourceUtil.getStringId("mgp_login_success_info");
		mgp_login_dialog_logining = mResourceUtil.getStringId("mgp_login_dialog_logining");
		
		//color
		mgp_refresh_btn_text_xbg = mResourceUtil.getColorId("mgp_refresh_btn_text_xbg");
		mgp_net_error_tv_color1 = mResourceUtil.getColorId("mgp_net_error_tv_color1");
		
		
		//drawable
		mgp_common_net_error_pic = mResourceUtil.getDrawableId("mgp_common_net_error_pic");
		mgp_confirm_btn_xbg = mResourceUtil.getDrawableId("mgp_confirm_btn_xbg");
		
		//dimen
		mgp_text_max = mResourceUtil.getDimenId("mgp_text_max");
		mgp_net_error_tv1_margin_top = mResourceUtil.getDimenId("mgp_net_error_tv1_margin_top");
		mgp_net_error_tv2_margin_top = mResourceUtil.getDimenId("mgp_net_error_tv2_margin_top");
		mgp_text_small = mResourceUtil.getDimenId("mgp_text_small");
		mgp_net_error_btn_margin_top = mResourceUtil.getDimenId("mgp_net_error_btn_margin_top");
		mgp_net_error_btn_padding_left_right = mResourceUtil.getDimenId("mgp_net_error_btn_padding_left_right");
		mgp_net_error_btn_padding_top_bottom = mResourceUtil.getDimenId("mgp_net_error_btn_padding_top_bottom");
		cymg_flow_win_size = mResourceUtil.getDimenId("cymg_flow_win_size");
		
		mHadInit = true;
	}
	
}
