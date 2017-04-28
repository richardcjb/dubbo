package com.xiaoka.template.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCustomerServiceUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**客户信息id*/
	private Integer infoId;
		
	/**字典代码*/
	private String dictCode;
		
	/**字典名称*/
	private String dictName;
		
	/**字典类型代码*/
	private String dictTypeCode;
		
}