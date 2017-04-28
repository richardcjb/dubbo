/**
 * a.java
 * com.xiaoka.test.jiexi
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.xiaoka.test.jiexi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.linyun.airline.admin.search.entities.ParsingSabreEntity;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   朱晓川
 * @Date	 2017年4月1日 	 
 */
public class a {
	public static Object parsingPNR(String sabrePNR) {

		//判断以哪种格式解析
		String parsingType = "";
		String[] sabrePnrs = sabrePNR.split("\\s+");
		String sabrePnrsStr = sabrePnrs[0];
		if (sabrePnrsStr.contains("/D￥") && sabrePnrsStr.contains("<<")) {
			parsingType = "1";
		} else if (sabrePnrsStr.contains("WP<<")) {
			parsingType = "3";
		} else {
			parsingType = "2";
		}

		ArrayList<Object> arrayList = Lists.newArrayList();
		HashMap<String, Object> map = Maps.newHashMap();

		if (parsingType == "1") {
			//分割sabre组
			//(?s)表示开启跨行匹配，\\d{1}一位数字，[A-Za-z]{2}两位字母，/斜线，\\s空白字符,.+任意字符出现1到多次，?非贪婪模式，最后以\n换行结束
			String regex = "(?s)\\d{1}[A-Za-z]{2}/.{2}\\s.+?\\d\n";
			Pattern pattern = Pattern.compile(regex);
			Matcher m = pattern.matcher(sabrePNR);
			ArrayList<String> sabreGroup = Lists.newArrayList();
			while (m.find()) {
				sabreGroup.add(m.group());
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

				id = Integer.parseInt(pnr[0].substring(0, 1));
				airCompName = pnr[0].substring(1);
				flightNum = pnr[1];
				airLeavelDate = sabrePnrs[1];
				String containStr = pnr[7];
				if (containStr.contains("*")) {
					String[] seatLine = containStr.split("[*]");
					for (String seat : pnr) {
						if (!seat.contains("/E") && seat.length() == 2) {
							airSeats += (" " + seat);
						}
					}
					airLine = seatLine[1];
					airSeats += (" " + seatLine[0]);
					airDepartureTime = pnr[8];
					airLandingTime = pnr[9];
				} else {
					for (String seat : pnr) {
						if (!seat.contains("/E") && seat.length() == 2) {
							airSeats += (" " + seat);
						}
					}
					airLine = pnr[8];
					airDepartureTime = pnr[9];
					airLandingTime = pnr[10];
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

		return null;
	}

	public static void main(String[] args) {
		String sabrePNR = "";
		a.parsingPNR(sabrePNR);
	}
}
