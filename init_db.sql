create sequence generate_id;

alter sequence generate_id owner to luizalabs;


create table if not exists sending_schedule
(
    status               varchar,
    recipient            varchar,
    message              varchar,
    date_time_submission timestamp,
    id_sending_schedule  integer not null
    constraint sending_schedule_pk
    primary key,
    recipient_type       varchar
);

alter table sending_schedule
    owner to luizalabs;

