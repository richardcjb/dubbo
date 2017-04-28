package com.linyun.airline.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TVisitorInfoUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**订单id*/
	private Integer ordernum;
		
	/**姓名*/
	private String visitorname;
		
	/**电话*/
	private String phonenum;
		
}