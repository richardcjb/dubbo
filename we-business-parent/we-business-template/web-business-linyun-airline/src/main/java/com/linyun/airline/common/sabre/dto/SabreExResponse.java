/**
 * SabreExResponse.java
 * com.linyun.airline.common.sabre.dto
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.common.sabre.dto;

import lombok.Data;

/**
 * Sabre接口异常信息
 * <p>
 *
 * @author   朱晓川
 * @Date	 2016年12月5日 	 
 */
@Data
public class SabreExResponse {

	/**
	 * 状态
	 */
	private String status;

	private String reportingSystem;

	/**
	 * 时间
	 */
	private String timeStamp;

	/**
	 * 错误类型，比如Validation
	 */
	private String type;

	/**
	 * 错误代码
	 */
	private String errorCode;

	/**
	 * 实例，唯一标识当前出错的请求
	 */
	private String instance;

	/**
	 * 提示信息
	 */
	private String message;

}
