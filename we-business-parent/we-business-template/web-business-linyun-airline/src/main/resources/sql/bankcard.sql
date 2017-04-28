/*bankcard_dictinfo_list*/
SELECT
	i.id,
	i.typeCode,
	i.dictCode,
	i.dictName,
	i.description,
	i.`status`,
	i.quanPin,
	i.jianpin,
	i.createTime
FROM
	dict_info i
$condition
