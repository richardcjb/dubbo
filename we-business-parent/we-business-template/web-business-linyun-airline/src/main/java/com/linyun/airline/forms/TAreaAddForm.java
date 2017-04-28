package com.linyun.airline.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAreaAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**区域id*/
	private Long id;

	/**用户id*/
	private Long userId;

	/**公司id*/
	private Long comId;

	/**区域id*/
	private Long areaId;

	/**区域名称*/
	private String areaName;

	/**备注*/
	private String remark;

	/**创建时间*/
	private Date createTime;

}