/*dict_type_list*/
SELECT
t.id,
t.typeCode,
t.typeName,
t.description,
t.`status`,
t.createTime,
i.dictCode,
i.dictName,
i.quanPin,
i.jianpin
FROM dict_type t INNER JOIN dict_info i
ON i.`typeCode`=t.`typeCode`
$condition