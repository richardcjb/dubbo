package com.linyun.airline.admin.customer.service;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.upload.UploadAdaptor;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.linyun.airline.admin.Company.service.CompanyViewService;
import com.linyun.airline.admin.customer.base.Select2Option2;
import com.linyun.airline.admin.customer.form.TCustomerInfoSqlForm;
import com.linyun.airline.admin.dictionary.departurecity.entity.TDepartureCityEntity;
import com.linyun.airline.admin.dictionary.external.externalInfoService;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.operationsArea.service.RemindMessageService;
import com.linyun.airline.admin.order.international.enums.InternationalStatusEnum;
import com.linyun.airline.common.base.MobileResult;
import com.linyun.airline.common.base.UploadService;
import com.linyun.airline.common.enums.AccountPayEnum;
import com.linyun.airline.common.enums.AccountReceiveEnum;
import com.linyun.airline.common.enums.CompanyTypeEnum;
import com.linyun.airline.common.enums.MessageLevelEnum;
import com.linyun.airline.common.enums.MessageRemindEnum;
import com.linyun.airline.common.enums.MessageSourceEnum;
import com.linyun.airline.common.enums.MessageStatusEnum;
import com.linyun.airline.common.enums.MessageTypeEnum;
import com.linyun.airline.common.enums.OrderStatusEnum;
import com.linyun.airline.common.enums.OrderTypeEnum;
import com.linyun.airline.common.enums.UserStatusEnum;
import com.linyun.airline.common.enums.UserTypeEnum;
import com.linyun.airline.common.result.Select2Option;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TCustomerInfoEntity;
import com.linyun.airline.entities.TCustomerInvoiceEntity;
import com.linyun.airline.entities.TCustomerOutcityEntity;
import com.linyun.airline.entities.TUpcompanyEntity;
import com.linyun.airline.entities.TUserEntity;
import com.linyun.airline.forms.TCustomerInfoAddForm;
import com.linyun.airline.forms.TCustomerInfoUpdateForm;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.DbSqlUtil;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;
import com.uxuexi.core.web.util.FormUtil;

@IocBean
public class CustomerViewService extends BaseService<TCustomerInfoEntity> {
	private static final Log log = Logs.get();

	//付款中订单状态
	private static final int APPROVALPAYING = AccountPayEnum.APPROVALPAYING.intKey();
	//收款中订单状态
	private static final int RECEIVINGMONEY = AccountReceiveEnum.RECEIVINGMONEY.intKey();

	private static final int TEAM = OrderTypeEnum.TEAM.intKey(); //国际
	private static final int FIT = OrderTypeEnum.FIT.intKey(); //内陆

	//订单状态  内陆
	private static final int SEARCH = OrderStatusEnum.SEARCH.intKey();
	private static final int CLOSE = OrderStatusEnum.CLOSE.intKey();
	//国际
	private static final int INTERS = InternationalStatusEnum.SEARCH.intKey();
	private static final int INTERC = InternationalStatusEnum.CLOSE.intKey();

	//公司类型
	private static final int UPCOMPANY = CompanyTypeEnum.UPCOMPANY.intKey();
	private static final int AGENT = CompanyTypeEnum.AGENT.intKey();
	//普通用户类型
	private static final int UPCOM_USER = UserTypeEnum.UPCOM.intKey();
	private static final int AGENT_USER = UserTypeEnum.AGENT.intKey();
	//管理员用户类型
	private static final int UP_MANAGER = UserTypeEnum.UP_MANAGER.intKey();
	private static final int AGENT_MANAGER = UserTypeEnum.AGENT_MANAGER.intKey();
	//功能id
	private static final int RECPAY_MENU_ID = 81;
	private static final int INVOICE_MENU_ID = 97;

	private static final int USER_VALID = UserStatusEnum.VALID.intKey();

	@Inject
	private externalInfoService externalInfoService;

	@Inject
	private CompanyViewService companyViewService;

	@Inject
	private UploadService qiniuUploadService;

	@Inject
	private RemindMessageService remindService;

	//负责人
	public Object agent(HttpSession session) {
		Map<String, Object> obj = new HashMap<String, Object>();
		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		int userType = loginUser.getUserType(); //用户类型
		long userId = loginUser.getId();

		//管理员
		if (Util.eq(UP_MANAGER, userType) || Util.eq(AGENT_MANAGER, userType)) {
			//当前公司id
			TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
			long companyId = tCompanyEntity.getId();
			Sql sql = Sqls.create(sqlManager.get("customer_agent_list"));
			sql.setParam("comid", companyId);
			sql.setParam("status", USER_VALID);
			/*sql.setParam("userid", userId);*/
			List<Record> record = dbDao.query(sql, null, null);
			obj.put("userlist", record);
		}

		//普通用户
		if (Util.eq(UPCOM_USER, userType) || Util.eq(AGENT_USER, userType)) {
			List<TUserEntity> record = new ArrayList<TUserEntity>();
			record.add(loginUser);
			obj.put("userlist", record);
		}

		return obj;
	}

	public Object listData(TCustomerInfoSqlForm queryForm, HttpSession session) {
		//当前公司id
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();//得到公司关系表id
		queryForm.setCompanyId(companyId);
		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		int userType = loginUser.getUserType(); //用户类型
		long userId = loginUser.getId();

		String userIds = "";

		//管理员
		if (Util.eq(UP_MANAGER, userType) || Util.eq(AGENT_MANAGER, userType)) {
			Sql sql = Sqls.create(sqlManager.get("customer_agent_list"));
			sql.setParam("comid", companyId);
			sql.setParam("status", USER_VALID);
			/*sql.setParam("userid", userId);*/
			List<Record> record = dbDao.query(sql, null, null);
			for (Record r : record) {
				String id = r.getString("id");
				userIds += id + ",";
			}
		}
		//普通用户
		if (Util.eq(UPCOM_USER, userType) || Util.eq(AGENT_USER, userType)) {
			userIds = userId + ",";
		}
		userIds = userIds.substring(0, userIds.length() - 1);
		queryForm.setUserIds(userIds);

		return listPage4Datatables(queryForm);
	}

