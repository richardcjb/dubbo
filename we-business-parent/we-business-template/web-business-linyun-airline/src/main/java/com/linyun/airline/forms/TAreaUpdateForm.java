package com.linyun.airline.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAreaUpdateForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**用户id*/
	private Long userId;

	/**区域id*/
	private Long areaId;

	/**区域名称*/
	private String areaName;

	/**创建时间*/
	private Date createTime;

	/**备注*/
	private String remark;

}