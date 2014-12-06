package com.changyou.mgp.sdk.mbi.entity;
/**
 * 
 * 功能描述：服务器返回的错误信息实例
 *
 * @author 严峥(yanzheng)
 * @date 2014-1-20 上午10:20:10
 * 修改历史：(修改人，修改时间，修改原因/内容)
 */
public class ErrorInfo {

	private String request;
	private int error_code;
	private String sys_message;
	private String client_message;

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public int getError_code() {
		return error_code;
	}

	public void setError_code(int error_code) {
		this.error_code = error_code;
	}

	public String getSys_message() {
		return sys_message;
	}

	public void setSys_message(String sys_message) {
		this.sys_message = sys_message;
	}

	public String getClient_message() {
		return client_message;
	}

	public void setClient_message(String client_message) {
		this.client_message = client_message;
	}

	@Override
	public String toString() {
		return "ErrorInfo [request=" + request + ", error_code=" + error_code
				+ ", sys_message=" + sys_message + ", client_message="
				+ client_message + "]";
	}
}
