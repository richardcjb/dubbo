/*company_dicttype_list*/
SELECT
	cd.id,
	cd.comId,
	c.comName,
	cd.comTypeCode,
	cd.comTypeName,
	cd.`status`,
	cd.createTime,
	cd.updateTime,
	cd.remark
FROM
	t_company_dicttype cd
LEFT JOIN t_company c ON c.id = cd.comId
$condition

/*company_dictinfo_list*/
SELECT
	cmd.*
FROM
	t_company_dictinfo cmd
INNER JOIN t_company c ON c.id = cmd.comId
$condition

/*company_dictinfo_thirdpayment_list_data*/
SELECT
	tp.id,
	tp.comId,
	tp.userId,
	tp.comTypeCode,
	tp.comDictCode,
	tp.thirdCompanyName,
	tp.bankCardNum,
	tp.`status`,
	tp.createTime,
	tp.updateTime,
	tp.remark,
	tp.bankCardName,
	tp.res2,
	tp.res3,
	tp.res4,
	tp.res5
FROM
	t_third_payment tp
INNER JOIN t_company c ON c.id=tp.comId
$condition

/*company_select_dicttypename*/
SELECT
	cty.id,
	cty.comId,
	cty.comTypeCode,
	cty.comTypeName,
	cty.`status`,
	cty.createTime,
	cty.updateTime,
	cty.remark
FROM
	t_company_dicttype cty
LEFT JOIN t_company c ON c.id=cty.comId
$condition

/*company_dict_updateinfo*/
SELECT
	cin.id,
	cin.dictTypeId,
	cin.comId,
	cin.comTypeCode,
	cin.comDdictCode,
	cin.comDictName,
	cin.`status`,
	cin.quanPin,
	cin.jianPin,
	cin.createTime,
	cin.updateTime,
	cin.remark
FROM
	t_company_dictinfo cin
LEFT JOIN t_company c ON c.id = cin.comId
$condition

/*company_dict_loginNum_listDate*/
SELECT
	lob.id,
	lob.comId,
	dt.dictName,
	lob.comTypeCode,
	lob.comDdictCode,
	lob.webURl,
	lob.loginNumName,
	lob.airlineName,
	lob.`status`,
	lob.createTime,
	lob.updateTime,
	lob.remark
FROM
	t_login_number lob
INNER JOIN dict_info dt ON dt.dictName=lob.airlineName
$condition

/*company_dict_airlineName_select2*/
SELECT
	dt.id AS airLineId,
	dt.typeCode,
	dt.dictCode,
	dt.dictName,
	dt.description,
	dt.`status`,
	dt.quanPin,
	dt.jianpin,
	dt.createTime
FROM
	dict_info dt
$condition
)
LIMIT 0,5

/*company_dict_airlineName_update*/
SELECT
	dt.id AS dictId,
	lob.*,
	dt.typeCode,
	dt.dictCode,
	dt.dictName,
	dt.description,
	dt.`status`,
	dt.quanPin,
	dt.jianpin,
	dt.createTime
FROM
	dict_info dt
INNER JOIN t_login_number lob ON lob.comDdictCode=dt.typeCode
$condition
LIMIT 0,5