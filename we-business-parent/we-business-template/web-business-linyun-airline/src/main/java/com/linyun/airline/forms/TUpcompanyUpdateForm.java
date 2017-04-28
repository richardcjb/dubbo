package com.linyun.airline.forms;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TUpcompanyUpdateForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**公司id*/
	private long comId;

	/**公司名称*/
	private String name;

	/**备注*/
	private String remark;

}