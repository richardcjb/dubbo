package com.linyun.airline.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TLineTypeAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**类型名称*/
	private String name;
		
}