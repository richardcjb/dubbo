/**
 * TTurnOverAddForm.java
 * TTurnOverAddForm
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年3月7日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TTurnOverAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**银行卡id*/
	private Integer bankCardId;

	/**交易日期*/
	private Date tradeDate;

	/**平均汇率*/
	private String averageRate;

	/**金额*/
	private double money;

	/**创建时间*/
	private Date createTime;

	/**修改时间*/
	private Date updateTime;

	/**卡号*/
	private String cardNum;

	/**用途*/
	private String purpose;

	/**币种*/
	private String currency;

	/**备注*/
	private String remark;
	/**备注*/
	private String bankName;
	/**状态*/
	private Integer status;
	/**公司名称*/
	private String companyName;
	/**公司id*/
	private Long companyNameId;
	/**项目名称*/
	private String projectName;
	/**发票状态*/
	private String invoiceStatus;
	/**卡里历史余额*/
	private double historymoney;

}
