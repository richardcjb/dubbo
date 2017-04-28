/**
 * InternationalStatusEnum.java
 * com.linyun.airline.admin.order.international.enums
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.international.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年3月15日 	 
 */
public enum InternationalStatusEnum implements IEnum {

	SEARCH(1, "查询"), BOOKING(2, "预订"), ONEBOOK(3, "一订"), TWOBOOK(4, "二订"), THREEBOOK(5, "三订"), FULLAMOUNT(6, "全款"), TAILMONEY(
			7, "尾款"), TICKETING(8, "出票"), CLOSE(9, "关闭");
	private int key;
	private String value;

	private InternationalStatusEnum(final int key, final String value) {
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
