/**
 * TSalaryIncreaseEntity.java
 * com.linyun.airline.admin.user.entity
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.user.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * 工资设计
 * @author   崔建斌
 * @Date	 2017年3月27日 	 
 */
@Data
@Table("t_salary_increase")
public class TSalaryIncreaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column
	@Id(auto = true)
	@Comment("主键")
	private long id;

	@Column
	@Comment("公司id")
	private long comId;

	@Column
	@Comment("用户id")
	private long userId;

	@Column
	@Comment("基本工资")
	private Double baseWages;

	@Column
	@Comment("五险一金")
	private Double wuXianYiJin;

	@Column
	@Comment("奖金")
	private Double bonus;

	@Column
	@Comment("纳税")
	private Double ratepaying;

	@Column
	@Comment("提成")
	private float commission;

	@Column
	@Comment("罚款")
	private Double forfeit;

	@Column
	@Comment("状态,1-已发，2--未发")
	private int status;

	@Column
	@Comment("创建时间")
	private Date createTime;

	@Column
	@Comment("更新时间")
	private Date updateTime;

	@Column
	@Comment("备注")
	private String remark;
}
