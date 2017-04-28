package com.linyun.airline.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TVisitorsPnrAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**PNR信息id*/
	private Integer pNRid;
		
	/**游客名单id*/
	private Integer visitorslistid;
		
}