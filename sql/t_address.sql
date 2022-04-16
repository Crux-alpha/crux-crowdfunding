drop table if exists t_address;

create table t_address(
    id int auto_increment,
    receive_name varchar(64) comment '收件人',
    phone_num char(11) comment '手机号',
    address varchar(255) comment '收货地址',
    member_id int comment '用户id',
    primary key(id)
);