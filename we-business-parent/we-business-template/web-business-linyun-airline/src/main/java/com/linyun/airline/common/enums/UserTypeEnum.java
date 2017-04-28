package com.linyun.airline.common.enums;

import com.uxuexi.core.common.enums.IEnum;

public enum UserTypeEnum implements IEnum {

	UPCOM(0, "上游公司普通用户"), UP_MANAGER(1, "上游公司管理员"), PLAT(2, "平台用户"), AGENT(3, "代理商普通用户"), AGENT_MANAGER(4, "代理商管理员");
	private int key;
	private String value;

	private UserTypeEnum(final int key, final String value) {
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
