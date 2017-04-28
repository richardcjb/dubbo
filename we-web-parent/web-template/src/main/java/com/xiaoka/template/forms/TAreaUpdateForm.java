package com.xiaoka.template.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAreaUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**区域名称*/
	private String areaName;
		
	/**备注*/
	private String remark;
		
}