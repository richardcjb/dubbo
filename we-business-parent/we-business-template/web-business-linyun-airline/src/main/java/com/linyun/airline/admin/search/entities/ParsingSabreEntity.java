/**
 * ParsingSabreEntity.java
 * com.linyun.airline.admin.search.entities
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.search.entities;

import java.io.Serializable;

import lombok.Data;

/**
 * TODO(解析sabre PNR)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   彭辉
 * @Date	 2017年2月15日 	 
 */
@Data
public class ParsingSabreEntity implements Serializable {

	//序号
	private int id;

	//航空公司
	private String airlineComName;

	//航班号 
	private String flightNum;

	//出发城市
	private String airDepartureCity;

	//抵达城市
	private String airLandingCity;

	//航段
	private String airLine;

	//舱位
	private String airSeats;

	//起飞日期
	private String airLeavelDate;

	//起飞时间
	private String airDepartureTime;

	//降落时间 
	private String airLandingTime;

	//座位数 
	private String airSeatNum;

	//价格
	private String airTicketPrice;

	//预订时间
	private String presetDate;

	//舱位价格
	private String airSeatsPrice;

	//价格币种
	private String airSeatsCurrency;

}
