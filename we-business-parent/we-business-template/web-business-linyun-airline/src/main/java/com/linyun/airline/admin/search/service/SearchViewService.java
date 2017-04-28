package com.linyun.airline.admin.search.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.linyun.airline.admin.customer.service.CustomerViewService;
import com.linyun.airline.admin.customneeds.service.EditPlanService;
import com.linyun.airline.admin.dictionary.departurecity.entity.TDepartureCityEntity;
import com.linyun.airline.admin.dictionary.external.externalInfoService;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.operationsArea.entities.TMessageEntity;
import com.linyun.airline.admin.operationsArea.service.RemindMessageService;
import com.linyun.airline.admin.order.international.enums.InternationalStatusEnum;
import com.linyun.airline.admin.search.entities.ParsingSabreEntity;
import com.linyun.airline.admin.search.form.SearchTicketSqlForm;
import com.linyun.airline.common.enums.AccountReceiveEnum;
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
import com.linyun.airline.common.sabre.dto.FlightSegment;
import com.linyun.airline.common.sabre.dto.InstalFlightAirItinerary;
import com.linyun.airline.common.sabre.dto.SabreExResponse;
import com.linyun.airline.common.sabre.dto.SabreResponse;
import com.linyun.airline.common.sabre.form.InstaFlightsSearchForm;
import com.linyun.airline.common.sabre.service.SabreService;
import com.linyun.airline.common.sabre.service.impl.SabreServiceImpl;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TAirlineInfoEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TCustomerInfoEntity;
import com.linyun.airline.entities.TFlightInfoEntity;
import com.linyun.airline.entities.TOrderCustomneedEntity;
import com.linyun.airline.entities.TUpOrderEntity;
import com.linyun.airline.entities.TUpcompanyEntity;
import com.linyun.airline.entities.TUserEntity;
import com.uxuexi.core.common.util.DateTimeUtil;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.DbSqlUtil;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class SearchViewService extends BaseService<TMessageEntity> {
	private static final Log log = Logs.get();

	private static final int TEAM = OrderTypeEnum.TEAM.intKey(); //国际
	private static final int FIT = OrderTypeEnum.FIT.intKey(); //内陆
	private static final int RECEIVINGMONEY = AccountReceiveEnum.RECEIVINGMONEY.intKey();
	private static final int SEARCH = OrderStatusEnum.SEARCH.intKey();
	private static final int CLOSE = OrderStatusEnum.CLOSE.intKey();
	private static final int INTERS = InternationalStatusEnum.SEARCH.intKey();
	private static final int INTERC = InternationalStatusEnum.CLOSE.intKey();

	private static final int USER_VALID = UserStatusEnum.VALID.intKey();
	private static final int UPCOM_USER = UserTypeEnum.UPCOM.intKey();
	private static final int AGENT_USER = UserTypeEnum.AGENT.intKey();
	private static final int UP_MANAGER = UserTypeEnum.UP_MANAGER.intKey();
	private static final int AGENT_MANAGER = UserTypeEnum.AGENT_MANAGER.intKey();

	@Inject
	private CustomerViewService customerViewService;
	@Inject
	private externalInfoService externalInfoService;
	@Inject
	private EditPlanService editPlanService;
	@Inject
	private RemindMessageService remindMessageService;

	private static final int INLAND = OrderTypeEnum.FIT.intKey();
	private static final int INTER = OrderTypeEnum.TEAM.intKey();
	private static final String INLANDTYPE = "inlandOrderType";
	private static final String INTERTYPE = "interOrderType";
	private static final String CITYCODE = "CFCS";
	private static final String AIRCOMCODE = "HKGS";

	/**
	 * 
	 *获取客户姓名下拉列表
	 *
	 * @param linkname
	 * @return 
	 */
	public Object getLinkNameSelect(String linkname, HttpSession session) {
		//当前登录公司id
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();

		TUpcompanyEntity upcompany = dbDao.fetch(TUpcompanyEntity.class, Cnd.where("comId", "=", companyId));
		long upcompanyRelationId = 0;
		if (!Util.isEmpty(upcompany)) {

			upcompanyRelationId = upcompany.getId();
		}
		//获得用户id
		String userIds = getUserIds(session);
		List<TCustomerInfoEntity> customerInfos = new ArrayList<TCustomerInfoEntity>();
		Cnd cnd = Cnd.NEW();
		cnd.and("linkMan", "like", Strings.trim(linkname) + "%");
		cnd.and("upComId", "=", upcompanyRelationId);
		cnd.and("responsibleId", "in", userIds);
		customerInfos = dbDao.query(TCustomerInfoEntity.class, cnd, null);
		if (customerInfos.size() > 5) {
			customerInfos = customerInfos.subList(0, 5);
		}
		return customerInfos;
	}

	/**
	 * 
	 *获取联系电话下拉列表
	 *
	 * @param linkname
	 * @return 
	 */
	public Object getPhoneNumSelect(String phonenum, HttpSession session) {
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();
		TUpcompanyEntity upcompany = dbDao.fetch(TUpcompanyEntity.class, Cnd.where("comId", "=", companyId));
		long upcompanyRelationId = 0;
		if (!Util.isEmpty(upcompany)) {
			upcompanyRelationId = upcompany.getId();
		}
		//获得用户id
		String userIds = getUserIds(session);
		List<TCustomerInfoEntity> customerInfos = new ArrayList<TCustomerInfoEntity>();
		Cnd cnd = Cnd.NEW();
		cnd.and("telephone", "like", Strings.trim(phonenum) + "%");
		cnd.and("upComId", "=", upcompanyRelationId);
		cnd.and("responsibleId", "in", userIds);
		customerInfos = dbDao.query(TCustomerInfoEntity.class, cnd, null);
		if (customerInfos.size() > 5) {
			customerInfos = customerInfos.subList(0, 5);
		}
		return customerInfos;
	}

	/**
	 * 
	 * 获取id查询客户信息
	 *
	 * @param linkname
	 * @return 
	 */
	public Object getCustomerById(Long id) {
		Map<String, Object> obj = new HashMap<String, Object>();

		TCustomerInfoEntity customerInfoEntity = dbDao.fetch(TCustomerInfoEntity.class, id);
		long responsibleId = customerInfoEntity.getResponsibleId();
		TUserEntity userEntity = dbDao.fetch(TUserEntity.class, Cnd.where("id", "=", responsibleId));
		String responsibleName = userEntity.getUserName();

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

		//统计历史欠款  
		Double arrears = getMoney(id);//历史欠款
		customerInfoEntity.setArrears(arrears);
		obj.put("responsibleName", responsibleName);
		obj.put("customerInfoEntity", customerInfoEntity);
		Double creditLine = customerInfoEntity.getCreditLine();//信用额度
		if (Util.isEmpty(creditLine)) {
			creditLine = 0.0;
		}
		double subNum = creditLine - arrears;
		if (subNum < 10000) {
			obj.put("isArrearsRed", "true");
		}
		return JsonUtil.toJson(obj);
	}

	public Object getCitys(Long id) {
		Map<String, Object> obj = new HashMap<String, Object>();
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
		return obj;
	}

	/**
	 * 获取城市下拉
	 * <p>
	 * 获取城市下拉
	 *
	 * @param cityname
	 * @return 返回城市下拉列表
	 */
	public Object getCitySelect(String cityname, String typeCode, String ids) {
		List<TDepartureCityEntity> citySelect = new ArrayList<TDepartureCityEntity>();
		try {
			citySelect = externalInfoService.findCityByCode(cityname, typeCode);
			//出发抵达城市去重
			if (!Util.isEmpty(ids)) {
				//已选中的城市
				List<TDepartureCityEntity> existCitys = new ArrayList<TDepartureCityEntity>();
				for (TDepartureCityEntity dictInfoEntity : citySelect) {
					if (dictInfoEntity.getDictCode().equals(ids)) {
						existCitys.add(dictInfoEntity);
					}
				}
				citySelect.removeAll(existCitys);
			}
			if (citySelect.size() > 5) {
				citySelect = citySelect.subList(0, 5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return citySelect;
	}

	/**
	 * 获取航空公司下拉
	 * <p>
	 * 获取城市下拉
	 *
	 * @param cityname
	 * @return 返回航空下拉列表
	 */
	public Object getAirLineSelect(String airlinename, String ids) {
		List<DictInfoEntity> airlineSelect = new ArrayList<DictInfoEntity>();
		try {
			airlineSelect = externalInfoService.findDictInfoByText(airlinename, this.AIRCOMCODE);
			if (airlineSelect.size() > 5) {
				airlineSelect = airlineSelect.subList(0, 5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return airlineSelect;
	}

	/**
	 * 查询跨海内陆飞机票
	 */
	public Object searchSingleTickets(InstaFlightsSearchForm searchForm) {
		InstaFlightsSearchForm form = new InstaFlightsSearchForm();
		form.setOrigin(searchForm.getOrigin());
		form.setDestination(searchForm.getDestination());
		form.setDeparturedate(searchForm.getDeparturedate());
		if (!Util.isEmpty(searchForm.getDeparturedate())) {
			//默认设置返回日期为15天
			Date outD = DateTimeUtil.string2Date(searchForm.getDeparturedate(), "yyyy-MM-dd");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String returnD = df.format(new Date(outD.getTime() + 15 * 24 * 60 * 60 * 1000));
			form.setReturndate(returnD);
		}
		//如果返回日期不为空， 则用
		if (!Util.isEmpty(searchForm.getReturndate())) {
			form.setReturndate(searchForm.getReturndate());
		}
		//乘客数量
		String childrenSelect = searchForm.getChildrenSelect();
		String agentSelect = searchForm.getAgentSelect();
		String babySelect = searchForm.getBabySelect();
		int passengercount = Integer.valueOf(childrenSelect) + Integer.valueOf(agentSelect)
				+ Integer.valueOf(babySelect);
		if (passengercount > 0) {
			form.setPassengercount(passengercount);
		}
		//航空公司
		if (!Util.isEmpty(searchForm.getIncludedcarriers())) {
			form.setIncludedcarriers(searchForm.getIncludedcarriers());
		}
		form.setPointofsalecountry("US");
		form.setOffset(1);
		form.setLimit(10);
		SabreResponse resp = new SabreResponse();
		try {
			SabreService service = new SabreServiceImpl();
			resp = service.instaFlightsSearch(form);
			String departureDateTime = "";
			String arrivalDateTime = "";
			if (resp.getStatusCode() == 200) {
				List<InstalFlightAirItinerary> list = (List<InstalFlightAirItinerary>) resp.getData();
				for (InstalFlightAirItinerary instalFlightAirItinerary : list) {
					List<FlightSegment> flightSegment = instalFlightAirItinerary.getList();
					for (FlightSegment flight : flightSegment) {
						departureDateTime = flight.getDepartureDateTime();
						arrivalDateTime = flight.getArrivalDateTime();
						if (!Util.isEmpty(departureDateTime) || "" != departureDateTime) {
							departureDateTime = departureDateTime.substring(11, 13)
									+ departureDateTime.substring(14, 16);
							flight.setDepartureDateTime(departureDateTime);
						}
						if (!Util.isEmpty(arrivalDateTime) || "" != arrivalDateTime) {
							arrivalDateTime = arrivalDateTime.substring(11, 13) + arrivalDateTime.substring(14, 16);
							flight.setArrivalDateTime(arrivalDateTime);
						}
					}
					String airlineCode = instalFlightAirItinerary.getAirlineCode();
				}
			}
			if (resp.getStatusCode() == 400) {
				SabreExResponse sabreExResponse = (SabreExResponse) resp.getData();
				String message = sabreExResponse.getMessage();
				if (message.contains("Parameter 'origin' must be specified")) {
					sabreExResponse.setMessage("出发城市不能为空");
				}
				if (message.contains("Parameter 'destination' must be specified")) {
					sabreExResponse.setMessage("到达城市不能为空");
				}
				if (message.contains("Parameter 'departuredate' must be specified")) {
					sabreExResponse.setMessage("出发日期不能为空");
				}
				if (message.contains("arrivalDateTime")) {
					sabreExResponse.setMessage("返回日期不能为空");
				}
				if (message.contains("No results")) {
					sabreExResponse.setMessage("未查询到结果");
				}
				if (message.contains("Date range in 'departuredate' and 'returndate' exceeds the maximum allowed")) {
					sabreExResponse.setMessage("出发日期和返回日期之差不超过15天");
				}
				if (message.contains("Parameter 'passengercount' must be between 0 and 10")) {
					sabreExResponse.setMessage("乘客数量必须是 0 到 10 之间");
				}
			}
			if (resp.getStatusCode() == 404) {
				SabreExResponse sabreExResponse = (SabreExResponse) resp.getData();
				String message = sabreExResponse.getMessage();
				if (message.contains("No results")) {
					sabreExResponse.setMessage("未查询到结果");
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return resp;
	}

	/**
	 * 查询飞机库
	 */
	public Object listPage4Datatables(SearchTicketSqlForm sqlForm, HttpSession session) {
		//获得当前公司id
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();
		sqlForm.setCompanyId(companyId);
		Map<String, Object> listPageData = this.listPage4Datatables(sqlForm);
		List<Record> list = (List<Record>) listPageData.get("data");
		boolean isOrigin = !Util.isEmpty(sqlForm.getOrigin());
		boolean isDestination = !Util.isEmpty(sqlForm.getDestination());
		boolean isDeparturedate = !Util.isEmpty(sqlForm.getDeparturedate());
		boolean isReturndate = !Util.isEmpty(sqlForm.getReturndate());
		for (Record record : list) {
			Cnd cnd = Cnd.NEW();
			cnd.and("planid", "=", record.get("id"));
			if (isOrigin || isDestination) {
				if (isOrigin) {
					cnd.and("leavecity", "=", sqlForm.getOrigin());
				}
				if (isDestination) {
					cnd.and("arrvicity", "=", sqlForm.getDestination());
				}
			}
			cnd.orderBy("leavedate", "asc");
			List<TAirlineInfoEntity> query = dbDao.query(TAirlineInfoEntity.class, cnd, null);
			record.put("airinfo", query);
		}
		listPageData.remove("data");
		List<Record> list2 = new ArrayList<Record>();
		if (isOrigin || isDestination) {
			for (Record record : list) {
				Object obj = record.get("airinfo");
				if (!Util.isEmpty(obj)) {
					list2.add(record);
				}
			}
			listPageData.put("data", list2);
			listPageData.put("recordsFiltered", list2.size());
		} else {
			listPageData.put("data", list);
		}
		return listPageData;
	}

	/**
	 * 获取城市下拉
	 * <p>
	 * 获取城市下拉
	 *
	 * @param cityname
	 * @return 返回城市下拉列表
	 */
	public Object getCustomerCitySelect(String cityname, String exname) {
		List<TDepartureCityEntity> citySelect = new ArrayList<TDepartureCityEntity>();
		try {
			citySelect = externalInfoService.findCityByCode(cityname, CITYCODE);
			//移除的城市
			TDepartureCityEntity removeinfo = new TDepartureCityEntity();
			for (TDepartureCityEntity dictInfoEntity : citySelect) {
				if (!Util.isEmpty(exname) && dictInfoEntity.getDictCode().equals(exname)) {
					removeinfo = dictInfoEntity;
				}
			}
			citySelect.remove(removeinfo);
			if (citySelect.size() > 5) {
				citySelect = citySelect.subList(0, 5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return citySelect;
	}

	/**
	 * 
	 * 获取航班号下拉框
	 * <p>
	 * 获取航班号下拉框
	 *
	 * @param airlinename
	 * @param exname 
	 * @return 获取航班号下拉框
	 */
	public Object getCAirNumSelect(String airlinename, String exname) {
		//List<DictInfoEntity> airlineSelect = new ArrayList<DictInfoEntity>();
		List<TFlightInfoEntity> airlineSelect = new ArrayList<TFlightInfoEntity>();
		try {
			//airlineSelect = externalInfoService.findDictInfoByName(airlinename, this.AIRLINECODE);
			airlineSelect = dbDao.query(TFlightInfoEntity.class,
					Cnd.where("airlinenum", "like", Strings.trim(airlinename) + "%"), null);
			TFlightInfoEntity exinfo = new TFlightInfoEntity();
			for (TFlightInfoEntity tFlightInfoEntity : airlineSelect) {
				if (!Util.isEmpty(exname) && tFlightInfoEntity.getAirlinenum().equals(exname)) {
					exinfo = tFlightInfoEntity;
				}
			}
			airlineSelect.remove(exinfo);
			if (airlineSelect.size() > 5) {
				airlineSelect = airlineSelect.subList(0, 5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return airlineSelect;
	}

	/**
	 * 
	 * 根据航空公司代码，查询其对应的名称
	 * <p>
	 *
	 * @param airCompCode
	 * @param typeCode
	 * @return 
	 */
	public String getCAirNameByCode(String airCompCode, String typeCode) {
		DictInfoEntity dictInfo = dbDao.fetch(DictInfoEntity.class,
				Cnd.where("dictCode", "=", airCompCode).and("typeCode", "=", typeCode));
		return dictInfo.getDictName();
	}

	/**
	 * 
	 * 解析 sabre
	 * <p>
	 *
	 * @param sabreText
	 * @return 
	 */
	public Object parsingPNR(String sabrePNR) {

		//判断以哪种格式解析
		String parsingType = "";
		sabrePNR += "\n";
		/*String[] sabrePnrs = sabrePNR.split("\\s+");*/
		String[] sabrePnrList = sabrePNR.split("\\s+");
		List<String> sabreList = new ArrayList<String>();
		for (String s : sabrePnrList) {
			if (!Util.isEmpty(s)) {
				sabreList.add(s);
			}
		}
		int sabreSize = sabreList.size();
		String[] sabrePnrs = new String[sabreSize];
		for (int i = 0; i < sabreSize; i++) {
			sabrePnrs[i] = sabreList.get(i);
		}

		String[] sabrePnrsn = sabrePNR.split("\\n+");
		//去除 空行
		List<String> pnrList = new ArrayList<String>();
		for (String str : sabrePnrsn) {
			if (!Util.isEmpty(str)) {
				pnrList.add(str);
			}
		}
		int size = pnrList.size();
		String[] sabrePnrs1 = new String[size];
		for (int i = 0; i < size; i++) {
			sabrePnrs1[i] = pnrList.get(i);
		}
		String sabrePnrsStr = sabrePnrs1[0];
		/*String sabrePnrsStr = sabrePnrs[0];*/
		if (sabrePnrsStr.contains("/D￥") && sabrePnrsStr.contains("<<")) {
			parsingType = "1";
		} else if (sabrePnrsStr.contains("WP<<") || sabrePnrsStr.contains("wp<<")) {
			parsingType = "3";
		} else {
			parsingType = "2";
		}

		ArrayList<Object> arrayList = Lists.newArrayList();
		HashMap<String, Object> map = Maps.newHashMap();

		if (parsingType == "1") {
			//分割sabre组
			//(?s)表示开启跨行匹配，\\d{1}一位数字，[A-Za-z]{2}两位字母，/斜线，\\s空白字符,.+任意字符出现1到多次，?非贪婪模式，最后以\n换行结束
			/*String regex = "(?s)\\d{1}[A-Za-z]{2}/.{2}\\s.+?\\d\n";
			Pattern pattern = Pattern.compile(regex);
			Matcher m = pattern.matcher(sabrePNR);*/
			ArrayList<String> sabreGroup = Lists.newArrayList();
			/*while (m.find()) {
				sabreGroup.add(m.group());
			}
			 */
			for (int i = 0; i < sabrePnrs1.length; i++) {
				if (i % 2 == 0 && i != 0) {
					sabreGroup.add(sabrePnrs1[i].trim() + " " + sabrePnrs1[i + 1].trim());
				}
			}
			for (String pnrs : sabreGroup) {
				/***********************根据 128FEBAKLSYD/D￥VA<< 解析***********************/
				//序号
				int id = 0;
				//航空公司
				String airCompName = "";
				//航班号 
				String flightNum = "";
				//航段
				String airLine = "";
				//起飞日期
				String airLeavelDate = "";
				//起飞时间
				String airDepartureTime = "";
				//降落时间
				String airLandingTime = "";
				//舱位
				String airSeats = "";
				ParsingSabreEntity pSabreEntity = new ParsingSabreEntity();

				String[] pnr = pnrs.split("\\s+");
				String regex = "[a-zA-Z]{6}";
				Pattern pattern = Pattern.compile(regex);
				Matcher m = pattern.matcher(pnrs);
				String pStr = "";
				while (m.find()) {
					pStr = m.group();
				}
				int pIndex = 0;
				for (int i = 0; i < pnr.length; i++) {
					boolean contains = pnr[i].contains(pStr);
					if (contains) {
						pIndex = i;
					}
				}

				id = Integer.parseInt(pnr[0].substring(0, 1));
				airCompName = pnr[0].substring(1);
				flightNum = pnr[1];
				airLeavelDate = sabrePnrs[1];
				String containStr = pnr[pIndex];
				int a = 0;
				if (containStr.contains("*")) {
					String[] seatLine = containStr.split("[*]");
					for (String seat : pnr) {
						a++;
						if (!seat.contains("/E") && seat.length() == 2) {

							airSeats += (" " + seat);
							airSeats += (" " + seatLine[0]);
						}
					}
					airLine = seatLine[1];
					airDepartureTime = pnr[pIndex + 1];
					airLandingTime = pnr[pIndex + 2];
				} else {
					for (String seat : pnr) {
						if (!seat.contains("/E") && seat.length() == 2) {
							airSeats += (" " + seat);
						}
					}
					airLine = pnr[pIndex];
					airDepartureTime = pnr[pIndex + 1];
					airLandingTime = pnr[pIndex + 2];
				}
				pSabreEntity.setId(id);
				pSabreEntity.setAirlineComName(airCompName);
				pSabreEntity.setFlightNum(flightNum);
				pSabreEntity.setAirLeavelDate(airLeavelDate);
				pSabreEntity.setAirLine(airLine);
				pSabreEntity.setAirSeats(airSeats);
				pSabreEntity.setAirDepartureTime(airDepartureTime);
				pSabreEntity.setAirLandingTime(airLandingTime);

				arrayList.add(pSabreEntity);
				map.put("parsingType", "D￥");
				map.put("arrayList", arrayList);
			}
		}

		//根据07v4解析
		if (parsingType == "2") {
			ParsingSabreEntity pSabreEntity = new ParsingSabreEntity();
			int id = Integer.parseInt(sabrePnrs[1]);
			String airCompName = sabrePnrs[2];
			String flightNum = sabrePnrs[3].split("[a-zA-Z]")[0];
			int seatL = sabrePnrs[3].length();
			String airSeat = sabrePnrs[3].substring(seatL - 1, seatL);
			String airLeavelDate = sabrePnrs[4] + "(" + sabrePnrs[5] + ")";
			String airLine = sabrePnrs[6];
			String airDepartureTime = sabrePnrs[8];
			String airLandingTime = sabrePnrs[9];
			String airSeatNum = sabrePnrs[7];
			String sevenLine = sabrePnrs1[5];
			String str[] = sevenLine.split("\\s+");
			String str1 = str[str.length - 1].trim();
			/*String str2 = str1.substring(0, str1.lastIndexOf(".") + 3);*/
			String str2 = str1;
			pSabreEntity.setAirSeatsPrice(str2);
			pSabreEntity.setId(id); //序号
			pSabreEntity.setAirlineComName(airCompName); //航空公司
			pSabreEntity.setFlightNum(flightNum); //航班号
			pSabreEntity.setAirSeats(airSeat); //舱位
			pSabreEntity.setAirLeavelDate(airLeavelDate); //起飞日期
			pSabreEntity.setAirLine(airLine); //航段
			pSabreEntity.setAirSeatNum(airSeatNum); //座位数
			pSabreEntity.setAirDepartureTime(airDepartureTime); //起飞时间
			pSabreEntity.setAirLandingTime(airLandingTime); //降落时间

			arrayList.add(pSabreEntity);
			map.put("parsingType", "00v0");
			map.put("arrayList", arrayList);
		}

		return map;
	}

	/**
	 * 
	 *解析etem
	 * <p>
	 *
	 * @param etemStr
	 * @return 
	 */
	public Object parsingEtem(String etemStr) {
		//判断以哪种格式解析
		String parsingType = "";
		etemStr = etemStr + "\n";
		String[] etemPnrss = etemStr.split("\\s+");
		//去除空行
		List<String> etemList = new ArrayList<String>();
		for (String s : etemPnrss) {
			if (!Util.isEmpty(s)) {
				etemList.add(s);
			}
		}
		int etemSize = etemList.size();
		String[] etemPnrs = new String[etemSize];
		for (int i = 0; i < etemSize; i++) {
			etemPnrs[i] = etemList.get(i);
		}

		String etem = etemPnrs[0];
		if (etem.contains("AVH/") || etem.contains("avh/")) {
			parsingType = "1";
		} else if (etem.contains("SD") || etem.contains("sd")) {
			parsingType = "2";
		} else if (etem.contains("QTE:/") || etem.contains("FSI")) {
			parsingType = "3";
		}

		ArrayList<Object> arrayList = Lists.newArrayList();
		HashMap<String, Object> map = Maps.newHashMap();

		/***********************黑屏查询：AVH/AKLSYD/28FEB/EK**************************/
		if (parsingType == "1") {
			//(?s)表示开启跨行匹配，\\d{1}一位数字，[A-Za-z]{2}两位字母，/斜线，\\s空白字符,.+任意字符出现1到多次，?非贪婪模式，最后以\n换行结束
			/*String regex = "(?s)((\\d{1}.{2}[*])|(\\s{4}))[A-Za-z]{2}\\d+\\s.+?\\d\n";*/
			String regex = "((\\d{1}.{2}.)|())[A-Za-z]{2}\\d+.+\\s+.+?[:]\\d{2}";
			Pattern pattern = Pattern.compile(regex);
			Matcher m = pattern.matcher(etemStr);
			ArrayList<String> etemGroup = Lists.newArrayList();
			while (m.find()) {
				etemGroup.add(m.group());
			}

			String[] etemStrss = etemStr.split("\\s+");
			List<String> etemLists = new ArrayList<String>();
			for (String s : etemStrss) {
				if (!Util.isEmpty(s)) {
					etemLists.add(s);
				}
			}
			int etemSizes = etemLists.size();
			String[] etemStrs = new String[etemSizes];
			for (int i = 0; i < etemSize; i++) {
				etemStrs[i] = etemLists.get(i);
			}

			//航空公司
			String airCompName = etemStrs[4];
			//起飞日期
			String airLeavelDate = etemStrs[1];
			int a = 1;
			for (String pnrs : etemGroup) {
				/***********************根据 avh/aklsyd/28feb/ek\n 解析***********************/
				//序号
				int id = 0;

				//航班号 
				String flightNum = "";
				//航段
				String airLine = "";
				//起飞时间
				String airDepartureTime = "";
				//降落时间
				String airLandingTime = "";
				//舱位
				String airSeats = "";
				ParsingSabreEntity pSabreEntity = new ParsingSabreEntity();

				String[] pnr = pnrs.split("\\s+");
				/*id = Integer.parseInt(pnr[0].substring(0, 1));*/
				id = a;
				if (pnr[0].contains(a + "")) {
					airDepartureTime = pnr[14];
					airLandingTime = pnr[15];
					a++;
				} else {
					airDepartureTime = pnr[13];
					airLandingTime = pnr[14];
					id = a - 1;
				}
				pSabreEntity.setId(id);
				flightNum = pnr[1];
				airLine = pnr[13];

				for (String seat : pnr) {
					if (!seat.contains("-") && !seat.contains("+") && seat.length() == 2) {
						airSeats += (" " + seat);
					}
				}

				pSabreEntity.setAirlineComName(airCompName);
				pSabreEntity.setFlightNum(flightNum);
				pSabreEntity.setAirLeavelDate(airLeavelDate);
				pSabreEntity.setAirLine(airLine);
				pSabreEntity.setAirSeats(airSeats);
				pSabreEntity.setAirDepartureTime(airDepartureTime);
				pSabreEntity.setAirLandingTime(airLandingTime);

				arrayList.add(pSabreEntity);
				map.put("parsingType", "avh/");
				map.put("arrayList", arrayList);
			}
		}
		/************************输入SD5Q9来预订********************************/
		if (parsingType == "2") {
			int indexOf = 0;
			for (int i = 0; i < etemPnrs.length; i++) {
				if (etemPnrs[i].contains("QTE:/") || etemPnrs[i].contains("FSI/")) {
					indexOf = i;
				}

			}

			ParsingSabreEntity pEtemEntity = new ParsingSabreEntity();
			int id = Integer.parseInt(etemPnrs[1].substring(0, 1));
			String flightNum = etemPnrs[2];
			String airSeats = etemPnrs[3];
			String presetDate = etemPnrs[4];
			String airLine = etemPnrs[5];
			String airSeatNum = etemPnrs[6];
			String airDepartureTime = etemPnrs[7];
			String airLandingTime = etemPnrs[8];
			String airSeatsPrice = etemPnrs[indexOf + 45];
			String airSeatsCurrency = etemPnrs[indexOf + 44];

			pEtemEntity.setId(id);
			pEtemEntity.setFlightNum(flightNum);
			pEtemEntity.setAirSeats(airSeats);
			pEtemEntity.setPresetDate(presetDate);
			pEtemEntity.setAirLine(airLine);
			pEtemEntity.setAirSeatNum(airSeatNum);
			pEtemEntity.setAirDepartureTime(airDepartureTime);
			pEtemEntity.setAirLandingTime(airLandingTime);
			pEtemEntity.setAirSeatsPrice(airSeatsPrice);
			pEtemEntity.setAirSeatsCurrency(airSeatsCurrency);

			arrayList.add(pEtemEntity);
			map.put("parsingType", "SD0Q0");
			map.put("arrayList", arrayList);
		}
		/*********************输入QTE:/EK来查询价格*****************************/
		if (parsingType == "3") {
			ParsingSabreEntity pEtemEntity = new ParsingSabreEntity();
			String airSeatsPrice = etemPnrs[46];
			String airSeatsCurrency = etemPnrs[45];
			pEtemEntity.setAirSeatsPrice(airSeatsPrice);
			pEtemEntity.setAirSeatsCurrency(airSeatsCurrency);
			arrayList.add(pEtemEntity);
			map.put("parsingType", "QTE:/");
			map.put("arrayList", arrayList);
		}

		return map;
	}

	/**
	 * 添加查询结果
	 * <p>
	 *
	 * @param data  JSON数据
	 * @return 
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public Object saveCustomerNeeds(String data, HttpSession session) {

		Map<String, Object> fromJson = JsonUtil.fromJson(data, Map.class);
		List<Map<String, Object>> customdata = (List<Map<String, Object>>) fromJson.get("customdata");

		//获取当前登录用户
		TUserEntity user = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		//获取当前公司
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);

		/**************************添加 订单查询信息*****************************/
		Integer customerId = Integer.valueOf((String) fromJson.get("customerId"));
		boolean generateOrder = (boolean) fromJson.get("generateOrder");

		//订单类型
		String orderTypeStr = (String) fromJson.get("orderType");
		Integer orderType = 0;
		if (Util.eq(INLANDTYPE, orderTypeStr)) {
			orderType = INLAND;
		}
		if (Util.eq(INTERTYPE, orderTypeStr)) {
			orderType = INTER;
		}
		//订单状态
		Integer orderStatus = Integer.valueOf((String) fromJson.get("orderStatus"));
		TUpOrderEntity orderinfo = new TUpOrderEntity();
		orderinfo.setUserid(customerId);
		//订单号
		String generateOrderNum = "";
		//生成订单号
		if (generateOrder) {
			generateOrderNum = editPlanService.generateOrderNum();
			orderinfo.setOrdersnum(generateOrderNum);
		}
		//订单类型
		orderinfo.setOrderstype(orderType);
		//用户id
		orderinfo.setLoginUserId(new Long(user.getId()).intValue());
		//公司id
		orderinfo.setCompanyId(new Long(company.getId()).intValue());

		//订单状态
		switch (orderTypeStr) {
		case INLANDTYPE:
			//跨海内陆订单状态
			orderinfo.setOrdersstatus(orderStatus);
			break;
		case INTERTYPE:
			//国际订单状态
			orderinfo.setInterOrderStatus(orderStatus);
			break;
		default:
			orderinfo.setOrdersstatus(orderStatus);
			break;
		}
		if (!Util.isEmpty(fromJson.get("cRemark"))) {
			String remark = (String) fromJson.get("cRemark");
			orderinfo.setRemark(remark);
		}
		TUpOrderEntity insertOrder = dbDao.insert(orderinfo);
		int upOrderId = insertOrder.getId();

		/***************************操作台 消息提醒************************/
		/*addRemindMsg(fromJson, generateOrderNum, "", upOrderId, orderStatus, session);*/

		/****************************客户需求数据*************************/
		addCustomerNeed(customdata, insertOrder);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", upOrderId);
		if (Util.eq("1", orderStatus)) {
			map.put("orderStatus", orderStatus);
		} else {
			map.put("orderStatus", orderStatus);
		}

		return map;

	}

	/**
	 * 
	 * ****************************添加客户需求数据*******************************
	 *
	 * @param customdata 客户需求数据
	 * @param insertOrder 订单实体
	 * @return 
	 */
	public String addCustomerNeed(List<Map<String, Object>> customdata, TUpOrderEntity insertOrder) {
		for (Map<String, Object> map : customdata) {
			//客户需求出发城市
			String leavecity = (String) map.get("leavecity");
			//客户需求抵达城市
			String arrivecity = (String) map.get("arrivecity");
			//日期
			String leavedate = (String) map.get("leavedate");
			String pCount = (String) map.get("peoplecount");
			//人数
			Integer peoplecount = 0;
			if (!Util.eq(pCount, "")) {
				peoplecount = Integer.valueOf(pCount);
			}
			Integer tickettype = Integer.valueOf((String) map.get("tickettype"));
			//备注
			String cRemark = (String) map.get("cRemark");

			TOrderCustomneedEntity customneedEntity = new TOrderCustomneedEntity();
			customneedEntity.setLeavecity(leavecity);
			customneedEntity.setArrivecity(arrivecity);
			if (!Util.eq(leavedate, "")) {
				customneedEntity.setLeavetdate(DateUtil.string2Date(leavedate, DateUtil.FORMAT_YYYY_MM_DD));
			}
			customneedEntity.setPeoplecount(peoplecount);
			customneedEntity.setTickettype(tickettype);
			customneedEntity.setRemark(cRemark);
			//与订单相关
			Integer upOrderId = insertOrder.getId();
			customneedEntity.setOrdernum(upOrderId);
			TOrderCustomneedEntity insertCus = dbDao.insert(customneedEntity);
			//航班信息
			List<Map<String, Object>> airinfo = (List<Map<String, Object>>) map.get("airinfo");
			for (Map<String, Object> airmap : airinfo) {
				TAirlineInfoEntity airlineEntity = new TAirlineInfoEntity();
				//航空公司
				String aircom = (String) airmap.get("aircom");
				//航班号
				String ailinenum = (String) airmap.get("ailinenum");
				//出发时间
				String leavetime = (String) airmap.get("leavetime");
				//抵达时间
				String arrivetime = (String) airmap.get("arrivetime");
				String fPrice = (String) airmap.get("formprice");
				if (!Util.isEmpty(fPrice)) {
					//成本价
					Double formprice = Double.valueOf(fPrice);
					airlineEntity.setFormprice(formprice);
				}
				String priceStr = (String) airmap.get("price");
				if (!Util.isEmpty(priceStr)) {
					//销售价
					Double price = Double.valueOf(priceStr);
					airlineEntity.setPrice(price);
				}
				if (!Util.isEmpty(aircom)) {
					airlineEntity.setAircom(aircom.split("-")[0]);
				}
				if (!Util.isEmpty(ailinenum)) {
					airlineEntity.setAilinenum(ailinenum);
				}
				if (!Util.isEmpty(leavetime)) {
					airlineEntity.setLeavetime(leavetime);
				}
				if (!Util.isEmpty(arrivetime)) {
					airlineEntity.setArrivetime(arrivetime);
				}

				airlineEntity.setNeedid(insertCus.getId());
				//添加航班信息
				dbDao.insert(airlineEntity);
			}
		}
		return "客户需求添加成功";
	}

	/**
	 * 
	 * ******************************************添加消息提醒*************************************************
	 * <p>
	 *
	 * @param data Json数据
	 * @param generateOrderNum  订单号
	 * @param pnr  pnr号
	 * @param orderStatus  订单状态(使用消息提醒的枚举)
	 * @param session
	 * @return 
	 */
	public String addRemindMsg(Map<String, Object> fromJson, String generateOrderNum, String pnr, int upOrderId,
			int orderStatus, List<Long> receiveUids, HttpSession session) {
		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long userId = loginUser.getId();
		//查询当前公司下 会计id
		/*TCompanyEntity companyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Sql accountSql = Sqls.create(sqlManager.get("customer_search_accounter"));
		Cnd cnd = Cnd.NEW();
		cnd.and("j.`name`", "like", "%会计%");
		cnd.and("cj.comId", "=", companyEntity.getId());
		List<Record> accountingIds = dbDao.query(accountSql, cnd, null);*/

		//消息接收方ids
		List<Long> receiveUserIds = Lists.newArrayList();
		if (!Util.isEmpty(receiveUids)) {
			for (Long uid : receiveUids) {
				receiveUserIds.add(uid);
			}
		} else {
			receiveUserIds.add(userId);
		}

		//消息来源id
		long SourceUserId = userId;
		//消息来源方类型
		int sourceUserType = MessageSourceEnum.SYSTEMMSG.intKey();
		//消息接收方类型（个人、公司、系统）
		int receiveUserType = MessageSourceEnum.PERSONALMSG.intKey();
		//消息状态
		int msgStatus = MessageStatusEnum.UNREAD.intKey();

		//提醒日期 
		String remindDateStr = (String) fromJson.get("remindDate");
		//客户信息id
		/*String customerInfoId = (String) fromJson.get("customerInfoId");*/
		String customerInfoId = null;
		//消息提醒日期
		Date remindDateTime = DateUtil.nowDate();
		if (!Util.isEmpty(remindDateStr)) {
			remindDateTime = DateUtil.string2Date(remindDateStr);
		}

		//消息提醒方式
		String remindStr = fromJson.get("remindType").toString();
		if (Util.isEmpty(remindStr)) {
			remindStr = "6";
		}
		Integer remindType = Integer.valueOf(remindStr);
		switch (remindType) {
		case 0:
			//每15Min  消息表：5
			remindType = MessageRemindEnum.FIFTEENM.intKey();
			break;
		case 1:
			//每30Min  消息表：7
			remindType = MessageRemindEnum.THIRTYM.intKey();
			break;
		case 2:
			//每1H 消息表：4
			remindType = MessageRemindEnum.HOUR.intKey();
			break;
		case 3:
			//每一天 消息表：3
			remindType = MessageRemindEnum.DAY.intKey();
			break;
		case 4:
			//每一周 消息表：2
			remindType = MessageRemindEnum.WEEK.intKey();
			break;
		case 5:
			//每一月 消息表：1
			remindType = MessageRemindEnum.MOUTH.intKey();
			break;
		case 6:
			//不重复（只提醒一次） 消息表：8
			remindType = MessageRemindEnum.UNREPEAT.intKey();
			break;
		default:
			//自定义 消息表：6
			remindType = MessageRemindEnum.TIMED.intKey();
			break;
		}
		long reminderMode = remindType;

		//消息类型和订单状态 orderStatus有关
		int msgType = MessageTypeEnum.NOTICEMSG.intKey(); //消息类型---默认为"系统通知消息"
		int msgLevel = MessageLevelEnum.MSGLEVEL1.intKey(); //消息优先级---默认为"优先级一"
		String msgContent = ""; //消息内容
		switch (orderStatus) {
		case 1:
			//TODO  设置参数， 如果参数一致，则更新；  不一致，则添加
			//查询 4
			msgType = MessageTypeEnum.SEARCHMSG.intKey();
			//消息等级2
			msgLevel = MessageLevelEnum.MSGLEVEL2.intKey();
			//消息内容
			msgContent = "查询单号：" + generateOrderNum;
			break;
		case 2:
			//预订 5
			msgType = MessageTypeEnum.BOOKMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL2.intKey();
			msgContent = "预订单号：" + generateOrderNum;
			break;
		case 3:
			//开票 （操作人员）开飞机票  6
			msgType = MessageTypeEnum.DRAWBILLMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL3.intKey();
			msgContent = "单号：" + generateOrderNum + " PNR：" + pnr + "开发票中";
			/*receiveUserIds.removeAll(receiveUserIds);
			receiveUserIds.add(userId);*/
			break;
		case 4:
			//出票 (操作人员)出飞机票 7
			msgType = MessageTypeEnum.MAKEOUTBILLMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL3.intKey();
			msgContent = "单号：" + generateOrderNum + " PNR：" + pnr + "发票已开";
			break;
		case 5:
			//关闭 (操作人员)关闭订单  0
			msgType = MessageTypeEnum.CLOSEMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL1.intKey();
			msgContent = "单号：" + generateOrderNum + " PNR：" + pnr + "已关闭";
			break;
		case 6:
			//一订 8
			msgType = MessageTypeEnum.FIRBOOKMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL4.intKey();
			msgContent = generateOrderNum + "订单一订需处理";
			break;
		case 7:
			//二订 9
			msgType = MessageTypeEnum.SECBOOKMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL4.intKey();
			msgContent = generateOrderNum + "订单二订需处理";
			break;
		case 8:
			//三订 10
			msgType = MessageTypeEnum.THRBOOKMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL4.intKey();
			msgContent = generateOrderNum + "订单三订需处理";
			break;
		case 9:
			//全款 11
			msgType = MessageTypeEnum.ALLBOOKMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL4.intKey();
			msgContent = generateOrderNum + "订单全款需处理";
			break;
		case 10:
			//尾款 12
			msgType = MessageTypeEnum.LASTBOOKMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL4.intKey();
			msgContent = generateOrderNum + "订单尾款需结清";
			break;
		case 11:
			//已收款 14
			msgType = MessageTypeEnum.RECEIVEDMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL5.intKey();
			msgContent = "单号：" + generateOrderNum + "收款已确认";
			break;
		case 12:
			//已付款 15
			msgType = MessageTypeEnum.PAYEDMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL5.intKey();
			msgContent = "单号：" + generateOrderNum + " PNR：" + pnr + "付款已确认";
			break;
		case 13:
			//收款  已开发票 16
			msgType = MessageTypeEnum.INVIOCEMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL5.intKey();
			msgContent = "单号：" + generateOrderNum + "已开发票";
			break;
		case 14:
			//付款  已收发票 17
			msgType = MessageTypeEnum.RECINVIOCEMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL5.intKey();
			msgContent = "单号：" + generateOrderNum + " PNR：" + pnr + "已收发票";
			break;
		case 15:
			//付款 已审批 18
			msgType = MessageTypeEnum.RECINVIOCEMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL5.intKey();
			msgContent = "单号：" + generateOrderNum + " PNR：" + pnr + "审批已通过";
			break;
		case 16:
			//付款 已拒绝 19
			msgType = MessageTypeEnum.RECINVIOCEMSG.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL5.intKey();
			msgContent = "单号：" + generateOrderNum + " PNR：" + pnr + "审批已拒绝";
			break;
		case 17: //MessageWealthStatusEnum
			//付款 收款已提交 20
			msgType = MessageTypeEnum.RECSUBMITED.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL5.intKey();
			msgContent = "单号：" + generateOrderNum + "收款已提交";
			break;
		case 18: //MessageWealthStatusEnum
			//付款 需付款已提交申请 21  MessageTypeEnum
			msgType = MessageTypeEnum.PSAPPROVALING.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL5.intKey();
			msgContent = "单号：" + generateOrderNum + " PNR：" + pnr + "需付款/已提交申请";
			break;
		case 19: //MessageWealthStatusEnum
			//付款 （会计）开发票中 22  MessageTypeEnum
			msgType = MessageTypeEnum.INVIOCING.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL5.intKey();
			msgContent = "单号：" + generateOrderNum + "开发票中";
			break;
		case 20: //MessageWealthStatusEnum
			//付款 （会计）收发票中 23  MessageTypeEnum
			msgType = MessageTypeEnum.RECINVIOCING.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL5.intKey();
			msgContent = "单号：" + generateOrderNum + " PNR：" + pnr + "收发票中";
			break;
		case 21: //MessageWealthStatusEnum
			//减免
			String derateMoney = (String) fromJson.get("derateMoney");
			msgType = MessageTypeEnum.DERATEMONEY.intKey();
			msgLevel = MessageLevelEnum.MSGLEVEL5.intKey();
			msgContent = "单号：" + generateOrderNum + " 减免金额" + derateMoney + "元，已审批通过";
			break;
		}

		/*添加的消息 存放到map中*/
		Map<String, Object> mapMsg = Maps.newHashMap();
		mapMsg.put("msgContent", msgContent);
		mapMsg.put("msgType", msgType);
		mapMsg.put("msgLevel", msgLevel);
		mapMsg.put("msgStatus", msgStatus);
		mapMsg.put("reminderMode", reminderMode);
		mapMsg.put("SourceUserId", SourceUserId);
		mapMsg.put("sourceUserType", sourceUserType);
		mapMsg.put("receiveUserIds", receiveUserIds);
		mapMsg.put("receiveUserType", receiveUserType);
		mapMsg.put("customerInfoId", customerInfoId);
		mapMsg.put("remindMsgDate", remindDateTime);
		mapMsg.put("upOrderId", upOrderId);
		mapMsg.put("upOrderStatus", orderStatus);
		remindMessageService.addMessageEvent(mapMsg);
		return "消息添加成功";
	}

	/**
	 * 
	 * 统计客户已欠款
	 * <p>
	 *
	 * @param id  客户信息id
	 */
	public Double getMoney(long id) {
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
		return arrears;
	}

	/**
	 * 
	 * 管理员和用户 权限分配
	 * <p>
	 *
	 * @param session
	 * @return 用户id
	 */
	public String getUserIds(HttpSession session) {
		String userIds = "";
		//当前登录公司id
		TCompanyEntity tCompanyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		long companyId = tCompanyEntity.getId();
		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		int userType = loginUser.getUserType(); //用户类型
		long userId = loginUser.getId();
		//管理员
		if (Util.eq(UP_MANAGER, userType) || Util.eq(AGENT_MANAGER, userType)) {
			Sql sql = Sqls.create(sqlManager.get("customer_agent_list"));
			sql.setParam("comid", companyId);
			sql.setParam("status", USER_VALID);
			/*sql.setParam("userid", userId);*/
			List<Record> record = dbDao.query(sql, null, null);
			for (Record r : record) {
				String uId = r.getString("id");
				userIds += uId + ",";
			}
		}

		//普通用户
		if (Util.eq(UPCOM_USER, userType) || Util.eq(AGENT_USER, userType)) {
			userIds = userId + ",";
		}
		userIds = userIds.substring(0, userIds.length() - 1);

		return userIds;
	}

}
