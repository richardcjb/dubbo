package com.xiaoka.template.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.common.util.DateTimeUtil;
import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class SFunctionAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**上级功能id*/
	private Integer parentId;

	/**功能名称*/
	private String name = "";

	/**访问地址*/
	private String url = "";

	/**功能等级，是指在功能树中所处的层级*/
	private Integer level = 0;

	/**备注*/
	private String remark = "";

	/**创建时间*/
	private Date createTime;

	/**更新时间*/
	private Date updateTime = DateTimeUtil.nowDate();

	/**序号*/
	private Integer sort = 0;

}