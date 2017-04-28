package com.linyun.airline.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TPayReceiveRecordAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**记录类型*/
	private Integer recordtype;

	/**成本单价*/
	private Double costprice;

	/**预付款比例*/
	private Double prepayratio;

	/**实际人数*/
	private Integer actualnumber;

	/**免罚金可减人数*/
	private Integer freenumber;

	/**本期罚金*/
	private Double currentfine;

	/**本期应付*/
	private Double currentdue;

	/**税金单价*/
	private Double ataxprice;

	/**本期实付*/
	private Double currentpay;

	/**币种*/
	private Integer currency;

	/**操作人*/
	private Integer opid;

	/**操作时间*/
	private Date optime;

	/**订单id*/
	private Integer orderid;

	/**订单状态*/
	private String orderstatus;
}