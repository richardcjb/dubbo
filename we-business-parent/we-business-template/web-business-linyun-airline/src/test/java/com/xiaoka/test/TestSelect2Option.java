/**
 * TestSelect2Option.java
 * com.xiaoka.test
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.xiaoka.test;

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
public class TestSelect2Option extends Select2Option {

	/**选项id*/
	public long id;

	/**选项内容，对应下拉列表*/
	public String text;

	/**内容类型*/
	public long type;
}
