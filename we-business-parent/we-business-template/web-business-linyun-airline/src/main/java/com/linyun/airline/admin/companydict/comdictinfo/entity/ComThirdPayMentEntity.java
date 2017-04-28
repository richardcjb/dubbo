/**
 * ComThirdPayMent.java
 * com.linyun.airline.admin.companydict.comdictinfo.entity
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.companydict.comdictinfo.entity;

import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * 第三方支付
 * @author   崔建斌
 * @Date	 2017年4月2日 	 
 */
@Data
@Table("t_third_payment")
public class ComThirdPayMentEntity {
	@Column
	@Id(auto = true)
	@Comment("主键")
	private Integer id;

	@Column
	@Comment("公司id")
	private Long comId;

	@Column
	@Comment("用户id")
	private Integer userId;

	@Column
	@Comment("字典类别编码")
	private String comTypeCode;

	@Column
	@Comment("字典代码")
	private String comDictCode;

	@Column
	@Comment("第三方公司名称")
	private String thirdCompanyName;

	@Column
	@Comment("银行卡名称")
	private String bankCardName;

	@Column
	@Comment("银行卡账号")
	private String bankCardNum;

	@Column
	@Comment("状态,1-启用，2--删除")
	private Integer status;

	@Column
	@Comment("创建时间")
	private Date createTime;

	@Column
	@Comment("更新时间")
	private Date updateTime;

	@Column
	@Comment("备注")
	private String remark;

	@Column
	@Comment("预留字段2")
	private String res2;

	@Column
	@Comment("预留字段3")
	private String res3;

	@Column
	@Comment("预留字段4")
	private String res4;

	@Column
	@Comment("预留字段5")
	private String res5;

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
		ComThirdPayMentEntity other = (ComThirdPayMentEntity) obj;
		if (bankCardNum == null) {
			if (other.bankCardNum != null)
				return false;
		} else if (!bankCardNum.equals(other.bankCardNum))
			return false;
		if (comDictCode == null) {
			if (other.comDictCode != null)
				return false;
		} else if (!comDictCode.equals(other.comDictCode))
			return false;
		if (comId == null) {
			if (other.comId != null)
				return false;
		} else if (!comId.equals(other.comId))
			return false;
		if (comTypeCode == null) {
			if (other.comTypeCode != null)
				return false;
		} else if (!comTypeCode.equals(other.comTypeCode))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (bankCardName == null) {
			if (other.bankCardName != null)
				return false;
		} else if (!bankCardName.equals(other.bankCardName))
			return false;
		if (res2 == null) {
			if (other.res2 != null)
				return false;
		} else if (!res2.equals(other.res2))
			return false;
		if (res3 == null) {
			if (other.res3 != null)
				return false;
		} else if (!res3.equals(other.res3))
			return false;
		if (res4 == null) {
			if (other.res4 != null)
				return false;
		} else if (!res4.equals(other.res4))
			return false;
		if (res5 == null) {
			if (other.res5 != null)
				return false;
		} else if (!res5.equals(other.res5))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (thirdCompanyName == null) {
			if (other.thirdCompanyName != null)
				return false;
		} else if (!thirdCompanyName.equals(other.thirdCompanyName))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
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
		result = prime * result + ((bankCardNum == null) ? 0 : bankCardNum.hashCode());
		result = prime * result + ((comDictCode == null) ? 0 : comDictCode.hashCode());
		result = prime * result + ((comId == null) ? 0 : comId.hashCode());
		result = prime * result + ((comTypeCode == null) ? 0 : comTypeCode.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((bankCardName == null) ? 0 : bankCardName.hashCode());
		result = prime * result + ((res2 == null) ? 0 : res2.hashCode());
		result = prime * result + ((res3 == null) ? 0 : res3.hashCode());
		result = prime * result + ((res4 == null) ? 0 : res4.hashCode());
		result = prime * result + ((res5 == null) ? 0 : res5.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((thirdCompanyName == null) ? 0 : thirdCompanyName.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

}
