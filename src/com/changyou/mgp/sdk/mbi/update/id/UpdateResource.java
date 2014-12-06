package com.changyou.mgp.sdk.mbi.update.id;

import android.content.Context;

import com.changyou.mgp.sdk.mbi.utils.ResourceUtil;

public class UpdateResource {

	private static UpdateResource mInstance;
	private ResourceUtil mResourceUtil;
	private boolean mHadInit;
	
	private UpdateResource(Context context){
		this.mResourceUtil = ResourceUtil.getInstance(context);
		if(mHadInit){
			return;
		}else{
			init(context);
		}
	}
	
	public static UpdateResource getInstance(Context context){
		if(mInstance == null){
			synchronized (UpdateResource.class) {
				if(mInstance == null){
					mInstance = new UpdateResource(context);
				}
			}
		}
		return mInstance;
	}
	
	//2.0
	//layout
	public int mgp_sdk_2_0_init_splash;
	public int mgp_sdk_2_0_dialog_update_download;
	public int mgp_sdk_2_0_dialog_update_force_notice;
	public int mgp_sdk_2_0_dialog_update_neterror;
	public int mgp_sdk_2_0_dialog_update_not_force_notice;
	public int mgp_sdk_2_0_dialog_update_wifi_notice;
	public int mgp_sdk_2_0_splash_root;
	//id
	public int mgp_splash_iv;
	public int mgp_update_title_txt_tv;
	public int mgp_update_desc_txt_tv;
	public int mgp_update_force_title_txt_tv;
	public int mgp_update_force_desc_txt_tv;
	public int mgp_update_not_force_title_tv;
	public int mgp_update_not_force_desc_tv;
	public int mgp_update_download_percent_txt_tv;
	public int mgp_update_download_progress_bar;
	public int mgp_update_download_operatios_btn;
	public int mgp_update_force_immediately_btn_ll;
	public int mgp_update_neterror_retry_btn_ll;
	public int mgp_update_no_force_nexttime_btn_ll;
	public int mgp_update_no_force_immediately_btn_ll;
	public int mgp_update_download_cancel_btn_ll;
	public int mgp_update_download_confirm_btn_ll;
	public int mgp_sdk_2_0_splash_root_ll;
	
	//string
	public int mgp_net_error_hint;
	public int mgp_game_pre_update_info_txt;
	public int mgp_game_pre_update_complete_info_txt;
	//style
	public int mgp_sdk_2_0_update_mian_dialog;
	//drawable
	public int mgp_sdk_splash_horizontal_01;
	public int mgp_sdk_splash_horizontal_02;
	public int mgp_sdk_splash_horizontal_03;
	public int mgp_sdk_splash_vertical_01;
	public int mgp_sdk_splash_vertical_02;
	public int mgp_sdk_splash_vertical_03;
	
