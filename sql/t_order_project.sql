drop table if exists t_order_project;

create table t_order_project(
    id int auto_increment,
    project_name varchar(255) comment '项目名称',
    launch_name varchar(127) comment '发起人',
    return_content varchar(255) comment '回报内容',
    return_count int comment '回报数量',
    support_price decimal(7,2) comment '支持单价',
    freight decimal(5,2) comment '配送费用',
    order_id int comment '订单表id',
    primary key(id)
);