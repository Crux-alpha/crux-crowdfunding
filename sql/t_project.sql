drop table if exists t_project;

create table t_project(
    id int auto_increment,
    project_name varchar(255) comment '项目名称',
    project_description varchar(255) comment '项目描述',
    money decimal(8,2) comment '筹集金额',
    day int comment '筹集天数',
    status int(4) comment '0-即将开始;1-众筹中;2-众筹成功;3-众筹失败',
    deploy_date datetime comment '项目发起时间',
    support_money decimal(8,2) comment '已筹集金额',
    supporter int comment '支持人数',
    completion int(3) comment '百分比完成度',
    member_id int comment '发起人会员ID',
    create_date datetime comment '项目创建时间',
    follower int comment '关注人数',
    header_picture_path varchar(255) comment '头图路径',
    primary key(id)
);