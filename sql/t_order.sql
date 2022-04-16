drop table if exists t_order;

create table t_order(
    id int auto_increment,
    order_num char(127) comment '订单号',
    pay_order_num char(127) comment '支付宝流水号',
    order_amount decimal(8,2) comment '订单金额',
    invoice tinyint(1) comment '是否开发票，0-不开，1-开',
    invoice_title varchar(127) comment '发票抬头',
    order_remark varchar(255) comment '订单备注',
    primary key(id)
);