/**
 * HttpResult.java
 * com.linyun.airline.common.result
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.common.result;

import lombok.Data;

/**
 * 封装http相应结果
 * <p>
 *
 * @author   朱晓川
 * @Date	 2016年12月22日 	 
 */
@Data
public class HttpResult {

	/**状态码*/
	private int statusCode;

	/**相应结果*/
	private String result;

}
