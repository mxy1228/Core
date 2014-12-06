package com.changyou.mgp.sdk.mbi.entity;

import com.changyou.mgp.sdk.mbi.entity.base.BaseEntity;

public class PaymentItem extends BaseEntity {

	private int logoResID;
	private String name;
	public int getLogoResID() {
		return logoResID;
	}
	public void setLogoResID(int logoResID) {
		this.logoResID = logoResID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
