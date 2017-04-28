/**
 * SabreServiceImpl.java
 * com.linyun.airline.common.sabre.service.impl
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.common.sabre.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.collect.Lists;
import com.jayway.jsonpath.JsonPath;
import com.linyun.airline.common.result.HttpResult;
import com.linyun.airline.common.sabre.SabreConfig;
import com.linyun.airline.common.sabre.SabreTokenFactory;
import com.linyun.airline.common.sabre.bean.SabreAccessToken;
import com.linyun.airline.common.sabre.dto.FlightPriceInfo;
import com.linyun.airline.common.sabre.dto.FlightSegment;
import com.linyun.airline.common.sabre.dto.InstalFlightAirItinerary;
import com.linyun.airline.common.sabre.dto.SabreExResponse;
import com.linyun.airline.common.sabre.dto.SabreResponse;
import com.linyun.airline.common.sabre.form.InstaFlightsSearchForm;
import com.linyun.airline.common.sabre.service.SabreService;
import com.linyun.airline.common.util.HttpClientUtil;
import com.linyun.airline.common.util.JsonPathGeneric;
import com.uxuexi.core.common.util.Util;

/**
 * @author   朱晓川
 * @Date	 2016年11月17日 	 
 */
@IocBean(name = "sabreService")
public class SabreServiceImpl implements SabreService {

	//打log用
	static Log log = Logs.getLog(SabreServiceImpl.class);

	@Override
	public SabreResponse instaFlightsSearch(InstaFlightsSearchForm paramForm) {
		SabreResponse resp = new SabreResponse();

		String searchUrl = SabreConfig.environment + SabreConfig.INSTAL_FLIGHTS_URl
				+ HttpClientUtil.getParams(paramForm);

		HttpGet httpget = new HttpGet(searchUrl);

		SabreAccessToken accessToken = SabreTokenFactory.getAccessToken();
		String token = accessToken.getAccess_token();
		httpget.addHeader("Authorization", "Bearer " + token);
		log.info("executing request " + httpget.getRequestLine());

		HttpResult hr = HttpClientUtil.httpsGet(httpget);
		String result = hr.getResult();
		log.info(result);

		int statusCode = hr.getStatusCode();
		if (HttpClientUtil.SUCCESS_CODE != statusCode) {
			SabreExResponse exResp = new JsonPathGeneric().getGenericObject(result, "$", SabreExResponse.class);
			resp.setStatusCode(statusCode);
			resp.setData(exResp);
			return resp;
		}

		List<Map<String, Object>> pricedItineraries = JsonPath.read(result, "$.PricedItineraries[*]");

		List<InstalFlightAirItinerary> list = Lists.newArrayList();
		if (!Util.isEmpty(pricedItineraries)) {
			for (Map<String, Object> map : pricedItineraries) {
				//不换行，不忽略空值
				String json = Json.toJson(map, JsonFormat.tidy());
				InstalFlightAirItinerary ir = new InstalFlightAirItinerary();

				//航空公司代码(两字)
				String airlineCode = JsonPath.read(json, "$.TPA_Extensions.ValidatingCarrier.Code");
				int sequenceNumber = JsonPath.read(json, "$.SequenceNumber");
				String ticketType = JsonPath.read(json, "$.TicketingInfo.TicketType");

				//TODO 航空公司名称、图片

				//价格信息 
				//TODO 类型转换异常 java.lang.String cannot be cast to java.lang.Double
				readPriceInfo(json, ir);

				ir.setAirlineCode(airlineCode);
				ir.setSequenceNumber(sequenceNumber);
				ir.setTicketType(ticketType);

				log.info("sequenceNumber:" + sequenceNumber);
				log.info("airlineCode:" + airlineCode);
				log.info("ticketType:" + ticketType);
				log.info("======================================");

				readSegmentsInfo(json, ir);

				list.add(ir);
			}
		}

		resp.setStatusCode(statusCode);
		resp.setData(list);
		return resp;
	}

