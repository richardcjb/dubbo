/*airlinepolicy_select2_airlinecompany*/
SELECT
	id,
	dictName,
	dictCode
FROM
	dict_info 
$condition
)
LIMIT 0,5
/*airlinepolicy_select2_area*/
SELECT
	id,
	areaName
FROM
	t_area 
$condition
LIMIT 0,5
/*airlinepolicy_select2_newarea*/
SELECT
	id,
	areaName
FROM
	t_airlinepolicy 
$condition
LIMIT 0,5
/*airlinepolicy_datalist*/
select * from(
select ap.*,(select dictCode from dict_info where id = ap.airlineCompanyId ) as 'airlineCompanyName'
from
t_airlinepolicy ap) temp
$condition