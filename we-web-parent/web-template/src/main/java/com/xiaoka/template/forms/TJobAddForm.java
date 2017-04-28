package com.xiaoka.template.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TJobAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**部门id*/
	private Integer deptId;
		
	/**职位名称*/
	private String name;
		
	/**创建时间*/
	private Date createTime;
		
	/**备注*/
	private String remark;
		
}