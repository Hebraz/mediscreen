DROP DATABASE IF EXISTS mediscreentest ;
CREATE DATABASE mediscreentest;

use mediscreentest;

CREATE TABLE `person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `family_name` varchar(50) NOT NULL,
  `given_name` varchar(50) NOT NULL,
  `birthdate` DATE,
  `address` varchar(200),
  `phone` varchar(30),
  `sex`  varchar(11),
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;