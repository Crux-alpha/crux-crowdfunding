drop table if exists t_return;

create table t_return(
    id int auto_increment,
    project_id int,
    type int(4) comment '0-实物回报,1-虚拟物品回报',
    support_money decimal(7,2) comment '支持金额',
    content varchar(255) comment '回报产品限额,0为不汇报数量',
    signal_purchase int comment '设置单笔限购',
    purchase int comment '具体限购数量',
    freight decimal(6,2) comment '运费,0表示包邮',
    invoice int(4) comment '0-不开发票,1-开发票',
    return_date int comment '项目结束后多少天向支持者发送回报',
    describ_pic_path varchar(255) comment '说明图片路径',
    primary key(id)
);