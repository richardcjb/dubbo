/*salary_list*/
select * from(
select ts.*,date(ts.createTime) as modifyDate,year(ts.createTime) as years,month(ts.createTime) as months
from t_salary ts) ms
$condition
/*salary_drawer*/
select DISTINCT ms.drawer from(
select ts.drawer,date(ts.createTime) as modifyDate,year(ts.createTime) as years,month(ts.createTime) as months,
ts.comId
from t_salary ts 
) ms
$condition
/*salary_years*/
select DISTINCT ms.years from(
select ts.drawer,date(ts.createTime) as modifyDate,year(ts.createTime) as years,month(ts.createTime) as months
,ts.comId
from t_salary ts 
) ms
$condition
/*salary_months*/
select DISTINCT ms.months from(
select ts.drawer,date(ts.createTime) as modifyDate,year(ts.createTime) as years,month(ts.createTime) as months
,ts.comId
from t_salary ts 
) ms
$condition
/*salary_add*/
select * from (
select *,count(*) as 'groupNumber',sum(aa.cost) as 'costTotal',sum(aa.income) as 'incomeTotal',sum(aa.head) as 'headCount' from (
select  fi.costtotal as 'cost',fi.incometotal as 'income',fi.personcount as 'head',u.fullName
as 'drawer',si.baseWages as 'basePay',si.commission,si.comId,month(fi.billingdate) as 'month',year(fi.billingdate) as 'year',fi.issuerid
as 'drawerId'
from
t_user u
LEFT JOIN t_salary_increase si on si.userId=u.id
LEFT JOIN t_finance_info fi on fi.issuerid=u.id) aa

GROUP BY aa.drawerId,aa.`month`,aa.`year`) uu 
where  month=month(now()) AND year=year(now())
/*salary_add_no*/
select * from(
select u.fullName
as 'drawer',si.baseWages as 'basePay',si.commission,si.comId,month(max(fi.billingdate)) as 'month1',year(max(fi.billingdate)) as 'year1',u.id
as 'drawerId',fi.orderid,u.id,u.status
from
t_user u
LEFT JOIN t_salary_increase si on si.userId=u.id
LEFT JOIN t_finance_info fi on fi.issuerid=u.id
GROUP BY u.id
) uu
where (uu.month1 !=month(now()) Or uu.year1!=year(now()) or uu.month1  is null) and uu.status=1