package com.linyun.airline.admin.operationsArea.form;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TUserMsgUpdateForm extends ModForm implements Serializable {
	private static final Long serialVersionUID = 1L;

	/**消息Id*/
	private Integer msgId;

	/**用户Id*/
	private Integer userId;

	/**用户类型*/
	private Integer userType;

	/**是否阅读*/
	private Integer isRead;

	/**阅读时间*/
	private Date readTime;

	/**来源Id*/
	private Integer fromId;

	/**消息来源*/
	private Integer msgSource;

	/**发送时间*/
	private Date sendTime;

}