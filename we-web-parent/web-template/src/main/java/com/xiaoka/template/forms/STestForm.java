package com.xiaoka.template.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.QueryForm;
import com.uxuexi.core.web.form.support.Condition;
import com.uxuexi.core.web.form.support.Condition.MatchType;

@Data
@EqualsAndHashCode(callSuper = true)
public class STestForm extends QueryForm {
	/**测试用户名称*/
	@Condition(match = MatchType.LIKE)
	private String sname;
}