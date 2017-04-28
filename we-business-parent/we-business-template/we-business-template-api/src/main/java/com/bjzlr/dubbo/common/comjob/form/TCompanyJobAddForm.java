package com.bjzlr.dubbo.common.comjob.form;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCompanyJobAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**职位id*/
	private long posid;

	/**公司id*/
	private long comId;

}