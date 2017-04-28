package com.linyun.airline.admin.drawback.grabfile.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_grab_file")
public class TGrabFileEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	@Column
	@Comment("主键")
	private long id;

	@Column
	@Comment("抓取邮件id")
	private long mailId;

	@Column
	@Comment("上级id")
	private long parentId;

	@Column
	@Comment("文件夹名称")
	private String folderName;

	@Column
	@Comment("文件名称")
	private String fileName;

	@Column
	@Comment("文件地址")
	private String url;

	@Column
	@Comment("文件大小")
	private String fileSize;

	@Column
	@Comment("文件类型(1-文件夹;2-文件;)")
	private int type;

	@Column
	@Comment("状态(1-已删除;2-已启用;)")
	private int status;

	@Column
	@Comment("创建时间")
	private Date createTime;

	@Column
	@Comment("修改时间")
	private Date updateTime;

	@Column
	@Comment("层级")
	private long level;

	@Column
	@Comment("完整路径")
	private String fullPath;

	@Column
	@Comment("序号")
	private long sort;

	@Column
	@Comment("散团类型")
	private String groupType;
}