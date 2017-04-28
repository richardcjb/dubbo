/*msg_user_company*/
SELECT
	m.id,
	m.generateTime,
  	c.comName, 
	u.userName,
	m.msgContent
FROM
 	t_message m,
 	t_user_msg um,
 	t_user u,
 	t_user_job uj,
 	t_company_job cj,
 	t_company c
WHERE 
	m.id=um.msgId 
AND 
	um.userId = u.id
AND 
	u.id=uj.userid 
AND 
	uj.companyJobId=cj.posid 
AND 
	cj.comId=c.id 
AND 
	u.id=@userId
AND
	m.msgStatus=@msgStatus
AND 
	m.generateTime between @start AND @end
	

/*msg_user_company_task*/
SELECT
	m.id,
	m.generateTime,
  	c.comName, 
	u.userName,
	m.msgContent,
	m.msgType,
	m.reminderMode
FROM
 	t_message m,
 	t_user_msg um,
 	t_user u,
 	t_user_job uj,
 	t_company_job cj,
 	t_company c
WHERE 
	m.id=um.msgId 
AND 
	um.userId = u.id
AND 
	u.id=uj.userid 
AND 
	uj.companyJobId=cj.posid 
AND 
	cj.comId=c.id
AND 
	u.id=@userId
AND
	um.msgSource=@msgSource
AND
    m.msgStatus=@msgStatus
AND
    m.generateTime<@generateTime
ORDER BY m.generateTime DESC



/*msg_count_list*/
SELECT DISTINCT
	(
		DATE_FORMAT(m.generateTime, '%Y-%m-%d')
	) AS gtime,
	COUNT(m.generateTime) AS counts,
	m.msgType
FROM
	t_message m
WHERE
(
	date_format(m.generateTime, '%Y-%m') = date_format(@MincalTimes1,'%Y-%m') 
	OR date_format(m.generateTime, '%Y-%m') = date_format(@MincalTimes2,'%Y-%m')
	OR date_format(m.generateTime, '%Y-%m') = date_format(@MincalTimes3,'%Y-%m')
)
GROUP BY
	generateTime

/*msg_type_list*/
SELECT DISTINCT
	(
		DATE_FORMAT(m.generateTime, '%Y-%m-%d')
	) AS gtime,
	m.msgContent,
	m.msgType
FROM
	t_message m
INNER JOIN t_user_msg um ON m.id = um.msgId
WHERE
	m.msgStatus = @msgStatus
AND 
	um.userId = @userid
AND
(
	date_format(m.generateTime, '%Y-%m') = date_format(@MincalTimes1,'%Y-%m')
	OR date_format(m.generateTime, '%Y-%m') = date_format(@MincalTimes2,'%Y-%m')
	OR date_format(m.generateTime, '%Y-%m') = date_format(@MincalTimes3,'%Y-%m')
)

	

/*msg_type*/
SELECT DISTINCT
	(
	DATE_FORMAT(m.generateTime, '%Y-%m-%d')
	) AS gtime,
	m.msgContent,
	m.msgType
FROM
	t_message m
INNER JOIN t_user_msg um ON m.id = um.msgId
WHERE
	m.msgStatus = @msgStatus
AND 
	um.userId = @userid
AND
	date_format(m.generateTime, '%Y-%m-%d') = date_format(@MincalTimes1,'%Y-%m-%d')

	
/*agentCompany_list*/
SELECT
	*
FROM
	t_company c
INNER JOIN t_agent a ON a.comId=c.id
AND
	c.comType=@comtype
AND
	c.deletestatus=@deletestatus
LIMIT 0,5

/*get_checkbox_status*/
SELECT
	*
FROM
	t_checkbox_status t
WHERE
	t.userId =@userid

/*operationsArea_taskList_customerInfo*/
SELECT
	cInfo.id,
	cInfo.createTime generatetime,
	cInfo.shortName comname,
	cInfo.linkMan username,
	m.msgContent,
	m.msgType,
	cInfo.payType remindermode
FROM
	t_message m
INNER JOIN t_user_msg um ON um.msgId = m.id
INNER JOIN t_customer_info cInfo ON cInfo.id = um.customerInfoId
WHERE
	um.userId = @userId
AND m.msgType = @msgType
ORDER BY
	cInfo.createTime DESC
	
	
	
/*operationsArea_existMsg*/
SELECT
	*
FROM
	t_message m
INNER JOIN t_user_msg um ON m.id = um.msgId
WHERE
	m.msgType = @msgType
AND um.customerInfoId =@infoId

/*operationsArea_order_msg*/
SELECT
	m.id,
	um.id umid,
	m.generateTime,
	c.comName,
	u.fullName username,
	m.msgContent,
	m.msgType,
	m.reminderMode,
	m.upOrderId,
	um.readTime,
	um.isRead
FROM
	t_user_msg um
LEFT JOIN t_message m ON m.id = um.msgId
LEFT JOIN t_user u ON u.id = um.fromId
LEFT JOIN t_user_job uj ON uj.userid = u.id
LEFT JOIN t_company_job cj ON cj.id = uj.companyJobId
LEFT JOIN t_company c ON c.id = cj.comId
LEFT JOIN t_upCompany uc ON uc.comId = c.id
$condition
ORDER BY
	m.generateTime DESC
	
/*operationsArea_function_nums*/
SELECT
  uj.userid,
  COUNT(f.`name`) funNum
FROM
	t_function f
LEFT JOIN t_company_function_map cfm ON cfm.funId = f.id
LEFT JOIN t_com_fun_pos_map cfpm ON cfpm.companyFunId = cfm.id
LEFT JOIN t_job j ON j.id = cfpm.jobId
LEFT JOIN t_company_job cj ON cj.posid = j.id
LEFT JOIN t_user_job uj ON uj.companyJobId = cj.id
$condition
