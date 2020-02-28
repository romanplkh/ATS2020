DROP SCHEMA IF EXISTS atsnovember;

CREATE DATABASE IF NOT EXISTS atsnovember;

USE atsnovember;

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

DELIMITER //
DROP procedure IF EXISTS spAddEmployee;
// DELIMITER ;

DELIMITER $$
CREATE PROCEDURE spAddEmployee(
    IN firstNameParam NVARCHAR(255),
    IN lastNameParam NVARCHAR(255),
    IN sinParam NVARCHAR(11),
    IN hourlyRateParam DECIMAL(13,2),
    OUT id_out INT
    )
BEGIN
    INSERT INTO employees (firstName, lastName, sin, hourlyRate, isDeleted, createdAt, updatedAt, deletedAt) 
    values(firstNameParam,lastNameParam, sinParam, hourlyRateParam, false, now(), null, null);

     SET id_out = LAST_INSERT_ID();
END$$
DELIMITER ;

DELIMITER //
DROP procedure IF EXISTS spGetEmployeeLookup;
// DELIMITER ;

DELIMITER $$
USE `ats`$$
CREATE PROCEDURE spGetEmployeeLookup()
BEGIN
SELECT id, firstName, lastName from employees;
END$$

DELIMITER ;

-- Get a LIST of TASKS procedure
DELIMITER //
DROP PROCEDURE IF EXISTS spGetAllTasks;
// DELIMITER ;

DELIMETER //
CREATE PROCEDURE spGetTasks(
    IN idParam INT
)
BEGIN
    SELECT *  FROM tasks;
    WHERE (idParam IS NULL OR id = idParam)
    ORDER BY name;
END //

DELIMITER ;

-- CREATE TASK procedure
DELIMITER //
DROP PROCEDURE IF EXISTS spCreateTask;
// DELIMITER ;

DELIMITER //
CREATE PROCEDURE spCreateTask(
	IN taskName nvarchar(255),
    IN taskDuration int,
    IN descr nvarchar(255),
    IN created datetime,
    IN updated datetime,
    OUT id_out INT
)
BEGIN

    INSERT INTO tasks(`name`, `duration`, `description`, `createdAt`, `updatedAt`)
    VALUES(taskName, taskDuration, descr, created, null);

    SET id_out = LAST_INSERT_ID();
END //

DELIMITER ;

-- GET TASK details procedure
DELIMITER //
DROP PROCEDURE IF EXISTS spGetTaskDetails;
// DELIMITER ;

DELIMITER //
CREATE PROCEDURE spGetTaskDetails(
	IN taskId int
)
BEGIN
   SELECT id, name, description, duration
   FROM tasks
   WHERE id = taskId;
END //

DELIMITER ;