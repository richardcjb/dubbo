/**
 * MailUtils.java
 * com.linyun.airline.admin.drawback.grabfile.timer
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.drawback.grabfile.timer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.linyun.airline.admin.drawback.grabfile.commonmail.GrabMailConstants;
import com.linyun.airline.admin.drawback.grabfile.entity.TGrabFileEntity;
import com.linyun.airline.admin.drawback.grabfile.enums.FileNavalEnum;
import com.linyun.airline.admin.drawback.grabfile.enums.FileTypeEnum;
import com.linyun.airline.admin.drawback.grabfile.enums.GrabTypeEnum;
import com.linyun.airline.admin.drawback.grabmail.entity.TGrabMailEntity;
import com.linyun.airline.common.base.UploadService;
import com.linyun.airline.common.constants.CommonConstants;
import com.linyun.airline.common.enums.DataStatusEnum;
import com.uxuexi.core.common.util.BeanUtil;
import com.uxuexi.core.common.util.DateTimeUtil;
import com.uxuexi.core.common.util.FileUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.DbSqlUtil;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * 邮件抓取
 * @author   崔建斌
 * @Date	 2017年2月8日 	 
 */
@SuppressWarnings("rawtypes")
@IocBean(name = "grabMailService")
public class MailScrabService extends BaseService {

	@Inject
	private UploadService qiniuUploadService;

	private Logger logger = LoggerFactory.getLogger(MailScrabService.class);

	public MailScrabService() {

	}

	/** 
	 * 接收邮件 
	 */
	public void receivePop3(String user, String passwd) throws Exception {
		String pop3Server = "pop3.sina.com";
		String pop3Port = "110";
		//对于user和password，qq邮箱的password填写授权key
		/*String user = "liuxuli232@sina.cn";
		String passwd = "6832156775";*/
		/*String user = "in2020072@sina.com";
		String passwd = "tlywy2017jan";*/
		/*String user = "lftravel@sina.com";
		String passwd = "mar2016lywy";*/

		String userTeam = "";
		if ("in2020072@sina.com".equals(user)) {
			userTeam = "team";
		} else if ("lftravel@sina.com".equals(user)) {
			userTeam = "fit";
		}

		// 准备连接服务器的会话信息  
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "pop3"); // 协议  
		/* 新浪:imap.sina.com
		 * 腾讯:imap.qq.com
		 */
		props.setProperty("mail.pop3.host", pop3Server); // imap服务器
		props.setProperty("mail.pop3.port", pop3Port); // 端口  

		// 创建Session实例对象  
		Session session = Session.getInstance(props);
		Store store = session.getStore("pop3");
		store.connect(user, passwd);

		// 获得收件箱  
		Folder folder = store.getFolder("INBOX");
		/* Folder.READ_ONLY：只读权限 
		 * Folder.READ_WRITE：可读可写（可以修改邮件的状态） 
		 */
		folder.open(Folder.READ_WRITE); //打开收件箱  

		// 由于POP3协议无法获知邮件的状态,所以getUnreadMessageCount得到的是收件箱的邮件总数  
		System.out.println("未读邮件数: " + folder.getUnreadMessageCount());

		// 由于POP3协议无法获知邮件的状态,所以下面得到的结果始终都是为0  
		System.out.println("删除邮件数: " + folder.getDeletedMessageCount());
		System.out.println("新邮件: " + folder.getNewMessageCount());

		// 获得收件箱中的邮件总数  
		System.out.println("邮件总数: " + folder.getMessageCount());

		// 得到收件箱中的所有邮件,并解析  
		Message[] messages = folder.getMessages();
		parseMessage(userTeam, messages);

