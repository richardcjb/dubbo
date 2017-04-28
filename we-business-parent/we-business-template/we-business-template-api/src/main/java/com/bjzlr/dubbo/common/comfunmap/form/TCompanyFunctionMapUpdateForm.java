package com.bjzlr.dubbo.common.comfunmap.form;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCompanyFunctionMapUpdateForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**功能id*/
	private long funId;

	/**公司id*/
	private long comId;

}