/**
 * TTurnOverFindForm.java
 * com.linyun.airline.forms
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.forms;

import java.io.Serializable;

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
public class TSalaryFindForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;
	//开票人
	private String drawer;
	//搜索的年份
	private Integer year;
	//搜索的月份
	private Integer month;

}
