/**
 * TypeModForm.java
 * com.xiaoka.template.admin.dictionary.dirtype.form
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.dictionary.dirtype.form;

import java.io.Serializable;
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
public class TypeModForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 248685602143257341L;
	//字典类别编码
	private String typeCode;
	//字典类别名称
	private String typeName;
	//描述
	private String description;
	//状态
	private long status;
	//创建时间
	private Date createTime;
}
