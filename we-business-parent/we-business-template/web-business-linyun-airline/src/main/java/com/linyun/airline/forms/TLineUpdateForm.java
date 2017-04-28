package com.linyun.airline.forms;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TLineUpdateForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**线路类型id*/
	private long typeId;

	/**线路名称*/
	private String name;

	/**描述*/
	private String description;

}