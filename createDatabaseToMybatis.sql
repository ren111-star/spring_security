create table role
(
    id   bigint auto_increment
        primary key,
    name varchar(255) null,
    constraint id
        unique (id)
);

create table user
(
    id       bigint auto_increment
        primary key,
    name     varchar(255) null,
    password varchar(255) null,
    username varchar(255) null,
    constraint id
        unique (id)
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


