package com.linyun.airline.admin.log.service;

import com.linyun.airline.admin.authority.function.entity.TFunctionEntity;
import com.linyun.airline.admin.log.form.SLogAddForm;
import com.linyun.airline.entities.TUserEntity;

public interface SLogService {

	/**添加系统日志*/
	public boolean addSyslog(SLogAddForm addForm);

	/**
	 * 添加系统日志
	 */
	public boolean addSyslog(TFunctionEntity function, TUserEntity user, String ip);

}
