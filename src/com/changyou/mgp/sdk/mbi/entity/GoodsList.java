package com.changyou.mgp.sdk.mbi.entity;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class GoodsList {
	
	private List<GoodsItem> data;

	public List<GoodsItem> getData() {
		return data;
	}

	public void setData(List<GoodsItem> data) {
		this.data = data;
	}
	
	public String toJsonString(){
		JSONArray array = new JSONArray();
		try {
			for(GoodsItem item : data){
				JSONObject obj = new JSONObject();
				obj.put("goodsDescribe", item.getGoods_describe());
				obj.put("goodsIcon", item.getGoods_icon());
				obj.put("goodsId", item.getGoods_id());
				obj.put("goodsName", item.getGoods_name());
				obj.put("goodsNumber", item.getGoods_number());
				obj.put("goodsPrice", item.getGoods_price());
				obj.put("goodsRegisterId", item.getGoods_register_id());
				obj.put("type", item.getType());
				array.put(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array.toString();
	}
}
