package com.xiaoka.game.wap.pay.form;

import lombok.Data;

@Data
public class WxPayNotifyForm {
	
	/**
	 * 订单号
	 */
	private String orderId  ;
	
	/**
	 * 用户openid
	 */
	private String openId  ;
	
	/**
	 * 调用微信支付的时候使用的
	 * out_trade_no
	 */
	private String out_trade_no  ;

}
