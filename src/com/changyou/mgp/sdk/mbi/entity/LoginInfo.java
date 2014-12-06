package com.changyou.mgp.sdk.mbi.entity;

/**
 * 
 * 功能描述：登录信息实例
 *
 * @author 严峥(yanzheng)
 * @date 2014-3-4 下午7:57:00
 * 修改历史：(修改人，修改时间，修改原因/内容)
 */
public class LoginInfo {
	private String uid;
	private String token;
	private String tag;
	private String app_key;
	private String channel_id;
	private String activate;
	private String cn;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getApp_key() {
		return app_key;
	}
	public void setApp_key(String app_key) {
		this.app_key = app_key;
	}
	public String getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}	
	public String getActivate() {
		return activate;
	}
	public void setActivate(String activate) {
		this.activate = activate;
	}
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	
}
