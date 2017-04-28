package com.linyun.airline.admin.receivePayment.form.inter;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class TUpdateInterPayAddFrom implements Serializable {
	private static final long serialVersionUID = 1L;

	/**付款订单pay ids */
	private String payIds;

	/**操作人员id*/
	private String operators;

	/**收款单位是否一致*/
	private String payNames;

	/**银行名称*/
	private String bankComp;

	/**银行卡名称*/
	private String cardName;

	/**银行卡卡号*/
	private String cardNum;

	/**支付地址*/
	private Integer payAddress;

	/**用途*/
	private Integer purpose;

	/**资金类型*/
	private Integer fundType;

	/**付款时间*/
	private Date payDate;

	/**手续费*/
	private Double payFees;

	/**金额*/
	private Double payMoney;

	/**金额大写*/
	private String payChineseMoney;

	/**币种*/
	private Integer payCurrency;

	/**发票*/
	private Integer isInvioce;

	/**水单url*/
	private String receiptUrl;

	/**合计*/
	private Double totalMoney;

}
