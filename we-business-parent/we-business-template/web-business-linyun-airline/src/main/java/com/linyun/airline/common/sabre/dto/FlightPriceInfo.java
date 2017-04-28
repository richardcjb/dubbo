/**
 * FlightPriceInfo.java
 * com.linyun.airline.common.sabre.dto
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.common.sabre.dto;

import lombok.Data;

/**
 * 机票价格信息
 * <p>
 *
 * @author   朱晓川
 * @Date	 2016年12月13日 	 
 */
@Data
public class FlightPriceInfo {

	/**
	 * 货币代码:
	 * <p>
	 * 比如USD代表美元
	 */
	private String currencyCode;

	/**
	 * 总价
	 */
	private double totalAmount;

	/**航空运输公司收取的价格，不含税*/
	private double baseAmount;

	/**等值货币价格，不含税*/
	private double equivFareAmount;

}
