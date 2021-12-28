/* Setting up paymybuddy DB */
create database paymybuddy;
use paymybuddy;

create table client(
email varchar(25) PRIMARY KEY NOT NULL,
name varchar(25) NOT NULL,
password varchar(25) NOT NULL
);

create table compte(
 code_compte varchar(25) PRIMARY KEY,
 amount double,
 date_creation date NOT NULL,
 FOREIGN KEY (email_client)
 REFERENCES client(email)
 );

 create table operation(
 operation_id bigint(20) PRIMARY KEY AUTO_INCREMENT,
 type_operation varchar(1),
 amount double,
 date_operation date NOT NULL,
 description varchar(25)
 FOREIGN KEY (code_compte)
 REFERENCES compte(code_compte)
 );
 

/* Setting up TEST DB */
create database test;
use test;

create table client(
email varchar(25) PRIMARY KEY NOT NULL,
name varchar(25) NOT NULL,
password varchar(25) NOT NULL
);

create table compte(
 code_compte varchar(25) PRIMARY KEY,
 amount double,
 date_creation date NOT NULL,
 FOREIGN KEY (email_client)
 REFERENCES client(email)
 );

 create table operation(
 operation_id bigint(20) PRIMARY KEY AUTO_INCREMENT,
 type_operation varchar(1),
 amount double,
 date_operation date NOT NULL,
 description varchar(25)
 FOREIGN KEY (code_compte)
 REFERENCES compte(code_compte)
 );
