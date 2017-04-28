package com.xiaoka.template.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCustomerLineUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**客户信息id*/
	private Integer infoId;
		
	/**线路ID*/
	private Integer lineId;
		
}