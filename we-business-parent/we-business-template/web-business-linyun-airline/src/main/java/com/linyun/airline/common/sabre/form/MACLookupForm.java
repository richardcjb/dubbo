/**
 * MACLookupForm.java
 * com.linyun.airline.common.sabre.form
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.common.sabre.form;

import lombok.Data;

/**
 * 多机场城市查询form
 * @author   朱晓川
 * @Date	 2016年12月21日 	 
 */
@Data
public class MACLookupForm {

	/**
	 * ISO 3166 标准2字母国家代码，多个请使用逗号分隔
	 */
	private String country;

}
