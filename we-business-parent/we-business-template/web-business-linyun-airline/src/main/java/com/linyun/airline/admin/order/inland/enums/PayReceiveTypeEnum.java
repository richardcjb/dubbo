/**
 * PayReceiveTypeEnum.java
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
 * @Date	 2017年3月3日 	 
 */
public enum PayReceiveTypeEnum implements IEnum {
	REPEAT(0, "重复提醒"), RECEIVE(1, "收款"), PAY(2, "付款");
	private int key;
	private String value;

	private PayReceiveTypeEnum(final int key, final String value) {
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
