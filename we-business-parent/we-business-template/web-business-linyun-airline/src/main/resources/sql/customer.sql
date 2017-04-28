/*customer_city_list*/
SELECT
	o.dictInfoId id,
	d.dictCode,
	d.EnglishName,
	d.countryName
FROM
	t_customer_outcity o
INNER JOIN t_departure_city d ON o.dictInfoId = d.id
AND o.infoId = @companyId
$condition

/*customer_line_list*/
select 
	d.* 
from 
	t_customer_line l inner join dict_info d on l.dictInfoId=d.id
and l.infoId=@companyId
$condition

/*customer_invioce_list*/
select 
	l.infoId,
	l.dictInfoId id,
	d.dictName
from 
	t_customer_invioce l inner join dict_info d on l.dictInfoId=d.id
and l.infoId=@companyId
$condition

/*customer_cityOption_list*/
select
	d.*
FROM t_customer_outcity c INNER JOIN t_departure_city d on c.dictInfoId = d.id
$condition

/*customer_user_company*/
SELECT
    uj.userid,
	c.id,
	c.comName
FROM
	t_user_job uj,
	t_company_job cj,
	t_company c
WHERE
	uj.companyJobId = cj.posid
AND cj.comId = c.id
AND uj.userid = @userId

/*customer_agent*/
SELECT 
 u.id,u.userName
from t_customer_info c INNER JOIN
t_user u 
where c.responsibleId=u.id
and c.id=@agentId
$condition

/*customer_agent_list*/
SELECT
	u.*
FROM
	(
		t_user_job uj
		INNER JOIN t_user u ON uj.userid = u.id
	)
INNER JOIN t_company_job cj ON uj.companyJobId = cj.id
WHERE
	cj.comId = @comid
AND u.status = @status
AND userName IS NOT NULL
ORDER BY
	u.userName


/*customer_comOption_list*/
SELECT
	c.id,
	c.comName,
	c.comType,
	c.deletestatus
FROM
	t_company c
$condition
LIMIT 0,5

/*customer_agOption_list*/
SELECT
	a.id,
	c.comName,
	c.comType,
	c.deletestatus
FROM
	t_agent a
INNER JOIN t_company c ON a.comId = c.id
	$condition

/*customer_upOption_list*/
SELECT
	a.id,
	c.comName,
	c.comType,
	c.deletestatus
FROM
	t_upcompany a
INNER JOIN t_company c ON a.comId = c.id
	$condition

/*customer_islineOption_list*/
select 
  	o.infoId,
	d.*
FROM t_customer_line o INNER JOIN dict_info d on o.dictInfoId = d.id
$condition

/*customer_invioceOption_list*/
select 
	l.infoId,
	l.dictInfoId id,
	d.*
from 
	t_customer_invioce l inner join dict_info d on l.dictInfoId=d.id
$condition

/*customer_list_info*/
SELECT 	i.id, 
	i.upComId, 
	i.agentId, 
	i.name, 
	i.shortName, 
	i.linkMan, 
	i.telephone, 
	i.fax, 
	i.siteUrl, 
	i.address, 
	i.responsibleId, 
	i.createTime, 
	i.departureCity, 
	i.appendix, 
	i.travelType, 
	i.payWay, 
	i.paywayName, 
	i.invoice, 
	i.sellPrice, 
	i.moneyUnit, 
	i.payType, 
	i.paytypeName, 
	i.cooperateTime, 
	i.cooperateDueTime, 
	i.contractTime, 
	i.contractDueTime, 
	i.contract, 
	i.forbid, 
	i.business,
	u.fullName userName
	FROM 
t_customer_info i INNER JOIN t_upCompany uc ON i.upComId=uc.id
INNER JOIN t_user u ON i.responsibleId=u.id
$condition

/*customer_search_accounter*/
SELECT
	uj.userid
FROM
	t_function f
LEFT JOIN t_company_function_map cfm ON cfm.funId = f.id
LEFT JOIN t_com_fun_pos_map cfpm ON cfpm.companyFunId = cfm.id
LEFT JOIN t_job j ON j.id = cfpm.jobId
LEFT JOIN t_company_job cj ON cj.posid = j.id
LEFT JOIN t_user_job uj ON uj.companyJobId = cj.id
WHERE
	(f.parentId = @recPayId
AND cj.comId = @companyid
AND (
	f.`name` LIKE '%内陆订单%'
	OR f.`name` LIKE '%国际订单%'
))
OR
(f.parentId = @invioceId
AND cj.comId = @companyid
AND (
	f.`name` LIKE '%内陆发票%'
	OR f.`name` LIKE '%国际发票%'
))
GROUP BY
	(uj.userid)


/*customer_search__msg*/
SELECT
	*
FROM
	t_message m
INNER JOIN t_user_msg um ON um.msgId = m.id
WHERE
	um.userId = @userId
AND m.msgType = @msgType
AND um.customerInfoId = @customerInfoId


/*customer_inland_arrearsMoney_byId*/
SELECT
	SUM(fi.receivable) arrears
FROM
	t_up_order uo
LEFT JOIN t_finance_info fi ON fi.orderid = uo.id
LEFT JOIN t_order_receive orec ON orec.orderid = uo.id
LEFT JOIN t_receive r ON r.id = orec.receiveid
WHERE
	uo.userid = @customerId
AND uo.orderstype = @orderType
AND (r.`status` = @rStatus OR r.`status` IS NULL)
AND NOT uo.ordersstatus IN (@oStatus)

/*customer_inter_arrearsMoney_byId*/
SELECT
	SUM(prr.currentpay) arrears
FROM
	t_up_order uo
LEFT JOIN t_pay_receive_record prr ON prr.orderid = uo.id
LEFT JOIN t_order_receive orec ON orec.orderid = uo.id
LEFT JOIN t_receive r ON r.id = orec.receiveid
WHERE
	uo.userid = @customerId
AND uo.orderstype = @orderType
AND prr.recordtype = @recordType
AND (r.`status` = @rStatus OR r.`status` IS NULL)
AND NOT prr.orderstatusid IN (@oStatus)