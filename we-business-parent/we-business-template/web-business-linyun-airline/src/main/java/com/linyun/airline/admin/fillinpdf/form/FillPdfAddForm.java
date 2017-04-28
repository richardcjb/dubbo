package com.linyun.airline.admin.fillinpdf.form;

/**
 * TAirlinePolicyAddForm.java
 * com.linyun.airline.forms
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

import java.io.File;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年3月11日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FillPdfAddForm extends AddForm {

	private String xing;
	private String birthxing;
	private String birthname;
	private String birthtime;
	private String birtharea;
	private String birthcountry;
	private String phoneurl;
	private File fileName;

	private String nowcountry;
	private String vormundnameandaddress;
	private String identitycard;
	private String travelnum;
	private String issuetime;
	private String effectivetime;
	private String issueoffice;
	private String applicantaddressandemail;
	private String phonenumber;
	private String permitnumber;
	private String effectivetimeofcountry;

}
