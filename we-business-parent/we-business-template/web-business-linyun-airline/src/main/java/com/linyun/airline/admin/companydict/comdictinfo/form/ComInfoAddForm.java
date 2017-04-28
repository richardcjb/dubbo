/**
 * InfoAddForm.java
 * com.xiaoka.template.admin.dictionary.dirinfo.form
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/
package com.linyun.airline.admin.companydict.comdictinfo.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

/** 
* @ClassName: InfoAddForm
* @author 崔建斌 
* @date 2016年11月3日 下午1:49:20  
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class ComInfoAddForm extends AddForm {
	//主键
	private long id;
	//字典类型id
	private long dictTypeId;
	//公司id
	private long comId;
	//字典类别编码
	private String comTypeCode;
	//字典代码
	private String comDdictCode;
	//字典信息
	private String comDictName;
	//字典信息状态,1-启用，2--删除
	private int status;

	private String statusName;
	//全拼
	private String quanPin;
	//简拼
	private String jianpin;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;
	//备注
	private String remark;
}
