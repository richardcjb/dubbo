/******************************************************************************
version : 1.0.0   BEGIN
******************************************************************************/ 
/*航空政策管理增加地区字段*/
ALTER TABLE `t_airlinepolicy`
ADD COLUMN `areaName`  varchar(128) NULL COMMENT '地区' AFTER `companyId`;

ALTER TABLE `t_pay`
ADD COLUMN `orderstatus`  int NULL COMMENT '订单状态' AFTER `status`;

ALTER TABLE `t_receive`
ADD COLUMN `orderstatus`  int NULL COMMENT '订单状态' AFTER `companyid`;

ALTER TABLE `t_invoice_info`
ADD COLUMN `orderpayid`  int NULL COMMENT '订单付款id' AFTER `ordertype`;

ALTER TABLE `t_invoice_info`
ADD COLUMN `orderstatus`  int NULL COMMENT '订单状态' AFTER `orderpayid`;

ALTER TABLE `t_up_order`
ADD COLUMN `airlinecom`  varchar(32) NULL COMMENT '航空公司' AFTER `companyId`,
ADD COLUMN `peoplecount`  int NULL COMMENT '人数' AFTER `airlinecom`,
ADD COLUMN `costsingleprice`  double NULL COMMENT '成本单价' AFTER `peoplecount`;

ALTER TABLE `t_pay_receive_record`
ADD COLUMN `orderstatusid`  int NULL COMMENT '订单状态id' AFTER `orderstatus`;

ALTER TABLE `t_pay_receive_record`
ADD COLUMN `actualyreduce`  int NULL COMMENT '实际减少人数' AFTER `orderstatusid`;

ALTER TABLE `t_order_customneed`
ADD COLUMN `thirdcustomid`  int NULL COMMENT '第三方支付ID' AFTER `paymethod`;

ALTER TABLE `t_up_order`
ADD COLUMN `remindTime`  datetime NULL COMMENT '提醒时间' AFTER `costsingleprice`,
ADD COLUMN `remindType`  int NULL COMMENT '提醒类型' AFTER `remindTime`;

ALTER TABLE `t_up_order`
ADD COLUMN `remark`  text NULL COMMENT '备注' AFTER `remindType`;

/*新增登录账号表*/
/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     星期三/2017/4/5 下午 3:04:26                      */
/*==============================================================*/


drop table if exists t_login_number;

/*==============================================================*/
/* Table: t_login_number                                        */
/*==============================================================*/
create table t_login_number
(
   id                   int not null auto_increment comment '主键',
   comId                int comment '公司id',
   comTypeCode          varchar(64) comment '字典类别编码',
   comDdictCode         varchar(64) comment '字典代码',
   webURl               varchar(255) comment '网站地址',
   loginNumName         varchar(64) comment '内容',
   airlineName          varchar(128) comment '航空公司',
   status               int comment '状态',
   createTime           datetime comment '创建时间',
   updateTime           datetime comment '修改时间',
   remark               text comment '备注',
   primary key (id)
);

alter table t_login_number comment '登录账号';

alter table t_login_number modify column id int comment '主键';

alter table t_login_number modify column comId int comment '公司id';

alter table t_login_number modify column comTypeCode varchar(64) comment '字典类别编码';

alter table t_login_number modify column comDdictCode varchar(64) comment '字典代码';

alter table t_login_number modify column webURl varchar(255) comment '网站地址';

alter table t_login_number modify column loginNumName varchar(64) comment '内容';

alter table t_login_number modify column airlineName varchar(128) comment '航空公司';

alter table t_login_number modify column status int comment '状态';

alter table t_login_number modify column createTime datetime comment '创建时间';

alter table t_login_number modify column updateTime datetime comment '修改时间';

alter table t_login_number modify column remark text comment '备注';

/*新增第三方支付表*/
/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     星期三/2017/4/5 下午 3:06:06                      */
/*==============================================================*/


drop table if exists t_third_payment;

/*==============================================================*/
/* Table: t_third_payment                                       */
/*==============================================================*/
create table t_third_payment
(
   id                   int not null auto_increment comment '主键',
   comId                int comment '公司id',
   userId               int comment '用户id',
   comTypeCode          varchar(64) comment '类别编码',
   comDictCode          varchar(64) comment '字典编码',
   thirdCompanyName     varchar(128) comment '第三方公司名称',
   bankCardName         varchar(128) comment '银行卡名称',
   bankCardNum          varchar(64) comment '银行卡账号',
   status               int comment '状态',
   createTime           datetime comment '创建时间',
   updateTime           datetime comment '更新时间',
   remark               text comment '备注',
   res2                 varchar(64) comment '预留字段2',
   res3                 varchar(64) comment '预留字段3',
   res4                 varchar(64) comment '预留字段4',
   res5                 varchar(64) comment '预留字段5',
   primary key (id)
);

