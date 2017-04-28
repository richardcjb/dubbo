package com.xiaoka.template.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAgentUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**上游公司id*/
	private Integer comId;
		
	/**代理商名称*/
	private String name;
		
}