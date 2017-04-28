/**
 * ComDictTypeEnum.java
 * com.linyun.airline.admin.companydict.comdicttype.enums
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.companydict.comdictinfo.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 字典类别编码
 * @author   崔建斌
 * @Date	 2017年3月24日 	 
 */
public enum ComDictTypeEnum implements IEnum {

	DICTTYPE_LSXM("LSXM", "流水项目"), DICTTYPE_XMYT("XMYT", "项目用途"), DICTTYPE_DLZH("DLZH", "登录账号"), DICTTYPE_ZJZL("ZJZL",
			"资金种类"), DICTTYPE_DSFZF("DSFZF", "第三方支付");
	private String key;
	private String value;

	private ComDictTypeEnum(final String key, final String value) {
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
}
