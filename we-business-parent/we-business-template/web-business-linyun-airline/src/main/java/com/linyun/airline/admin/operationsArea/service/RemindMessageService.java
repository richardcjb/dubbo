package com.linyun.airline.admin.operationsArea.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.linyun.airline.admin.operationsArea.entities.TMessageEntity;
import com.linyun.airline.admin.operationsArea.form.TMessageAddForm;
import com.linyun.airline.common.enums.MessageIsRemindEnum;
import com.linyun.airline.common.enums.MessageStatusEnum;
import com.linyun.airline.entities.TUserMsgEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.DbSqlUtil;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class RemindMessageService extends BaseService<TMessageEntity> {
	private static final Log log = Logs.get();

	private static final int CUSTOMER_MSG = 2;
	private static final int CUSTOM_MSG = 3;

	/**
	 * 
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param msgData
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object addMessageEvent(Map<String, Object> msgData) {

		//消息内容
		String msgContent = (String) msgData.get("msgContent");
		//消息类型
		Integer msgType = (Integer) msgData.get("msgType");
		//消息等级
		Integer msgLevel = (Integer) msgData.get("msgLevel");
		//消息状态
		Integer msgStatus = (Integer) msgData.get("msgStatus");
		//提醒模式
		Long reminderMode = (Long) msgData.get("reminderMode");
		//消息来源id
		Long SourceUserId = (Long) msgData.get("SourceUserId");
		//消息提醒日期
		Date remindMsgDate = (Date) msgData.get("remindMsgDate");
		//消息来源类型（个人、公司、系统）
		Long sourceUserType = Long.valueOf((Integer) msgData.get("sourceUserType"));
		//消息接收方id 
		List<Long> receiveUserIds = (List<Long>) msgData.get("receiveUserIds");
		//消息接收方类型（个人、公司、系统）
		Long receiveUserType = Long.valueOf((Integer) msgData.get("receiveUserType"));
		//客户消息表id  查询id
		Long custId = (Long) msgData.get("customerInfoId");
		Long customerInfoId = custId;
		//相关订单id
		Long upOrderId = 0L;
		if (!Util.isEmpty(msgData.get("upOrderId"))) {
			upOrderId = ((Integer) msgData.get("upOrderId")).longValue();
		}
		int upOrderStatus = 0;
		if (!Util.isEmpty(msgData.get("upOrderStatus"))) {
			upOrderStatus = (int) (msgData.get("upOrderStatus"));
		}

		//判断消息是否存在， 不存在则添加
		if (!Util.isEmpty(customerInfoId)) {
			Sql sql = Sqls.create(sqlManager.get("operationsArea_existMsg"));
			sql.params().set("msgType", msgType);
			sql.params().set("infoId", customerInfoId);
			List<Record> query = DbSqlUtil.query(dbDao, sql, null, null);
			if (!Util.isEmpty(query)) {
				return "消息已存在，请勿重复添加！！！";
			}
		}

		/*******************************操作  消息表**************************************/
		TMessageAddForm addForm = new TMessageAddForm();
		//消息内容
		addForm.setMsgContent(msgContent);
		//消息类型（查询、一订、二订、出票等）
		addForm.setMsgType(msgType);
		//消息优先级  MSGLEVEL1.intKey()表示等级最低 ， 由小到大
		addForm.setPriorityLevel(msgLevel);
		//消息   默认为删除(用 0 表示)
		addForm.setMsgStatus(msgStatus);
		//消息生成日期
		if (Util.isEmpty(remindMsgDate)) {
			Date nowDate = DateUtil.nowDate();
			if (!Util.isEmpty(nowDate)) {
				addForm.setGenerateTime(nowDate);
			}
		} else {
			addForm.setGenerateTime(remindMsgDate);
		}

		//订单id
		if (upOrderId != 0L) {
			addForm.setUpOrderId(upOrderId);
		}
		//订单类型
		addForm.setUpOrderStatus(upOrderStatus);
		//消息提醒模式
		addForm.setReminderMode(reminderMode);
		//消息是否提醒  （默认为为提醒）
		addForm.setIsRemind(Long.valueOf(MessageIsRemindEnum.YES.intKey()));
		List<TUserMsgEntity> userMsgEntityList = new ArrayList<TUserMsgEntity>();
		//添加消息数据  不存在则添加， 存在则更新
		TMessageEntity msgEntity = dbDao.fetch(
				TMessageEntity.class,
				Cnd.where("msgContent", "=", msgContent).and("msgType", "!=", CUSTOM_MSG)
						.and("msgType", "!=", CUSTOMER_MSG));
		if (!Util.isEmpty(msgEntity)) {
			//消息已存在， 更新消息
			msgEntity.setGenerateTime(remindMsgDate);
			msgEntity.setReminderMode(reminderMode);
			dbDao.update(msgEntity);
		} else {
			//不存在，则添加
			TMessageEntity tMessageEntity = this.add(addForm);
			/*******************************操作   用户-消息  关系表**************************************/
			List<TUserMsgEntity> userMsgList = new ArrayList<>();
			for (long receiveUserId : receiveUserIds) {
				TUserMsgEntity tUserMsgEntity = new TUserMsgEntity();
				//用户ID(接收方id)
				tUserMsgEntity.setUserId(receiveUserId);
				//消息ID
				tUserMsgEntity.setMsgId(Long.valueOf(tMessageEntity.getId()));
				//接收方用户类型（个人、公司、系统）
				tUserMsgEntity.setUserType(receiveUserType);
				//来源方类型  （个人、公司、系统）
				tUserMsgEntity.setMsgSource(sourceUserType);
				//来源方ID  
				tUserMsgEntity.setFromId(SourceUserId);
				//是否阅读  默认为未读状态
				tUserMsgEntity.setIsRead(Long.valueOf(MessageStatusEnum.UNREAD.intKey()));
				//自定义事件的时间
				tUserMsgEntity.setSendTime(addForm.getGenerateTime());
				//客户消息表id
				if (!Util.isEmpty(customerInfoId)) {
					tUserMsgEntity.setCustomerInfoId(customerInfoId);
				}

				//添加数组中
				userMsgList.add(tUserMsgEntity);
			}
			userMsgEntityList = dbDao.insert(userMsgList);
		}

		return userMsgEntityList;
	}
}
