package com.linyun.airline.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_user_msg")
public class TUserMsgEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Long id;

	@Column
	@Comment("消息Id")
	private Long msgId;

	@Column
	@Comment("用户Id")
	private Long userId;

	@Column
	@Comment("用户类型")
	private Long userType;

	@Column
	@Comment("是否阅读")
	private Long isRead;

	@Column
	@Comment("阅读时间")
	private Date readTime;

	@Column
	@Comment("来源Id")
	private Long fromId;

	@Column
	@Comment("消息来源")
	private Long msgSource;

	@Column
	@Comment("发送时间")
	private Date sendTime;

	@Column
	@Comment("客户消息id")
	private Long customerInfoId;

}