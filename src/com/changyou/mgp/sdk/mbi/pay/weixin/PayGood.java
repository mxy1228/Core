/*******************************************************************************
 *    工程名称   ： CYMGSDK
 *    文件名    ： PayGood.java
 *              (C) Copyright Cyou-inc Corporation 2014
 *               All Rights Reserved.
 *   
 ******************************************************************************/
package com.changyou.mgp.sdk.mbi.pay.weixin;

import com.changyou.mgp.sdk.mbi.entity.GoodsItem;

/**
 * <PRE>
 * 作用
 *       支付商品类，由WeixinPayModel构造传入保存，等支付完毕清空
 * 限制
 *       无。
 * 注意事项
 *       无。
 * </PRE>
 */

public class PayGood {
		public static String uid;
		public static GoodsItem goodsItem;
		public static String orderID;
		public static void store(String mUid,GoodsItem mGoodsItem,String mOrderID){
			uid=mUid;
			goodsItem=mGoodsItem;
			orderID=mOrderID;
		}
		public static void clear(){
			uid=null;
			goodsItem=null;
			orderID=null;
		}



}
