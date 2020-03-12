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

CREATE TABLE `employeetasks` (
    `employeeId` INT  NOT NULL,
    `taskId` INT NOT NULL,
    PRIMARY KEY (`employeeId`, `taskId`),
    CONSTRAINT `Constr_employeetasks_employees_fk`
        FOREIGN KEY `employeeId_fk` (`employeeId`) REFERENCES `employees` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `Constr_employeetasks_tasks_fk`
        FOREIGN KEY `taskId_fk` (`taskId`) REFERENCES `tasks` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=INNODB CHARACTER SET ascii COLLATE ascii_general_ci;


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


-- SET SQL_MODE=@OLD_SQL_MODE;
-- SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
-- SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


-- ALTER TABLE `atsnovember`.`teammembers` 
-- ADD CONSTRAINT `EmployeeId`
--   FOREIGN KEY (`EmployeeId` , `TeamId`)
--   REFERENCES `atsnovember`.`employees` (`id` , `id`)
--   ON DELETE NO ACTION
--   ON UPDATE NO ACTION,
-- ADD CONSTRAINT `TeamId`
--   FOREIGN KEY (`EmployeeId` , `TeamId`)
--   REFERENCES `atsnovember`.`teams` (`id` , `id`)
--   ON DELETE NO ACTION
--   ON UPDATE NO ACTION;




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


-- GET EMPLOYEE

DELIMITER //
DROP procedure IF EXISTS spGetEmployee;
// DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `spGetEmployee`(IN idParam INT )
BEGIN
SELECT * from employees
WHERE ((idParam IS NULL AND employees.isDeleted <> true) OR id = idParam)
ORDER BY lastName;
END$$



DELIMITER //
DROP procedure IF EXISTS `spGetEmployeeDetails`;
// DELIMITER ;

DELIMITER $$
USE `atsnovember`$$
CREATE PROCEDURE `spGetEmployeeDetails` (IN id_param INT)
BEGIN

SELECT * FROM employees 
LEFT JOIN teammembers on employees.id = teammembers.EmployeeId 
LEFT JOIN teams on teams.id = teammembers.TeamId 
WHERE employees.id = id_param;

END$$



INSERT INTO employees (firstName, lastName, sin, hourlyRate, createdAt)
VALUES ('John', 'Doe', '123-456-321','34.00','2020-02-01 21:57:37');
INSERT INTO employees (firstName, lastName, sin, hourlyRate, createdAt)
VALUES ('Dave', 'Davidson', '743-832-123','42.00','2020-01-04 15:16:46');
INSERT INTO employees (firstName, lastName, sin, hourlyRate, createdAt)
VALUES ('Mike', 'Tomson', '444-555-333','44.00', now());


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
	IF idParam IS NULL THEN
		SELECT id, name, description  FROM tasks
		ORDER BY name;
    
    ELSE 
		SELECT * from tasks
        WHERE id = idParam
        order by name;
    END IF;
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
INSERT INTO `atsnovember`.`tasks` (`name`, `duration`, `description`, `createdAt`)
VALUES ('Network Security', '240', 'Network Security', now());
INSERT INTO `atsnovember`.`tasks` (`name`, `duration`, `description`, `createdAt`)
VALUES ('Mobile hardware build and repair', '120', 'Mobile hardware build and repair', now());


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

DELIMITER //
CREATE PROCEDURE `spCheckMembersSelected`(IN id_member1 INT, IN id_member2 INT)
BEGIN
SELECT teams.id, Name,  CONCAT(firstName, " ", lastName) AS FullName, EmployeeId, TeamId 
FROM teams INNER JOIN teammembers 
ON TeamId = id INNER JOIN employees 
ON employees.id = teammembers.EmployeeId
WHERE teammembers.EmployeeId IN (id_member1, id_member2) AND teams.isDeleted = false;
END 
//
DELIMITER ;

CREATE TABLE IF NOT EXISTS `atsnovember`.`jobs` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(255) NOT NULL,
  `clientName` VARCHAR(100) NOT NULL,
  `start` DATETIME NOT NULL,
  `end` DATETIME NOT NULL,
  `teamId` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id` (`id` ASC),
  INDEX `teamId_idx` (`teamId` ASC),
  CONSTRAINT `teamId`
    FOREIGN KEY (`teamId`)
    REFERENCES `atsnovember`.`teams` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS `atsnovember`.`jobstasks` (
  `taskId` INT NOT NULL,
  `jobId` INT NOT NULL,
  `operatingCost` DECIMAL(13,2) NOT NULL,
  `operatingRevenue` DECIMAL(13,2) NOT NULL,
  PRIMARY KEY (`taskId`, `jobId`),
  INDEX `jobId_idx` (`jobId` ASC),
  CONSTRAINT `taskId`
    FOREIGN KEY (`taskId`)
    REFERENCES `atsnovember`.`tasks` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `jobId`
    FOREIGN KEY (`jobId`)
    REFERENCES `atsnovember`.`jobs` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);


DELIMITER //
DROP PROCEDURE IF EXISTS spDeleteTask;
// DELIMITER ;

-- RETURNES 0 - deleted, -1 - an employee has a skill, -2 - the job has this task assigned

DELIMITER //
CREATE PROCEDURE spDeleteTask ( 
	IN taskId_param int,
	OUT code int)
BEGIN
	IF (SELECT COUNT(*) 
 		FROM employeetasks 
        WHERE taskId = taskId_param > 0)		
		THEN SET code = -1;
    
	ELSEIF (SELECT COUNT(*) 
			FROM jobstasks
            WHERE taskId = taskId_param > 0)
		THEN SET code = -2;
    
    ELSE
		DELETE FROM tasks
		WHERE id = taskId_param;
		SET code = 0;
    END IF;
    
END 
//
DELIMITER ;

CREATE TABLE IF NOT EXISTS `employeetasks` (
  `employeeId` int(11) NOT NULL,
  `taskId` int(11) NOT NULL,
  PRIMARY KEY (`employeeId`,`taskId`),
  KEY `employeeId_idx` (`employeeId`),
  CONSTRAINT `employeeId` FOREIGN KEY (`employeeId`) REFERENCES `employees` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



-- INSERT JOB 
USE `atsnovember`;
DROP procedure IF EXISTS `spInsertJob`;

DELIMITER $$
USE `atsnovember`$$
CREATE DEFINER=`dev`@`localhost` PROCEDURE `spInsertJob`(IN desc_param VARCHAR(255), IN client_param VARCHAR(255), start_param DATETIME, end_param DATETIME, team_id_param INT(11), 
cost_param DOUBLE, revenue_param DOUBLE, tasks_param VARCHAR(255), OUT id_OUT INT)
BEGIN
DECLARE delimiterCount int;
DECLARE task_id INT;
DECLARE loopCount int;

START TRANSACTION;

INSERT INTO jobs (description, clientName, start, end, teamId)
VALUES (desc_param, client_param, start_param, end_param, team_id_param);

 SET id_out = LAST_INSERT_ID();

 -- Remove space
SET tasks_param = REPLACE(tasks_param, ' ', '');
-- Get number of values without commas
 SET numTasks = LENGTH(tasks_param) - LENGTH(REPLACE(tasks_param, ',', ''));

 SET loopCount = 1;
        WHILE loopCount <= numTasks + 1 DO
            -- get number id
            SET task_id = SUBSTRING_INDEX(tasks_param, ',', 1);
            
				INSERT INTO jobstasks (taskId, jobId, operatingCost, operatingRevenue)
				VALUES (task_id, id_out, cost_param, revenue_param);
                
            /* Remove last used id with comma from input string */
            SET tasks_param = REPLACE(tasks_param, CONCAT(task_id, ','), ''); 
            SET loopCount = loopCount + 1;

        END WHILE;

 COMMIT;
 
END$$

DELIMITER ;
-- JOB DETAILS

DELIMITER //
DROP PROCEDURE IF EXISTS spGetJobDetails;
// DELIMITER ;

DELIMITER //
CREATE PROCEDURE spGetJobDetails(
	IN jobId_param INT
)
BEGIN

	SELECT jobs.id, jobs.description, clientName, 
		   start, end, 
           CONCAT(teams.Name) AS team, 
           CONCAT(tasks.name) AS task
	FROM jobs
    INNER JOIN teams
    ON jobs.teamId = teams.id
    INNER JOIN jobstasks
    ON jobs.id = jobstasks.jobId
    INNER JOIN tasks
    ON jobstasks.taskId = tasks.id
    WHERE jobs.id = jobId_param;
END //

DELIMITER ;

-- NOT WORKING
DELIMITER //
DROP PROCEDURE IF EXISTS spAddTaskToEmployee;
// DELIMITER ;

DELIMITER //
CREATE PROCEDURE spAddTaskToEmployee(
	IN employeeId_param INT,
	IN taskArray VARCHAR(255)
)
BEGIN

	START TRANSACTION;
		BEGIN 
			SET @sql = CONCAT('insert into employeetasks(employeeId, taskId) 
             VALUES(employeeId_param, ',taskArray, ')');
                
			PREPARE stmt FROM @sql; 
			EXECUTE stmt; 
			DEALLOCATE PREPARE stmt; 
		END;
    COMMIT;
END //

DELIMITER ;

-- SET @taskArray = '2,3';
-- CALL atsnovember.spAddTaskToEmployee(1, @taskArray);





