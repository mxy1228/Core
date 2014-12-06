package com.changyou.mgp.sdk.mbi.entity;

public class OrderReplyEntity {

	private String remarks;
	private int reply_type;
	private String reply_content;
	private String order_id;
	private String order_ustomer_uid;
	private String create_date;
	private String update_date;
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getReply_type() {
		return reply_type;
	}
	public void setReply_type(int reply_type) {
		this.reply_type = reply_type;
	}
	public String getReply_content() {
		return reply_content;
	}
	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getOrder_ustomer_uid() {
		return order_ustomer_uid;
	}
	public void setOrder_ustomer_uid(String order_ustomer_uid) {
		this.order_ustomer_uid = order_ustomer_uid;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	
	
}
