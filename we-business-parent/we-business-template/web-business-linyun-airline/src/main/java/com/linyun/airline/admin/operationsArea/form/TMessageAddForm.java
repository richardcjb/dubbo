package com.linyun.airline.admin.operationsArea.form;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TMessageAddForm extends AddForm implements Serializable {
	private static final Long serialVersionUID = 1L;

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

	/**消息状态*/
	private Integer msgStatus;

	/**生成日期*/
	private Date generateTime;
	private String generateTimeString;

	/**优先级*/
	private Integer priorityLevel;

	/**跳转订单id*/
	private Long upOrderId;

	/**订单状态*/
	private Integer upOrderStatus;

	//用户id
	private Integer recUserId;
	//用户类型
	private Integer recUserType;
	//来源id
	private Integer sendUserId;
	//来源类型
	private Integer sendUserType;

	//自定义界面多选框
	private String checkboxname;
	//当前用户
	private Integer userId;

}