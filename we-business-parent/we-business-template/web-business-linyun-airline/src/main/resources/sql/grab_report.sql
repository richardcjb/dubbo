/*grab_report_list*/
SELECT
	oc.id,
	oc.aircom,
	oc.leavecity,
	oc.arrivecity,
	oc.leavetdate,
	oc.backdate,
	oc.originalprice,
	oc.price,
	oc.currencyCode,
	oc.peoplecount,
	oc.passengertype,
	oc.tickettype,
	oc.rengetype,
	oc.ordernum,
	oc.remark,
	oc.realtimexrate,
	oc.avgexrate,
	oc.paycurrency,
	oc.paymethod,
	p.costprice,
	a.price
FROM
	t_order_customneed oc
LEFT JOIN t_pnr_info p ON oc.id = p.needid
LEFT JOIN t_airline_info a ON oc.id = a.needid
WHERE
	p.PNR = @pnr