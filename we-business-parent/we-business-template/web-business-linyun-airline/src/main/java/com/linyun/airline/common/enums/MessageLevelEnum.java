package com.linyun.airline.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 消息优先级
 * <p>
 * 
 * @author   彭辉
 * @Date	 2016年12月07日
 */
public enum MessageLevelEnum implements IEnum {
	MSGLEVEL1(1, "优先级低"), MSGLEVEL2(2, "优先级中"), MSGLEVEL3(3, "优先级高"), MSGLEVEL4(4, "优先级较高"), MSGLEVEL5(5, "优先级最高");

	private int key;
	private String value;

	private MessageLevelEnum(final int key, final String value) {
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
