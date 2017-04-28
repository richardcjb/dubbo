/**
 * InvoiceInfoEnum.java
 * com.linyun.airline.admin.invoicemanage.invoiceinfo.enums
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.invoicemanage.invoiceinfo.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 收开发票枚举
 * @author   崔建斌
 * @Date	 2017年3月16日 	 
 */
public enum InvoiceInfoEnum implements IEnum {
	INVOIC_ING(1, "开发票中"), INVOIC_ED(2, "已开发票"), RECEIPT_INVOIC_ING(3, "收发票中"), Already_INVOICe(4, "已收发票");
	private int key;
	private String value;

	private InvoiceInfoEnum(final int key, final String value) {
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
