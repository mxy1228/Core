package com.changyou.mgp.sdk.mbi.entity;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.telephony.gsm.GsmCellLocation;

import com.changyou.mgp.sdk.mbi.log.CYLog;

public class UploadImgResultBean {

	private String code;
	private List<UploadImgResultRowsBean> rows;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<UploadImgResultRowsBean> getRows() {
		return rows;
	}
	public void setRows(List<UploadImgResultRowsBean> rows) {
		this.rows = rows;
	}
	
	public String getRowsStr(){
		try {
			JSONArray array = new JSONArray();
			for(UploadImgResultRowsBean bean : rows){
				JSONObject obj = new JSONObject();
				obj.put("l", bean.getL());
				obj.put("m", bean.getM());
				obj.put("s", bean.getS());
				array.put(obj);
			}
			CYLog.getInstance().v("array length = "+array.length()+" and array to string="+array.toString());
			return array.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
