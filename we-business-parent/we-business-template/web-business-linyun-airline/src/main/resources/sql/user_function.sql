/*user_function_all*/
SELECT F.* FROM 
 t_function f INNER JOIN t_company_function_map cf ON f.id=cf.funId
INNER JOIN t_com_fun_pos_map cfp ON cfp.companyFunId=cf.id
INNER JOIN t_company_job cj ON cfp.jobId=cj.posid
INNER JOIN t_user_job uj ON uj.companyJobId=cj.id
WHERE uj.userid=@userId
AND uj.status=@status