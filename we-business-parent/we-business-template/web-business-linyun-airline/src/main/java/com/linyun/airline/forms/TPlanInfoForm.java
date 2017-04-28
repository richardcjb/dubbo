package com.linyun.airline.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.entities.TPlanInfoEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.SQLParamForm;

@Data
public class TPlanInfoForm implements SQLParamForm, Serializable {
	private static final long serialVersionUID = 1L;
	/**主键ID*/
	private Integer id;

	/**旅行社名称*/
	private String travelname;

	/**航空公司名称*/
	private String airlinename;

	/**去程日期*/
	private String leavesdate;

	/**去程航班*/
	private String leaveairline;

	/**出发城市*/
	private String leavescity;

	/**回程日期*/
	private String backsdate;

	/**回程航班*/
	private String backairline;

	/**返回城市*/
	private String backscity;

	/**人数*/
	private Integer peoplecount;

	/**天数*/
	private Integer dayscount;

	/**联运城市*/
	private String unioncity;

	/**1、系列团，2、临时团；3关闭*/
	private Integer teamtype;

	/**订单号*/
	private String ordernumber;

	/**是否保存*/
	private Integer issave;

	/**操作人*/
	private Integer opid;

	/**创建时间*/
	private Date createtime;

	/**最后修改时间*/
	private Date laseupdatetime;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TPlanInfoEntity.class);
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//TODO 添加自定义查询条件（可选）

		return cnd;
	}
}