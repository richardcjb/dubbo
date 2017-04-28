package com.linyun.airline.admin.operationsArea.form;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCheckboxStatusUpdateForm extends ModForm implements Serializable {
	private static final Long serialVersionUID = 1L;

	/**用户Id*/
	private Integer userId;

	/**任务栏是否隐藏*/
	private Integer taskShow;

	/**大日历是否隐藏*/
	private Integer maxCShow;

	/**小日历是否隐藏*/
	private Integer minCShow;

}