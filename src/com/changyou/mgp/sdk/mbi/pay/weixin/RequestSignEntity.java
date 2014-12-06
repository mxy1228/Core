/*******************************************************************************
 *    工程名称   ： CYMGSDK
 *    文件名    ： RequestSignEntity.java
 *              (C) Copyright Cyou-inc Corporation 2014
 *               All Rights Reserved.
 *   
 ******************************************************************************/
package com.changyou.mgp.sdk.mbi.pay.weixin;

/**
 * <PRE>
 * 作用
 *       XXXX
 * 限制
 *       无。
 * 注意事项
 *       无。
 * </PRE>
 */

public class RequestSignEntity {

	protected String app_id;
	protected String app_key;
	protected String app_secret;
	protected String body;
	protected String out_trade_no;
	protected String total_fee;
	protected String notify_url;
	protected String spbill_create_ip;
	protected String nonceStr;
	protected long timestamp;
	protected String traceid;
	protected String appSignature;
	protected String partnerId;
	protected String packageValue;
	protected String token;
}
