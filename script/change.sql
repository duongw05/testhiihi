drop table if exists adn_subject_other_mapping
create table dbo.adn_subject_other_mapping
(
    id                 bigint identity
        primary key,
    subject_id         bigint,
    barcode            nvarchar(20),
    object_id          bigint,
    object_code        nvarchar(50),
    type               nvarchar(20),
    status             int,
    option_value1      nvarchar(200),
    option_value2      nvarchar(200),
    option_value3      nvarchar(200),
    option_value4      nvarchar(200),
    option_value5      nvarchar(200),
    created_by         nvarchar(100),
    last_modified_by   nvarchar(100),
    created_date       datetime,
    last_modified_date datetime,
    deleted            int default 0,
    deleted_date       datetime,
    deleted_by         nvarchar(100),
);

alter table adn_subject_provisional
drop column request_code
go

alter table adn_subject_other_mapping
    add default 1 for status
go


