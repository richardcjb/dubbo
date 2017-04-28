package com.linyun.airline.common.sabre.service;

import com.linyun.airline.common.sabre.dto.SabreResponse;
import com.linyun.airline.common.sabre.form.InstaFlightsSearchForm;

/**
 *	Sabre机票接口instaFlights
 *
 * @author   朱晓川
 * @Date	 2016年11月17日 	 
 */
public interface SabreService {

	/***
	 * Sabre接口查询机票
	 */
	public SabreResponse instaFlightsSearch(InstaFlightsSearchForm paramForm);

}
