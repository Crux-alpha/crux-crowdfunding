drop table if exists t_project_type;

create table t_project_type(
    id int auto_increment,
    project_id int,
    type_id int,
    primary key(id)
);