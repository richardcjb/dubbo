/**
 * MailStatus.java
 * com.linyun.airline.admin.drawback.grabfile.entity
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.drawback.grabfile.entity;

import java.util.Date;

import lombok.Data;

/**
 * TODO(用来判断服务器上的邮件读取状态和本地抓取到的邮件读取状态)
 * @author   崔建斌
 * @Date	 2017年2月17日 	 
 */
@Data
public class MailStatusBean {

	/**主题*/
	private String theme;

	/**发件人*/
	private String sender;

	/**收件人*/
	private String addressee;

	/**发送时间*/
	private Date sendTime;

	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MailStatusBean other = (MailStatusBean) obj;
		if (addressee == null) {
			if (other.addressee != null)
				return false;
		} else if (!addressee.equals(other.addressee))
			return false;
		if (sendTime == null) {
			if (other.sendTime != null)
				return false;
		} else if (!sendTime.equals(other.sendTime))
			return false;
		if (sender == null) {
			if (other.sender != null)
				return false;
		} else if (!sender.equals(other.sender))
			return false;
		if (theme == null) {
			if (other.theme != null)
				return false;
		} else if (!theme.equals(other.theme))
			return false;
		return true;
	}

	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addressee == null) ? 0 : addressee.hashCode());
		result = prime * result + ((sendTime == null) ? 0 : sendTime.hashCode());
		result = prime * result + ((sender == null) ? 0 : sender.hashCode());
		result = prime * result + ((theme == null) ? 0 : theme.hashCode());
		return result;
	}

}
