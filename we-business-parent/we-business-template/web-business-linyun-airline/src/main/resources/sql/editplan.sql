/*get_editplan_info_list*/
SELECT
	tt.*, ifnull(
		tu.ordersnum,
		'无订单号'
	) dingdanhao,
	ifnull(tt.travelname,'') travelnames
FROM
	(
		SELECT
			tf.*, tfi.leavetime bleavetime,
			tfi.backtime bbacktime
		FROM
			(
				SELECT
					t.*, f.leavetime lleavetime,
					f.backtime lbacktime
				FROM
					t_plan_info t
				INNER JOIN t_flight_info f
				WHERE
					t.leaveairline = f.airlinenum
			) tf
		INNER JOIN t_flight_info tfi ON tf.backairline = tfi.airlinenum
	) tt
LEFT JOIN t_up_order_ticket tuo ON tt.id = tuo.ticketid
LEFT JOIN t_up_order tu ON tuo.orderid = tu.id
$condition

/*select_max_order_num*/
select  max(SUBSTR(ordersnum ,9,5)) maxnum,t.*
from t_up_order  t
$condition