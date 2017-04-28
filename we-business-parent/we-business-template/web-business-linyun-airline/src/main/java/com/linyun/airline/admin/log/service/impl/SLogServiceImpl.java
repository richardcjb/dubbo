package com.linyun.airline.admin.log.service.impl;

import org.nutz.ioc.loader.annotation.IocBean;

import com.linyun.airline.admin.authority.function.entity.TFunctionEntity;
import com.linyun.airline.admin.log.form.SLogAddForm;
import com.linyun.airline.admin.log.service.SLogService;
import com.linyun.airline.entities.SLogEntity;
import com.linyun.airline.entities.TUserEntity;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.util.FormUtil;

@IocBean(name = "sLogService")
public class SLogServiceImpl extends BaseService<SLogEntity> implements SLogService {

	@Override
	public boolean addSyslog(SLogAddForm addForm) {
		FormUtil.add(dbDao, addForm, SLogEntity.class);
		return true;
	}

	@Override
	public boolean addSyslog(TFunctionEntity function, TUserEntity user, String ip) {
		SLogAddForm addForm = new SLogAddForm();
		addForm.setFunctionId(function.getId());
		addForm.setFunctionName(function.getName());
		addForm.setPath(function.getUrl());

		addForm.setIp(ip);

		addForm.setOperatorId(user.getId());
		addForm.setOperatorName(user.getUserName());
		return addSyslog(addForm);
	}

}
