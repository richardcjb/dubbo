package com.linyun.airline.admin.customer.base;

/**
 * TestSelect2Option.java
 * com.xiaoka.test
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

import lombok.Data;

import com.linyun.airline.common.result.Select2Option;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   彭辉
 * @Date	 2017年3月11日 	 
 */
@Data
public class Select2Option2 extends Select2Option {

	/**选项id*/
	private long id;

	/**选项内容，对应下拉列表*/
	private String text;

	/**内容类型*/
	private long comType;
}
