package com.linyun.airline.admin.drawback.grabfile.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TGrabFileUpdateForm extends ModForm {

	/**前端传来的ids*/
	private String ids;

	/**状态(1-已删除;2-已启用;)*/
	private int status;

	/**文件夹名称*/
	private String fileName;

	/**修改时间*/
	private Date updateTime;
}