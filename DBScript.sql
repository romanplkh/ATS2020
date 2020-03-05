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


CREATE TABLE IF NOT EXISTS teams (
	id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Name nvarchar(255) NOT NULL,
    isOnCall bit DEFAULT false NOT NULL,
    isDeleted bit DEFAULT false NOT NULL,
    createdAt datetime NOT NULL,
    updatedAt datetime DEFAULT NULL,
    deletedAt datetime DEFAULT NULL
)ENGINE=InnoDB;


-- -----------------------------------------------------
-- Table `atsnovember`.`teammembers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `atsnovember`.`teammembers` (
  `EmployeeId` INT(11) NOT NULL,
  `TeamId` INT(11) NOT NULL,
  PRIMARY KEY (`EmployeeId`, `TeamId`),
  CONSTRAINT `fk_employees_has_teams_employees`
    FOREIGN KEY (`EmployeeId`)
    REFERENCES `atsnovember`.`employees` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_employees_has_teams`
    FOREIGN KEY (`TeamId`)
    REFERENCES `atsnovember`.`teams` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


ALTER TABLE `atsnovember`.`teammembers` 
ADD CONSTRAINT `EmployeeId`
  FOREIGN KEY (`EmployeeId` , `TeamId`)
  REFERENCES `atsnovember`.`employees` (`id` , `id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `TeamId`
  FOREIGN KEY (`EmployeeId` , `TeamId`)
  REFERENCES `atsnovember`.`teams` (`id` , `id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;




-- ADD EMPLOYEE

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
DROP procedure IF EXISTS spGetEmployee;
// DELIMITER ;


-- GET EMPLOYEE

DELIMITER $$
CREATE PROCEDURE `spGetEmployee`(IN idParam INT )
BEGIN
SELECT * from employees
WHERE ((idParam IS NULL AND employees.isDeleted <> true) OR id = idParam);
END$$

DELIMITER ;

DELIMITER //
DROP procedure IF EXISTS `spGetEmployeeDetails`;
// DELIMITER ;

DELIMITER $$
USE `atsnovember`$$
CREATE PROCEDURE `spGetEmployeeDetails` (IN id_param INT)
BEGIN

SELECT * FROM employees LEFT JOIN teammembers on employees.id = teammembers.EmployeeId LEFT JOIN teams on teams.id = teammembers.TeamId WHERE employees.id = id_param;

END$$
DELIMITER ;

-- Get a LIST of TASKS procedure or
-- one task details if Id is provided
DELIMITER //
DROP PROCEDURE IF EXISTS spGetTasks;
// DELIMITER ;

DELIMITER //
CREATE PROCEDURE spGetTasks(
    IN idParam INT
)
BEGIN
    SELECT *  FROM tasks
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

INSERT INTO `atsnovember`.`tasks` (`name`, `duration`, `description`, `createdAt`)
VALUES ('Network Design', '45', 'Design network infrastructure', '2020-03-01');
INSERT INTO `atsnovember`.`tasks` (`name`, `duration`, `description`, `createdAt`)
VALUES ('Router Configuration', '60', 'Configure routers', '2020-03-01');


-- TEAMS
DELIMITER //
DROP PROCEDURE IF EXISTS spCreateTeam;
// DELIMITER ;

DELIMITER //
CREATE PROCEDURE spCreateTeam(
	IN teamName varchar(255),
    IN created datetime,
    IN member1Id INT,
    IN member2Id INT,
    OUT id_out INT
)
BEGIN
START TRANSACTION;
 
-- 1. insert a new team
INSERT INTO teams (Name, isOnCall, isDeleted, createdAt, updatedAt, deletedAt)
	   VALUES(teamName, b'0', b'0', created, null, null);
       
       SET id_out = LAST_INSERT_ID();
        
-- 2. Insert team members in TeamMembers table
INSERT INTO teammembers (EmployeeId,
                         TeamId)
VALUES(member1Id, id_out),
      (member2Id, id_out); 
      
-- 3. commit changes    
COMMIT;

END //
DELIMITER ;

-- VALIDATE AVAILABILITY EMPLOYEES TO BE ADDED TO TEAM
DELIMITER //
DROP PROCEDURE IF EXISTS spCheckMembersSelected;
// DELIMITER ;

CREATE PROCEDURE `spCheckMembersSelected`(IN id_member1 INT, IN id_member2 INT)
BEGIN
SELECT teams.id, Name,  CONCAT(firstName, " ", lastName) AS FullName, EmployeeId, TeamId 
FROM teams INNER JOIN teammembers 
ON TeamId = id INNER JOIN employees 
ON employees.id = teammembers.EmployeeId
WHERE teammembers.EmployeeId IN (id_member1, id_member2) AND teams.isDeleted = false
END //
DELIMITER ;