/**
 * PayMethodEnum.java
 * com.linyun.airline.admin.order.inland.enums
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.inland.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年3月8日 	 
 */
public enum PayMethodEnum implements IEnum {

	THIRDPART(1, "第三方支付"), INTERNATIONAL(2, "国际段专用卡");
	private int key;
	private String value;

	private PayMethodEnum(final int key, final String value) {
		this.value = value;
		this.key = key;
	}

	@Override
	public String key() {
		return String.valueOf(key);
	}

	@Override
	public String value() {
		return value;
	}

	public int intKey() {
		return key;
	}
}
