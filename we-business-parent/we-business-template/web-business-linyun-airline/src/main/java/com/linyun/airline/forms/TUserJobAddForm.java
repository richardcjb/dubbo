package com.linyun.airline.forms;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.joda.time.DateTime;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TUserJobAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**用户id*/
	private long userid;

	/**公司职位id*/
	private long companyJobId;

	/**状态*/
	private long status;

	/**入职时间*/
	private DateTime hireDate;

	/**离职时间*/
	private DateTime leaveDate;

	/**备注*/
	private String remark;

}