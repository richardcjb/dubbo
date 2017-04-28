package com.linyun.airline.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCustomerneedsAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**航空公司名称*/
	private String airline;

	/**旅行社*/
	private String travel;

	/**人数*/
	private Integer totalcount;

	/**天数*/
	private Integer totalday;

	/**联运要求*/
	private String uniontransport;

	/**去程日期*/
	private Date leavedate;

	private String leavedateString;

	/**出发城市*/
	private String leavecity;

	/**出发航班*/
	private String leaveflight;

	/**回程日期*/
	private Date backdate;

	private String backdateString;
	/**返回城市*/
	private String backcity;

	/**回程航班*/
	private String backflight;

	/**是否关闭*/
	private int isclose;

	/**操作人*/
	private String opid;

	/**操作时间*/
	private Date optime;

	/**最后修改时间*/
	private Date lastupdatetime;

	/**所属公司*/
	private long companyid;

	/**备注*/
	private String remark;

}