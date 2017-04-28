/*turnoverlist_list*/
select tu.*,tb.balance,tb.currency as curren,DATE_FORMAT(tradeDate, '%Y-%c-%d') as modifyDate,tb.companyId,tb.cardName
from t_turnover tu 
left join t_bankcard tb
on tu.bankCardId=tb.id
$condition
order by updateTime DESC

/*turnoverlist_select2_comName*/
SELECT
	c.id,
	c.comName
FROM
	t_company c
$condition
LIMIT 0,5