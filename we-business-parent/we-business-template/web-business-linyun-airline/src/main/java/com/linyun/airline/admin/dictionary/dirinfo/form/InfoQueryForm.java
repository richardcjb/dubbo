/**
 * InfoQueryForm.java
 * com.xiaoka.template.admin.dictionary.dirinfo.form
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.dictionary.dirinfo.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;

import com.linyun.airline.common.enums.DataStatusEnum;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.QueryForm;
import com.uxuexi.core.web.form.support.Condition;
import com.uxuexi.core.web.form.support.Condition.MatchType;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月3日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InfoQueryForm extends QueryForm {
	//字典类别编码
	@Condition(match = MatchType.LIKE)
	private String typeCode;
	//字典信息
	@Condition(match = MatchType.LIKE)
	private String dictName;
	//按状态查询
	@Condition(match = MatchType.EQ)
	private int status = DataStatusEnum.ENABLE.intKey();

	//按创建时间排序
	private Date createTime;

	@Override
	public Cnd createCnd() {
		Cnd cnd = super.createCnd();
		cnd.orderBy("status", "DESC").orderBy("createTime", "DESC");
		return cnd;
	}

	@SuppressWarnings("unused")
	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//TODO 添加自定义查询条件（可选）
		if (!Util.isEmpty(dictName) || !Util.isEmpty(typeCode)) {
			cnd.and("dictName", "LIKE", "%" + dictName + "%").or("typeCode", "LIKE", "%" + typeCode + "%");
		}
		if (!Util.isEmpty(status)) {
			cnd.and("status", "=", 1);
		}
		cnd.orderBy("status", "DESC").orderBy("createTime", "DESC");
		return cnd;
	}
}
