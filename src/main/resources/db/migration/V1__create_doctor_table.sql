create table doctor
(
    id bigserial not null,
    first_name varchar not null,
    last_name varchar not null,
    date_of_birth timestamp,
    specialization varchar not null
);

create unique index table_name_id_uindex
    on doctor (id);

alter table doctor
    add constraint table_name_pk
        primary key (id);
