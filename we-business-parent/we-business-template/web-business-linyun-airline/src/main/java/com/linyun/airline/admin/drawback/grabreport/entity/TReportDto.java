/**
 * TReportDto.java
 * com.linyun.airline.admin.drawback.grabreport.entity
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.drawback.grabreport.entity;

import lombok.Data;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2017年3月11日 	 
 */
@Data
public class TReportDto {
	//成本单价
	Double costprice;
	//人数
	Integer peoplecount;
	//实收单价(销售价)
	Double price;
}
