package com.linyun.airline.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TOrderCustomneedAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**航空公司代码*/
	private String aircom;
		
	/**出发城市*/
	private String leavecity;
		
	/**抵达城市*/
	private String arrivecity;
		
	/**去程日期*/
	private String leavetdate;
		
	/**回程日期*/
	private String backdate;
		
	/**原价(单价)*/
	private Double originalprice;
		
	/**售价(单价)*/
	private Double price;
		
	/**结算货币代码*/
	private String currencyCode;
		
	/**数量*/
	private Integer peoplecount;
		
	/**乘客类型*/
	private Integer passengertype;
		
	/**机票类型*/
	private Integer tickettype;
		
	/**旅程类型*/
	private Integer rengetype;
		
	/**订单id*/
	private Integer ordernum;
		
}