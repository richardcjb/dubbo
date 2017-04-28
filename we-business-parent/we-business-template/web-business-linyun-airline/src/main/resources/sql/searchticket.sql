/*plan_ticket_list*/
SELECT
	p.id id,
	travelname,
	leavesdate,
	leaveairline,
    a.leavecity leavescity,
    a.arrvicity backscity,
	peoplecount,
	dayscount,
	unioncity,
	teamtype,
	ordernumber,
	issave,
	opid,
	companyid,
	timetype,
	starttime,
	endtime,
	isclose,
	foc,
	p.currencycode currencycode,
	p.passengertype passengertype,
	p.tickettype tickettype,
	rengetype
FROM
	t_plan_info p
INNER JOIN
	t_airline_info a
ON
	p.id=a.planid
$condition
