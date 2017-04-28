package com.xiaoka.template.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCompanyUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**管理员账号id*/
	private Integer adminId;
		
	/**公司名称*/
	private String comName;
		
	/**公司类型*/
	private String comType;
		
	/**备注*/
	private String remark;
		
}