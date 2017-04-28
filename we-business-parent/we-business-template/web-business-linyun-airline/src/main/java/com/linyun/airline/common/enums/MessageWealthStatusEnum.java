package com.linyun.airline.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 消息中的  消息状态
 * <p>
 * 
 * @author   彭辉
 * @Date	 2016年12月07日
 */
public enum MessageWealthStatusEnum implements IEnum {
	SEARCH(1, "查询"), BOOKING(2, "预订"), TICKETING(3, "出票"), BILLING(4, "开票"), CLOSE(5, "关闭"), FIRBOOKING(6, "一订"), SECBOOKING(
			7, "二订"), THRBOOKING(8, "三订"), ALLBOOKING(9, "全款"), LASTBOOKING(10, "尾款"), RECEIVED(11, "已收款"), PAYED(12,
			"已付款"), INVIOCE(13, "已开发票"), RECINVIOCE(14, "已收发票"), APPROVAL(15, "已审批"), UNAPPROVAL(16, "已拒绝"), RECSUBMITED(
			17, "收款已提交"), PSAPPROVALING(18, "需付款已提交申请"), INVIOCING(19, "开发票中"), RECINVIOCING(20, "收发票中"), DERATEMONEY(
			21, "减免");

	private int key;
	private String value;

	private MessageWealthStatusEnum(final int key, final String value) {
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
