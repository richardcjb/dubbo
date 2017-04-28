package com.xiaoka.template.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCustomerInvoiceUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**客户信息id*/
	private Integer infoId;
		
	/**发票项id*/
	private Integer invoiceId;
		
}