	public int mgp_sdk_white;
	//2.0end
	public int mgp_game_update_zoom_out;
	public int mgp_game_update_zoom_in;
	
	
	private void init(Context context){
		//2.0
		//layout
		mgp_sdk_2_0_dialog_update_download=mResourceUtil.getLayoutId("mgp_sdk_2_0_dialog_update_download");
		mgp_sdk_2_0_dialog_update_force_notice = mResourceUtil.getLayoutId("mgp_sdk_2_0_dialog_update_force_notice");
		mgp_sdk_2_0_dialog_update_neterror=mResourceUtil.getLayoutId("mgp_sdk_2_0_dialog_update_neterror");
		mgp_sdk_2_0_dialog_update_not_force_notice=mResourceUtil.getLayoutId("mgp_sdk_2_0_dialog_update_not_force_notice");
		mgp_sdk_2_0_dialog_update_wifi_notice=mResourceUtil.getLayoutId("mgp_sdk_2_0_dialog_update_wifi_notice");
		mgp_sdk_2_0_splash_root = mResourceUtil.getLayoutId("mgp_sdk_2_0_splash_root");
		//id
		mgp_update_title_txt_tv=mResourceUtil.getId("mgp_update_title_txt_tv");
		mgp_update_desc_txt_tv=mResourceUtil.getId("mgp_update_desc_txt_tv");
		
		mgp_update_not_force_title_tv=mResourceUtil.getId("mgp_update_not_force_title_tv");
		mgp_update_not_force_desc_tv=mResourceUtil.getId("mgp_update_not_force_desc_tv");
		
		mgp_update_force_title_txt_tv=mResourceUtil.getId("mgp_update_force_title_txt_tv");
		mgp_update_force_desc_txt_tv=mResourceUtil.getId("mgp_update_force_desc_txt_tv");
		
		mgp_update_download_percent_txt_tv=mResourceUtil.getId("mgp_update_download_percent_txt_tv");
		mgp_update_download_progress_bar=mResourceUtil.getId("mgp_update_download_progress_bar");
		mgp_update_download_operatios_btn=mResourceUtil.getId("mgp_update_download_operatios_btn");
		mgp_update_force_immediately_btn_ll=mResourceUtil.getId("mgp_update_force_immediately_btn_ll");
		mgp_update_neterror_retry_btn_ll=mResourceUtil.getId("mgp_update_neterror_retry_btn_ll");
		mgp_update_no_force_nexttime_btn_ll=mResourceUtil.getId("mgp_update_no_force_nexttime_btn_ll");
		mgp_update_no_force_immediately_btn_ll=mResourceUtil.getId("mgp_update_no_force_immediately_btn_ll");
		mgp_update_download_cancel_btn_ll=mResourceUtil.getId("mgp_update_download_cancel_btn_ll");
		mgp_update_download_confirm_btn_ll=mResourceUtil.getId("mgp_update_download_confirm_btn_ll");
		mgp_sdk_2_0_splash_root_ll = mResourceUtil.getId("mgp_sdk_2_0_splash_root_ll");
		//string
		mgp_game_pre_update_info_txt=mResourceUtil.getStringId("mgp_game_pre_update_info_txt");
		mgp_game_pre_update_complete_info_txt=mResourceUtil.getStringId("mgp_game_pre_update_complete_info_txt");
		//style
		mgp_sdk_2_0_update_mian_dialog=mResourceUtil.getStyleId("mgp_sdk_2_0_update_mian_dialog");
		//drawable
		
		// layout
		mgp_sdk_2_0_init_splash = mResourceUtil
				.getLayoutId("mgp_sdk_2_0_init_splash");
		// id
		mgp_splash_iv = mResourceUtil.getId("mgp_splash_iv");
		//String
		mgp_net_error_hint = mResourceUtil.getStringId("mgp_net_error_hint");
		// drawable
		mgp_sdk_splash_horizontal_01 = mResourceUtil
				.getDrawableId("mgp_sdk_splash_horizontal_01");
		mgp_sdk_splash_horizontal_02 = mResourceUtil
				.getDrawableId("mgp_sdk_splash_horizontal_02");
		mgp_sdk_splash_horizontal_03 = mResourceUtil
				.getDrawableId("mgp_sdk_splash_horizontal_03");
		mgp_sdk_splash_vertical_01 = mResourceUtil
				.getDrawableId("mgp_sdk_splash_vertical_01");
		mgp_sdk_splash_vertical_02 = mResourceUtil
				.getDrawableId("mgp_sdk_splash_vertical_02");
		mgp_sdk_splash_vertical_03 = mResourceUtil
				.getDrawableId("mgp_sdk_splash_vertical_03");
		mgp_sdk_white = mResourceUtil.getColorId("mgp_white");
		
		mgp_game_update_zoom_out = mResourceUtil.getAnimId("mgp_game_update_zoom_out");
		mgp_game_update_zoom_in = mResourceUtil.getAnimId("mgp_game_update_zoom_in");
		
		mHadInit = true;
	}
	
}
