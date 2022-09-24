create table hibernate_sequence
(
    next_val bigint null
);

create table role
(
    id   bigint       not null
        primary key,
    name varchar(255) null
);

create table user
(
    id       bigint       not null
        primary key,
    name     varchar(255) null,
    password varchar(255) null,
    username varchar(255) null
);

create table user_roles
(
    user_id  bigint not null,
    roles_id bigint not null,
    constraint FK55itppkw3i07do3h7qoclqd4k
        foreign key (user_id) references user (id),
    constraint FKj9553ass9uctjrmh0gkqsmv0d
        foreign key (roles_id) references role (id)
);


