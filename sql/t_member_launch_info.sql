drop table if exists t_member_launch_info;

create table t_member_launch_info(
    id int auto_increment,
    member_id int comment '会员id',
    description_simple varchar(255) comment '简单介绍',
    description_detail varchar(255) comment '详细介绍',
    phone_num varchar(255) comment '联系电话',
    service_num varchar(255) comment '客服电话',
    primary key(id)
);
