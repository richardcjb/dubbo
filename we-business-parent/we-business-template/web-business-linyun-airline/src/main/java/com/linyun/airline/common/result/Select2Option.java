/**
 * Select2Option.java
 * com.linyun.airline.common.result
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.common.result;

import lombok.Data;

/**
 * 用于select2插件的option选项
 * <p>
 *
 * @author   朱晓川
 * @Date	 2016年11月29日 	 
 */
@Data
public class Select2Option {

	/**选项id*/
	private long id;

	/**选项内容，对应下拉列表*/
	private String text;

}
