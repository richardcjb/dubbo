package com.linyun.airline.forms;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCompanyFunctionMapAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**功能id*/
	private long funId;

	/**公司id*/
	private long comId;

}