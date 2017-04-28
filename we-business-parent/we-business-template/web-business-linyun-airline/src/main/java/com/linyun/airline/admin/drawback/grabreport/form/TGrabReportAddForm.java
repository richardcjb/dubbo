package com.linyun.airline.admin.drawback.grabreport.form;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TGrabReportAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**备注*/
	private String remark;

	/**汇款*/
	private Double remit;

	/**blance(备用金余额)*/
	private Double depositBalance;

	/**票价(含行李)*/
	private Double ticketPrice;

	/**刷卡费*/
	private Double swipe;

	/**税金/杂项*/
	private Double tax;

	/**消费税(GST)*/
	private Double exciseTax1;

	/**代理费*/
	private Double agencyFee;

	/**税返点*/
	private Double taxRebate;

	/**退税状态*/
	private Integer backStatus;

	/**实收单价(含操作费)*/
	private Double realIncome;

	/**实收合计(含操作费)*/
	private Double realTotal;

	/**代理费2*/
	private Double agencyFee2;

	/**代理返点*/
	private Double agentRebate;

	/**入澳时间*/
	private String inAustralianTime;

	/**出澳时间*/
	private String outAustralianTime;

	/**pnrInfoId*/
	private Integer pnrInfoId;

	/**PNR*/
	private String PNR;

}