	private void readPriceInfo(String json, InstalFlightAirItinerary ir) {
		String currencyCode = JsonPath.read(json, "$.AirItineraryPricingInfo.ItinTotalFare.TotalFare.CurrencyCode");
		double totalAmount = readDouble(json, "$.AirItineraryPricingInfo.ItinTotalFare.TotalFare.Amount");
		double baseAmount = readDouble(json, "$.AirItineraryPricingInfo.ItinTotalFare.BaseFare.Amount");
		double equivFareAmount = readDouble(json, "$.AirItineraryPricingInfo.ItinTotalFare.EquivFare.Amount");

		log.debug("currencyCode:" + currencyCode);
		log.debug("totalAmount:" + totalAmount);
		log.debug("baseAmount:" + baseAmount);
		log.debug("equivFareAmount:" + equivFareAmount);

		FlightPriceInfo fi = new FlightPriceInfo();
		fi.setCurrencyCode(currencyCode);
		fi.setTotalAmount(totalAmount);
		fi.setBaseAmount(baseAmount);
		fi.setEquivFareAmount(equivFareAmount);
		ir.setPriceInfo(fi);
	}

	/***
	 * 读取航段信息
	 */
	private void readSegmentsInfo(String json, InstalFlightAirItinerary ir) {

		List<Map<String, Object>> odopts = JsonPath.read(json,
				"$.AirItinerary.OriginDestinationOptions.OriginDestinationOption[*]");
		if (!Util.isEmpty(odopts)) {
			List<FlightSegment> segLst = Lists.newArrayList();
			for (Map<String, Object> each : odopts) {
				String optJ = Json.toJson(each, JsonFormat.tidy());

				List<Map<String, Object>> segments = JsonPath.read(optJ, "$.FlightSegment[*]");
				if (!Util.isEmpty(segments)) {

					for (Map<String, Object> eachSeg : segments) {
						String segJ = Json.toJson(eachSeg, JsonFormat.tidy());

						FlightSegment seg = new FlightSegment();

						//经停次数
						int StopQuantity = JsonPath.read(segJ, "$.StopQuantity");
						//出发机场
						String DepartureAirport = JsonPath.read(segJ, "$.DepartureAirport.LocationCode");
						//到达机场
						String ArrivalAirport = JsonPath.read(segJ, "$.ArrivalAirport.LocationCode");
						//航段合并数
						String MarriageGrp = JsonPath.read(segJ, "$.MarriageGrp");
						//出发时间
						String DepartureDateTime = JsonPath.read(segJ, "$.DepartureDateTime");
						//抵达时间
						String ArrivalDateTime = JsonPath.read(segJ, "$.ArrivalDateTime");

						//航班号
						int FlightNumber = JsonPath.read(segJ, "$.FlightNumber");
						//耗时
						int ElapsedTime = JsonPath.read(segJ, "$.ElapsedTime");
						//准点率(%)
						//						int OnTimePerformance = JsonPath.read(segJ, "$.OnTimePerformance.Percentage");
						/**实际执行的航空公司代码*/
						String opAirlineCode = JsonPath.read(segJ, "$.OperatingAirline.Code");
						/**实际乘坐的航班号*/
						int opFlightNumber = JsonPath.read(segJ, "$.OperatingAirline.FlightNumber");
						/**出发时区*/
						int DepartureTimeZone = JsonPath.read(segJ, "$.DepartureTimeZone.GMTOffset");
						/**到达时区*/
						int ArrivalTimeZone = JsonPath.read(segJ, "$.ArrivalTimeZone.GMTOffset");

						String ResBookDesigCode = JsonPath.read(segJ, "$.ResBookDesigCode");
						//						String Equipment = JsonPath.read(segJ, "$.Equipment.AirEquipType");

						seg.setStopQuantity(StopQuantity);
						seg.setArrivalAirport(ArrivalAirport);
						seg.setDepartureAirport(DepartureAirport);
						seg.setMarriageGrp(MarriageGrp);
						seg.setDepartureDateTime(DepartureDateTime);
						seg.setArrivalDateTime(ArrivalDateTime);

						seg.setFlightNumber(FlightNumber);
						seg.setElapsedTime(ElapsedTime);
						//						seg.setOnTimePerformance(OnTimePerformance);
						seg.setOpAirlineCode(opAirlineCode);
						seg.setOpFlightNumber(opFlightNumber);
						seg.setDepartureTimeZone(DepartureTimeZone);
						seg.setArrivalTimeZone(ArrivalTimeZone);
						//扩展
						seg.setResBookDesigCode(ResBookDesigCode);
						//						seg.setEquipment(Equipment);

						log.info(seg);

						segLst.add(seg);
					}
					ir.setList(segLst);
				}//end of odopts for loop
			}
		}
	}

	private static double readDouble(String json, String key) {
		Object obj = JsonPath.read(json, key);
		double totalAmount = 0d;
		if (obj instanceof String) {
			totalAmount = Double.valueOf((String) obj);
		} else {
			totalAmount = Double.valueOf((Double) obj);
		}
		return totalAmount;
	}
}
