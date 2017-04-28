/*get_international_list_sql*/
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

/*get_international_receive_list_sql*/
SELECT
  tpi.*, tuo.ordersnum,
	tuo.ordersstatus,
	tr.orderstatus receiveorderstatus,
	tprr.orderstatus statusname
FROM
	t_pay_receive_record tprr
INNER JOIN t_up_order tuo ON tprr.orderid = tuo.id
INNER JOIN t_plan_info tpi ON tpi.ordernumber = tuo.id
LEFT JOIN (
	SELECT
		*
	FROM
		t_order_receive
	WHERE
		orderstatus = @ordersstatus
) tor ON tor.orderid = tuo.id
LEFT JOIN t_receive tr ON tor.receiveid = tr.id
$condition

/*get_international_pay_list_sql*/
SELECT
	tprr.orderstatus,tprr.currentpay,
	tpi.*, tuo.ordersnum,
	tuo.ordersstatus,
	tp.orderstatus receiveorderstatus,
	tprr.orderstatus statusname
FROM
	t_pay_receive_record tprr
INNER JOIN t_up_order tuo ON tprr.orderid = tuo.id
INNER JOIN t_plan_info tpi ON tpi.ordernumber = tuo.id
LEFT JOIN (
	SELECT
		*
	FROM
		t_pay_order
	WHERE
		orderstatus = @ordersstatus
) tpo ON tpo.orderid = tuo.id
LEFT JOIN t_pay tp ON tp.id = tpo.payid
$condition

/*get_international_receive_list*/
SELECT
	tr.*, tii.id invoiceid,
	tu.fullName
FROM
	t_receive tr
LEFT JOIN t_invoice_info tii ON tr.id = tii.receiveid
INNER JOIN t_user tu ON tr.userid = tu.id
$condition

/*get_international_receive_list_order*/
SELECT
	tuo.ordersnum,
	tuo.id orderid,
	tfi.personcount,
	tfi.incometotal,
	tpi.*, tprr.orderstatus,
	tprr.currentpay,
	tprr.actualnumber
FROM
	t_up_order tuo
LEFT JOIN t_finance_info tfi ON tuo.id = tfi.orderid
INNER JOIN t_order_receive tor ON tuo.id = tor.orderid
INNER JOIN t_receive tr ON tor.receiveid = tr.id
INNER JOIN t_plan_info tpi ON tuo.id = tpi.ordernumber
LEFT JOIN t_pay_receive_record tprr ON tprr.orderid = tuo.id
AND tprr.orderstatusid = tor.orderstatus
AND tprr.recordtype = @recordtype
$condition

/*get_international_pay_list*/
SELECT
	tpo.*, tuo.ordersnum,
	tpi.peoplecount,
	tii.remark,
	tii.id invoiceid,
	tfi.costtotal,
	tprr.currentpay,
	tci.shortName customename,
	tprr.actualnumber
FROM
	t_pay_order tpo
INNER JOIN t_up_order tuo ON tpo.orderid = tuo.id
INNER JOIN t_pay tp ON tpo.payid = tp.id
INNER JOIN t_customer_info tci ON tuo.userid = tci.id
LEFT JOIN t_plan_info tpi ON tuo.id = tpi.ordernumber
LEFT JOIN t_finance_info tfi ON tfi.orderid = tuo.id
LEFT JOIN t_invoice_info tii ON tpo.id = tii.orderpayid
LEFT JOIN t_pay_receive_record tprr ON tprr.orderid = tpo.orderid
AND tprr.orderstatusid = tpo.orderstatus
AND tprr.recordtype = @recordtype
$condition

/*get_international_pay_list_order*/
SELECT
	tuo.ordersnum,tfi.personcount,tfi.incometotal,tpi.*
FROM
	t_up_order tuo
LEFT JOIN t_finance_info tfi ON tuo.id = tfi.orderid
INNER JOIN t_pay_order tpo ON tuo.id = tpo.orderid
INNER JOIN t_pay tp ON tpo.payid = tp.id
INNER JOIN t_plan_info  tpi ON tuo.id = tpi.ordernumber
$condition

/*get_inter_shou_invoice_list_order*/
select tuo.* FROM
t_up_order tuo
INNER JOIN t_order_receive tor ON tuo.id = tor.orderid
INNER JOIN t_receive tr ON tor.receiveid = tr.id
INNER JOIN t_invoice_info tii ON tr.id = tii.receiveid
$condition

/*get_international_invoice_list*/
SELECT
	tii.*, tuo.ordersnum
FROM
	t_invoice_info tii
INNER JOIN t_pay_order tpo ON tii.orderpayid = tpo.id
INNER JOIN t_up_order tuo ON tpo.orderid = tuo.id
$condition

/*get_international_last_payreceive_record*/
SELECT
	t.*
FROM
	t_pay_receive_record t
INNER JOIN (
	SELECT
		max(optime) optime,
		orderid
	FROM
		t_pay_receive_record
	GROUP BY
		orderid,
		recordtype
) t1 ON t.orderid = t1.orderid
AND t.optime = t1.optime
$condition

/*get_international_record_sumprice*/
SELECT
	SUM(currentdue) yishou,
	orderid,
	recordtype
FROM
	t_pay_receive_record
$condition

/*get_international_sea_invoce_table_data*/
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

/*get_international_kai_invoice_list_order*/
SELECT
	tuo.*, tprr.actualnumber,tprr.orderstatus
FROM
	t_up_order tuo
INNER JOIN t_order_receive tor ON tuo.id = tor.orderid
INNER JOIN t_receive tr ON tor.receiveid = tr.id
INNER JOIN t_invoice_info tii ON tr.id = tii.receiveid
LEFT JOIN t_pay_receive_record tprr ON tor.orderid = tprr.orderid
AND tprr.orderstatusid = tor.orderstatus
AND tprr.recordtype = @recordtype
$condition