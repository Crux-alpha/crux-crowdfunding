drop table if exists inner_role_auth;

create table inner_role_auth(
    id int primary key auto_increment,
    role_id int,
    auth_id int
);