	//客户公司
	public Object company(String comName, HttpSession session) {

		List<Record> records = new ArrayList<Record>();

		//得到当前用户所在公司的id
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();
		TUpcompanyEntity upcompany = dbDao.fetch(TUpcompanyEntity.class, Cnd.where("comId", "=", companyId));

		Sql sql = Sqls.create(sqlManager.get("customer_comOption_list"));
		Cnd cnd = Cnd.NEW();
		cnd.and("comName", "like", Strings.trim(comName) + "%");
		cnd.and("deletestatus", "=", 0);
		cnd.and("c.comName", "!=", "平台后台管理");
		cnd.and("c.id", "!=", companyId);
		List<Record> agentCompanyList = dbDao.query(sql, cnd, null);
		String upCustomerId = "";
		String agCustomerId = "";
		for (Record record : agentCompanyList) {
			int comType = Integer.valueOf(record.getString("comType"));
			if (Util.eq(UPCOMPANY, comType)) {
				upCustomerId += record.getString("id") + ",";
			}
			if (Util.eq(AGENT, comType)) {
				agCustomerId += record.getString("id") + ",";
			}
		}

		//上游公司
		if (upCustomerId.length() > 1) {
			upCustomerId = upCustomerId.substring(0, upCustomerId.length() - 1);
			Sql upSql = Sqls.create(sqlManager.get("customer_upOption_list"));
			Cnd upCnd = Cnd.NEW();
			upCnd.and("deletestatus", "=", 0);
			upCnd.and("c.id", "in", upCustomerId);
			List<Record> upRecord = dbDao.query(upSql, upCnd, null);
			for (Record record : upRecord) {
				records.add(record);
			}
		}

		//代理商
		if (agCustomerId.length() > 1) {
			agCustomerId = agCustomerId.substring(0, agCustomerId.length() - 1);
			Sql agSql = Sqls.create(sqlManager.get("customer_agOption_list"));
			Cnd agCnd = Cnd.NEW();
			agCnd.and("deletestatus", "=", 0);
			agCnd.and("c.id", "in", agCustomerId);
			List<Record> agRecord = dbDao.query(agSql, agCnd, null);
			for (Record record : agRecord) {
				records.add(record);
			}
		}

		List<Select2Option2> result = transform2SelectOptions(records);
		return result;
	}

	private List<Select2Option2> transform2SelectOptions(List<Record> companyList) {
		return Lists.transform(companyList, new Function<Record, Select2Option2>() {

			@Override
			public Select2Option2 apply(Record record) {
				Select2Option2 op = new Select2Option2();

				op.setId(record.getInt("id"));
				op.setText(record.getString("comName"));
				op.setComType(record.getInt("comtype"));
				return op;
			}
		});
	}

	//批量删除
	public Object batchDelete(long[] ids) {
		FormUtil.delete(dbDao, TCustomerInfoEntity.class, ids);
		return JsonResult.success("删除成功");
	}

	//附件上传 返回值文件存储地址
	@POST
	@AdaptBy(type = UploadAdaptor.class, args = { "ioc:imgUpload" })
	@Ok("json")
	public Object upload(File file, HttpSession session) {
		try {
			String ext = Files.getSuffix(file);
			FileInputStream fileInputStream = new FileInputStream(file);
			String url = qiniuUploadService.uploadImage(fileInputStream, ext, null);
			//文件存储地址
			System.out.println(url);
			return url;
			//业务
		} catch (Exception e) {
			return MobileResult.error("操作失败", null);
		}
	}

