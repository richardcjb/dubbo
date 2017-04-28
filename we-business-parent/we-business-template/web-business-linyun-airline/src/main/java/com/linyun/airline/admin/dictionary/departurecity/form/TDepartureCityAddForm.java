package com.linyun.airline.admin.dictionary.departurecity.form;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TDepartureCityAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**字典类别编码*/
	private String typeCode;

	/**三字代码*/
	private String dictCode;

	/**国家*/
	private String countryName;

	/**州*/
	private String stateName;

	/**英文名称*/
	private String englishName;

	/**中文名称*/
	private String chineseName;

	/**拼音*/
	private String pinYin;

	/**描述*/
	private String description;

	/**状态*/
	private Integer status;

	/**创建时间*/
	private Date createTime;

	/**修改时间*/
	private Date updateTime;

	/**国际状态*/
	private Integer internatStatus;
}
