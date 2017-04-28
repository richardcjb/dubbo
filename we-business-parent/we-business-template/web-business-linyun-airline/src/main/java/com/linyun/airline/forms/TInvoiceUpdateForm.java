package com.linyun.airline.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TInvoiceUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**发票项*/
	private String item;
		
	/**描述*/
	private String description;
		
}