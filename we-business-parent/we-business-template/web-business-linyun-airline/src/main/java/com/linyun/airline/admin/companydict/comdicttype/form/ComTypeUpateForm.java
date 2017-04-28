/**
 * TypeModForm.java
 * com.xiaoka.template.admin.dictionary.dirtype.form
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.companydict.comdicttype.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月3日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ComTypeUpateForm extends ModForm {
	//字典类别编码
	private String comTypeCode;

	//字典类别名称
	private String comTypeName;

	//状态,1-启用，2--删除
	private int status;

	//更新时间
	private Date updateTime;

	//备注
	private String remark;
}
