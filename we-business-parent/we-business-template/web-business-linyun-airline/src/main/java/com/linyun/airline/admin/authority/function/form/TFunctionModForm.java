/**
 * TFunctionModForm.java
 * com.linyun.airline.forms
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.authority.function.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月18日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TFunctionModForm extends ModForm {

	/**上级功能id*/
	private long parentId;

	/**功能名称*/
	private String name;

	/**访问地止*/
	private String url;

	/**功能等级，是指在功能树中所处的层级*/
	private long level;

	/**创建时间*/
	private Date createTime;

	/**更新时间*/
	private Date updateTime;

	/**备注*/
	private String remark;

	/**序号*/
	private long sort;

	/**头像*/
	private String portrait;
}
