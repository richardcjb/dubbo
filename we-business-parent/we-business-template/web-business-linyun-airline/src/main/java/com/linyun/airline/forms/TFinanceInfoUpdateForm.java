package com.linyun.airline.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TFinanceInfoUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**订单id*/
	private Integer orderid;
		
	/**客户团号*/
	private String cusgroupnum;
		
	/**类型*/
	private Integer teamtype;
		
	/**开票日期*/
	private String billingdate;
		
	/**付款币种*/
	private Integer paycurrency;
		
	/**付款方式*/
	private Integer paymethod;
		
	/**销售人员*/
	private String salesperson;
		
	/**开票人*/
	private String issuer;
		
	/**人头数*/
	private Integer personcount;
		
	/**结算状态*/
	private Integer billingstatus;
		
	/**进澳时间*/
	private String enterausdate;
		
	/**出澳时间*/
	private String outausdate;
		
	/**应收*/
	private Double receivable;
		
	/**减免*/
	private Double relief;
		
	/**成本合计*/
	private Double costtotal;
		
	/**实收合计*/
	private Double incometotal;
		
	/**应返合计*/
	private Double returntotal;
		
	/**利润合计*/
	private Double profittotal;
		
}