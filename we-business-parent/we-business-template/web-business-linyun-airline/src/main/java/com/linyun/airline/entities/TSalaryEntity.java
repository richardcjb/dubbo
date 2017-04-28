/**
 * TBankCardEntity.java
 * com.linyun.airline.entities
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年3月2日 	 
 */
@Data
@Table("t_salary")
public class TSalaryEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("公司id")
	private Integer comId;
	@Column
	@Comment("开票人id")
	private Integer drawerId;

	@Column
	@Comment("成本合计")
	private double costTotal;

	@Column
	@Comment("实收合计")
	private double incomeTotal;

	@Column
	@Comment("提成")
	private double commission;
	@Column
	@Comment("实发提成")
	private double actualCommission;
	@Column
	@Comment("基本工资")
	private double basePay;
	@Column
	@Comment("工资合计")
	private double salaryTotal;
	@Column
	@Comment("创建时间")
	private Date createTime;
	@Column
	@Comment("更新时间")
	private Date updateTime;
	@Column
	@Comment("备注")
	private String remark;
	@Column
	@Comment("开票人")
	private String drawer;
	@Column
	@Comment("团数")
	private Integer groupNumber;
	@Column
	@Comment("人头数")
	private int headCount;

	@Column
	@Comment("奖金")
	private String welfare;
	@Column
	@Comment("五险一金")
	private String bonus;
	@Column
	@Comment("纳税")
	private String ratePaying;
	@Column
	@Comment("罚款")
	private double fine;
	@Column
	@Comment("人头数")
	private String other;
}
