package com.linyun.airline.forms;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCustomerLineUpdateForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**客户信息id*/
	private long infoId;

	/**线路ID*/
	private long lineId;

}