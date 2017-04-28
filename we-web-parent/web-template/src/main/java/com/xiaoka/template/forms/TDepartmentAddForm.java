package com.xiaoka.template.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TDepartmentAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**公司id*/
	private Integer comId;
		
	/**部门名称*/
	private String deptName;
		
	/**备注*/
	private String remark;
		
}