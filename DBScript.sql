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



-- GET EMPLOYEE DETAILS
USE `atsnovember`;
DROP procedure IF EXISTS `spGetEmployeeDetails`;

DELIMITER $$
USE `atsnovember`$$
CREATE PROCEDURE `spGetEmployeeDetails`(IN id_param INT)
BEGIN

SELECT e.id, e.firstName, e.lastName, e.sin, e.hourlyRate, e.isDeleted, e.createdAt, e.updatedAt, e.deletedAt, teams.Name, CONCAT(tasks.name) AS TaskName  FROM employees e
LEFT JOIN teammembers on e.id = teammembers.EmployeeId 
LEFT JOIN teams on teams.id = teammembers.TeamId 
LEFT JOIN employeetasks ON employeetasks.employeeId = e.id
LEFT JOIN tasks on employeetasks.taskId = tasks.id
WHERE e.id = id_param;

END$$

DELIMITER ;




-- GET EMPLOYEE WITH SKILLS
USE `atsnovember`;
DROP procedure IF EXISTS `spGetEmployeeWithSkills`;

DELIMITER $$
USE `atsnovember`$$
CREATE PROCEDURE `spGetEmployeeWithSkills` (IN empId_param INT)
BEGIN
SELECT e.id, e.firstName, e.lastName, e.sin, e.hourlyRate, e.isDeleted, e.createdAt, e.updatedAt, e.deletedAt, CONCAT(tasks.id) AS skillId, name  FROM employees e
LEFT JOIN employeetasks ON employeetasks.employeeId = e.id
LEFT JOIN tasks on employeetasks.taskId = tasks.id
WHERE e.id = empId_param;
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


-- CREATE TEAM ------
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

-- DELETE TASK -------
DELIMITER //
DROP PROCEDURE IF EXISTS spDeleteTask;
// DELIMITER ;

-- RETURN CODES:
-- 0 - deleted
-- -1 - an employee has a skill
-- -2 - the job has this task assigned

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
  CONSTRAINT `employeeId` FOREIGN KEY (`employeeId`) 
  REFERENCES `employees` (`id`) 
  ON DELETE NO ACTION 
  ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



-- INSERT JOB OLD WHERE TOTAL COST AND REVENUE ARE INSERTED--------
-- DELIMITER //
-- DROP procedure IF EXISTS `spInsertJob`;
-- DELIMITER ;

-- DELIMITER $$
-- CREATE PROCEDURE `spInsertJob`(IN desc_param VARCHAR(255), 
-- IN client_param VARCHAR(255), 
-- start_param DATETIME, 
-- end_param DATETIME, 
-- team_id_param INT(11), 
-- cost_param DOUBLE, 
-- revenue_param DOUBLE, 
-- tasks_param VARCHAR(255), OUT id_OUT INT)

-- BEGIN
-- DECLARE numTasks int;
-- DECLARE task_id INT;
-- DECLARE loopCount int;

-- START TRANSACTION;

-- INSERT INTO jobs (description, clientName, start, end, teamId)
-- VALUES (desc_param, client_param, start_param, end_param, team_id_param);

--  SET id_out = LAST_INSERT_ID();

--  -- Remove space
-- SET tasks_param = REPLACE(tasks_param, ' ', '');
-- -- Get number of values without commas
--  SET numTasks = LENGTH(tasks_param) - LENGTH(REPLACE(tasks_param, ',', ''));

--  SET loopCount = 1;
--         WHILE loopCount <= numTasks + 1 DO
--             -- get number id
--             SET task_id = SUBSTRING_INDEX(tasks_param, ',', 1);
            
-- 				INSERT INTO jobstasks (taskId, jobId, operatingCost, operatingRevenue)
-- 				VALUES (task_id, id_out, cost_param, revenue_param);
                
--             /* Remove last used id with comma from input string */
--             SET tasks_param = REPLACE(tasks_param, CONCAT(task_id, ','), ''); 
--             SET loopCount = loopCount + 1;

--         END WHILE;

--  COMMIT;
 
-- END$$
-- DELIMITER ;



DELIMITER //
DROP procedure IF EXISTS `spInsertJob`;
DELIMITER ;
-- INSERT JOB NEW WITH SEPARATED COST AND REVENUE
DELIMITER $$
CREATE PROCEDURE `spInsertJob`(IN desc_param VARCHAR(255), 
IN client_param VARCHAR(255), 
start_param DATETIME, 
end_param DATETIME, 
team_id_param INT(11), 
cost_param VARCHAR(255), 
revenue_param VARCHAR(255), 
tasks_param VARCHAR(255), OUT id_OUT INT)

