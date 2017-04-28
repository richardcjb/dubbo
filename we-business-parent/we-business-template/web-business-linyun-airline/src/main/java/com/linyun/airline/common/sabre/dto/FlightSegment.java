package com.linyun.airline.common.sabre.dto;

import lombok.Data;

/**
 * 航段
 * <p>
 * 一次旅程有多个航段，或者理解为一张机票有多个航班(经停)
 *
 * @author   朱晓川
 * @Date	 2016年12月5日 	 
 */
@Data
public class FlightSegment {

	/**
	 * 经停次数
	 */
	private int StopQuantity;

	/**出发机场(代码)*/
	private String DepartureAirport;
	/**出发机场(名称)*/
	private String DepartureAirportName;

	/**到达机场(代码)*/
	private String ArrivalAirport;
	/**到达机场(名称)*/
	private String ArrivalAirportName;

	private String ResBookDesigCode;

	/**
	 * 航段合并数
	 * <p>
	 * 0或1，同一次航旅此值唯一，1表示合并了之前的一个航段
	 */
	private String MarriageGrp;

	/**
	 * 出发时间
	 */
	private String DepartureDateTime;

	/**
	 * 抵达时间
	 */
	private String ArrivalDateTime;

	/**
	 * 航班号(售票分配的航班号)
	 */
	private int FlightNumber;

	/**
	 * 耗时（分）
	 */
	private int ElapsedTime;

	/**
	 * 准点率(%)
	 */
	private int OnTimePerformance;

	/**售票航空公司代码*/
	private String MarketingAirline;

	/**实际执行的航空公司代码*/
	private String opAirlineCode;

	/**实际乘坐的航班号*/
	private int opFlightNumber;

	/**出发时区*/
	private int DepartureTimeZone;

	/**到达时区*/
	private int ArrivalTimeZone;

	/**
	 * 飞机类型代码
	 * <p>
	 * 比如320代表空客320
	 */
	private String Equipment;

	@Override
	public String toString() {
		return "FlightSegment [StopQuantity=" + StopQuantity + ", DepartureAirport=" + DepartureAirport
				+ ", DepartureAirportName=" + DepartureAirportName + ", ArrivalAirport=" + ArrivalAirport
				+ ", ArrivalAirportName=" + ArrivalAirportName + ", ResBookDesigCode=" + ResBookDesigCode
				+ ", MarriageGrp=" + MarriageGrp + ", DepartureDateTime=" + DepartureDateTime + ", ArrivalDateTime="
				+ ArrivalDateTime + ", FlightNumber=" + FlightNumber + ", ElapsedTime=" + ElapsedTime
				+ ", OnTimePerformance=" + OnTimePerformance + ", opAirlineCode=" + opAirlineCode + ", opFlightNumber="
				+ opFlightNumber + ", DepartureTimeZone=" + DepartureTimeZone + ", ArrivalTimeZone=" + ArrivalTimeZone
				+ ", Equipment=" + Equipment + "]";
	}
}
