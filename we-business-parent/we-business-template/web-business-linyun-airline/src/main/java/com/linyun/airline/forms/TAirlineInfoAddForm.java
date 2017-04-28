package com.linyun.airline.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAirlineInfoAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**航空公司代码*/
	private String aircom;
		
	/**出发城市*/
	private String leavecity;
		
	/**到达城市*/
	private String arrvicity;
		
	/**去程日期*/
	private String leavedate;
		
	/**回程日期*/
	private String backdate;
		
	/**原价(单价)*/
	private Double formprice;
		
	/**售价(单价)*/
	private Double price;
		
	/**结算货币代码*/
	private String currencyCode;
		
	/**数量*/
	private Integer passengercount;
		
	/**乘客类型*/
	private Integer passengertype;
		
	/**机票类型*/
	private Integer tickettype;
		
	/**旅程类型*/
	private Integer traveltype;
		
	/**需求id*/
	private Integer needid;
		
	/**计划id*/
	private Integer planid;
		
	/**出发时间*/
	private String leavetime;
		
	/**抵达时间*/
	private String arrivetime;
		
	/**航班号*/
	private String ailinenum;
		
}