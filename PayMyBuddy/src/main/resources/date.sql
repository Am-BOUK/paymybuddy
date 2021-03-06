--
-- CREATION de la Base de données : `paymybuddy`
--

-- --------------------------------------------------------
create database paymybuddy;
use paymybuddy;

-- --------------------------------------------------------
--
-- CREATION de la table `client`
--
create table client(
ID_CLIENT bigint(20) PRIMARY KEY  AUTO_INCREMENT,
EMAIL varchar(20) UNIQUE NOT NULL,
FIRST_NAME varchar(25) NOT NULL,
LAST_NAME varchar(25) NOT NULL,
PASSWORD varchar(255) NOT NULL
);

-- --------------------------------------------------------
--
-- CREATION de la table `compte`
--
create table compte(
 ID_COMPTE bigint(20) PRIMARY KEY AUTO_INCREMENT,
 ID_CLIENT bigint(20) NOT NULL,
 AMOUNT double,
 DATE_CREATION DATETIME NOT NULL,
 FOREIGN KEY (ID_CLIENT)
 REFERENCES client(ID_CLIENT)
);

-- --------------------------------------------------------
--
-- CREATION de la table `operation`
--
create table operation(
 ID_OPERATION bigint(20) PRIMARY KEY AUTO_INCREMENT,
 ID_COMPTE bigint(20) NOT NULL,
 TYPE_OP varchar(3) NOT NULL,
 AMOUNT double NOT NULL,
 DATE_OPERATION DATETIME NOT NULL,
 DESCRIPTION varchar(255) NOT NULL,
 FACTURATION double DEFAULT NULL,
 ID_SENDER bigint(20) DEFAULT NULL,
 NAME_SENDER varchar(25) DEFAULT NULL,
 ID_RECIPIENT bigint(20) DEFAULT NULL,
 NAME_RECIPIENT varchar(25) DEFAULT NULL,
 FOREIGN KEY (ID_COMPTE)
 REFERENCES compte(ID_COMPTE)
);

-- --------------------------------------------------------
--
-- CREATION de la table `tbl_connections`
--
create table tbl_connections(
CLIENT_ID bigint(20),
CONNECTION_ID bigint(20),
FOREIGN KEY (CLIENT_ID)
 REFERENCES client(ID_CLIENT),
FOREIGN KEY (CONNECTION_ID)
 REFERENCES compte(ID_CLIENT)
);

-- --------------------------------------------------------
--
-- chargement des données dans la table `client`
-- password amal@gmail.com : amal123
-- password rostow@gmail.com : rostow123
--
insert into client(ID_CLIENT,EMAIL,FIRST_NAME, LAST_NAME, PASSWORD) 
values
(1,'amal@gmail.com','Amal','BOUKILI','$2a$12$JgjIRWRBSeqml5saEyiKbO5muSSUzAPSKVP1E7mgJ1SXZxaTvCKBG'),
(2,'rostow@gmail.com','Rostow','GOKENG','$2a$12$mXSX8.8m2D0yi4fEUcsWE.Pigzqp1DC0lkpiKmw3JuLA2ZL4RX.Au');

-- --------------------------------------------------------
--
-- chargement des données dans la table `compte`
--
insert into compte(ID_COMPTE,ID_CLIENT,AMOUNT, DATE_CREATION) 
values
(3,1,600,'2021-12-25 13:07:46'),
(4,2,350,'2021-06-25 13:07:46');

-- --------------------------------------------------------
--
-- chargement des données dans la table `operation`
--
insert into operation(ID_OPERATION,ID_COMPTE,AMOUNT,TYPE_OP,DATE_OPERATION,DESCRIPTION, FACTURATION) 
values
(5,3,9000,'V','2021-12-30 13:07:46','Versement', 9000*0.5/100),
(6,3,100,'R','2021-12-30 14:07:46','Retrait', 100*0.5/100),
(7,4,7000,'V','2022-01-01 13:07:46','Versement', 7000*0.5/100),
(8,4,100,'R','2022-01-01 14:07:46','Retrait', 100*0.5/100);
insert into operation(ID_OPERATION,ID_COMPTE,AMOUNT,TYPE_OP,DATE_OPERATION,DESCRIPTION, FACTURATION, ID_SENDER, NAME_SENDER,ID_RECIPIENT,NAME_RECIPIENT ) 
values
(9,3,300,'S','2022-01-02 14:07:46','Virement envoyé', 300*0.5/100, 3, 'BOUKILI', 4,'GOKENG'),
(10,3,300,'E','2022-01-02 14:07:46','Virement reçu', 0, 4, 'GOKENG', 3,'BOUKILI'),
(11,4,500,'S','2022-01-05 14:07:46','Virement envoyé', 500*0.5/100, 4, 'GOKENG', 3,'BOUKILI'),
(12,4,500,'E','2022-01-05 14:07:46','Virement reçu', 0,3,'BOUKILI', 4,'GOKENG');

-- --------------------------------------------------------
--
-- chargement des données dans la table `tbl_connections`
--
insert into tbl_connections(CLIENT_ID,CONNECTION_ID) 
values
(1,2),
(2,1);
