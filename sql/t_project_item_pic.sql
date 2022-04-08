drop table if exists t_project_item_pic;

create table t_project_item_pic(
    id int auto_increment,
    project_id int,
    item_pic_path varchar(255),
    primary key(id)
);