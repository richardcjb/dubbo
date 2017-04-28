package com.linyun.airline.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TPlanInfoAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**旅行社名称*/
	private String travelname;

	/**航空公司名称*/
	private String airlinename;

	/**去程日期*/
	private String leavesdate;

	/**去程航班*/
	private String leaveairline;

	/**出发城市*/
	private String leavescity;

	/**回程日期*/
	private String backsdate;

	/**回程航班*/
	private String backairline;

	/**返回城市*/
	private String backscity;

	/**人数*/
	private Integer peoplecount;

	/**天数*/
	private Integer dayscount;

	/**联运城市*/
	private String unioncity;

	/**1、系列团，2、临时团；3关闭*/
	private Integer teamtype;

	/**订单号*/
	private String ordernumber;

	/**是否保存*/
	private Integer issave;

	/**操作人*/
	private Integer opid;

	/**创建时间*/
	private Date createtime;

	/**最后修改时间*/
	private Date laseupdatetime;

	/**起始日期（页面提交）*/
	private String startdate;

	/**结束日期（页面提交）*/
	private String enddate;

	/**小日历选择日期（页面提交）*/
	private String calenderdate;

	/**每周几（页面提交）*/
	private String weekday;

	/**时间类型*/
	private int timetype;

	/**FOC*/
	private int foc;

	/**返程出发城市*/
	private String backleavecity;

	/**返程抵达城市*/
	private String backbackcity;

	/**出发城市、抵达城市、航班号json串*/
	private String cityairlinejson;

}