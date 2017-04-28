package com.linyun.airline.forms;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.joda.time.DateTime;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class SLogUpdateForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**操作员id*/
	private long operatorId;

	/**功能id*/
	private long functionId;

	/**操作员名称*/
	private String operatorName;

	/**访问路径*/
	private String path;

	/**功能名称*/
	private String functionName;

	/**操作时间*/
	private DateTime operatorTime;

	/**IP地址*/
	private String ip;

}