BEGIN
DECLARE numTasks int;
DECLARE task_id INT;
DECLARE cost_value DOUBLE;
DECLARE revenue_value DOUBLE;
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
            -- get task id
            SET task_id = SUBSTRING_INDEX(tasks_param, ',', 1);
            -- get cost value
            SET cost_value = SUBSTRING_INDEX(cost_param, ',', 1);
            -- get revenue value
            SET revenue_value = SUBSTRING_INDEX(revenue_param, ',', 1);
            
				INSERT INTO jobstasks (taskId, jobId, operatingCost, operatingRevenue)
				VALUES (task_id, id_out, cost_value, revenue_value);
                
            /* Remove last used id with comma from input string */
            SET tasks_param = REPLACE(tasks_param, CONCAT(task_id, ','), ''); 
            SET cost_param = replace(cost_param, CONCAT(cost_value, ','), '');
            SET revenue_param = replace(revenue_param, CONCAT(revenue_value, ','), '');
            SET loopCount = loopCount + 1;
        END WHILE;

 COMMIT;
 
END$$
DELIMITER ;

-- GET JOB DETAILS ------
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

-- ASSIGN TASKS TO EMPLOYEE --------
DELIMITER //
DROP PROCEDURE IF EXISTS spAddTaskToEmployee;
// DELIMITER ;

DELIMITER //
CREATE PROCEDURE spAddTaskToEmployee(
	IN employeeId_param INT,
	IN taskIdArray_param VARCHAR(255)
)
BEGIN

DECLARE numTasks int;
DECLARE task_id INT;
DECLARE loopCount int;

 START TRANSACTION;
    
 SET taskIdArray_param = REPLACE(taskIdArray_param, ' ', '');   
 -- Get number of values without commas
 SET numTasks = LENGTH(taskIdArray_param) - LENGTH(REPLACE(taskIdArray_param, ',', ''));

	BEGIN 
		SET loopCount = 1;
        WHILE loopCount <= numTasks + 1 DO
            -- get number id
            SET task_id = SUBSTRING_INDEX(taskIdArray_param, ',', 1);
            
				INSERT INTO employeetasks (employeeId, taskId)
				VALUES (employeeId_param, task_id);
                
            /* Remove last used id with comma from input string */
            SET taskIdArray_param = REPLACE(taskIdArray_param, CONCAT(task_id, ','), ''); 
            SET loopCount = loopCount + 1;

        END WHILE;
	END;	
    
    COMMIT;
	
END; //
DELIMITER ;


-- DELETE EMPLOYEE 

-- 0 - deleted

USE `atsnovember`;
DROP procedure IF EXISTS `spRemoveEmployee`;

DELIMITER $$
USE `atsnovember`$$
CREATE DEFINER=`dev`@`localhost` PROCEDURE `spRemoveEmployee`(IN id_param INT, OUT result INT)
BEGIN 
    IF ((SELECT COUNT(*) FROM teammembers WHERE EmployeeId = id_param) > 0)
		THEN UPDATE employees SET isDeleted = true, deletedAt = now() WHERE id = id_param;
     SET result = 0;
	ELSE 
		DELETE FROM employees WHERE id = id_param;
     SET result = 0;
	END IF;   
END$$

DELIMITER ;


-- DELETE SKILL FROM EMPLOYEE

/*
 NOT RUBUST BUT ALSO DO THE TRICK
  SELECT product_id, product_price
  FROM product
  WHERE FIND_IN_SET(product_type, param);

*/
USE `atsnovember`;
DROP procedure IF EXISTS `spRemoveEmployeeSkill`;


-- FLAG FOR MERGE
DELIMITER $$
USE `atsnovember`$$
CREATE PROCEDURE PROCEDURE `spRemoveEmployeeSkill`(IN id_param INT, IN idsSkill_param VARCHAR(255), OUT affected_out INT)
BEGIN

DECLARE numSkills INT;
DECLARE loopCount INT;
DECLARE curSkillId INT;
DECLARE rowsAffected INT;

START TRANSACTION;
SET rowsAffected = 0;
SET affected_out = 0;
SET numSkills = LENGTH(idsSkill_param) - LENGTH(REPLACE(idsSkill_param, ',', ''));

 SET loopCount = 1;
	 WHILE loopCount <= numSkills + 1 DO
     SET curSkillId = SUBSTRING_INDEX(idsSkill_param, ',', 1);
     DELETE FROM employeetasks WHERE employeeId = id_param AND taskId = curSkillId;
     SET idsSkill_param = REPLACE(idsSkill_param, CONCAT(curSkillId, ','), '');
     SET loopCount = loopCount + 1;
     END WHILE;
     
     SET rowsAffected = (SELECT row_count());
     IF (rowsAffected > 0) THEN 
		SET affected_out = 1;
	END IF;
COMMIT;

  
END$$

DELIMITER ;







INSERT INTO employees (firstName, lastName, sin, hourlyRate, createdAt)
VALUES ('John', 'Doe', '123-456-321','34.00','2020-02-01 21:57:37');
INSERT INTO employees (firstName, lastName, sin, hourlyRate, createdAt)
VALUES ('Dave', 'Davidson', '743-832-123','42.00','2020-01-04 15:16:46');
INSERT INTO employees (firstName, lastName, sin, hourlyRate, createdAt)
VALUES ('Mike', 'Tomson', '444-555-333','44.00', now());





-- SET @taskArray = '2,3';
-- CALL atsnovember.spAddTaskToEmployee(1, @taskArray);





