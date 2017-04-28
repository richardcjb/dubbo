/**
 * TTurnOverFindForm.java
 * com.linyun.airline.forms
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.forms;

import java.io.Serializable;
import java.util.Date;

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
public class TTurnOverFindForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;
	//是否启用
	private Integer status;
	//银行卡名称
	private String bankCardName;
	//项目
	private String projectName;
	//支出或者收入的操作
	private String operation;
	//币种
	private String currency;
	//公司名称
	private String companyName;

	//日期
	private Date tradeDate;

}
