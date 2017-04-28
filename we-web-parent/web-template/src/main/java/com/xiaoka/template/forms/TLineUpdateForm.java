package com.xiaoka.template.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TLineUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**线路类型id*/
	private Integer typeId;
		
	/**线路名称*/
	private String name;
		
	/**描述*/
	private String description;
		
}