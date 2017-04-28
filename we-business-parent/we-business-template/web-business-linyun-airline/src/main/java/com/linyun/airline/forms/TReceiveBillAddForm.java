package com.linyun.airline.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TReceiveBillAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**收款id*/
	private Integer receiveid;
		
	/**水单url*/
	private String receiptUrl;
		
}