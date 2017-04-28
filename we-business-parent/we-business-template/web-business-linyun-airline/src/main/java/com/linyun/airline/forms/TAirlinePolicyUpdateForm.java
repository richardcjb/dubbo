/**
 * TAirlinePolicyUpdateForm.java
 * com.linyun.airline.forms
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.forms;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年3月18日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TAirlinePolicyUpdateForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 1L;
	/**类型*/
	private String type;
	/**航空公司id*/
	private long airlineCompanyId;
	/**地区id*/
	private long areaId;
	/**地区名称*/
	private String areaName;
	/**文件名字*/
	private String fileName;
	/**手填的文件名字，以此为准*/
	private String fileRealName;
	/**文件路径*/
	private String url;
	/**文件pdf路径*/
	private String pdfUrl;
}
