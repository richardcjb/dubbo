/*company_list*/
SELECT
	t.id,
	t.adminId,
	t.comName,
	CASE
WHEN comType = 1 THEN
	'上游公司'
WHEN comType = 2 THEN
	'代理商'
ELSE
	'其他'
END comType,
 t.remark,
 t.connect,
 t.mobile,
 t.email,
 t.phonenumber,
 t.address,
 t.license,
 t.opid,
 t.createtime,
 t.lastupdatetime,
 t.deletestatus,
 IFNULL(t1.countuser, 0) renshu
FROM
	t_company t
LEFT JOIN (
	SELECT
		count(tu.id) - 1 countuser,
		tcj.comId
	FROM
		t_company_job tcj,
		t_user_job tuj,
		t_user tu
	WHERE
		tcj.id = tuj.companyJobId
	AND tu.id = tuj.userid
	AND tu. STATUS = 1
	GROUP BY
		tcj.comId
) t1 ON t1.comId = t.id			
$condition
/*company_list_count*/
select count(*)
from t_company t left join (select count(tuj.id) countuser,tcj.comId  
										from t_company_job tcj,t_user_job tuj 
										where tcj.id = tuj.companyJobId GROUP BY tcj.comId) t1 
				on t1.comId = t.id
$condition

/*company_user_info_list*/
select tu.*,tcj.comId,tuj.id ujid,tj.`name` zhiwei,td.deptName department
from 
	 t_user tu right join t_user_job tuj on tuj.userid = tu.id 
		left join t_company_job  tcj  on tcj.id = tuj.companyJobId 
		left join t_job tj on tcj.posid = tj.id 
		left join 	t_department td on tj.deptId = td.id
$condition
/*company_department_list*/
select td.*
from t_department td LEFT JOIN t_company tc on td.comId = tc.id
$condition
