package com.changyou.mgp.sdk.mbi.entity;

import java.util.List;

import com.changyou.mgp.sdk.mbi.entity.base.BaseEntity;

public class GlobalParamList extends BaseEntity {

	private List<GlobalParamItem> data;

	public List<GlobalParamItem> getData() {
		return data;
	}

	public void setData(List<GlobalParamItem> data) {
		this.data = data;
	}
	
}
