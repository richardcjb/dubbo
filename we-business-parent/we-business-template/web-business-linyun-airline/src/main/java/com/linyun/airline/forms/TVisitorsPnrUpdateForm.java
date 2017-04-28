package com.linyun.airline.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TVisitorsPnrUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**PNR信息id*/
	private Integer pNRid;
		
	/**游客名单id*/
	private Integer visitorslistid;
		
}