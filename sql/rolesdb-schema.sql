drop database if exists rolesdb;
create database rolesdb;
 
use rolesdb;
 
create table users (
username		varchar(50) not null primary key,
userpass		char(50) not null,
name			varchar(50) not null,
email			varchar(100) not null
);
 
create table user_roles (
username		varchar(20) not null,
rolename 		varchar(20) not null,
foreign key(username) references users(username) on delete cascade,
primary key (username, rolename)
);