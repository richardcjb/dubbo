/*login_select_company*/
SELECT 	com.id, 
	com.adminId, 
	com.comName, 
	com.comType, 
	com.remark, 
	com.connect, 
	com.mobile, 
	com.email, 
	com.phonenumber, 
	com.address, 
	com.license, 
	com.opid, 
	com.createtime, 
	com.lastupdatetime, 
	com.deletestatus
	FROM 
	t_company com,t_company_job cj,t_user_job uj
WHERE cj.comId=com.id
AND uj.companyJobId=cj.id
AND uj.userid=@userId
AND uj.status=@jobStatus
/*login_test_authority*/
select uj.*,f.url
from
t_user_job uj 
LEFT JOIN t_company_job cj on uj.companyJobId=cj.id
LEFT JOIN t_company c on c.id=cj.comId
LEFT JOIN t_company_function_map cfm on cfm.comId=c.id
LEFT JOIN t_function f on f.id=cfm.funId
$condition
GROUP BY f.url