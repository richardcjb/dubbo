package com.linyun.airline.admin.operationsArea.form;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TMessageUpdateForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**标题*/
	private String msgTitle;

	/**内容*/
	private String msgContent;

	/**消息类型*/
	private Integer msgType;

	/**提醒类型*/
	private Long reminderMode;

	/**是否提醒*/
	private Long isRemind;

	/**生成日期*/
	private Date generateTime;
	private String generateTimeString;

	/**优先级*/
	private Integer priorityLevel;

	/**消息状态*/
	private Integer msgStatus;

	/**生成日期*/
	private Date redDivDate;

	/**跳转订单id*/
	private Long upOrderId;
}