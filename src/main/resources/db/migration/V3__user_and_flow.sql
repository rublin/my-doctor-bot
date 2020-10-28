create table system_user
(
    id bigserial unique not null,
    telegram_id int unique not null,
    step        varchar(50),
    first_name  varchar(20),
    last_name   varchar(20)
);
