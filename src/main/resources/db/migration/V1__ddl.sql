CREATE DATABASE  IF NOT EXISTS classificados_db
DEFAULT CHARACTER SET utf8
DEFAULT COLLATE utf8_general_ci;
USE classificados_db;

CREATE TABLE IF NOT EXISTS person (
id_person INT PRIMARY KEY AUTO_INCREMENT,
name_person VARCHAR(60) NOT NULL,
birthday DATE,
cpf VARCHAR(11),
cnpj VARCHAR(14)
) DEFAULT CHARSET=utf8
ENGINE InnoDB;

CREATE TABLE IF NOT EXISTS address(
id_address INT PRIMARY KEY AUTO_INCREMENT,
cep VARCHAR(8),
street VARCHAR(100),
number_address VARCHAR(10),
neighborhood VARCHAR(50),
complement VARCHAR(30),
city VARCHAR(50),
state VARCHAR(2)
) DEFAULT CHARSET=utf8
ENGINE InnoDB;

CREATE TABLE IF NOT EXISTS user(
id_user INT PRIMARY KEY AUTO_INCREMENT,
email VARCHAR(100),
password_user VARCHAR(60),
notice BOOLEAN
) DEFAULT CHARSET=utf8
ENGINE InnoDB;

CREATE TABLE IF NOT EXISTS ad (
id_ad INT PRIMARY KEY AUTO_INCREMENT,
ad_description text
) DEFAULT CHARSET=utf8
ENGINE InnoDB;


CREATE TABLE IF NOT EXISTS service_category (
id_service_category INT PRIMARY KEY AUTO_INCREMENT,
category_name VARCHAR(30)
) DEFAULT CHARSET=utf8
ENGINE InnoDB;

CREATE TABLE IF NOT EXISTS ad_city (
id_city INT PRIMARY KEY AUTO_INCREMENT,
city_name VARCHAR(30),
state_abb VARCHAR(2)
) DEFAULT CHARSET=utf8
ENGINE InnoDB;

CREATE TABLE IF NOT EXISTS ad_region_area (
id_area INT PRIMARY KEY AUTO_INCREMENT,
area_name VARCHAR(30)
) DEFAULT CHARSET=utf8
ENGINE InnoDB;

CREATE TABLE IF NOT EXISTS statistics (
id_statistics INT PRIMARY KEY AUTO_INCREMENT,
evaluation DECIMAL,
views_counter INT,
evaluations_counter INT,
hired_services_counter INT,
messages_counter INT
) DEFAULT CHARSET=utf8
ENGINE InnoDB;

CREATE TABLE IF NOT EXISTS provider_statistics (
id_provider_statistics INT PRIMARY KEY AUTO_INCREMENT,
visualizations INT
) DEFAULT CHARSET=utf8
ENGINE InnoDB;

CREATE TABLE IF NOT EXISTS evaluate (
id_evaluate INT PRIMARY KEY AUTO_INCREMENT,
fk_id_client INT,
fk_id_provider INT,
message_date DATE,
hired BOOLEAN,
customer_evaluatad BOOLEAN,
provider_evaluated BOOLEAN
);

ALTER TABLE `user` ADD COLUMN `fk_id_person` INT;
ALTER TABLE `user` ADD FOREIGN KEY ( `fk_id_person`) 
REFERENCES `person` ( `id_person`);

ALTER TABLE `ad` ADD COLUMN `fk_id_user` INT;
ALTER TABLE  `ad` ADD FOREIGN KEY ( `fk_id_user`) 
REFERENCES `user` ( `id_user`);

ALTER TABLE `ad_city` ADD COLUMN `fk_id_ad` INT;
ALTER TABLE  `ad_city` ADD FOREIGN KEY ( `fk_id_ad`) 
REFERENCES `ad` ( `id_ad`);

ALTER TABLE `ad_region_area` ADD COLUMN `fk_id_city` INT;
ALTER TABLE `ad_region_area` ADD FOREIGN KEY ( `fk_id_city`) 
REFERENCES `ad_city` ( `id_city`);

ALTER TABLE `provider_statistics` ADD COLUMN `fk_id_statistics` INT;
ALTER TABLE  `provider_statistics` ADD FOREIGN KEY ( `fk_id_statistics`) 
REFERENCES `statistics` ( `id_statistics`);

ALTER TABLE `provider_statistics` ADD COLUMN `fk_id_service_category` INT;
ALTER TABLE `provider_statistics` ADD FOREIGN KEY ( `fk_id_service_category`) 
REFERENCES `service_category` ( `id_service_category`);

ALTER TABLE `evaluate` ADD FOREIGN KEY ( `fk_id_client`) 
REFERENCES `user` ( `id_user`);

ALTER TABLE `address` ADD COLUMN `fk_id_person` INT;
ALTER TABLE `address` ADD FOREIGN KEY ( `fk_id_person`) 
REFERENCES `person` ( `id_person`);

ALTER TABLE `evaluate` ADD FOREIGN KEY ( `fk_id_provider`) 
REFERENCES `user` ( `id_user`);

ALTER TABLE `statistics` ADD COLUMN `fk_id_user` INT;
ALTER TABLE `statistics` ADD FOREIGN KEY ( `fk_id_user`) 
REFERENCES `user` ( `id_user`);

ALTER TABLE `ad` ADD COLUMN `fk_id_service_category` INT;
ALTER TABLE `ad` ADD FOREIGN KEY ( `fk_id_service_category`) 
REFERENCES `service_category` ( `id_service_category`);
