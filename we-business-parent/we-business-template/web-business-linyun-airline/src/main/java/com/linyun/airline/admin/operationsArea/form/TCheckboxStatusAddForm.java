package com.linyun.airline.admin.operationsArea.form;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCheckboxStatusAddForm extends AddForm implements Serializable {
	private static final Long serialVersionUID = 1L;

	/**id*/
	private Integer id;

	/**用户Id*/
	private Integer userId;

	/**任务栏是否隐藏*/
	private Integer taskShow;

	/**大日历是否隐藏*/
	private Integer maxCShow;

	/**小日历是否隐藏*/
	private Integer minCShow;

}