	/**
	 * 添加信息
	 * @param  Form
	 * @return 
	 */
	public Object addCustomInfo(HttpSession session, TCustomerInfoAddForm addForm) {

		if (!Util.isEmpty(addForm.getContractDueTimeString())) {
			//客户信息保存
			addForm.setContractDueTime(DateUtil.string2Date(addForm.getContractDueTimeString(), "yyyy-MM-dd"));
		}
		if (!Util.isEmpty(addForm.getContractTimeString())) {
			addForm.setContractTime(DateUtil.string2Date(addForm.getContractTimeString(), "yyyy-MM-dd"));
		}
		addForm.setCreateTime(DateUtil.nowDate());
		//得到当前用户所在公司的id
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();

		TUpcompanyEntity upcompany = dbDao.fetch(TUpcompanyEntity.class, Cnd.where("comId", "=", companyId));
		TCompanyEntity company = dbDao.fetch(TCompanyEntity.class, Cnd.where("id", "=", companyId));
		if (!Util.isEmpty(upcompany)) {
			addForm.setUpComId(upcompany.getId());
		}

		TCustomerInfoEntity customerInfo = this.add(addForm);

		//出发城市城市截取
		Iterable<String> outcityids = Splitter.on(",").split(addForm.getOutcityname());
		//出发城市保存
		List<TCustomerOutcityEntity> outcityEntities = new ArrayList<TCustomerOutcityEntity>();
		for (String dictInfoId : outcityids) {
			TCustomerOutcityEntity outcityEntity = new TCustomerOutcityEntity();
			outcityEntity.setInfoId(customerInfo.getId());
			//判断是否为空
			if (!Util.isEmpty(dictInfoId) && Long.valueOf(dictInfoId) != 0) {
				outcityEntity.setDictInfoId(Long.valueOf(dictInfoId));
			}
			outcityEntities.add(outcityEntity);
		}
		dbDao.insert(outcityEntities);

		/*//国境内陆截取
		Iterable<String> sLine1s = Splitter.on(",").split(addForm.getSLine1());
		Iterable<String> internationLines = Splitter.on(",").split(addForm.getInternationLine());
		//国境内陆保存
		List<TCustomerLineEntity> lineEntities = new ArrayList<TCustomerLineEntity>();
		for (String dictInfoId : sLine1s) {
			TCustomerLineEntity lineEntity = new TCustomerLineEntity();
			lineEntity.setInfoId(customerInfo.getId());
			if (!Util.isEmpty(dictInfoId)) {
				lineEntity.setDictInfoId(Long.valueOf(dictInfoId));
			}
			lineEntities.add(lineEntity);
		}
		if (!Util.isEmpty(internationLines)) {
			for (String dictInfoId : internationLines) {
				TCustomerLineEntity lineEntity = new TCustomerLineEntity();
				lineEntity.setInfoId(customerInfo.getId());
				if (!Util.isEmpty(dictInfoId)) {
					lineEntity.setDictInfoId(Long.valueOf(dictInfoId));
				}
				lineEntities.add(lineEntity);
			}
		}
		dbDao.insert(lineEntities);*/

		//	dbDao.updateRelations(before, after);

		//发票信息截取
		Iterable<String> sInvNames = Splitter.on(",").split(addForm.getSInvName());
		//发票信息保存
		List<TCustomerInvoiceEntity> invoiceEntities = new ArrayList<TCustomerInvoiceEntity>();
		for (String dictInfoId : sInvNames) {
			TCustomerInvoiceEntity invoiceEntity = new TCustomerInvoiceEntity();
			invoiceEntity.setInfoId(customerInfo.getId());
			if (!Util.isEmpty(dictInfoId)) {
				invoiceEntity.setDictInfoId(Long.valueOf(dictInfoId));
			}
			invoiceEntities.add(invoiceEntity);
		}
		dbDao.insert(invoiceEntities);

		/*******************************操作台添加消息  提醒事件************************************/
		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long userId = loginUser.getId();
		//新添加客户id
		long customerInfoId = customerInfo.getId();

		//查询当前公司下 会计id
		TCompanyEntity companyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Sql accountSql = Sqls.create(sqlManager.get("customer_search_accounter"));
		accountSql.setParam("recPayId", RECPAY_MENU_ID);
		accountSql.setParam("invioceId", INVOICE_MENU_ID);
		accountSql.setParam("companyid", companyEntity.getId());
		List<Record> accountingIds = dbDao.query(accountSql, null, null);

		String msgContent = "今天 需要进行财务结算";
		int msgType = MessageTypeEnum.PROCESSMSG.intKey(); //消息类型
		int msgLevel = MessageLevelEnum.MSGLEVEL2.intKey(); //消息优先级
		int msgStatus = MessageStatusEnum.UNREAD.intKey(); //消息状态
		long reminderMode = MessageRemindEnum.MOUTH.intKey(); //消息提醒模式
		long SourceUserId = userId;//消息来源id
		int sourceUserType = MessageSourceEnum.SYSTEMMSG.intKey(); //消息来源方类型
		//消息接收方类型（个人、公司、系统）
		int receiveUserType = MessageSourceEnum.PERSONALMSG.intKey();
		//消息接收方ids
		ArrayList<Long> receiveUserIds = Lists.newArrayList();
		if (!Util.isEmpty(accountingIds)) {
			for (Record record : accountingIds) {
				if (!Util.isEmpty(record.getString("userId"))) {
					long accountingId = Long.parseLong(record.getString("userId"));
					receiveUserIds.add(accountingId);
				}
			}
		}
		receiveUserIds.add(userId);

		/*添加的消息 存放到map中*/
		Map<String, Object> map = Maps.newHashMap();
		map.put("msgContent", msgContent);
		map.put("msgType", msgType);
		map.put("msgLevel", msgLevel);
		map.put("msgStatus", msgStatus);
		map.put("reminderMode", reminderMode);
		map.put("SourceUserId", SourceUserId);
		map.put("sourceUserType", sourceUserType);
		map.put("receiveUserIds", receiveUserIds);
		map.put("receiveUserIds", receiveUserIds);
		map.put("receiveUserType", receiveUserType);
		map.put("customerInfoId", customerInfoId);

		/****************************查询 消息表中是否有 当前客户的消息*********************/
		Sql sql = Sqls.create(sqlManager.get("customer_search__msg"));
		sql.params().set("userId", userId);
		sql.params().set("msgType", msgType);
		sql.params().set("customerInfoId", customerInfoId);
		sql.setCallback(Sqls.callback.records());
		List<Record> records = dbDao.query(sql, null, null); //查询自定义的结果
		if (Util.isEmpty(records)) {
			remindService.addMessageEvent(map);
		}

		return null;
	}

	/**
	 * 
	 * 跳转到update 
	 * <p>
	 * 更新页面准备数据
	 *
	 * @param id
	 * @return 
	 */
	public Object toUpdatePage(HttpSession session, long id) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		//查询客户信息
		TCustomerInfoEntity tCustomerInfoEntity = dbDao.fetch(TCustomerInfoEntity.class, id);
		Date contractTime = tCustomerInfoEntity.getContractTime();
		Date contractDueTime = tCustomerInfoEntity.getContractDueTime();

