CREATE DATABASE IF NOT EXISTS ats;

USE ats;

CREATE TABLE IF NOT EXISTS tasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    duration INT NOT NULL,
    description NVARCHAR(255) NOT NULL,
    createdAt DATETIME NOT NULL,
    updatedAt DATETIME NULL
)  ENGINE=INNODB;


CREATE TABLE IF NOT EXISTS `employees` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(255) CHARACTER SET utf8 NOT NULL,
  `lastName` varchar(255) CHARACTER SET utf8 NOT NULL,
  `sin` varchar(11) CHARACTER SET utf8 NOT NULL,
  `hourlyRate` decimal(13,2) NOT NULL,
  `isDeleted` bit(1) NOT NULL DEFAULT b'0',
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime DEFAULT NULL,
  `deletedAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


USE `ats`;
DROP procedure IF EXISTS `Employee_ADD`;

DELIMITER $$
CREATE DEFINER=`dev`@`localhost` PROCEDURE `Employee_ADD`(IN firstNameParam NVARCHAR(255), IN lastNameParam NVARCHAR(255), 
IN sinParam NVARCHAR(11), IN hourlyRateParam DECIMAL(13,2))
BEGIN
    INSERT INTO employees (firstName, lastName, sin, hourlyRate, isDeleted, createdAt, updatedAt, deletedAt) 
    values(firstNameParam,lastNameParam, sinParam, hourlyRateParam, false, now(), null, null);
END$$
DELIMITER ;


USE `ats`;
DROP procedure IF EXISTS `Employee_Lookup`;

DELIMITER $$
USE `ats`$$
CREATE PROCEDURE `Employee_Lookup` ()
BEGIN
SELECT id, firstName, lastName from employees;
END$$

DELIMITER ;
