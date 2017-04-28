package com.linyun.airline.admin.drawback.grabfile.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.admin.drawback.grabfile.entity.TGrabFileEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TGrabFileSqlForm extends DataTablesParamForm {
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
	private long sort;

	/**散团类型*/
	private String groupType;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TGrabFileEntity.class);
		//sqlString += " AND (CASE WHEN TYPE=2 AND url NOT LIKE '%pdf' THEN 0 ELSE 1 END )=1";
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	//TODO 添加自定义查询条件（可选）
	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		if (!Util.isEmpty(parentId)) {
			cnd.and("parentId", "=", parentId);
		}
		return cnd;
	}
}