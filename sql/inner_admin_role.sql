drop table if exists inner_admin_role;

create table inner_admin_role(
    id int primary key auto_increment,
    admin_id int not null,
    role_id int not null
);