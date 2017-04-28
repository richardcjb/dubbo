/**
 * TypeQueryForm.java
 * com.xiaoka.template.admin.dictionary.dirtype.form
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.dictionary.dirtype.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;

import com.linyun.airline.common.enums.DataStatusEnum;
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
public class TypeQueryForm extends QueryForm {

	//类型名称
	@Condition(match = MatchType.LIKE)
	private String typeName;

	//按状态查询
	@Condition(match = MatchType.EQ)
	private int status = DataStatusEnum.ENABLE.intKey();

	//按字典类别编码查询
	@Condition(match = MatchType.LIKE)
	private String typeCode;

	//按创建时间排序
	private Date createTime;

	@Override
	public Cnd createCnd() {
		Cnd cnd = super.createCnd();
		cnd.orderBy("id", "DESC");
		cnd.orderBy("status", "DESC");
		cnd.orderBy("createTime", "DESC");
		return cnd;
	}

}
