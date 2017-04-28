/*get_inland_listdata*/
SELECT
	tuo.*, tfi.receivable,
	tfi.incometotal,
	tfi.personcount,
	tci.linkMan userName,
	tci.telephone,
	tci.id customeid
FROM
	t_up_order tuo
left JOIN t_finance_info tfi ON tuo.id = tfi.orderid
left JOIN t_customer_info tci ON tuo.userid = tci.id
$condition

/*get_pnr_visitor_info*/
SELECT
	tvi.*
FROM
	t_visitors_pnr tvp
INNER JOIN t_visitor_info tvi ON tvp.visitorslistid = tvi.id
$condition

/*get_customneed_visitor*/
SELECT
	tvi.*
FROM
	t_pnr_info tpi
INNER JOIN t_visitors_pnr tvp ON tpi.id = tvp.PNRid
INNER JOIN t_visitor_info tvi ON tvp.visitorslistid = tvi.id
$condition

/*get_sea_payapply_list*/
SELECT
	tpi.*, toc.arrivecity,
	toc.leavecity,
	toc.leavetdate,
	tuo.ordersstatus,
	tuo.ordersnum,
	tci.linkMan,
	tci.telephone,
	tai.ailinenum,
	tai.leavetime,
	tai.arrivetime
FROM
	t_pnr_info tpi
INNER JOIN t_order_customneed toc ON tpi.needid = toc.id
INNER JOIN t_up_order tuo ON toc.ordernum = tuo.id
INNER JOIN t_customer_info tci ON tuo.userid = tci.id
LEFT JOIN (select * from  t_airline_info GROUP BY needid) tai ON tai.needid = tpi.needid
$condition

/*get_sea_invoce_table_data*/
SELECT
	 tuo.*, tfi.billingdate,
	 tfi.cusgroupnum,
	 tci.shortName,
	 tci.NAME customename,
	 tci.linkMan,
	 tfi.issuer,
	 tfi.incometotal
FROM
	t_up_order tuo
left JOIN t_customer_info tci ON tuo.userid = tci.id
LEFT JOIN t_finance_info tfi ON tuo.id = tfi.orderid
$condition
/*get_sea_payapply_table_data*/
SELECT
	tpi.*, toc.arrivecity,
	toc.leavecity,
	toc.leavetdate,
	tuo.ordersstatus,
	tuo.ordersnum,
	tuo.id ordersid,
	tci.linkMan,
	tci.telephone,
	tci.shortName,
	tfi.cusgroupnum,
	tfi.issuer,
	tfi.incometotal,
	tfi.billingdate
FROM
	t_pnr_info tpi
INNER JOIN t_order_customneed toc ON tpi.needid = toc.id
INNER JOIN t_up_order tuo ON toc.ordernum = tuo.id
INNER JOIN t_customer_info tci ON tuo.userid = tci.id
INNER JOIN t_finance_info tfi on tuo.id = tfi.orderid
$condition
/*get_shoufukuan_shoukuan_list*/
SELECT
	tr.*, tii.id invoiceid,
	tii. STATUS invoicestatus
FROM
	t_receive tr
LEFT JOIN t_invoice_info tii ON tr.id = tii.receiveid
$condition

/*get_shoukuan_order_list*/
SELECT
	tuo.ordersnum,
	tfi.personcount,
	tfi.incometotal,
	tuo.id orderid,
	tfi.issuer
FROM
	t_up_order tuo
LEFT JOIN t_finance_info tfi ON tuo.id = tfi.orderid
INNER JOIN t_order_receive tor ON tuo.id = tor.orderid
INNER JOIN t_receive tr ON tor.receiveid = tr.id
$condition

/*get_pay_fukuan_list*/
SELECT
	tuo.ordersnum, tpi.*,
	toc.leavecity,
	toc.arrivecity,
	toc.leavetdate,
	tci.name customename,
	tpp.orderPnrStatus status,
	tii.id invoiceid,
	tii.status invoicestatus,
  tai.ailinenum,
  tai.leavetime,
  tai.arrivetime,
  tfi.issuer
FROM

	t_pnr_info tpi
INNER JOIN (
			SELECT
				paypnr1.*
			FROM
				t_pay_pnr paypnr1,
				(
					SELECT
						max(optime) optime,
						id
					FROM
						t_pay_pnr
					GROUP BY
						pnrId
				) paypnr
			WHERE
				paypnr1.id = paypnr.id
		) tpp ON tpi.id = tpp.pnrId
