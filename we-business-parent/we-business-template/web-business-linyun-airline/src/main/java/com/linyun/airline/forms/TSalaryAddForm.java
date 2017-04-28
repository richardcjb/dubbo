package com.linyun.airline.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TSalaryAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**成本合计*/
	private Double costTotal;

	/**收入合计*/
	private Double incomeTotal;

	/**提成*/
	private String commission;

	/**实发提成*/
	private Double actualCommission;

	/**基本工资*/
	private Double basePay;

	/**创建时间*/
	private Date createTime;

	/**修改时间*/
	private Date updateTime;

	/**工资合计*/
	private Double salaryTotal;

	/**备注*/
	private String remark;
	/**开票人*/
	private String drawer;
	/**团数*/
	private int groupNumber;
	/**人头数*/
	private int headCount;

	/**奖金*/
	private String welfare;
	/**五险一金*/
	private String bonus;

	/**纳税*/
	private String ratePaying;

	/**罚款*/
	private Double fine;

	/**人头数*/
	private String other;

}