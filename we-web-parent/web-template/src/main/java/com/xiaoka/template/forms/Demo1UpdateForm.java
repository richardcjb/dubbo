package com.xiaoka.template.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class Demo1UpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**名称*/
	private String name;
		
	/**描述*/
	private String description;
		
}