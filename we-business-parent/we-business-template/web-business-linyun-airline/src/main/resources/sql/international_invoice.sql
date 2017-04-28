/*international_invoice_list_sql*/
SELECT
	tpi.*, tuo.ordersnum,
	tuo.ordersstatus,
	tor.orderstatus receiveorderstatus
FROM
	t_up_order tuo
INNER JOIN t_plan_info tpi ON tpi.ordernumber = tuo.id
LEFT JOIN t_order_receive tor ON tor.orderid = tuo.id
AND tor.orderstatus = @ordersstatus
$condition

/*international_invoice_sea_invoce_table_data*/
SELECT
	tprr.orderstatus,
	tuo.*, tfi.billingdate,
	tfi.cusgroupnum,
	tci.shortName,
	tci. NAME customename,
	tci.linkMan,
	tfi. ISSUER,
	tfi.incometotal,
	tprr.currentpay
FROM
	t_up_order tuo
LEFT JOIN t_customer_info tci ON tuo.userid = tci.id
LEFT JOIN t_finance_info tfi ON tuo.id = tfi.orderid
LEFT JOIN t_pay_receive_record tprr ON tprr.orderid = tuo.id
AND tprr.orderstatusid = @orderstatus
AND tprr.recordtype = @recordtype
$condition

/*international_invoice_receive_list*/
SELECT
	tr.*, tii.id invoiceid,
	tu.userName
FROM
	t_receive tr
LEFT JOIN t_invoice_info tii ON tr.id = tii.receiveid
INNER JOIN t_user tu ON tr.userid = tu.id
$condition

/*international_invoice_receive_list_order*/
SELECT
	tuo.ordersnum,tfi.personcount,tfi.incometotal,tpi.*
FROM
	t_up_order tuo
LEFT JOIN t_finance_info tfi ON tuo.id = tfi.orderid
INNER JOIN t_order_receive tor ON tuo.id = tor.orderid
INNER JOIN t_receive tr ON tor.receiveid = tr.id
INNER JOIN t_plan_info  tpi ON tuo.id = tpi.ordernumber
$condition

/*international_invoice_pay_list*/
SELECT
	tpo.*, tuo.ordersnum,
	tpi.peoplecount,
	tii.remark,
	tii.id invoiceid,
	tfi.costtotal
FROM
	t_pay_order tpo
INNER JOIN t_up_order tuo ON tpo.orderid = tuo.id
INNER JOIN t_pay tp ON tpo.payid = tp.id
INNER JOIN t_plan_info tpi ON tuo.id = tpi.ordernumber
LEFT JOIN t_finance_info tfi ON tfi.orderid = tuo.id
LEFT JOIN t_invoice_info tii ON tpo.id = tii.orderpayid
$condition

/*international_invoice_pay_list_order*/
SELECT
	tuo.ordersnum,tfi.personcount,tfi.incometotal,tpi.*
FROM
	t_up_order tuo
LEFT JOIN t_finance_info tfi ON tuo.id = tfi.orderid
INNER JOIN t_pay_order tpo ON tuo.id = tpo.orderid
INNER JOIN t_pay tp ON tpo.payid = tp.id
INNER JOIN t_plan_info  tpi ON tuo.id = tpi.ordernumber
$condition

/*international_invoice_inter_shou_invoice_list_order*/
SELECT
	tuo.*, tprr.actualnumber
FROM
	t_up_order tuo
INNER JOIN t_order_receive tor ON tuo.id = tor.orderid
INNER JOIN t_receive tr ON tor.receiveid = tr.id
INNER JOIN t_invoice_info ii ON tr.id = ii.receiveid
LEFT JOIN t_pay_receive_record tprr ON tor.orderid = tprr.orderid
AND tprr.orderstatusid = tor.orderstatus
AND tprr.recordtype = @recordtype
$condition

/*international_invoice_international_invoice_list*/
SELECT
	tuo.id AS orderid,
	tii.*, tuo.ordersnum
FROM
	t_invoice_info tii
INNER JOIN t_pay_order tpo ON tii.orderpayid = tpo.id
INNER JOIN t_up_order tuo ON tpo.orderid = tuo.id
$condition

/*international_invoice_table_data*/
SELECT
	tprr.orderstatus,
	 tuo.*, tfi.billingdate,
	 tfi.cusgroupnum,
	 tci.shortName,
	 tci.NAME customename,
	 tci.linkMan,
	 tfi.issuer,
	 tfi.incometotal,
     tprr.currentpay
FROM
	t_up_order tuo
left JOIN t_customer_info tci ON tuo.userid = tci.id
LEFT JOIN t_finance_info tfi ON tuo.id = tfi.orderid
LEFT JOIN t_pay_receive_record tprr ON tprr.orderid = tuo.id
$condition	

/*international_invoice__search_list*/
SELECT
	tuo.ordersnum,
	ii.*, 
	u.id AS userIds,
	u.fullName
FROM
	t_invoice_info ii
LEFT JOIN t_order_receive ore ON ii.receiveid = ore.receiveid
LEFT JOIN t_up_order tuo ON tuo.id = ore.orderid
LEFT JOIN t_customer_info cus ON cus.id = tuo.userid
LEFT JOIN t_user u ON u.id = ii.billuserid
LEFT JOIN dict_info info ON info.id = ii.invoiceitem
$condition