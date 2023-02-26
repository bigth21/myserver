-- DDL
create table mydb.role
(
    id           bigint auto_increment
        primary key,
    created_date datetime(6) not null,
    updated_date datetime(6) not null,
    name         varchar(16) not null,
    constraint UNQ_Role_Name
        unique (name)
);

create table mydb.user
(
    id           bigint auto_increment
        primary key,
    created_date datetime(6)  not null,
    updated_date datetime(6)  not null,
    email        varchar(50)  not null,
    password     varchar(100) not null,
    username     varchar(20)  not null,
    constraint UNQ_User_Username
        unique (username)
);

create table mydb.user_role
(
    id           bigint auto_increment
        primary key,
    created_date datetime(6) not null,
    updated_date datetime(6) not null,
    role_id      bigint      not null,
    user_id      bigint      not null,
    constraint UNQ_UserRole_UserIdRoleId
        unique (user_id, role_id)
);
