drop table if exists t_tag;

create table t_tag(
    id int auto_increment,
    pid int,
    name varchar(255),
    primary key(id)
);