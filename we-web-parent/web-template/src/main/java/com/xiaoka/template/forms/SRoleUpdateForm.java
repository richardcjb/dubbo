package com.xiaoka.template.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.common.util.DateTimeUtil;
import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class SRoleUpdateForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**角色名称*/
	private String name = "";

	/**备注*/
	private String remark = "";

	/**创建时间*/
	private Date createTime = DateTimeUtil.nowDate();

}