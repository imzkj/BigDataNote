drop table if exists user_mess;

create table user_mess
(
name varchar(50),
phone varchar(10),
channel varchar(50),
ymd varchar(10),
primary key (name,phone)
);