drop database if exists librosdb;
create database librosdb;
use librosdb;
 
create table books (
	id				int not null auto_increment primary key,
    titulo	  		varchar(50) not null,
	autor			varchar(50) not null,
	lengua			varchar(50) not null,
	edicion			varchar(50) not null,
	fedicion		varchar(50) not null,
	fimpresion		varchar(50) not null,
	editorial		varchar(50) not null,
	lastModified	timestamp
);
 
create table users (
	username			varchar(50) not null primary key,
	name				varchar(50) not null,
	email				varchar(50) not null	
);
create table reviews (
	id 						int not null auto_increment primary key,
	bookid 					int(50) not null,
	username				varchar(50) not null,
	content					varchar(500) not null,
	last_modified			timestamp,
	foreign key(bookid) references books(id),
	foreign key(username) references users(username)
	
);