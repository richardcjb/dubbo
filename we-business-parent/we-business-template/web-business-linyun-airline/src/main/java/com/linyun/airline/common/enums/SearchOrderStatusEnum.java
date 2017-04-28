/**
 * OrderStatusEnum.java
 * com.linyun.airline.common.enums
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.linyun.airline.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 内陆跨海订单状态
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年2月27日 	 
 */
public enum SearchOrderStatusEnum implements IEnum {
	SEARCH(1, "查询"), BOOKING(2, "预订"), TICKETING(3, "出票"), BILLING(4, "开票"), CLOSE(5, "关闭"), FIRBOOKING(6, "一订"), SECBOOKING(
			7, "二订"), THRBOOKING(8, "三订"), ALLBOOKING(9, "全款"), LASTBOOKING(10, "尾款");

	private int key;
	private String value;

	private SearchOrderStatusEnum(final int key, final String value) {
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
