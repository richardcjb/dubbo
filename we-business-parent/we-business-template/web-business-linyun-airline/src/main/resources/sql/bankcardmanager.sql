/*bankcardmanager_find_money*/
select bc.*
from t_bankcard bc 

$condition
order by bc.updateTime desc  

/*bankcardmanager_find_bankName*/
SELECT
	id,
	dictName
FROM
	dict_info 
$condition
LIMIT 0,5