package com.linyun.airline.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class DictInfoAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**字典类别编码*/
	private String typeCode;

	/**字典代码*/
	private String dictCode;

	/**字典信息*/
	private String dictName;

	/**描述*/
	private String description;

	/**字典信息状态,1-启用，2--删除*/
	private long status;

	/**全拼*/
	private String quanPin;

	/**简拼*/
	private String jianpin;

	/**创建时间*/
	private Date createTime;

}
