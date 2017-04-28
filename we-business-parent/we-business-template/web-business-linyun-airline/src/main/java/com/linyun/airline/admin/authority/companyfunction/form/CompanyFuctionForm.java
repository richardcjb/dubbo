/**
 * TCompanyModForm.java
 * com.linyun.airline.admin.authority.companyfunction.form
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.authority.companyfunction.form;

import javax.validation.constraints.Pattern;

import lombok.Data;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年12月1日 	 
 */
@Data
public class CompanyFuctionForm {

	/**公司id*/
	private long companyId;

	/**职位关联的功能id列表,数字和逗号组成,可以为空*/
	@Pattern(regexp = "(\\d+,?)*")
	private String functionIds;
}
