/**
 * InfoModForm.java
 * com.xiaoka.template.admin.dictionary.dirinfo.form
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.dictionary.dirinfo.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.NotEmpty;

import com.uxuexi.core.web.form.ModForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月3日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InfoModForm extends ModForm {

	//字典类别编码
	private String typeCode;
	//字典代码
	@NotEmpty
	private String dictCode;
	//字典信息
	private String dictName;
	//描述
	private String description;
	//状态
	private int status;
	//创建时间
	private Date createTime;
}