		//根据客户信息id， 查询已欠款   TODO
		//INLAND 欠款统计
		Sql arrearsSql = Sqls.create(sqlManager.get("customer_inland_arrearsMoney_byId"));
		arrearsSql.setCallback(Sqls.callback.records());
		arrearsSql.setParam("customerId", id);
		arrearsSql.setParam("orderType", FIT);
		arrearsSql.setParam("rStatus", RECEIVINGMONEY);
		arrearsSql.setParam("oStatus", SEARCH + "," + CLOSE);
		Double arrears = 0.0;
		if (!Util.isEmpty(id)) {
			Record arrearsRecord = dbDao.fetch(arrearsSql);
			String arrearsStr = arrearsRecord.getString("arrears");
			if (!Util.isEmpty(arrearsStr)) {
				arrears = Double.valueOf(arrearsStr);
			}
		}
		//国际欠款统计
		Sql arrearsInterSql = Sqls.create(sqlManager.get("customer_inter_arrearsMoney_byId"));
		arrearsInterSql.setCallback(Sqls.callback.records());
		arrearsInterSql.setParam("customerId", id);
		arrearsInterSql.setParam("orderType", TEAM);
		arrearsInterSql.setParam("rStatus", RECEIVINGMONEY);
		arrearsInterSql.setParam("recordType", RECEIVINGMONEY);
		arrearsInterSql.setParam("oStatus", INTERS + "," + INTERC);
		if (!Util.isEmpty(id)) {
			Record arrearsRecord = dbDao.fetch(arrearsInterSql);
			String arrearsStr = arrearsRecord.getString("arrears");
			if (!Util.isEmpty(arrearsStr)) {
				arrears += Double.valueOf(arrearsStr);
			}
		}

		tCustomerInfoEntity.setArrears(arrears);

		//日期格式转换
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String contractTimeStr = null;
		if (!Util.isEmpty(contractTime)) {
			contractTimeStr = sdf.format(contractTime);
			tCustomerInfoEntity.setContractTimeString(contractTimeStr);
		}
		String contractDueTimeStr = null;
		if (!Util.isEmpty(contractDueTime)) {
			contractDueTimeStr = sdf.format(contractDueTime);
			tCustomerInfoEntity.setContractDueTimeString(contractDueTimeStr);
		}
		obj.put("customer", tCustomerInfoEntity);

		//负责人查询
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();
		Sql sql = Sqls.create(sqlManager.get("customer_agent_list"));
		sql.setParam("comid", companyId);
		sql.setParam("status", USER_VALID);
		List<Record> record = dbDao.query(sql, null, null);
		obj.put("userlist", record);

		//查询公司名称
		long agentId = tCustomerInfoEntity.getAgentId();
		long customerInfoId = tCustomerInfoEntity.getId();
		TCustomerInfoEntity customerInfo = dbDao.fetch(TCustomerInfoEntity.class, customerInfoId);
		long customerType = customerInfo.getCustomerType();

		List<Record> agentCompanyList = new ArrayList<Record>();
		if (Util.eq(UPCOMPANY, customerType)) {
			//上游公司
			Sql upSql = Sqls.create(sqlManager.get("customer_upOption_list"));
			Cnd upCnd = Cnd.NEW();
			upCnd.and("deletestatus", "=", 0);
			upCnd.and("a.id", "in", agentId);
			agentCompanyList = dbDao.query(upSql, upCnd, null);
		}
		if (Util.eq(AGENT, customerType)) {
			//代理商
			Sql agSql = Sqls.create(sqlManager.get("customer_agOption_list"));
			Cnd agCnd = Cnd.NEW();
			agCnd.and("deletestatus", "=", 0);
			agCnd.and("a.id", "in", agentId);
			agentCompanyList = dbDao.query(agSql, agCnd, null);
		}
		//公司名称id 拼串
		String comIds = "";
		String comName = "";
		String comType = "";
		for (Record r : agentCompanyList) {
			comIds = r.getString("id") + "";
			comName = r.getString("name");
			comType = r.getString("comtype");
		}
		obj.put("comIds", comIds);
		obj.put("comType", comType);
		List<Select2Option2> result = transform2SelectOptions(agentCompanyList);
		obj.put("comEntity", result);

		//查询出发城市
		Sql citySql = Sqls.create(sqlManager.get("customer_cityOption_list"));
		Cnd cityCnd = Cnd.NEW();
		cityCnd.and("c.infoId", "=", id);
		cityCnd.orderBy("d.dictCode", "desc");
		citySql.setCondition(cityCnd);
		List<TDepartureCityEntity> outcityEntities = DbSqlUtil.query(dbDao, TDepartureCityEntity.class, citySql);
		//出发城市id 拼串
		String outcityIds = "";
		for (TDepartureCityEntity outcityEntity : outcityEntities) {
			outcityIds += outcityEntity.getId() + ",";
		}
		if (outcityIds.length() > 0) {
			outcityIds = outcityIds.substring(0, outcityIds.length() - 1);
		}
		obj.put("outcityIds", outcityIds);
		obj.put("outcitylist", Lists.transform(outcityEntities, new Function<TDepartureCityEntity, Select2Option>() {
			@Override
			public Select2Option apply(TDepartureCityEntity record) {
				Select2Option op = new Select2Option();
				String text = record.getDictCode() + " - " + record.getEnglishName() + " - " + record.getCountryName();
				op.setId(record.getId());
				op.setText(text);
				return op;
			}
		}));

		//国境内陆
		Sql lineSql = Sqls.create(sqlManager.get("customer_islineOption_list"));
		Cnd lineCnd = Cnd.NEW();
		lineCnd.and("o.infoId", "=", id);
		lineCnd.and("d.typeCode", "=", "GJNL");
		lineCnd.orderBy("d.dictName", "desc");
		lineSql.setCondition(lineCnd);
		List<DictInfoEntity> innerLineEntities = DbSqlUtil.query(dbDao, DictInfoEntity.class, lineSql);
		//国境内陆id 拼串
		String innerLineIds = "";
		for (DictInfoEntity innerLineEntity : innerLineEntities) {
			innerLineIds += innerLineEntity.getId() + ",";
		}
		if (innerLineIds.length() > 0) {
			innerLineIds = innerLineIds.substring(0, innerLineIds.length() - 1);
		}
		obj.put("innerCityIds", innerLineIds);
		obj.put("innerlinelist", Lists.transform(innerLineEntities, new Function<DictInfoEntity, Select2Option>() {
			@Override
			public Select2Option apply(DictInfoEntity record) {
				Select2Option op = new Select2Option();
				op.setId(record.getId());
				op.setText(record.getDictName());
				return op;
			}
		}));

