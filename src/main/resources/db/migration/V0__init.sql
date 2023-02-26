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

-- DML
INSERT INTO mydb.role (id, created_date, updated_date, name) VALUES (1, '2023-02-25 11:23:23.981219', '2023-02-25 11:23:23.981219', 'ROLE_USER');
INSERT INTO mydb.role (id, created_date, updated_date, name) VALUES (2, '2023-02-25 11:23:23.984384', '2023-02-25 11:23:23.984384', 'ROLE_ADMIN');
INSERT INTO mydb.user (id, created_date, updated_date, email, password, username) VALUES (1, '2023-02-25 11:23:23.961469', '2023-02-25 11:23:23.961469', 'taekhyeon.nam@gmail.com', '$2a$10$S8zHVXwfAFBryDTq8oaTpuMbEB4vL1Qtps5nS64O0TdBDKCRoWXaG', 'taekhyeon');
INSERT INTO mydb.user (id, created_date, updated_date, email, password, username) VALUES (2, '2023-02-25 11:23:24.061224', '2023-02-25 11:23:24.061224', 'user@email.com', '$2a$10$3DjWjJVRJn4GX5/wqCSPYuIpA4qeSGoHAEVBUhcNQV3eWsBcjioSu', 'user');
INSERT INTO mydb.user_role (id, created_date, updated_date, role_id, user_id) VALUES (1, '2023-02-25 11:23:23.987371', '2023-02-25 11:23:23.987371', 1, 1);
INSERT INTO mydb.user_role (id, created_date, updated_date, role_id, user_id) VALUES (2, '2023-02-25 11:23:23.991788', '2023-02-25 11:23:23.991788', 2, 1);
INSERT INTO mydb.user_role (id, created_date, updated_date, role_id, user_id) VALUES (3, '2023-02-25 11:23:24.063851', '2023-02-25 11:23:24.063851', 1, 2);
