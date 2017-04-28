package com.linyun.airline.admin.invoicemanage.invoicedetail.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TInvoiceDetailAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**发票信息id*/
	private Integer invoiceinfoid;
		
	/**发票号*/
	private String invoicenum;
		
	/**发票金额*/
	private Double invoicebalance;
		
	/**发票图片url*/
	private String invoiceurl;
		
	/**操作人*/
	private Integer opid;
		
	/**操作时间*/
	private Date optime;
		
}