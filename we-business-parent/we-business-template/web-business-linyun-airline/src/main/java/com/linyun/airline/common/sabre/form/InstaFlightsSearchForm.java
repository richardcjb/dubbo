/**
 * InstaFlightsSearchParam.java
 * com.xiaoka.test.entity
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.common.sabre.form;

import lombok.Data;

/**
 * Sabre-InstaFlightsSearch接口查询参数
 * 
 * @author   朱晓川
 * @Date	 2016年11月15日 	 
 */
@Data
public class InstaFlightsSearchForm {

	/**
	 * 起飞机场/出发城市（必须）
	 * <p>
	 * IATA机场3字代码或者MAC城市三字代码,比如ATL,BJS(北京)
	 */
	private String origin;

	/**
	 * 降落机场/到达城市（必须）
	 * <p>
	 * IATA机场3字代码或者MAC城市三字代码,比如ATL,BJS(北京)
	 */
	private String destination;

	/**
	 * 出发日期（必须）
	 * <P>
	 * 格式:yyyy-MM-dd,最大日期:当前日期 + 192 (天)
	 */
	private String departuredate;

	/**
	 * 返程日期（可选）
	 * <P>
	 * 格式:yyyy-MM-dd,最大日期:出发日期 + 16 (天)
	 */
	private String returndate;

	/**
	 * 航空公司（可选）
	 * <P>
	 * 2-letter IATA 代码,多个航空公司用逗号分隔.比如UA,DL,US
	 * 
	 */
	private String includedcarriers;

	/**
	 * 旅程最低总价（可选）
	 * <P>
	 * 数字
	 */
	private Integer minfare;

	/**
	 * 旅程最高总价（可选）
	 * <P>
	 * 数字
	 */
	private Integer maxfare;

	/**
	 * 销售国家（可选）
	 * <p>
	 * ISO 3166 标准2字母国家代码,默认值为US（美国）
	 */
	private String pointofsalecountry;

	/**
	 * 每次查询记录数（可选）
	 * <p>
	 * 也就是pageSize，默认值为50
	 */
	private Integer limit;

	/**
	 * 从总记录的第几条开始查（可选）
	 * <p>
	 * limit 1,n;分页--从第几条开始查，查几条。最小值为1
	 */
	private Integer offset;

	/**
	 * 乘客数(可选)
	 * <P>
	 *  0-10之间的数字
	 */
	private Integer passengercount;

	/**
	 * 排序依据(可选)
	 * <p>
	 * 可选值为:
	 * totalfare(默认),departuretime,elapsedtime三者之一.
	 * 如果不制定sortby和order参数则默认按照totalfare升序排列
	 */
	private String sortby;

	/**
	 * 排序类型(可选)
	 * <p>
	 * asc（Default）或者dsc，升序或降序
	 */
	private String order;

	/**第二排序字段*/
	private String sortby2;

	/**第二排序方式*/
	private String order2;

	/**
	 * 起飞时段（可选）
	 * <P>
	 * 起飞日期当天的时段，格式为HHMMHHMM.
	 * 首班出境航班，一般就是指从起飞机场出发的第一班。
	 */
	//	private String outbounddeparturewindow;

	//成人
	private String agentSelect;
	//儿童
	private String childrenSelect;
	//婴儿
	private String babySelect;
	//舱位等级
	private String airLevel;

}
