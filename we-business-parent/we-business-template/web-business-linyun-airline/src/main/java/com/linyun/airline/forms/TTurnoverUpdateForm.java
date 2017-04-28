package com.linyun.airline.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TTurnoverUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**银行卡的id*/
	private Integer bankCardId;
		
	/**交易日期*/
	private Date tradeDate;
		
	/**平均汇率*/
	private String averageRate;
		
	/**金额*/
	private Double money;
		
	/**创建时间*/
	private Date createTime;
		
	/**修改时间*/
	private Date updateTime;
		
	/**备注*/
	private String remark;
		
	/**银行卡名称*/
	private String bankName;
		
	/**卡号*/
	private String cardNum;
		
	/**用途*/
	private String purpose;
		
	/**币种*/
	private String currency;
		
	/**状态*/
	private Integer status;
		
	/**公司名称*/
	private String companyName;
		
	/**项目名称*/
	private String projectName;
		
	/**发票状态*/
	private String invoiceStatus;
		
}