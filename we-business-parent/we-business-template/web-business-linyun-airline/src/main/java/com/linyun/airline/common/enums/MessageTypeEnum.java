package com.linyun.airline.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 消息类型
 * <p>
 * 
 * @author   彭辉
 * @Date	 2016年12月07日
 */
public enum MessageTypeEnum implements IEnum {

	CLOSEMSG(0, "关闭"), NOTICEMSG(1, "系统通知消息"), PROCESSMSG(2, "客户管理消息"), CUSTOMMSG(3, "自定义型消息"), SEARCHMSG(4, "查询"), BOOKMSG(
			5, "预订"), DRAWBILLMSG(6, "出票"), MAKEOUTBILLMSG(7, "开票"), FIRBOOKMSG(8, "一订"), SECBOOKMSG(9, "二订"), THRBOOKMSG(
			10, "三订"), ALLBOOKMSG(11, "全款"), LASTBOOKMSG(12, "尾款"), FINANCIALMSG(13, "财务"), RECEIVEDMSG(14, "已收款"), PAYEDMSG(
			15, "已付款"), INVIOCEMSG(16, "已开发票"), RECINVIOCEMSG(17, "已收发票"), APPROVALEDMSG(18, "已审批"), UNAPPROVALMSG(19,
			"已拒绝"), RECSUBMITED(20, "收款已提交"), PSAPPROVALING(21, "需付款已提交申请"), INVIOCING(22, "开发票中"), RECINVIOCING(23,
			"收发票中"), DERATEMONEY(24, "收减免");

	private int key;
	private String value;

	private MessageTypeEnum(final int key, final String value) {
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
