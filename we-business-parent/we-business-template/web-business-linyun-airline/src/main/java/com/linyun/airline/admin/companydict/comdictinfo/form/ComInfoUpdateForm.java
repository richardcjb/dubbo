/**
 * InfoModForm.java
 * com.xiaoka.template.admin.dictionary.dirinfo.form
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.companydict.comdictinfo.form;

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
public class ComInfoUpdateForm extends ModForm {
	//字典类型id
	private long dictTypeId;
	//字典类别编码
	private String comTypeCode;
	//字典代码
	private String comDdictCode;
	//字典信息
	private String comDictName;
	//字典信息状态,1-启用，2--删除
	private int status;
	//字典信息状态,1-启用，2--删除
	private Date updateTime;
	//备注
	private String remark;
}
