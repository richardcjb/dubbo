/**
 * TSalaryIncreaseUpdateForm.java
 * com.linyun.airline.admin.user.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.user.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2017年3月27日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TSalaryIncreaseUpdateForm extends ModForm {
	//公司id
	private Long comId;

	//用户id
	private Long userId;

	//基本工资
	private Double baseWages;

	//五险一金
	private Double wuXianYiJin;

	//奖金
	private Double bonus;

	//纳税
	private Double ratepaying;

	//提成
	private float commission;

	//罚款
	private Double forfeit;

	//状态,1-已发，2--未发
	private Integer status;

	//创建时间
	private Date createTime;

	//更新时间
	private Date updateTime;

	//备注
	private String remark;
}
