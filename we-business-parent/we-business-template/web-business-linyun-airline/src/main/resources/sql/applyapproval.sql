/*applyapproval_list*/
select * from(
select uo.orderstype,ti.id,ti.PNR,ti.orderPnrStatus,ti.costpricesum,uo.ordersnum,ti.optime,p.purpose,

p.proposer,ci.shortName,p.fundType,p.payFees,p.payCurrency,p.isInvioce,p.approveTime,p.approveResult,p.id as 'usingId',uo.id as 'orderId',
u.fullName,(select dictCode from dict_info where id=p.payCurrency) as 'currencyStr',(select comDictName from t_company_dictinfo where id=p.purpose) as 'purposeStr',
(select comDictName from t_company_dictinfo where id=p.fundType) as 'fundTypeStr',p.companyId,u.id as 'userId'

FROM t_pnr_info  ti
LEFT JOIN t_order_customneed oc on ti.needid=oc.id
LEFT JOIN t_up_order uo on oc.ordernum =uo.id
LEFT JOIN (
			SELECT
				paypnr1.*
			FROM
				t_pay_pnr paypnr1,
				(
					SELECT
						max(optime) optime,
						id
					FROM
						t_pay_pnr
					GROUP BY
						pnrId
				) paypnr
			WHERE
				paypnr1.id = paypnr.id
		) pp on pp.pnrId=ti.id
LEFT JOIN t_pay p on p.id = pp.payId
LEFT JOIN t_customer_info ci on ci.id=uo.userid
LEFT JOIN t_user u on u.id=p.proposer
) temp
$condition
order by optime desc
/*applyapproval_list_international*/
select * from(
select  uo.orderstype,uo.ordersnum,p.purpose,
p.proposer,ci.shortName,p.fundType,p.payFees,p.payCurrency,p.isInvioce,p.approveTime,p.approveResult,p.id as 'usingId',p.id,uo.id as 'orderId',
u.fullName,(select dictCode from dict_info where id=p.payCurrency) as 'currencyStr',(select comDictName from t_company_dictinfo  where id=p.purpose) as 'purposeStr',
(select comDictName from t_company_dictinfo  where id=p.fundType) as 'fundTypeStr',p.companyId,po.paystauts as 'paystatus',prr.currentpay as 'amount',po.payDate as 'orderstime',prr.orderstatusid,prr.orderstatus
,po.id as 'resultId',u.id as 'userId'
from 
t_pay_receive_record prr
LEFT JOIN 
t_up_order uo on prr.orderid=uo.id and prr.recordtype =@recordtype
LEFT JOIN t_pay_order po on po.orderid=uo.id and prr.orderstatusid=po.orderstatus
LEFT JOIN t_pay p on p.id=po.payid
LEFT JOIN t_customer_info ci on ci.id=uo.userid
LEFT JOIN t_user u on u.id=p.proposer
) temp1
$condition
ORDER BY orderstime desc

/*applyapproval_message_reminder*/
select uo.id,uo.ordersnum
from 
t_pnr_info pi 
LEFT JOIN t_order_customneed oc on oc.id =pi.needid
LEFT JOIN t_up_order uo on uo.id=oc.ordernum
$condition
/*applyapproval_reduce_inland*/
select mi.*,u.fullName,uo.ordersnum,u.id as 'userId',mi.application as 'purposeStr'
from 
t_mitigate_info mi 
LEFT JOIN t_up_order uo on mi.orderid=uo.id
LEFT JOIN t_user u on mi.applyid=u.id
$condition 
ORDER BY orderstime desc





