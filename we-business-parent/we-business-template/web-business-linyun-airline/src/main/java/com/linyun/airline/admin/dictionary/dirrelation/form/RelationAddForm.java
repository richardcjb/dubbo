/**
 * RelationAddForm.java
 * com.xiaoka.template.admin.dictionary.dirrelation.form
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.dictionary.dirrelation.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月3日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RelationAddForm extends AddForm {

	//字典id
	private int sourceId;

	//关联字典id
	private String targetId;

	//删除标识
	private int dr;
}
