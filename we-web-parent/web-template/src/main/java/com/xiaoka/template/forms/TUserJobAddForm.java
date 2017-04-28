package com.xiaoka.template.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TUserJobAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**用户id*/
	private Integer userid;
		
	/**公司职位id*/
	private Integer companyJobId;
		
	/**状态*/
	private Integer status;
		
	/**入职时间*/
	private Date hireDate;
		
	/**离职时间*/
	private Date leaveDate;
		
	/**备注*/
	private String remark;
		
}