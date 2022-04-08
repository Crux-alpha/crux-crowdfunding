drop table if exists t_project_tag;

create table t_project_tag(
    id int auto_increment,
    project_id int,
    tag_id int,
    primary key(id)
);