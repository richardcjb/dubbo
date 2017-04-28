package com.linyun.airline.admin.drawback.mailroportmap.entity;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_grab_mail_report_map")
public class TGrabMailReportMapEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("抓取报表id")
	private Integer exportId;
	
	@Column
    @Comment("抓取邮件id")
	private Integer mailId;
	

}