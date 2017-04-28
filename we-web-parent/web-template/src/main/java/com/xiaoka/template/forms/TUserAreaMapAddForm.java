package com.xiaoka.template.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TUserAreaMapAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**用户id*/
	private Integer userId;
		
	/**区域id*/
	private Integer areaId;
		
}