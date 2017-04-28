/**
 * TJobModForm.java
 * com.linyun.airline.forms
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.authority.job.form;

import java.util.Date;

import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.NotEmpty;

import com.uxuexi.core.web.form.ModForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月18日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TJobModForm extends ModForm {

	/**部门id*/
	private long deptId;

	/**职位名称*/
	@NotEmpty
	private String name;

	/**创建时间*/
	private Date createTime;

	/**备注*/
	private String remark;

	/**职位关联的功能id列表,数字和逗号组成,可以为空*/
	@Pattern(regexp = "(\\d+,?)*")
	private String functionIds;
}
