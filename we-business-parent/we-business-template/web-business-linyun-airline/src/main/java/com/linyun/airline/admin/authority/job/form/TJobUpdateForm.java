package com.linyun.airline.admin.authority.job.form;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TJobUpdateForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**部门id*/
	private long deptId;

	/**职位名称*/
	private String name;

	/**创建时间*/
	private Date createTime;

	/**备注*/
	private String remark;

}