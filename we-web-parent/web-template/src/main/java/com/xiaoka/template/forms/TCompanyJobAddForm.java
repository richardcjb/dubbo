package com.xiaoka.template.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCompanyJobAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**职位id*/
	private Integer posid;
		
	/**公司id*/
	private Integer comId;
		
}