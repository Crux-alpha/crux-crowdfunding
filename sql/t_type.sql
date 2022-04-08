drop table if exists t_type;

create table t_type(
    id int auto_increment,
    name varchar(255) comment '分类名称',
    remark varchar(255) comment '分类介绍',
    primary key(id)
);