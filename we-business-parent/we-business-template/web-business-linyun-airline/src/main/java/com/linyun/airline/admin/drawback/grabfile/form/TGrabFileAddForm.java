package com.linyun.airline.admin.drawback.grabfile.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TGrabFileAddForm extends AddForm {

	/**主键*/
	private long id;

	/**抓取邮件id*/
	private long mailId;

	/**上级id*/
	private long parentId;

	/**文件夹名称*/
	private String folderName;

	/**文件名称*/
	private String fileName;

	/**文件地址*/
	private String url;

	/**文件大小*/
	private String fileSize;

	/**文件类型(1-文件夹;2-文件;)*/
	private int type;

	/**状态(1-已删除;2-已启用;)*/
	private int status;

	/**创建时间*/
	private Date createTime;

	/**修改时间*/
	private Date updateTime;

	/**层级*/
	private long level;

	/**完整路径*/
	private String fullPath;

	/**序号*/
	private int sort;

	/**散团类型*/
	private String groupType;

}