package com.linyun.airline.admin.drawback.mailroportmap.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TGrabMailReportMapUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**抓取报表id*/
	private Integer exportId;
		
	/**抓取邮件id*/
	private Integer mailId;
		
}