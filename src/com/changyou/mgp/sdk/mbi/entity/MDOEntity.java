package com.changyou.mgp.sdk.mbi.entity;

import com.changyou.mgp.sdk.mbi.entity.base.BaseEntity;

public class MDOEntity extends BaseEntity{

	private String msgcontent;
	private String msgphone;
	public String getMsgcontent() {
		return msgcontent;
	}
	public void setMsgcontent(String msgcontent) {
		this.msgcontent = msgcontent;
	}
	public String getMsgphone() {
		return msgphone;
	}
	public void setMsgphone(String msgphone) {
		this.msgphone = msgphone;
	}
	
}