		//国际
		Sql interLineSql = Sqls.create(sqlManager.get("customer_islineOption_list"));
		Cnd interLineCnd = Cnd.NEW();
		interLineCnd.and("o.infoId", "=", id);
		interLineCnd.and("d.typeCode", "=", "GJ");
		interLineCnd.orderBy("d.dictName", "desc");
		interLineSql.setCondition(interLineCnd);
		List<DictInfoEntity> interLineEntities = DbSqlUtil.query(dbDao, DictInfoEntity.class, interLineSql);
		//国际线路id 拼串
		String interLineIds = "";
		for (DictInfoEntity interLineEntity : interLineEntities) {
			interLineIds += interLineEntity.getId() + ",";
		}
		if (interLineIds.length() > 0) {
			interLineIds = interLineIds.substring(0, interLineIds.length() - 1);
		}

		obj.put("interLineIds", interLineIds);
		obj.put("interlinelist", Lists.transform(interLineEntities, new Function<DictInfoEntity, Select2Option>() {
			@Override
			public Select2Option apply(DictInfoEntity record) {
				Select2Option op = new Select2Option();
				op.setId(record.getId());
				op.setText(record.getDictName());
				return op;
			}
		}));

		//发票项
		Sql invioceSql = Sqls.create(sqlManager.get("customer_invioceOption_list"));
		Cnd invioceCnd = Cnd.NEW();
		invioceCnd.and("l.infoId", "=", id);
		invioceCnd.and("d.typeCode", "=", "FPXM");
		invioceCnd.orderBy("d.dictName", "desc");
		invioceSql.setCondition(invioceCnd);
		List<DictInfoEntity> invioceEntities = DbSqlUtil.query(dbDao, DictInfoEntity.class, invioceSql);
		String invioceIds = "";
		for (DictInfoEntity invicoeEntity : invioceEntities) {
			invioceIds += invicoeEntity.getId() + ",";
		}
		if (invioceIds.length() > 0) {
			invioceIds = invioceIds.substring(0, invioceIds.length() - 1);
		}
		obj.put("invioceIds", invioceIds);
		obj.put("invoicelist", Lists.transform(invioceEntities, new Function<DictInfoEntity, Select2Option>() {
			@Override
			public Select2Option apply(DictInfoEntity record) {
				Select2Option op = new Select2Option();
				op.setId(record.getId());
				op.setText(record.getDictName());
				return op;
			}
		}));

