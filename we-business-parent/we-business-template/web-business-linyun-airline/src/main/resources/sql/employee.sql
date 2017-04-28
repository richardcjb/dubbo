/*employee_list*/
SELECT
	u.id AS userId,
	u.userName,
	u.fullName,
	u.telephone,
	d.id AS deptId,
	d.deptName,
	j.id AS jobId,
	j.`name` AS jobName
FROM
	t_user u
INNER JOIN t_user_job uj ON u.id = uj.userid
INNER JOIN t_company_job cj ON cj.id = uj.companyJobId
INNER JOIN t_job j ON j.id = cj.posid
INNER JOIN t_department d ON d.id = j.deptId
$condition

/*employee_area_list*/
SELECT
	ta.id AS areaId,
	ta.comId,
	ta.areaName,
	ta.createTime,
	ta.remark
FROM
	t_area ta
$condition
LIMIT 0,5

/*employee_add_data*/
SELECT
	uj.id,
	uj.userid,
	uj.companyJobId,
	uj.`status`,
	uj.hireDate,
	uj.leaveDate,
	uj.remark
FROM
	t_user_job uj
INNER JOIN t_company_job cj ON cj.id = uj.companyJobId
INNER JOIN t_company c ON c.id = cj.comId
INNER JOIN t_job j ON j.id = cj.posid
WHERE
	uj.userid =@userId
AND uj.`status` =@statusId

/*employee_update_area*/
SELECT
	a.id,
	a.comId,
	a.createTime,
	a.areaName,
	a.remark
FROM
	t_area a
INNER JOIN t_user_area_map am ON a.id = am.areaId
$condition
LIMIT 0,5

/*employee_update_data*/
SELECT
	u.id,
	u.userName,
	u.fullName,
	j.deptId,
	j.`name` AS jobName,
	am.areaId,
	a.areaName,
	u.`password`,
	u.telephone,
	u.landline,
	u.qq,
	u.email,
	u.userType,
	u.`status`,
	u.createTime,
	u.updateTime,
	u.disableStatus,
	u.remark
FROM
	t_user u
LEFT JOIN t_user_area_map am ON u.id = am.userId
LEFT JOIN t_area a ON a.id = am.areaId
LEFT JOIN t_user_job uj ON u.id = uj.userid
LEFT JOIN t_company_job cj ON cj.id = uj.companyJobId
LEFT JOIN t_job j ON j.id = cj.posid
WHERE u.id=@userId

/*employee_delete_data*/
SELECT
	u.id,
	uj.id AS userJobId,
	u.userName,
	u.`password`,
	u.telephone,
	u.landline,
	u.qq,
	u.email,
	u.userType,
	u.`status`,
	u.createTime,
	u.updateTime,
	u.disableStatus,
	u.remark
FROM
	t_user u
INNER JOIN t_user_job uj ON u.id = uj.userid
WHERE
	u.id =@userId
AND u.`status` =@userStatus

/*employee_personalInfo_list*/
SELECT
	u.id,
	u.userName,
	u.fullName,
	u.`password`,
	u.telephone,
	u.landline,
	u.qq,
	u.email,
	u.userType,
	u.`status`,
	u.createTime,
	u.updateTime,
	u.disableStatus,
	u.remark,
	d.id AS deptId,
	d.deptName,
	j.id AS jobId,
	j.`name` AS jobName,
	GROUP_CONCAT(DISTINCT a.areaName) AS areaName
FROM
	t_user u
INNER JOIN t_user_job uj ON u.id = uj.userid
INNER JOIN t_company_job cj ON cj.id = uj.companyJobId
INNER JOIN t_job j ON j.id = cj.posid
INNER JOIN t_department d ON d.id = j.deptId
INNER JOIN t_user_area_map am ON u.id = am.userId
INNER JOIN t_area a ON a.id = am.areaId
WHERE u.id =@userId

/*employee_select_dept_list*/
SELECT
	d.id,
	d.comId,
	d.deptName,
	d.remark
FROM
	t_department d
LEFT JOIN t_company c ON c.id = d.comId
$condition

/*employee_add_data_update_old_user*/
SELECT
	u.id,
	u.userName,
	u.`password`,
	u.fullName,
	u.telephone,
	u.landline,
	u.qq,
	u.email,
	u.userType,
	u.`status`,
	u.createTime,
	u.updateTime,
	u.disableStatus,
	u.remark
FROM
	t_user u
LEFT JOIN t_user_job uj ON u.id = uj.userid
LEFT JOIN t_company_job cj ON cj.id = uj.companyJobId
$condition