alter table t_third_payment comment '第三方支付';

alter table t_third_payment modify column id int comment '主键';

alter table t_third_payment modify column comId int comment '公司id';

alter table t_third_payment modify column userId int comment '用户id';

alter table t_third_payment modify column comTypeCode varchar(64) comment '类别编码';

alter table t_third_payment modify column comDictCode varchar(64) comment '字典编码';

alter table t_third_payment modify column thirdCompanyName varchar(128) comment '第三方公司名称';

alter table t_third_payment modify column bankCardName varchar(128) comment '银行卡名称';

alter table t_third_payment modify column bankCardNum varchar(64) comment '银行卡账号';

alter table t_third_payment modify column status int comment '状态';

alter table t_third_payment modify column createTime datetime comment '创建时间';

alter table t_third_payment modify column updateTime datetime comment '更新时间';

alter table t_third_payment modify column remark text comment '备注';

alter table t_third_payment modify column res2 varchar(64) comment '预留字段2';

alter table t_third_payment modify column res3 varchar(64) comment '预留字段3';

alter table t_third_payment modify column res4 varchar(64) comment '预留字段4';

alter table t_third_payment modify column res5 varchar(64) comment '预留字段5';

ALTER TABLE `t_finance_info`
ADD COLUMN `enteraircom`  varchar(32) NULL COMMENT '进澳航空公司' AFTER `issuerid`,
ADD COLUMN `outaircom`  varchar(32) NULL COMMENT '出澳航空公司' AFTER `enteraircom`,
ADD COLUMN `enterstarttime`  varchar(16) NULL COMMENT '进澳出发时间' AFTER `outaircom`,
ADD COLUMN `enterarrivetime`  varchar(16) NULL COMMENT '进澳抵达时间' AFTER `enterstarttime`,
ADD COLUMN `outstarttime`  varchar(16) NULL COMMENT '出澳出发时间' AFTER `enterarrivetime`,
ADD COLUMN `outarrivetime`  varchar(16) NULL COMMENT '出澳抵达时间' AFTER `outstarttime`;
/******************************************************************************
version : 1.0.0   END   2017-04-12
******************************************************************************/ 

/******************************************************************************
version : 1.0.1   BEGIN   2017-04-13
******************************************************************************/ 
ALTER TABLE `t_message`
ADD COLUMN `upOrderStatus`  int NULL COMMENT '订单状态' AFTER `upOrderId`;

create table t_inter_message
(
   id                   int not null auto_increment comment '主键id',
   reminddate           datetime comment '提醒时间',
   remindtype           int comment '提醒类型',
   orderstatus          int comment '订单状态',
   orderid              int comment '订单ID',
   primary key (id)
);

alter table t_inter_message comment '国际消息提醒表';

ALTER TABLE `t_receive`
ADD COLUMN `bankcardnameid`  int NULL COMMENT '银行卡id' AFTER `orderstatus`;
/******************************************************************************
version : 1.0.1   END   2017-04-17
******************************************************************************/ 
/******************************************************************************
version : 1.0.2   BEGIN   2017-04-19
******************************************************************************/ 
ALTER TABLE `t_turnover`
ADD COLUMN `historymoney`  double(32,2) NULL DEFAULT NULL COMMENT '卡里历史余额' AFTER `invoiceStatus`;
ALTER TABLE `t_bankcard`
MODIFY COLUMN `initialAmount`  double(32,2) NULL DEFAULT NULL COMMENT '初始金额' AFTER `balance`;
ALTER TABLE `t_turnover`
MODIFY COLUMN `money`  double(32,2) NULL DEFAULT NULL COMMENT '金额' AFTER `averageRate`;
/******************************************************************************
version : 1.0.2   END   2017-04-20
******************************************************************************/ 

/******************************************************************************
version : 1.0.3   BEGIN   2017-04-21
******************************************************************************/
ALTER TABLE `t_visitor_info`
ADD COLUMN `num`  varchar(32) NULL COMMENT '序号' AFTER `pnrid`,
ADD COLUMN `birthday`  varchar(32) NULL COMMENT '出生日期' AFTER `num`,
ADD COLUMN `validuntil`  varchar(32) NULL COMMENT '有效期至' AFTER `birthday`;
/******************************************************************************
version : 1.0.3   END   2017-04-21
******************************************************************************/

/******************************************************************************
version : 1.0.4   BEGIN   2017-04-26
******************************************************************************/
ALTER TABLE `t_mitigate_info`
ADD COLUMN `application`  varchar(32) NULL COMMENT '用途' AFTER `ordertype`;

ALTER TABLE `t_customerneeds`
ADD COLUMN `remark`  text NULL COMMENT '备注' AFTER `companyid`;
/******************************************************************************
version : 1.0.4   END   2017-04-26
******************************************************************************/