/**
 * SabreAccessToken.java
 * com.xiaoka.test.entity
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.common.sabre.bean;

import lombok.Data;

/**
 * sabre  API调用凭据
 * <p>
 *
 * @author   朱晓川
 * @Date	 2016年11月15日 	 
 */
@Data
public class SabreAccessToken {

	/**
	 * 凭据
	 */
	private String access_token;

	/**
	 * 凭据类型
	 */
	private String token_type;

	/**
	 * 过期时间
	 */
	private int expires_in;

	private long loadTimeMillis;

}
