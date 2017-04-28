/*function_manage_list*/
SELECT
  t.id,
  t.parentId,
  t.level nLevel,
  ifnull(p.name,'') parentName,
  t.name,
  ifnull(t.url,'') url,
  ifnull(t.remark,'') remark,
  t.createTime,
  t.updateTime,
  t.sort
FROM t_function t LEFT JOIN t_function p
ON t.parentId=p.id
$condition

/*function_manage_list_count*/
SELECT COUNT(*) 
FROM t_function t LEFT JOIN t_function p
ON t.parentId=p.id
$condition