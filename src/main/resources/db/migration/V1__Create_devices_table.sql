create table if not exists devices (
    id                          bigint auto_increment   primary key,
    creation_time               datetime                not null,
    updated_time                datetime                not null,
    name                        varchar(320)            not null,
    brand                       varchar(100)            not null
    -- add the following constraint to the table if needed:
    --     constraint uq_device_name unique key (name));
);
