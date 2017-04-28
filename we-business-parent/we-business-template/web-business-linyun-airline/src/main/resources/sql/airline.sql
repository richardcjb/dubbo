/*airline_company_info*/
SELECT t.*,di2.dictCode,
		di2.dictName
FROM t_flight_info t
INNER JOIN dict_info di2
    ON t.aircomid = di2.id
 $condition
 
/*get_plan_make_info_withtime*/
select tf.*,tfi.leavetime bleavetime,tfi.backtime bbacktime
FROM
(
SELECT
t.*,f.leavetime lleavetime,f.backtime lbacktime
FROM
t_plan_info t
INNER JOIN t_flight_info f
WHERE
t.leaveairline = f.airlinenum
) tf INNER JOIN t_flight_info tfi
on tf.backairline=tfi.airlinenum
  $condition