INNER JOIN t_order_customneed toc ON tpi.needid = toc.id
INNER JOIN t_up_order tuo ON toc.ordernum = tuo.id
LEFT JOIN t_customer_info tci ON tci.id = tuo.userid
INNER JOIN t_finance_info tfi ON tuo.id = tfi.orderid
LEFT JOIN t_invoice_info tii ON tii.pnrid = tpi.id
LEFT JOIN (select * from t_airline_info GROUP BY needid) tai ON tai.needid = toc.id
$condition

/*get_fukuan_info_list*/
SELECT
	tpi.*, tuo.ordersnum,
	tfi.billingdate,
	tuo.id ordersid,
	tfi.cusgroupnum,
	tci.shortName customename,
	tci.linkMan,
	(
		SELECT
			fullName
		FROM
			t_user
		WHERE
			id = tfi.ISSUERid
	) issuer
FROM
	t_pnr_info tpi
INNER JOIN t_order_customneed toc ON tpi.needid = toc.id
INNER JOIN t_up_order tuo ON toc.ordernum = tuo.id
INNER JOIN t_finance_info tfi ON tuo.id = tfi.orderid
LEFT JOIN t_customer_info tci ON tuo.userid = tci.id
$condition

/*get_kai_invoice_list_order*/
select tuo.* FROM
t_up_order tuo
INNER JOIN t_order_receive tor ON tuo.id = tor.orderid
INNER JOIN t_receive tr ON tor.receiveid = tr.id
INNER JOIN t_invoice_info tii ON tr.id = tii.receiveid
$condition

/*get_kai_invoice_search_list*/
SELECT
	tuo.ordersnum,
	ii.*, 
	u.id AS userIds,
	u.userName
FROM
	t_invoice_info ii
LEFT JOIN t_order_receive ore ON ii.receiveid = ore.receiveid
LEFT JOIN t_up_order tuo ON tuo.id = ore.orderid
LEFT JOIN t_customer_info cus ON cus.id = tuo.userid
LEFT JOIN t_user u ON u.id = ii.billuserid
LEFT JOIN dict_info info ON info.id = ii.invoiceitem
$condition

/*get_shou_invoice_list_order*/
SELECT
	tuo.ordersnum,
	tpi.*,
	tii.invoicedate,
	tii.`status`,
	tii.invoiceitem,
	tii.paymentunit,
	tii.id invoiceid,
	tii.remark,
	(
		SELECT
			count(*)
		FROM
			t_invoice_detail
		WHERE
			invoiceinfoid = tii.id
	) invoicecount,
	tu.userName,
	tu.fullName
FROM
	t_pnr_info tpi
INNER JOIN t_invoice_info tii ON tii.pnrid = tpi.id
LEFT JOIN t_user tu ON tii.billuserid = tu.id
INNER JOIN t_order_customneed toc ON tpi.needid = toc.id
INNER JOIN t_up_order tuo ON toc.ordernum = tuo.id
$condition


/*get_check_pnr_order_sql*/
SELECT
	tuo.*
FROM
	t_pnr_info tpi
INNER JOIN t_order_customneed toc ON tpi.needid = toc.id
INNER JOIN t_up_order tuo ON toc.ordernum = tuo.id
$condition

/*get_fukuan_invoice_page_data*/
select tcb.* FROM
t_pay_pnr tpp
LEFT JOIN t_pay tp ON tpp.payId = tp.id
left JOIN t_company_bank_card tcb ON tp.bankId = tcb.id
$condition
/*select_order_pnrs_info*/
SELECT
	tpi.*
FROM
	t_order_customneed toc
INNER JOIN t_pnr_info tpi ON toc.id = tpi.needid
$condition
/*get_bank_info_select*/
SELECT
	*
FROM
	dict_info
WHERE
	dictname IN (
		SELECT DISTINCT
			bankName
		FROM
			t_bankcard
		WHERE
			companyId = @companyId
	)
AND typecode = @typeCode


/*select_receive_order_info*/
SELECT
	tuo.*
FROM
	t_order_receive tor
INNER JOIN t_up_order tuo ON tor.orderid = tuo.id
$condition

/*select_function_user_ids*/
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
	f.parentId = @parentid
AND cj.comId = @companyid
AND (
	f.`name` LIKE @functionname
)
GROUP BY
	(uj.userid)