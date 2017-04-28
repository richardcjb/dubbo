package com.xiaoka.template.forms;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class STestAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**测试用户名称*/
	private String sname;

	/**年龄*/
	private Integer sage;

	/**性别*/
	private String sex;

	/**积分*/
	private Float score;

	/**创建时间*/
	private Timestamp createTime;

}