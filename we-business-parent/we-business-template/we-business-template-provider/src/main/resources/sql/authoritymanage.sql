/*authoritymanage_list*/
SELECT
	c.comName,
	c.createtime,
	d.id AS deptId,
	d.deptName,
	j.id AS jobId,
	GROUP_CONCAT(DISTINCT j.`name`) AS jobName,
	f.id AS functionId,
	GROUP_CONCAT(DISTINCT f.`name`) AS moduleName
FROM
	t_company c 
	LEFT JOIN t_department d ON c.id=d.comId
	LEFT JOIN t_job j ON d.id=j.deptId 
    LEFT JOIN t_com_fun_pos_map cfp ON j.id=cfp.jobId
    LEFT JOIN t_company_function_map cf ON cfp.companyFunId=cf.id
    LEFT JOIN t_function f ON cf.funId=f.id
$condition

/*authoritymanage_area_list*/
SELECT
	a.id,
	a.createTime,
	a.areaName,
	a.remark
FROM
	t_area a
INNER JOIN t_user_area_map am ON a.id=am.areaId
$condition

/*deptJob_select*/
SELECT
	tj.id AS jobId,
	tj.`name` AS jobName,
	dp.id AS deptId,
	dp.deptName
FROM
	t_job tj
INNER JOIN t_department dp ON dp.id=tj.deptId
WHERE tj.id=@jobId

/*company_function*/
SELECT
	tf.id, 
	tf.parentId, 
	tf.NAME, 
	tf.url, 
	tf.LEVEL, 
	tf.createTime, 
	tf.updateTime, 
	tf.remark, 
	tf.sort,
	tf.portrait
FROM
	t_function tf
INNER JOIN t_company_function_map cfm ON tf.id = cfm.funId
WHERE
	cfm.comId =@comId

/*job_function*/
SELECT
	tf.id, 
	tf.parentId, 
	tf.NAME, 
	tf.url, 
	tf.LEVEL, 
	tf.createTime, 
	tf.updateTime, 
	tf.remark, 
	tf.sort
FROM
t_com_fun_pos_map cfm
INNER JOIN t_company_function_map cf ON cf.id = cfm.companyFunId
INNER JOIN t_function tf ON tf.id = cf.funId
WHERE cfm.jobId=@jobId

/*authoritymanage_companyJob*/
SELECT
	j.id,
	j.createTime,
	j.deptId,
	j.remark,
	j.`name`
FROM
	t_job j
INNER JOIN t_department d ON j.deptId = d.id
INNER JOIN t_company c ON c.id = d.comId
WHERE c.id=@comId
AND j.`name`=@jobName

/*authoritymanage_companyJob_update*/
SELECT
	j.id,
	j.createTime,
	j.deptId,
	j.remark,
	j.`name`
FROM
	t_job j
INNER JOIN t_department d ON j.deptId = d.id
INNER JOIN t_company c ON c.id = d.comId
WHERE c.id=@comId
AND j.`name`=@jobName
AND j.id !=@jobId

/*authoritymanage_companyDept*/
SELECT
	d.id,
	d.deptName,
	d.comId,
	d.remark
FROM
	t_department d
INNER JOIN t_company c ON c.id = d.comId
WHERE
	c.id =@comId
AND d.deptName =@deptName

/*authoritymanage_delete_job*/
SELECT
	u.id,
	u.userName
FROM
	t_user u
INNER JOIN t_user_job uj ON u.id = uj.userid
INNER JOIN t_company_job cj ON uj.companyJobId = cj.id
WHERE
	cj.posid =@jobId
AND uj.`status` =@jobStatus
AND cj.comId=@companyId

/*authoritymanage_delete_t_job*/
delete from t_job
where deptId=@deptId

/*authoritymanage_delete_t_company_job*/
DELETE
FROM
	t_company_job
WHERE
	posid IN (
		SELECT
			id
		FROM
			t_job j
		WHERE
			j.deptId =@deptId
	)
	
/*authoritymanage_delete_t_com_fun_pos_map*/
DELETE
FROM
	t_com_fun_pos_map
WHERE
	jobId IN (
		SELECT
			id
		FROM
			t_job j
		WHERE
			j.deptId =@deptId
	)