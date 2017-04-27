package com.linyun.airline.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TBankCardUpdateForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**银行卡名称*/
	private String cardName;

	/**卡号*/
	private String cardNum;

	/**银行卡类型*/
	private String bankCardType;

	/**银行名称*/
	private String bankName;

	/**银行id*/
	private long bankNameId;

	/**币种*/
	private String currency;

	/**修改时间*/
	private Date updateTime;

	/**状态*/
	private Integer status;

	/**备注*/
	private String remark;

}