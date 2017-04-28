package com.xiaoka.template.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.common.util.DateTimeUtil;
import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class SLogAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**操作员id*/
	private Integer operatorId = 0;

	/**功能id*/
	private Integer functionId = 0;

	/**操作员名称*/
	private String operatorName = "";

	/**访问路径*/
	private String path = "";

	/**功能名称*/
	private String functionName = "";

	/**操作时间*/
	private Date operatorTime = DateTimeUtil.nowDate();

	/**IP地址*/
	private String ip;

}