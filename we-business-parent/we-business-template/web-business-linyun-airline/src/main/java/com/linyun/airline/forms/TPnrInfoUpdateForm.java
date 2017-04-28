package com.linyun.airline.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TPnrInfoUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**PNR*/
	private String pNR;
		
	/**成本单价*/
	private Double costprice;
		
	/**成本总价*/
	private Double costpricesum;
		
	/**销售单价*/
	private Double salesprice;
		
	/**销售总价*/
	private Double salespricesum;
		
	/**人数*/
	private Integer peoplecount;
		
	/**登陆账号*/
	private String loginid;
		
	/**币种*/
	private Integer currency;
		
	/**航班号信息id*/
	private Integer airinfoid;
		
}