		//释放资源  
		folder.close(true);
		store.close();
	}

	public void loginMailAccount() {
		String userTeam = "in2020072@sina.com";
		String passwdTeam = "tlywy2017jan";
		String userFit = "lftravel@sina.com";
		String passwdFit = "mar2016lywy";
		try {
			receivePop3(userTeam, passwdTeam);
			receivePop3(userFit, passwdFit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 
	 * 解析邮件 
	 * @param messages 要解析的邮件列表 
	 * @throws ParseException 
	 */
	public void parseMessage(String userTeam, Message... messages) throws MessagingException, IOException {
		if (messages == null || messages.length < 1) {
			throw new MessagingException("未找到要解析的邮件!");
		}
		// 解析所有邮件  
		for (int i = 0, count = messages.length; i < count; i++) {
			MimeMessage msg = (MimeMessage) messages[i];
			eachHandler(msg, userTeam);
			boolean isRead = isRead(msg);
			if (isRead) {
				continue;
			}
		}
	}

	/**
	 * 解析每一封邮件
	 * @throws MessagingException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ParseException 
	 */
	private void eachHandler(MimeMessage msg, String userTeam) throws MessagingException, FileNotFoundException,
			IOException {
		//1、从邮件获取能直接保存的数据
		String theme = getSubject(msg);//主题
		String sender = getFrom(msg);//发件人
		String addressee = getReceiveAddress(msg, null);//收件人
		String sendTime = getSentDate(msg, null);//发送时间
		String fileSize = Integer.toString(msg.getSize());//邮件大小
		logger.info("邮件的主题是:" + theme);
		logger.info("发件人:" + sender);
		logger.info("收件人:" + addressee);
		logger.info("发送时间:" + sendTime);
		logger.info("邮件大小:" + fileSize + "k");
		//2、查询数据库判断是否已存在某条数据，如果存在直接返回，不存在则解析
		Sql sql = Sqls.create(sqlManager.get("grab_mail_file_level"));
		sql.params().set("theme", theme);
		sql.params().set("sender", sender);
		sql.params().set("addressee", addressee);
		sql.params().set("sendTime", sendTime);
		int count = DbSqlUtil.fetchInt(dbDao, sql);
		long grabRecordId = 0;
		if (count > 0) {
			return;
		} else {
			//3-1、保存邮件抓取记录
			TGrabMailEntity mailEntity = new TGrabMailEntity();
			mailEntity.setTheme(theme);
			mailEntity.setSender(sender);
			mailEntity.setAddressee(addressee);
			mailEntity.setSendTime(sendTime);
			mailEntity.setGrabTime(new Date());//抓取时间
			mailEntity.setGrabStatus(GrabTypeEnum.ALREADYGRAB.intKey());//已抓
			mailEntity = dbDao.insert(mailEntity);
			grabRecordId = mailEntity.getId();
		}
		//3-2、保存抓取文件
		FileNavalEnum structureType = structureType(sender);
		//TGrabFileEntity rootFile = dbDao.fetch(TGrabFileEntity.class, Cnd.where("mailId", "=", grabRecordId));
		long rootId = 0;
		switch (structureType) {
		case FITD7JQTT:
			String flag = "";
			if ("team".equals(userTeam)) {
				flag = "团";
			} else {
				flag = "散";
			}
			/**************************时间开始***************************/
			Date createTime = DateTimeUtil.nowDate();
			SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
			String fileName = format.format(createTime);
			rootId = getTopParentId(sender);
			//父id
			TGrabFileEntity timeFile = new TGrabFileEntity();
			timeFile.setParentId(rootId);

			//创建时间
			timeFile.setCreateTime(createTime);

			//名称
			timeFile.setFileName(fileName);
			//类型
			timeFile.setType(FileTypeEnum.FOLDER.intKey());//文件类型

			//抓取记录id
			timeFile.setMailId(grabRecordId);
			//启用
			timeFile.setStatus(DataStatusEnum.ENABLE.intKey());
			timeFile.setLevel(2);
			timeFile.setSort(0);
			timeFile.setGroupType(flag);//散团类型
			List<TGrabFileEntity> list = existFile(rootId, fileName);
			if (!Util.isEmpty(list)) {
				timeFile = list.get(0);
			} else {
				timeFile = dbDao.insert(timeFile);
			}
			/**************************时间结束***************************/

			/**************************客户团号***************************/
			long sort = getSort(timeFile.getId());

			String cusgroupnum = getcusGroupnum();//得到客户团号
			if (Util.isEmpty(cusgroupnum)) {
				sort += 1;
				cusgroupnum = "客户团号" + sort;
			}

			TGrabFileEntity groupNumFile = new TGrabFileEntity();

			//父id
			long pid = timeFile.getId();
			groupNumFile.setParentId(pid);

			groupNumFile.setCreateTime(createTime);

			//名称
			groupNumFile.setFileName(cusgroupnum);
			//类型
			groupNumFile.setType(FileTypeEnum.FOLDER.intKey());

			//抓取记录id
			groupNumFile.setMailId(grabRecordId);
			//启用
			groupNumFile.setStatus(DataStatusEnum.ENABLE.intKey());
			groupNumFile.setLevel(3);

			groupNumFile.setSort(sort);
			groupNumFile.setGroupType(flag);//散团类型

			List<TGrabFileEntity> lst = existGroupNumFile(pid, cusgroupnum);
			if (!Util.isEmpty(lst)) {
				groupNumFile = lst.get(0);
			} else {
				groupNumFile = dbDao.insert(groupNumFile);
			}

			/**********************客户团号结束**********************/

			/**********************************文件开始****************************************/
			//父id
			TGrabFileEntity fileProps = new TGrabFileEntity();
			fileProps.setParentId(groupNumFile.getId());

			//创建时间
			fileProps.setCreateTime(createTime);

			//类型
			fileProps.setType(FileTypeEnum.FOLDER.intKey());

			fileProps.setFileSize(fileSize);
			//抓取记录id
			fileProps.setMailId(grabRecordId);
			//启用
			fileProps.setStatus(DataStatusEnum.ENABLE.intKey());
			fileProps.setLevel(4);

			//散团类型
			fileProps.setGroupType(flag);
			//文件url
			List<TGrabFileEntity> grabFileList = Lists.newArrayList();
			saveAttachment(msg, grabFileList, fileProps);
			if (!Util.isEmpty(grabFileList)) {
				dbDao.insert(grabFileList);
			} else {
				logger.info("没有抓取到附件!");
			}
			break;
		case FITQF:
			//TODO
			/**************************客户团号***************************/
			rootId = getTopParentId(sender);
			long sort1 = getSort(rootId);

			String cusgroupnum1 = getcusGroupnum();//得到客户团号
			if (Util.isEmpty(cusgroupnum1)) {
				sort1 += 1;
				cusgroupnum1 = "客户团号" + sort1;
			}

			TGrabFileEntity groupNumFile1 = new TGrabFileEntity();

			//父id
			groupNumFile1.setParentId(rootId);

			//创建时间
			Date createTime1 = DateTimeUtil.nowDate();//当前时间
			groupNumFile1.setCreateTime(createTime1);

			//名称
			groupNumFile1.setFileName(cusgroupnum1);
			//类型
			groupNumFile1.setType(FileTypeEnum.FOLDER.intKey());//文件类型

			//抓取记录id
			groupNumFile1.setMailId(grabRecordId);
			//启用
			groupNumFile1.setStatus(DataStatusEnum.ENABLE.intKey());//删除状态
			groupNumFile1.setLevel(2);//层级

			groupNumFile1.setSort(sort1);
			groupNumFile1.setGroupType("散");//散团类型

			List<TGrabFileEntity> lst1 = existGroupNumFile(rootId, cusgroupnum1);
			if (!Util.isEmpty(lst1)) {
				groupNumFile1 = lst1.get(0);
			} else {
				groupNumFile1 = dbDao.insert(groupNumFile1);
			}

			/**********************************客户团号结束*************************************/
			/**********************************文件开始****************************************/
			//父id
			TGrabFileEntity fileProps1 = new TGrabFileEntity();
			fileProps1.setParentId(groupNumFile1.getId());

			//创建时间
			fileProps1.setCreateTime(createTime1);

			//类型
			fileProps1.setType(FileTypeEnum.FOLDER.intKey());

			fileProps1.setFileSize(fileSize);
			//抓取记录id
			fileProps1.setMailId(grabRecordId);
			//启用
			fileProps1.setStatus(DataStatusEnum.ENABLE.intKey());
			//层级
			fileProps1.setLevel(3);
			//TODO 
			fileProps1.setGroupType("散");
			//文件url
			List<TGrabFileEntity> grabFileList1 = Lists.newArrayList();
			saveAttachment(msg, grabFileList1, fileProps1);
			if (!Util.isEmpty(grabFileList1)) {
				dbDao.insert(grabFileList1);
			} else {
				logger.info("没有抓取到附件!");
			}
			break;
		case FITVA:
			//TODO
			/**************************时间开始***************************/
			String temp = "";
			if ("team".equals(userTeam)) {
				temp = "团";
			} else {
				temp = "散";
			}
			rootId = getTopParentId(sender);
			//父id
			TGrabFileEntity timeFile2 = new TGrabFileEntity();
			timeFile2.setParentId(rootId);

			//创建时间
			Date createTime2 = DateTimeUtil.nowDate();
			timeFile2.setCreateTime(createTime2);

			//名称
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy.MM.dd");
			String fileName2 = format2.format(createTime2);
			timeFile2.setFileName(fileName2);
			//类型
			timeFile2.setType(FileTypeEnum.FOLDER.intKey());//文件类型

			//抓取记录id
			timeFile2.setMailId(grabRecordId);
			//启用
			timeFile2.setStatus(DataStatusEnum.ENABLE.intKey());
			timeFile2.setLevel(2);
			timeFile2.setSort(0);
			timeFile2.setGroupType(temp);
			List<TGrabFileEntity> list2 = existFile(rootId, fileName2);
			if (!Util.isEmpty(list2)) {
				timeFile2 = list2.get(0);
			} else {
				timeFile2 = dbDao.insert(timeFile2);
			}
			/**************************时间结束***************************/

			/**************************客户团号***************************/
			long sort2 = getSort(timeFile2.getId());

			String cusgroupnum2 = getcusGroupnum();//得到客户团号
			if (Util.isEmpty(cusgroupnum2)) {
				sort2 += 1;
				cusgroupnum2 = "客户团号" + sort2;
			}

			TGrabFileEntity groupNumFile2 = new TGrabFileEntity();

			//父id
			long pid2 = timeFile2.getId();
			groupNumFile2.setParentId(pid2);

			groupNumFile2.setCreateTime(createTime2);

			//名称
			groupNumFile2.setFileName(cusgroupnum2);
			//类型
			groupNumFile2.setType(FileTypeEnum.FOLDER.intKey());

			//抓取记录id
			groupNumFile2.setMailId(grabRecordId);
			//启用
			groupNumFile2.setStatus(DataStatusEnum.ENABLE.intKey());
			//层级
			groupNumFile2.setLevel(3);

			groupNumFile2.setSort(sort2);
			groupNumFile2.setGroupType(temp);//散团类型

			List<TGrabFileEntity> lst2 = existGroupNumFile(pid2, cusgroupnum2);
			if (!Util.isEmpty(lst2)) {
				groupNumFile2 = lst2.get(0);
			} else {
				groupNumFile2 = dbDao.insert(groupNumFile2);
			}
			/**************************************************客户团号结束**************************************************/

			/**************************************************PNR开始**************************************************/
			//得到PNR
			String PNR = getAttachmentName();
			if (Util.isEmpty(PNR)) {
				sort2 += 1;
				PNR = "PNR" + sort2;
			}
			TGrabFileEntity pnrFile = new TGrabFileEntity();

			//父id
			long pid3 = groupNumFile2.getId();
			pnrFile.setParentId(pid3);

			pnrFile.setCreateTime(createTime2);

			//名称
			pnrFile.setFileName(PNR);
			//类型
			pnrFile.setType(FileTypeEnum.FOLDER.intKey());

			//抓取记录id
			pnrFile.setMailId(grabRecordId);
			//启用
			pnrFile.setStatus(DataStatusEnum.ENABLE.intKey());
			//层级
			pnrFile.setLevel(4);

			pnrFile.setSort(sort2);
			pnrFile.setGroupType(temp);//散团类型

			List<TGrabFileEntity> lst3 = existGroupNumFile(pid3, PNR);
			if (!Util.isEmpty(lst3)) {
				pnrFile = lst3.get(0);
			} else {
				pnrFile = dbDao.insert(pnrFile);
			}

			/*************************************************PNR结束*******************************************************/

			/*************************************************文件开始*******************************************************/
			//父id
			TGrabFileEntity fileProps3 = new TGrabFileEntity();
			fileProps3.setParentId(pnrFile.getId());

			//创建时间
			fileProps3.setCreateTime(createTime2);

			//类型
			fileProps3.setType(FileTypeEnum.FOLDER.intKey());

			fileProps3.setFileSize(fileSize);
			//抓取记录id
			fileProps3.setMailId(grabRecordId);
			//启用
			fileProps3.setStatus(DataStatusEnum.ENABLE.intKey());
			//层级
			fileProps3.setLevel(5);
			//TODO 散团类别
			fileProps3.setGroupType(temp);
			//文件url
			List<TGrabFileEntity> grabFileList3 = Lists.newArrayList();
			saveAttachment(msg, grabFileList3, fileProps3);
			if (!Util.isEmpty(grabFileList3)) {
				dbDao.insert(grabFileList3);
			} else {
				logger.info("没有抓取到附件!");
			}
			break;
		case TEAMJQTT:
			//TODO
			/*************************************************文件开始*******************************************************/
			String flagjq = "";
			if ("team".equals(userTeam)) {
				flagjq = "团";
			} else {
				flagjq = "散";
			}
			rootId = getTopParentId(sender);
			//父id
			TGrabFileEntity fileProps4 = new TGrabFileEntity();
			fileProps4.setParentId(rootId);

			//创建时间
			Date createTime4 = DateTimeUtil.nowDate();
			fileProps4.setCreateTime(createTime4);

			//类型
			fileProps4.setType(FileTypeEnum.FOLDER.intKey());

			fileProps4.setFileSize(fileSize);
			//抓取记录id
			fileProps4.setMailId(grabRecordId);
			//启用
			fileProps4.setStatus(DataStatusEnum.ENABLE.intKey());
			//层级
			fileProps4.setLevel(2);
			//TODO 散团类别
			fileProps4.setGroupType(flagjq);
			//文件url
			List<TGrabFileEntity> grabFileList4 = Lists.newArrayList();
			saveAttachment(msg, grabFileList4, fileProps4);
			if (!Util.isEmpty(grabFileList4)) {
				dbDao.insert(grabFileList4);
			} else {
				logger.info("没有抓取到附件!");
			}
			break;
		case TEAMVA:
			//TODO
			/**************************************************PNR开始**************************************************/
			String flagva = "";
			if ("team".equals(userTeam)) {
				flagva = "团";
			} else {
				flagva = "散";
			}
			rootId = getTopParentId(sender);
			long sort3 = getSort(rootId);
			//得到PNR
			String PNR2 = getAttachmentName();
			if (Util.isEmpty(PNR2)) {
				sort3 += 1;
				PNR2 = "PNR" + sort3;
			}
			TGrabFileEntity pnrFile2 = new TGrabFileEntity();

			//父id
			pnrFile2.setParentId(rootId);

			Date createTime5 = DateTimeUtil.nowDate();
			pnrFile2.setCreateTime(createTime5);

			//名称
			pnrFile2.setFileName(PNR2);
			//类型
			pnrFile2.setType(FileTypeEnum.FOLDER.intKey());

			//抓取记录id
			pnrFile2.setMailId(grabRecordId);
			//启用
			pnrFile2.setStatus(DataStatusEnum.ENABLE.intKey());
			//层级
			pnrFile2.setLevel(3);

			pnrFile2.setSort(sort3);
			pnrFile2.setGroupType(flagva);//散团类型

			List<TGrabFileEntity> lst4 = existGroupNumFile(rootId, PNR2);
			if (!Util.isEmpty(lst4)) {
				pnrFile2 = lst4.get(0);
			} else {
				pnrFile2 = dbDao.insert(pnrFile2);
			}

			/*************************************************PNR结束*******************************************************/

			/*************************************************文件开始*******************************************************/
			//父id
			TGrabFileEntity fileProps5 = new TGrabFileEntity();
			fileProps5.setParentId(pnrFile2.getId());

			//创建时间
			fileProps5.setCreateTime(createTime5);

			//类型
			fileProps5.setType(FileTypeEnum.FOLDER.intKey());

			fileProps5.setFileSize(fileSize);
			//抓取记录id
			fileProps5.setMailId(grabRecordId);
			//启用
			fileProps5.setStatus(DataStatusEnum.ENABLE.intKey());
			//层级
			fileProps5.setLevel(3);
			//TODO 散团类别
			fileProps5.setGroupType(flagva);
			//文件url
			List<TGrabFileEntity> grabFileList5 = Lists.newArrayList();
			saveAttachment(msg, grabFileList5, fileProps5);
			if (!Util.isEmpty(grabFileList5)) {
				dbDao.insert(grabFileList5);
			} else {
				logger.info("没有抓取到附件!");
			}
			break;
		default:
			break;
		}
	}

	private long getTopParentId(String sender) {
		String fileName = null;
		TGrabMailEntity listMail = dbDao.fetch(TGrabMailEntity.class, Cnd.where("sender", "=", sender));
		String senderDb = listMail.getSender();//得到抓取保存到数据库中的发件人
		if (senderDb.contains(GrabMailConstants.FIT_D7)) {
			fileName = "D7";
		} else if (senderDb.contains(GrabMailConstants.FIT_QF)) {
			fileName = "QF";
		} else if (senderDb.contains(GrabMailConstants.FIT_VA)) {
			fileName = "VA";
		} else if (senderDb.contains(GrabMailConstants.TEAM_JQ)) {
			fileName = "JQ";
		} else if (senderDb.contains(GrabMailConstants.TEAM_VA)) {
			fileName = "VA";
		} else if (senderDb.contains(GrabMailConstants.FIT_JQ)) {
			fileName = "JQ";
		} else if (senderDb.contains(GrabMailConstants.FIT_TT)) {
			fileName = "TT";
		} else if (senderDb.contains(GrabMailConstants.TEAM_TT)) {
			fileName = "TT";
		} else if (senderDb.contains(GrabMailConstants.FIT_CI)) {
			fileName = "CI";
		} else if (senderDb.contains(GrabMailConstants.TEAM_CI)) {
			fileName = "CI";
		} else if (senderDb.contains(GrabMailConstants.FIT_EK)) {
			fileName = "EK";
		} else if (senderDb.contains(GrabMailConstants.TEAM_EK)) {
			fileName = "EK";
		} else if (senderDb.contains(GrabMailConstants.FIT_NZ)) {
			fileName = "NZ";
		} else if (senderDb.contains(GrabMailConstants.FIT_SABRE)) {
			fileName = "SABRE";
		} else {
			fileName = "D7";
		}
		Cnd cnd = Cnd.NEW();
		cnd.and("fileName", "=", fileName);
		TGrabFileEntity fetch = dbDao.fetch(TGrabFileEntity.class, cnd);
		return fetch.getId();
	}

	/**
	 * 【时间文件夹】根据父级id和文件名称查询此文件是否已存在
	 * @param rootId 父级id【此时的父id为最顶层文件夹的id】
	 * @param fileName
	 */
	private List<TGrabFileEntity> existFile(long rootId, String fileName) {
		Sql sqlCount = Sqls.create(sqlManager.get("grab_mail_count"));
		sqlCount.params().set("fileName", fileName);
		sqlCount.params().set("parentId", rootId);
		List<TGrabFileEntity> list = DbSqlUtil.query(dbDao, TGrabFileEntity.class, sqlCount);
		return list;
	}

	/**
	 * 【客户团号文件夹】根据父级id和文件名称查询此文件是否已存在
	 * @param pid  父级id【此时的父id为上级时间文件夹的id】
	 * @param cusgroupnum 文件名
	 */
	private List<TGrabFileEntity> existGroupNumFile(long pid, String cusgroupnum) {
		Sql sqlCount = Sqls.create(sqlManager.get("grab_mail_count"));
		sqlCount.params().set("fileName", cusgroupnum);
		sqlCount.params().set("parentId", pid);
		List<TGrabFileEntity> lst = DbSqlUtil.query(dbDao, TGrabFileEntity.class, sqlCount);
		return lst;
	}

	/**
	 * 获取客户团号
	 */
	private String getcusGroupnum() {
		//TODO
		return null;
	}

	/**
	 * 获取序号
	 * @param parentId
	 */
	private int getSort(long parentId) {
		Sql mailsql = Sqls.create(sqlManager.get("grab_mail_max_sort"));
		mailsql.params().set("pid", parentId);
		int sort = DbSqlUtil.fetchInt(dbDao, mailsql);
		return sort;
	}

	/** 
	 * 获得邮件主题 
	 * @param msg 邮件内容 
	 * @return 解码后的邮件主题 
	 */
	public String getSubject(MimeMessage msg) throws UnsupportedEncodingException, MessagingException {
		return MimeUtility.decodeText(msg.getSubject());
	}

	/** 
	 * 获得邮件发件人 
	 * @param msg 邮件内容 
	 * @return 姓名 <Email地址> 
	 * @throws MessagingException 
	 * @throws UnsupportedEncodingException  
	 */
	public String getFrom(MimeMessage msg) throws MessagingException, UnsupportedEncodingException {
		String from = "";
		Address[] froms = msg.getFrom();
		if (!Util.isEmpty(froms)) {
			if (froms.length < 1)
				throw new MessagingException("没有发件人!");

			InternetAddress address = (InternetAddress) froms[0];
			String person = address.getPersonal();
			if (person != null) {
				person = MimeUtility.decodeText(person) + " ";
			} else {
				person = "";
			}
			from = person + "<" + address.getAddress() + ">";
		}
		return from;
	}

	/** 
	 * 根据收件人类型，获取邮件收件人、抄送和密送地址。如果收件人类型为空，则获得所有的收件人 
	 * <p>Message.RecipientType.TO  收件人</p> 
	 * <p>Message.RecipientType.CC  抄送</p> 
	 * <p>Message.RecipientType.BCC 密送</p> 
	 * @param msg 邮件内容 
	 * @param type 收件人类型 
	 * @return 收件人1 <邮件地址1>, 收件人2 <邮件地址2>, ... 
	 * @throws MessagingException 
	 */
	public String getReceiveAddress(MimeMessage msg, Message.RecipientType type) throws MessagingException {
		StringBuffer receiveAddress = new StringBuffer();
		Address[] addresss = null;
		if (type == null) {
			addresss = msg.getAllRecipients();
		} else {
			addresss = msg.getRecipients(type);
		}

		if (addresss == null || addresss.length < 1)
			throw new MessagingException("没有收件人!");
		for (Address address : addresss) {
			InternetAddress internetAddress = (InternetAddress) address;
			receiveAddress.append(internetAddress.toUnicodeString()).append(",");
		}

		receiveAddress.deleteCharAt(receiveAddress.length() - 1); //删除最后一个逗号  

		return receiveAddress.toString();
	}

	/** 
	 * 获得邮件发送时间 
	 * @param msg 邮件内容 
	 * @return yyyy年mm月dd日 星期X HH:mm 
	 * @throws MessagingException 
	 */
	public String getSentDate(MimeMessage msg, String pattern) throws MessagingException {
		Date receivedDate = msg.getSentDate();
		if (receivedDate == null)
			return "";

		if (pattern == null || "".equals(pattern))
			pattern = "yyyy年MM月dd日 E HH:mm ";

		return new SimpleDateFormat(pattern).format(receivedDate);
	}

	/** 
	 * 判断邮件中是否包含附件 
	 * @param msg 邮件内容 
	 * @return 邮件中存在附件返回true，不存在返回false 
	 * @throws MessagingException 
	 * @throws IOException 
	 */
	public boolean isContainAttachment(Part part) throws MessagingException, IOException {
		boolean flag = false;
		if (part.isMimeType("multipart/*")) {
			MimeMultipart multipart = (MimeMultipart) part.getContent();
			int partCount = multipart.getCount();
			for (int i = 0; i < partCount; i++) {
				BodyPart bodyPart = multipart.getBodyPart(i);
				String disp = bodyPart.getDisposition();
				if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {
					flag = true;
				} else if (bodyPart.isMimeType("multipart/*")) {
					flag = isContainAttachment(bodyPart);
				} else {
					String contentType = bodyPart.getContentType();
					if (contentType.indexOf("application") != -1) {
						flag = true;
					}

					if (contentType.indexOf("name") != -1) {
						flag = true;
					}
				}

				if (flag)
					break;
			}
		} else if (part.isMimeType("message/rfc822")) {
			flag = isContainAttachment((Part) part.getContent());
		}
		return flag;
	}

	/**  
	 * 判断邮件是否已读  
	 * @param msg 邮件内容  
	 * @return 如果邮件已读返回true,否则返回false  
	 * @throws MessagingException   
	 */
	public boolean isRead(MimeMessage msg) throws MessagingException {
		return msg.getFlags().contains(Flags.Flag.SEEN);
	}

	/** 
	 * 判断邮件是否需要阅读回执 
	 * @param msg 邮件内容 
	 * @return 需要回执返回true,否则返回false 
	 * @throws MessagingException 
	 */
	public boolean isReplySign(MimeMessage msg) throws MessagingException {
		boolean replySign = false;
		String[] headers = msg.getHeader("Disposition-Notification-To");
		if (headers != null)
			replySign = true;
		return replySign;
	}

	/** 
	 * 获得邮件的优先级 
	 * @param msg 邮件内容 
	 * @return 1(High):紧急  3:普通(Normal)  5:低(Low) 
	 * @throws MessagingException  
	 */
	public String getPriority(MimeMessage msg) throws MessagingException {
		String priority = "普通";
		String[] headers = msg.getHeader("X-Priority");
		if (headers != null) {
			String headerPriority = headers[0];
			if (headerPriority.indexOf("1") != -1 || headerPriority.indexOf("High") != -1)
				priority = "紧急";
			else if (headerPriority.indexOf("5") != -1 || headerPriority.indexOf("Low") != -1)
				priority = "低";
			else
				priority = "普通";
		}
		return priority;
	}

	/** 
	 * 获得邮件文本内容 
	 * @param part 邮件体 
	 * @param content 存储邮件文本内容的字符串 
	 * @throws MessagingException 
	 * @throws IOException 
	 */
	public void getMailTextContent(Part part, StringBuffer content) throws MessagingException, IOException {
		//如果是文本类型的附件，通过getContent方法可以取到文本内容，但这不是我们需要的结果，所以在这里要做判断  
		boolean isContainTextAttach = part.getContentType().indexOf("name") > 0;
		if (part.isMimeType("text/*") && !isContainTextAttach) {
			content.append(part.getContentType().toString());
		} else if (part.isMimeType("message/rfc822")) {
			getMailTextContent((Part) part.getContent(), content);
		} else if (part.isMimeType("multipart/*")) {
			Multipart multipart = (Multipart) part.getContent();
			int partCount = multipart.getCount();
			for (int i = 0; i < partCount; i++) {
				BodyPart bodyPart = multipart.getBodyPart(i);
				getMailTextContent(bodyPart, content);
			}
		}
	}

	/**  
	 * 保存附件  
	 * @param part 邮件中多个组合体中的其中一个组合体  
	 * @param fileName  附件保存目录  
	 * @throws UnsupportedEncodingException  
	 * @throws MessagingException  
	 * @throws FileNotFoundException  
	 * @throws IOException  
	 */
	private void saveAttachment(Part part, List<TGrabFileEntity> grabFileLst, TGrabFileEntity fileProps)
			throws UnsupportedEncodingException, MessagingException, FileNotFoundException, IOException {
		if (part.isMimeType("multipart/*")) {
			//TODO
			Multipart multipart = (Multipart) part.getContent(); //复杂体邮件  
			//复杂体邮件包含多个邮件体  
			int partCount = multipart.getCount();
			for (int i = 0; i < partCount; i++) {
				//获得复杂体邮件中其中一个邮件体  
				BodyPart bodyPart = multipart.getBodyPart(i);

				String fileName = bodyPart.getFileName();
				String fileExt = FileUtil.getSuffix(fileName);//获取附件后缀名
				//某一个邮件体也有可能是由多个邮件体组成的复杂体  
				String disp = bodyPart.getDisposition();
				if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {
					//向网络上上传一个文件，然后返回一个地址存储到数据库中
					InputStream is = bodyPart.getInputStream();
					String fileUrl = uploadFile(is, fileExt);

					TGrabFileEntity newFile = new TGrabFileEntity();
					BeanUtil.copyProperties(fileProps, newFile);
					String attachmentName = fileName;
					int fileSort = 0;
					if (Util.isEmpty(attachmentName)) {
						fileSort = getSort(fileProps.getParentId());
						fileSort += 1;
						attachmentName = "PNR" + fileSort;
					}
					//文件名和序号
					newFile.setFileName(attachmentName);
					fileProps.setSort(fileSort);
					newFile.setUrl(fileUrl);
					newFile.setType(FileTypeEnum.FILE.intKey());//文件
					grabFileLst.add(newFile);
				} else if (bodyPart.isMimeType("multipart/*")) {
					saveAttachment(bodyPart, grabFileLst, fileProps);
				} else {
					String contentType = bodyPart.getContentType();
					if (contentType.indexOf("name") != -1 || contentType.indexOf("application") != -1) {
						InputStream is = bodyPart.getInputStream();
						String fileUrl = uploadFile(is, fileExt);

						TGrabFileEntity newFile = new TGrabFileEntity();
						BeanUtil.copyProperties(fileProps, newFile);
						String attachmentName = fileName;
						int fileSort = 0;
						if (Util.isEmpty(attachmentName)) {
							fileSort = getSort(fileProps.getParentId());
							fileSort += 1;
							attachmentName = "PNR" + fileSort;
						}
						//文件名和序号
						newFile.setFileName(attachmentName);
						fileProps.setSort(fileSort);
						newFile.setUrl(fileUrl);
						newFile.setType(FileTypeEnum.FILE.intKey());//文件
						grabFileLst.add(newFile);
					}
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			saveAttachment((Part) part.getContent(), grabFileLst, fileProps);
		}
	}

	/**  
	 * 向网络上上传一个文件，然后返回一个地址存储到数据库中
	 * @param is 输入流  
	 * @param fileName 文件名  
	 * @return 文件URL地址
	 */
	private String uploadFile(InputStream is, String fileName) {
		String fileURL = CommonConstants.IMAGES_SERVER_ADDR + qiniuUploadService.uploadImage(is, fileName, fileName);
		//文件存储地址
		logger.info("文件存储地址:" + fileURL);
		return fileURL;
	}

	//通过发件人确定顶层目录文件夹
	/*private TGrabFileEntity getRootFile(int sender) {
		//TODO
		TGrabFileEntity singleFile = dbDao.fetch(TGrabFileEntity.class, Cnd.where("id", "=", 1));
		return singleFile;
	}*/

	//判断文件结构类型
	private FileNavalEnum structureType(String sender) {
		//TODO
		TGrabMailEntity listMail = dbDao.fetch(TGrabMailEntity.class, Cnd.where("sender", "=", sender));
		String senderDb = listMail.getSender();//得到抓取保存到数据库中的发件人
		if (senderDb.contains(GrabMailConstants.FIT_D7) || senderDb.contains(GrabMailConstants.FIT_JQ)
				|| senderDb.contains(GrabMailConstants.FIT_TT)) {
			return FileNavalEnum.FITD7JQTT;
		} else if (senderDb.contains(GrabMailConstants.FIT_QF)) {
			return FileNavalEnum.FITQF;
		} else if (senderDb.contains(GrabMailConstants.FIT_VA)) {
			return FileNavalEnum.FITVA;
		} else if (senderDb.contains(GrabMailConstants.TEAM_JQ) || senderDb.contains(GrabMailConstants.TEAM_TT)) {
			return FileNavalEnum.TEAMJQTT;
		} else if (senderDb.contains(GrabMailConstants.TEAM_VA)) {
			return FileNavalEnum.TEAMVA;
		}
		return FileNavalEnum.FITD7JQTT;
	}

	//根据邮件附件名获取PNR
	private String getAttachmentName() {
		//TODO
		Random random = new Random();
		int ss = random.nextInt();
		if (ss % 2 == 0) {
			return "X4WRZ8";
		} else {
			return null;
		}
	}

	/** 
	 * 文本解码 
	 * @param encodeText 解码MimeUtility.encodeText(String text)方法编码后的文本 
	 * @return 解码后的文本 
	 * @throws UnsupportedEncodingException 
	 */
	public String decodeText(String encodeText) throws UnsupportedEncodingException {
		if (encodeText == null || "".equals(encodeText)) {
			return "";
		} else {
			return MimeUtility.decodeText(encodeText);
		}
	}

}
