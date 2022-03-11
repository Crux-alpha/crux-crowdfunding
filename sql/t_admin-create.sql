use project_crowd;

drop table if exists t_admin;
create table t_admin(
    id int primary key auto_increment,
    login_acct varchar(255) not null,
    user_pswd char(32) not null,
    user_name varchar(255) not null,
    email varchar(255) not null,
    create_time char(19)
);