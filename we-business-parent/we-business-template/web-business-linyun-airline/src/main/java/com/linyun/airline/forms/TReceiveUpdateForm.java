package com.linyun.airline.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TReceiveUpdateForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**银行卡id*/
	private Integer bankcardid;

	/**银行名称*/
	private String bankcardname;

	/**银行卡号*/
	private String bankcardnum;

	/**合计*/
	private Double sum;

	/**收款时间*/
	private Date receivedate;

}