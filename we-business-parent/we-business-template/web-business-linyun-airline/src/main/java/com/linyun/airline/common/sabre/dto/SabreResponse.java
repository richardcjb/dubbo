package com.linyun.airline.common.sabre.dto;

import lombok.Data;

/**
 * Sabre接口返回数据，统一封装
 * <p>
 *
 * @author   朱晓川
 * @Date	 2016年12月5日 	 
 */
@Data
public class SabreResponse {

	/**
	 * http状态码
	 */
	private int statusCode;

	private Object data;

}
