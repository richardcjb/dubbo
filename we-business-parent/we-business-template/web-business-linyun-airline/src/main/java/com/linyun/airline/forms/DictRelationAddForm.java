package com.linyun.airline.forms;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class DictRelationAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**字典id*/
	private long sourceId;

	/**关联字典id*/
	private long targetId;

	/**删除标识,0-未删除，1-已删除*/
	private long dr;
}