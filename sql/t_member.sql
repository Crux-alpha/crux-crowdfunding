drop table if exists t_member;

create table t_member(
    id int primary key auto_increment,
    login_acct varchar(255) not null,
    user_name varchar(255),
    user_pswd char(127) not null,
    auth_status int(4) comment '实名认证状态 0-未实名认证, 1-实名认证申请中, 2-已实名认证',
    user_type int(4) comment '0-个人, 1-企业',
    real_name varchar(255),
    card_num varchar(255),
    acct_type int(4) comment '0-企业, 1-个体, 2-个人, 3-政府'
)