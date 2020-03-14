create database if not exists db;

use db;

create table if not exists x_table
(
    id_bigint           bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    x_bigint            bigint,
    x_varchar_24        varchar(24),
    x_varchar_64        varchar(64),
    x_char_11_null      char(11) NULL,
    x_char_11_notnull   char(11) NOT NULL,
    x_int               int,
    x_float             float,
    x_tinyint           tinyint,
    x_datetime_lower    datetime,
    x_datetime_upper    DATETIME,
    x_bit               bit
);
