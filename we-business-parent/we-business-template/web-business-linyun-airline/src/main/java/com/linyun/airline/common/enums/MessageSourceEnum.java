package com.linyun.airline.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 消息来源
 * <p>
 * 
 * @author   彭辉
 * @Date	 2016年12月07日
 */
public enum MessageSourceEnum implements IEnum {
	PERSONALMSG(1, "来自个人的消息"), COMPANYMSG(2, "来自公司的消息"), SYSTEMMSG(3, "来自系统的消息");

	private int key;
	private String value;

	private MessageSourceEnum(final int key, final String value) {
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
