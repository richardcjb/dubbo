/**
 * OrderStatusEnum.java
 * com.linyun.airline.common.enums
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 会计 收款订单状态
 * <p>
 * @author   彭辉
 * @Date	 2017年3月4日 	 
 */
public enum AccountReceiveEnum implements IEnum {
	RECEIVINGMONEY(1, "收款中"), RECEIVEDONEY(2, "已收款");

	private int key;
	private String value;

	private AccountReceiveEnum(final int key, final String value) {
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
