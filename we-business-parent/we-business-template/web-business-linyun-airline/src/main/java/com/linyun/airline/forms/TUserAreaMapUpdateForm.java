package com.linyun.airline.forms;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TUserAreaMapUpdateForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**用户id*/
	private long userId;

	/**区域id*/
	private long areaId;

}