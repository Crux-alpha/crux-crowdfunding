drop table if exists t_member_confirm_info;

create table t_member_confirm_info(
    id int auto_increment,
    member_id int comment '会员id',
    pay_num varchar(200) comment '易付宝企业账号',
    card_num varchar(200) comment '法人身份证号',
    primary key(id)
);