		return obj;
	}

	/**
	 * 
	 * 客户信息更新
	 *
	 * @param customerId
	 * @param typeCode
	 * @return 
	 */
	public Object updateCustomInfo(HttpSession session, TCustomerInfoUpdateForm updateForm) {
		if (!Util.isEmpty(updateForm.getContractDueTimeString())) {
			//客户信息保存
			updateForm.setContractDueTime(DateUtil.string2Date(updateForm.getContractDueTimeString(), "yyyy-MM-dd"));
		}
		if (!Util.isEmpty(updateForm.getContractTimeString())) {
			updateForm.setContractTime(DateUtil.string2Date(updateForm.getContractTimeString(), "yyyy-MM-dd"));
		}
		updateForm.setCreateTime(DateUtil.nowDate());
		//得到当前用户所在公司的id
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();
		TUpcompanyEntity upcompany = dbDao.fetch(TUpcompanyEntity.class, Cnd.where("comId", "=", companyId));
		if (!Util.isEmpty(upcompany)) {
			updateForm.setUpComId(upcompany.getId());
		}

		this.update(updateForm);

		//出发城市城市截取
		Iterable<String> outcityids = Splitter.on(",").split(updateForm.getOutcityname());
		//出发城市更新
		List<TCustomerOutcityEntity> outCitysAfter = new ArrayList<TCustomerOutcityEntity>();
		for (String dictInfoId : outcityids) {
			TCustomerOutcityEntity outcityEntity = new TCustomerOutcityEntity();
			outcityEntity.setInfoId(updateForm.getId());
			if (!Util.isEmpty(dictInfoId)) {
				outcityEntity.setDictInfoId(Long.valueOf(dictInfoId));
			}
			outCitysAfter.add(outcityEntity);
		}

		List<TCustomerOutcityEntity> outCitysBefore = dbDao.query(TCustomerOutcityEntity.class,
				Cnd.where("infoId", "=", updateForm.getId()), null);
		dbDao.updateRelations(outCitysBefore, outCitysAfter);

		/*//国境内陆截取
		Iterable<String> sLine1s = Splitter.on(",").split(updateForm.getSLine1());
		//国际截取
		Iterable<String> internationLines = Splitter.on(",").split(updateForm.getInternationLine());

		List<TCustomerLineEntity> linesAfter = new ArrayList<TCustomerLineEntity>();
		for (String dictInfoId : sLine1s) {
			TCustomerLineEntity lineEntity = new TCustomerLineEntity();
			lineEntity.setInfoId(updateForm.getId());
			if (!Util.isEmpty(dictInfoId)) {
				lineEntity.setDictInfoId(Long.valueOf(dictInfoId));
			}
			linesAfter.add(lineEntity);
		}
		for (String dictInfoId : internationLines) {
			TCustomerLineEntity lineEntity = new TCustomerLineEntity();
			lineEntity.setInfoId(updateForm.getId());
			if (!Util.isEmpty(dictInfoId)) {
				lineEntity.setDictInfoId(Long.valueOf(dictInfoId));
			}
			linesAfter.add(lineEntity);
		}
		List<TCustomerLineEntity> linesBefore = dbDao.query(TCustomerLineEntity.class,
				Cnd.where("infoId", "=", updateForm.getId()), null);
		dbDao.updateRelations(linesBefore, linesAfter);*/

		//发票信息截取
		Iterable<String> sInvNames = Splitter.on(",").split(updateForm.getSInvName());
		//发票信息更新
		List<TCustomerInvoiceEntity> invoicesAfter = new ArrayList<TCustomerInvoiceEntity>();
		for (String dictInfoId : sInvNames) {
			TCustomerInvoiceEntity invoiceEntity = new TCustomerInvoiceEntity();
			invoiceEntity.setInfoId(updateForm.getId());
			if (!Util.isEmpty(dictInfoId)) {
				invoiceEntity.setDictInfoId(Long.valueOf(dictInfoId));
			}
			invoicesAfter.add(invoiceEntity);
		}
		List<TCustomerInvoiceEntity> invioceBefore = dbDao.query(TCustomerInvoiceEntity.class,
				Cnd.where("infoId", "=", updateForm.getId()), null);
		dbDao.updateRelations(invioceBefore, invoicesAfter);

		/*******************************操作台添加消息  提醒事件************************************/
		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long userId = loginUser.getId();
		//新添加客户id
		long customerInfoId = updateForm.getId();

		//查询当前公司下 会计id
		TCompanyEntity companyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Sql accountSql = Sqls.create(sqlManager.get("customer_search_accounter"));
		accountSql.setParam("recPayId", RECPAY_MENU_ID);
		accountSql.setParam("invioceId", INVOICE_MENU_ID);
		accountSql.setParam("companyid", companyEntity.getId());
		List<Record> accountingIds = dbDao.query(accountSql, null, null);

		String msgContent = "今天 需要进行财务结算";
		int msgType = MessageTypeEnum.PROCESSMSG.intKey(); //消息类型
		int msgLevel = MessageLevelEnum.MSGLEVEL2.intKey(); //消息优先级
		int msgStatus = MessageStatusEnum.UNREAD.intKey(); //消息状态
		long reminderMode = MessageRemindEnum.MOUTH.intKey(); //消息提醒模式
		long SourceUserId = userId;//消息来源id
		int sourceUserType = MessageSourceEnum.SYSTEMMSG.intKey(); //消息来源方类型
		//消息接收方类型（个人、公司、系统）
		int receiveUserType = MessageSourceEnum.PERSONALMSG.intKey();
		//消息接收方ids
		ArrayList<Long> receiveUserIds = Lists.newArrayList();
		if (!Util.isEmpty(accountingIds)) {
			for (Record record : accountingIds) {
				if (!Util.isEmpty(record.getString("userId"))) {
					long accountingId = Long.parseLong(record.getString("userId"));
					receiveUserIds.add(accountingId);
				}
			}
		}
		receiveUserIds.add(userId);

		/*添加的消息 存放到map中*/
		Map<String, Object> map = Maps.newHashMap();
		map.put("msgContent", msgContent);
		map.put("msgType", msgType);
		map.put("msgLevel", msgLevel);
		map.put("msgStatus", msgStatus);
		map.put("reminderMode", reminderMode);
		map.put("SourceUserId", SourceUserId);
		map.put("sourceUserType", sourceUserType);
		map.put("receiveUserIds", receiveUserIds);
		map.put("receiveUserType", receiveUserType);
		map.put("customerInfoId", customerInfoId);

		/****************************查询 消息表中是否有 当前客户的消息*********************/
		Sql sql = Sqls.create(sqlManager.get("customer_search__msg"));
		sql.params().set("userId", userId);
		sql.params().set("msgType", msgType);
		sql.params().set("customerInfoId", customerInfoId);
		sql.setCallback(Sqls.callback.records());
		List<Record> records = dbDao.query(sql, null, null); //查询自定义的结果
		if (Util.isEmpty(records)) {
			remindService.addMessageEvent(map);
		}

		return null;
	}

	/**
	 * 线路查询
	 * <p>
	 * 根据参数不同， 分别查询国境内陆和国际线路
	 * @param customerId 客户id
	 * @param typeCode   线路类型
	 * @return
	 */
	private List<DictInfoEntity> getLineList(long customerId, String typeCode) {
		Sql sql = Sqls.create(sqlManager.get("customer_line_list"));
		Cnd cnd = Cnd.NEW();
		cnd.and("d.typeCode", "=", typeCode);
		cnd.and("l.infoId", "=", customerId);
		cnd.orderBy("d.dictName", "desc");
		sql.setCondition(cnd);
		List<DictInfoEntity> innerLineList = DbSqlUtil.query(dbDao, DictInfoEntity.class, sql);
		return innerLineList;
	}

	/**
	 * 
	 * TODO出发城市
	 * <p>
	 * @param outcityName
	 * @return
	 * @throws Exception
	 */
	public Object goCity(String outcityCode, String ids) throws Exception {

		Set<TDepartureCityEntity> set = Sets.newTreeSet();

		//出发城市
		Sql sql = Sqls.create(sqlManager.get("customer_city_list"));
		Cnd cnd = Cnd.NEW();
		cnd.and("dictCode", "like", outcityCode);
		sql.setCondition(cnd);
		List<TDepartureCityEntity> localOutCityList = DbSqlUtil.query(dbDao, TDepartureCityEntity.class, sql);

		if (localOutCityList.size() >= 5) {
			for (int i = 0; i < 5; i++) {
				TDepartureCityEntity info = localOutCityList.get(i);
				set.add(info);
			}
		} else {
			set.addAll(localOutCityList);

			//数据字典表中查找出发城市
			List<TDepartureCityEntity> dictLineList = externalInfoService.findCityByCode(outcityCode, "CFCS");
			int needmore = 5 - localOutCityList.size();
			if (!Util.isEmpty(dictLineList)) {
				if (dictLineList.size() <= needmore) {
					for (TDepartureCityEntity dict : dictLineList) {
						set.add(dict);
					}
				} else {
					for (int i = 0; i < needmore; i++) {
						set.add(dictLineList.get(i));
					}
				}
			}
		}

		List<Select2Option> list = new ArrayList<Select2Option>();
		//判断是否为空
		if (!Util.isEmpty(set)) {
			for (TDepartureCityEntity dict : set) {
				Select2Option op = new Select2Option();
				String text = dict.getDictCode() + " - " + dict.getEnglishName() + " - " + dict.getCountryName();
				op.setId(dict.getId());
				op.setText(text);
				list.add(op);
			}
		}
		if (!Util.isEmpty(ids)) {
			//删除已存在的
			Iterable<String> outcityid = Splitter.on(",").split(ids);
			List<Select2Option> removelist = new ArrayList<Select2Option>();
			for (String str : outcityid) {
				for (Select2Option sel : list) {
					if (sel.getId() == Long.valueOf(str)) {
						removelist.add(sel);
					}
				}
			}
			list.removeAll(removelist);
		}
		return list;
	}

	/**
	 * 
	 * TODO线路查询
	 * <p>
	 * TODO线路查询
	 *
	 * @param lineName
	 * @return
	 * @throws Exception
	 */
	public Object isLine(String lineName, String ids) throws Exception {
		Set<DictInfoEntity> set = Sets.newTreeSet();
		Sql sql = Sqls.create(sqlManager.get("customer_line_list"));
		Cnd cnd = Cnd.NEW();
		cnd.and("d.typeCode", "=", "GJNL");
		cnd.and("d.dictName", "like", lineName + "%");
		cnd.orderBy("d.dictName", "desc");
		sql.setCondition(cnd);
		List<DictInfoEntity> localLineList = DbSqlUtil.query(dbDao, DictInfoEntity.class, sql);
		if (localLineList.size() >= 5) {
			for (int i = 0; i < 5; i++) {
				DictInfoEntity info = localLineList.get(i);
				set.add(info);
			}
		} else {
			set.addAll(localLineList);
			//数据字典表中查找出发城市
			List<DictInfoEntity> dictLineList = externalInfoService.findDictInfoByName(lineName, "GJNL");
			int needmore = 5 - localLineList.size();
			if (!Util.isEmpty(dictLineList)) {
				if (dictLineList.size() <= needmore) {
					for (DictInfoEntity dict : dictLineList) {
						set.add(dict);
					}
				} else {
					for (int i = 0; i < needmore; i++) {
						set.add(dictLineList.get(i));
					}
				}
			}
		}

		List<Select2Option> list = new ArrayList<Select2Option>();
		//判断是否为空
		if (!Util.isEmpty(set)) {
			for (DictInfoEntity dict : set) {
				Select2Option op = new Select2Option();
				op.setId(dict.getId());
				op.setText(dict.getDictName());
				list.add(op);
			}
		}
		if (!Util.isEmpty(ids)) {
			//删除已存在的
			Iterable<String> outcityid = Splitter.on(",").split(ids);
			List<Select2Option> removelist = new ArrayList<Select2Option>();
			for (String str : outcityid) {
				for (Select2Option sel : list) {
					if (sel.getId() == Long.valueOf(str)) {
						removelist.add(sel);
					}
				}
			}
			list.removeAll(removelist);
		}
		return list;
	}

	/**
	 * 
	 * TODO查询发票项
	 * <p>
	 * @param invioceName
	 * @return
	 * @throws Exception 
	 */
	public Object isInvioce(String invioceName, String ids) throws Exception {
		Set<DictInfoEntity> set = Sets.newTreeSet();

		Sql sql = Sqls.create(sqlManager.get("customer_invioce_list"));
		Cnd cnd = Cnd.NEW();
		cnd.and("d.typeCode", "=", "FPXM");
		cnd.and("d.dictName", "like", invioceName);
		cnd.orderBy("d.dictName", "desc");
		sql.setCondition(cnd);
		List<DictInfoEntity> localInvioceList = DbSqlUtil.query(dbDao, DictInfoEntity.class, sql);

		if (localInvioceList.size() >= 5) {
			for (int i = 0; i < 5; i++) {
				DictInfoEntity info = localInvioceList.get(i);
				set.add(info);
			}
		} else {
			set.addAll(localInvioceList);

			//数据字典表中查找出发城市
			List<DictInfoEntity> dictLineList = externalInfoService.findDictInfoByName(invioceName, "FPXM");
			int needmore = 5 - localInvioceList.size();
			if (!Util.isEmpty(dictLineList)) {
				if (dictLineList.size() <= needmore) {
					for (DictInfoEntity dict : dictLineList) {
						set.add(dict);
					}
				} else {
					for (int i = 0; i < needmore; i++) {
						set.add(dictLineList.get(i));
					}
				}
			}
		}

		List<Select2Option> list = new ArrayList<Select2Option>();
		//判断是否为空
		if (!Util.isEmpty(set)) {
			for (DictInfoEntity dict : set) {
				Select2Option op = new Select2Option();
				op.setId(dict.getId());
				op.setText(dict.getDictName());
				list.add(op);
			}
		}
		if (!Util.isEmpty(ids)) {
			//删除已存在的
			Iterable<String> outcityid = Splitter.on(",").split(ids);
			List<Select2Option> removelist = new ArrayList<Select2Option>();
			for (String str : outcityid) {
				for (Select2Option sel : list) {
					if (sel.getId() == Long.valueOf(str)) {
						removelist.add(sel);
					}
				}
			}
			list.removeAll(removelist);
		}
		return list;
	}

	/**
	 * 
	 * TODO国际线路
	 * <p>
	 * TODO查询国际线路select2
	 *
	 * @param lineName
	 * @return
	 * @throws Exception 
	 */
	public Object international(String lineName, String ids) throws Exception {
		Set<DictInfoEntity> set = Sets.newTreeSet();

		Sql sql = Sqls.create(sqlManager.get("customer_line_list"));
		Cnd cnd = Cnd.NEW();
		cnd.and("d.dictName", "like", lineName + "%");
		cnd.and("d.typeCode", "=", "GJ");
		cnd.orderBy("d.dictName", "desc");
		sql.setCondition(cnd);
		List<DictInfoEntity> localLineList = DbSqlUtil.query(dbDao, DictInfoEntity.class, sql);

		if (localLineList.size() >= 5) {
			for (int i = 0; i < 5; i++) {
				DictInfoEntity info = localLineList.get(i);
				set.add(info);
			}
		} else {
			set.addAll(localLineList);

			//数据字典表中查找出发城市
			List<DictInfoEntity> dictLineList = externalInfoService.findDictInfoByName(lineName, "GJ");
			int needmore = 5 - localLineList.size();
			if (!Util.isEmpty(dictLineList)) {
				if (dictLineList.size() <= needmore) {
					for (DictInfoEntity dict : dictLineList) {
						set.add(dict);
					}
				} else {
					for (int i = 0; i < needmore; i++) {
						set.add(dictLineList.get(i));
					}
				}
			}
		}

		List<Select2Option> list = new ArrayList<Select2Option>();
		//判断是否为空
		if (!Util.isEmpty(set)) {
			for (DictInfoEntity dict : set) {
				Select2Option op = new Select2Option();
				op.setId(dict.getId());
				op.setText(dict.getDictName());
				list.add(op);
			}
		}

		if (!Util.isEmpty(ids)) {
			//删除已存在的
			Iterable<String> outcityid = Splitter.on(",").split(ids);
			List<Select2Option> removelist = new ArrayList<Select2Option>();
			for (String str : outcityid) {
				for (Select2Option sel : list) {
					if (sel.getId() == Long.valueOf(str)) {
						removelist.add(sel);
					}
				}
			}
			list.removeAll(removelist);
		}

		return list;

	}

	/**
	 * 
	 * 根据客户姓名查询客户信息
	 * <p>
	 *
	 * @param linkname
	 * @return 客户信息列表
	 */
	public List<TCustomerInfoEntity> getCustomerInfoByLinkName(String linkname) {
		List<TCustomerInfoEntity> customerInfos = new ArrayList<TCustomerInfoEntity>();
		try {
			customerInfos = dbDao.query(TCustomerInfoEntity.class,
					Cnd.where("linkMan", "like", Strings.trim(linkname) + "%"), null);
			if (customerInfos.size() > 5) {
				customerInfos = customerInfos.subList(0, 5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerInfos;
	}

	/**
	 * 
	 * 根据客户电话查询客户信息
	 * <p>
	 *
	 * @param linkname
	 * @return 客户信息列表
	 */
	public List<TCustomerInfoEntity> getCustomerInfoByPhone(String phonenum) {
		List<TCustomerInfoEntity> customerInfos = new ArrayList<TCustomerInfoEntity>();
		try {
			customerInfos = dbDao.query(TCustomerInfoEntity.class,
					Cnd.where("telephone", "like", Strings.trim(phonenum) + "%"), null);
			if (customerInfos.size() > 5) {
				customerInfos = customerInfos.subList(0, 5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerInfos;
	}

	/**
	 * 
	 * 根据id查询客户信息
	 * <p>
	 *
	 * @param id
	 * @return 客户信息
	 */
	public Object getCustomerById(Long id) {
		TCustomerInfoEntity customerInfoEntity = dbDao.fetch(TCustomerInfoEntity.class, id);
		long responsibleId = customerInfoEntity.getResponsibleId();
		TUserEntity userEntity = dbDao.fetch(TUserEntity.class, Cnd.where("id", "=", responsibleId));
		String responsibleName = userEntity.getUserName();
		Map<String, Object> obj = getOutCitys(id);
		obj.put("responsibleName", responsibleName);
		obj.put("customerInfoEntity", customerInfoEntity);
		return JsonUtil.toJson(obj);
	}

	/**
	 * 
	 * 根据客户id查询出发城市
	 * <p>
	 * @param id
	 * @return 
	 */
	public Map<String, Object> getOutCitys(Long id) {
		Map<String, Object> obj = new HashMap<String, Object>();
		Sql citySql = Sqls.create(sqlManager.get("customer_cityOption_list"));
		Cnd cityCnd = Cnd.NEW();
		cityCnd.and("c.infoId", "=", id);
		cityCnd.orderBy("d.dictCode", "desc");
		citySql.setCondition(cityCnd);
		List<DictInfoEntity> outcityEntities = DbSqlUtil.query(dbDao, DictInfoEntity.class, citySql);
		//出发城市id 拼串
		String outcityIds = "";
		for (DictInfoEntity outcityEntity : outcityEntities) {
			outcityIds += outcityEntity.getId() + ",";
		}
		if (outcityIds.length() > 0) {
			outcityIds = outcityIds.substring(0, outcityIds.length() - 1);
		}
		obj.put("outcityIds", outcityIds);
		obj.put("outcitylist", Lists.transform(outcityEntities, new Function<DictInfoEntity, Select2Option>() {
			@Override
			public Select2Option apply(DictInfoEntity record) {
				Select2Option op = new Select2Option();
				op.setId(record.getId());
				op.setText(record.getDictName());
				return op;
			}
		}));
		return obj;
	}

	/**
	 * 
	 * (查询当前用户所有的客户)
	 * <p>
	 * @param session
	 * @return 
	 */
	public List<TCustomerInfoEntity> getCustomerList(HttpSession session) {
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();//得到公司关系表comId
		TUpcompanyEntity up = dbDao.fetch(TUpcompanyEntity.class, Cnd.where("comId", "=", companyId));
		List<TCustomerInfoEntity> customerList = dbDao.query(TCustomerInfoEntity.class,
				Cnd.where("upComId", "=", up.getId()), null);
		return customerList;
	}

}