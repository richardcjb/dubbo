/**
 * MACCitty.java
 * com.linyun.airline.common.sabre.dto
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.common.sabre.dto;

import lombok.Data;

/**
 * 多机场城市dto
 * <p>
 *
 * @author   朱晓川
 * @Date	 2016年12月21日 	 
 */
@Data
public class MACCitty {

	/**
	 * 城市代码
	 */
	private String code;

	/**
	 * 城市名称
	 */
	private String name;

	/**国家代码*/
	private String countryCode;

	/**国家名称*/
	private String countryName;

	/**国际区域名称*/
	private String regionName;

}
