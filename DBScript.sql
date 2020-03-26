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

DROP procedure IF EXISTS `spGetEmployeeWithSkills`;

DELIMITER $$

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
INSERT INTO `atsnovember`.`tasks` (`name`, `duration`, `description`, `createdAt`) 
VALUES ('Rack mount server install', '30', 'Rack mount server install', '2020-03-22');
INSERT INTO `atsnovember`.`tasks` (`name`, `duration`, `description`, `createdAt`) 
VALUES ('Install Red Hat Linux', '120', 'Install Red Hat Linux', '2020-03-22');
INSERT INTO `atsnovember`.`tasks` (`name`, `duration`, `description`, `createdAt`) 
VALUES ('Install Cat5e cabling', '60', 'Install Cat5e cabling', '2020-03-22');
INSERT INTO `atsnovember`.`tasks` (`name`, `duration`, `description`, `createdAt`) 
VALUES ('Switch Installation', '60', 'Switch Installation', '2020-03-22');
INSERT INTO `atsnovember`.`tasks` (`name`, `duration`, `description`, `createdAt`) 
VALUES ('Install Cisco Switch', '60', 'Install Cisco Switch', '2020-03-22');


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




END


-- DELETE BELOW IF WORKING


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
tasks_param VARCHAR(255), OUT id_OUT_param INT)

BEGIN
DECLARE numTasks int;
DECLARE task_id INT;
DECLARE cost_value DOUBLE;
DECLARE revenue_value DOUBLE;
DECLARE loopCount int;
DECLARE id_out INT;

START TRANSACTION;

INSERT INTO jobs (description, clientName, start, end, teamId)
VALUES (desc_param, client_param, start_param, end_param, team_id_param);

SET id_out = LAST_INSERT_ID();
SET id_OUT_param = id_out;


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
            -- if(length(SUBSTRING_INDEX(cost_value, '.', -1))  = 1) THEN
				-- SET cost_value  = REPLACE(cost_value, CONCAT('.', '0'), '');
			-- end if;
                
            -- get revenue value
            SET revenue_value = SUBSTRING_INDEX(revenue_param, ',', 1);
        -- if(length(SUBSTRING_INDEX(revenue_value, '.', -1))  = 1) THEN
		-- SET revenue_value  = REPLACE(revenue_value, CONCAT('.', '0'), '');
		-- end if;
            
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
	IN taskIdArray_param VARCHAR(255),
    OUT rows_aff INT
)
BEGIN

DECLARE numTasks int;
DECLARE task_id INT;
DECLARE loopCount int;
DECLARE rowsAffected INT;

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
        SET rowsAffected = (SELECT row_count());
		IF (rowsAffected > 0) THEN 
		SET rows_aff = 1;
		END IF;
	END;	
    
    COMMIT;
	
END; //
DELIMITER ;


-- DELETE EMPLOYEE 

-- 0 - deleted
DELIMITER //
DROP procedure IF EXISTS `spRemoveEmployee`;
DELIMITER ;

DELIMITER $$

CREATE PROCEDURE `spRemoveEmployee`(IN id_param INT, OUT result INT)
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
DELIMITER //
DROP procedure IF EXISTS `spRemoveEmployeeSkill`;
DELIMITER ;

-- FLAG FOR MERGE
DELIMITER $$
CREATE PROCEDURE `spRemoveEmployeeSkill`(
IN id_param INT, 
IN idsSkill_param VARCHAR(255), 
OUT affected_out INT)
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

-- UPDATE SKILLS EMPLOYEE
DELIMITER //
DROP PROCEDURE IF EXISTS spUpdateEmployeeSkills;
// DELIMITER ;

DELIMITER //
CREATE PROCEDURE spUpdateEmployeeSkills(
IN empId_param INT, 
IN skillsDelete_param VARCHAR(255), 
IN skillsAdd_param VARCHAR(255), 
OUT affected_out INT)
	BEGIN
		START TRANSACTION;
			BEGIN
				CALL spRemoveEmployeeSkill(empId_param, skillsDelete_param, @numDeleted);
				SELECT @numDeleted;
		
				CALL spAddTaskToEmployee(empId_param, skillsAdd_param, @numAdded);
                SELECT @numAdded;
                
                 IF (@numDeleted > 0 AND @numAdded > 0) THEN 
					SET affected_out = 1;
				END IF;
        
        COMMIT;
        END;
	END;
//
DELIMITER ;




INSERT INTO employees (firstName, lastName, sin, hourlyRate, createdAt)
VALUES ('John', 'Doe', '123-456-321','34.00','2020-02-01 21:57:37');
INSERT INTO employees (firstName, lastName, sin, hourlyRate, createdAt)
VALUES ('Dave', 'Davidson', '743-832-123','42.00','2020-01-04 15:16:46');
INSERT INTO employees (firstName, lastName, sin, hourlyRate, createdAt)
VALUES ('Mike', 'Tomson', '444-555-333','44.00', now());
INSERT INTO employees (firstName, lastName, sin, hourlyRate, createdAt)
VALUES ('Sam', 'Donovan', '423-133-289','60.00', now());
INSERT INTO employees (firstName, lastName, sin, hourlyRate, createdAt)
VALUES ('Amanda', 'Sallivan', '508-889-343','85.00', now());
INSERT INTO employees (firstName, lastName, sin, hourlyRate, createdAt)
VALUES ('Phoebe', 'Smith', '332-453-345','45.00', now());
INSERT INTO `atsnovember`.`employees` (`firstName`, `lastName`, `sin`, `hourlyRate`, `isDeleted`, `createdAt`) 
VALUES ('Alexa', 'Perry', '345-234-989', '30', b'0', '2020-03-22');
INSERT INTO `atsnovember`.`employees` (`firstName`, `lastName`, `sin`, `hourlyRate`, `isDeleted`, `createdAt`) 
VALUES ('Gordon', 'Short', '989-345-344', '45', b'0', '2020-03-22');
INSERT INTO `atsnovember`.`employees` (`firstName`, `lastName`, `sin`, `hourlyRate`, `isDeleted`, `createdAt`) 
VALUES ('Anna', 'Lee', '230-900-898', '67.50', b'0', '2020-03-22');
INSERT INTO `atsnovember`.`employees` (`firstName`, `lastName`, `sin`, `hourlyRate`, `isDeleted`, `createdAt`) 
VALUES ('Leonard', 'Carr', '443-098-332', '50.50', b'0', '2020-03-22');
INSERT INTO `atsnovember`.`employees` (`firstName`, `lastName`, `sin`, `hourlyRate`, `isDeleted`, `createdAt`) 
VALUES ('Dorothy', 'MacDonald', '788-099-337', '36.00', b'0', '2020-03-22');
INSERT INTO `atsnovember`.`employees` (`firstName`, `lastName`, `sin`, `hourlyRate`, `isDeleted`, `createdAt`) 
VALUES ('Nathan', 'Paige', '348-667-454', '43', b'0', '2020-03-22');


-- SEARCH EMPLOYEE
DELIMITER //
DROP PROCEDURE IF EXISTS spSearchEmployees;
// DELIMITER ;

DELIMITER //
CREATE PROCEDURE spSearchEmployees(
IN search_param varchar(255))
	BEGIN
		SELECT * 
		FROM employees
		WHERE (lastName LIKE CONCAT('%',search_param,'%'))
		OR (sin = search_param);
	END;
  
//
DELIMITER ;


INSERT INTO `atsnovember`.`employeetasks` (`employeeId`, `taskId`) VALUES ('1', '1');
INSERT INTO `atsnovember`.`employeetasks` (`employeeId`, `taskId`) VALUES ('1', '2');
INSERT INTO `atsnovember`.`employeetasks` (`employeeId`, `taskId`) VALUES ('2', '1');
INSERT INTO `atsnovember`.`employeetasks` (`employeeId`, `taskId`) VALUES ('2', '3');
INSERT INTO `atsnovember`.`employeetasks` (`employeeId`, `taskId`) VALUES ('4', '3');
INSERT INTO `atsnovember`.`employeetasks` (`employeeId`, `taskId`) VALUES ('4', '2');
INSERT INTO `atsnovember`.`employeetasks` (`employeeId`, `taskId`) VALUES ('3', '4');


INSERT INTO `atsnovember`.`teams` (`Name`, `isOnCall`, `isDeleted`, `createdAt`) 
VALUES ('November', b'0', b'0', '2020-03-03');
INSERT INTO `atsnovember`.`teams` (`Name`, `isOnCall`, `isDeleted`, `createdAt`, `updatedAt`) 
VALUES ('December', b'0', b'0', '2020-03-01', '2020-03-02');

INSERT INTO `atsnovember`.`teammembers` (`EmployeeId`, `TeamId`) VALUES ('1', '1');
INSERT INTO `atsnovember`.`teammembers` (`EmployeeId`, `TeamId`) VALUES ('2', '1');
INSERT INTO `atsnovember`.`teammembers` (`EmployeeId`, `TeamId`) VALUES ('3', '2');
INSERT INTO `atsnovember`.`teammembers` (`EmployeeId`, `TeamId`) VALUES ('4', '2');

INSERT INTO `atsnovember`.`jobs` (`description`, `clientName`, `start`, `end`, `teamId`) 
VALUES ('Configure Router', 'Advatek Systems', '2020-03-16 10:00', '2020-03-16 11:00', '2');
INSERT INTO `atsnovember`.`jobs` (`description`, `clientName`, `start`, `end`, `teamId`) 
VALUES ('Design Network', 'Dovico', '2020-03-16 10:00', '2020-03-16 10:45', '1');
INSERT INTO `atsnovember`.`jobs` (`id`, `description`, `clientName`, `start`, `end`, `teamId`) 
VALUES ('3', 'Mobile hardware repair', 'Samsung Inc', '2020-03-16 12:00', '2020-03-16 14:00:00', '1');


INSERT INTO `atsnovember`.`jobstasks` (`taskId`, `jobId`, `operatingCost`, `operatingRevenue`) 
VALUES ('1', '2', '31.50', '94.50');
INSERT INTO `atsnovember`.`jobstasks` (`taskId`, `jobId`, `operatingCost`, `operatingRevenue`) 
VALUES ('2', '1', '60', '180');
INSERT INTO `atsnovember`.`jobstasks` (`taskId`, `jobId`, `operatingCost`, `operatingRevenue`) 
VALUES ('4', '3', '78.00', '234.00');


DELIMITER //
DROP PROCEDURE IF EXISTS spGetJobsSchedule;
// DELIMITER ;

DELIMITER //
CREATE PROCEDURE `spGetJobsSchedule`(
IN date_param VARCHAR(255)
)
BEGIN
	SELECT jobs.id, CONCAT(date_format(start, '%H:%i:%s')) as start_time, 
		CONCAT(date_format(end, '%H:%i:%s')) as end_time,  
		CONCAT(teams.name) as team
	FROM jobs
	INNER JOIN teams
	ON jobs.teamId = teams.id
	WHERE date_format(start, '%Y-%m-%d') = date_param 
	GROUP BY start, teams.name
    ORDER BY team DESC, start_time;
END;
//
DELIMITER ;



-- DELETE JOB
DELIMITER //
DROP procedure IF EXISTS `spDeleteJob`;
// DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `spDeleteJob` (IN jobId_param INT, OUT rows_affected_param INT)
BEGIN
	START TRANSACTION;
		DELETE FROM jobstasks 
        WHERE jobId IN (jobId_param);
        
		SET rows_affected_param = (SELECT row_count());
		DELETE FROM jobs WHERE id = jobId_param;

		SET rows_affected_param = rows_affected_param  + (SELECT row_count());
	COMMIT;
END$$

DELIMITER ;



-- IS AVAILABEL 
-- 0 - is available
DELIMITER $$
DROP procedure IF EXISTS `spTeamIsAvailable`;
$$ DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `spTeamIsAvailable` (
IN teamId_param INT, start_param DATETIME, end_param DATETIME)
	BEGIN
		SELECT COUNT(*) FROM jobs
		WHERE teamId = teamId_param AND (start_param < end AND end_param >= start);
	END;$$

DELIMITER ;



-- IS EMERGENCY

-- 1 - is onEmergencyCall
DELIMITER $$
DROP procedure IF EXISTS `TeamIsOnEmergency`;
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `TeamIsOnEmergency` (IN teamId_param INT)
BEGIN
	SELECT COUNT(*) FROM teams WHERE isOnCall = true AND id = teamId_param;
END$$

DELIMITER ;


DELIMITER $$
DROP procedure IF EXISTS `spGetTeamWithEmployeesDetails`;
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE spGetTeamWithEmployeesDetails(
	IN idParam INT )
BEGIN
	SELECT CONCAT(teams.id) as teamId, 
		CONCAT(employees.id) as empId,
        CONCAT(employees.firstName) as empFname,
        CONCAT(employees.lastName) as empLname,
        CONCAT(employees.hourlyRate) as hRate,
        CONCAT(employeetasks.taskId) as empSkillId
    FROM teams 
    INNER JOIN teammembers 
    ON teams.id = teammembers.teamId
    INNER JOIN employees 
    ON employees.id = teammembers.employeeId
    INNER JOIN employeetasks
    ON employees.id = employeetasks.employeeId
	WHERE teams.id = idParam
	ORDER BY employees.id ASC;
    
END$$
 DELIMITER ;



-- Get Teams Lookup
DELIMITER $$
DROP procedure IF EXISTS `spTeamLookup`;
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `spTeamLookup`()
BEGIN
SELECT id, Name, isOnCall FROM teams WHERE isDeleted = false;

END$$

DELIMITER ;

-- UPDATE TASK
DELIMITER //
DROP procedure IF EXISTS spUpdateTask;
DELIMITER ;

DELIMITER //
CREATE PROCEDURE spUpdateTask(
	IN taskId_param INT(11),
	IN name_param VARCHAR(255),
    IN duration_param INT(11),
    IN description_param VARCHAR(255),
    IN updated_param DATETIME,
    OUT rowAff INT
    )
BEGIN
	UPDATE tasks 
    SET name = name_param, duration = duration_param,
		description = description_param,
        updatedAt = updated_param
    WHERE id = taskId_param;
    
    SET rowAff = row_count();

END; //

DELIMITER ;


-- DELETE TEAM
DELIMITER //
DROP procedure IF EXISTS spDeleteTeam;
DELIMITER ;

DELIMITER //
CREATE PROCEDURE spDeleteTeam (
IN teamId_param INT(11), 
OUT rowAff INT(11)
)
BEGIN
	DECLARE count INT;
    
    SET count = (SELECT COUNT(*) FROM jobs
				 WHERE teamId = teamId_param);
    
	START TRANSACTION;
    
		IF count = 0 THEN
			DELETE FROM teammembers
			WHERE TeamId = teamId_param;
        
			DELETE FROM teams
			WHERE id = teamId_param;
            
            SET rowAff = row_count();
            
        ELSEIF count > 0 THEN
			UPDATE teams
            SET isDeleted = b'1',
            deletedAt = now()
            WHERE id = teamId_param;
            
            SET rowAff = row_count();
        END IF;
	COMMIT;
    
END; //
DELIMITER ;


-- UPDATE EMPLOYEE
DELIMITER //
DROP procedure IF EXISTS `spUpdateEmployeeDetails`;
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `spUpdateEmployeeDetails` (IN fName_param VARCHAR(255), 
IN lName_param VARCHAR(255), IN sin_param VARCHAR(11), 
IN hRate_param DOUBLE, 
IN empId_param INT(11), OUT rows_affected_param INT)
BEGIN
	UPDATE employees 
    SET firstName = fName_param, 
		lastName = lName_param, 
		sin = sin_param, 
		hourlyRate = hRate_param, 
		updatedAt = now() 
        WHERE employees.id = empId_param;
        SET rows_affected_param = (SELECT row_count());
        
END;$$

DELIMITER ;



-- GET ALL TEAMS TO DISPLAY TABLE
DELIMITER //
DROP procedure IF EXISTS `spGetAllTeams`;
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `spGetAllTeams` ()
BEGIN
SELECT teams.name, 
CONCAT(teams.id) AS teamId , 
EmployeeId, firstName, lastName, isOnCall FROM teams 
INNER JOIN teammembers ON teams.id = teammembers.TeamId 
INNER JOIN employees ON employees.id = teammembers.EmployeeId
ORDER BY teams.name ASC;
END$$

DELIMITER ;



-- PLACE TEAM ON CALL
DELIMITER //
DROP procedure IF EXISTS spPlaceTeamOnCall;
DELIMITER ;
-- returns codes
-- 1 - team onCall was switched
-- 0 - requested team is deleted

DELIMITER //
CREATE PROCEDURE spPlaceTeamOnCall (
IN teamId_param INT(11), 
OUT code INT(11)
)
BEGIN
	DECLARE currentOnCallTeamId INT;
    DECLARE teamActive INT;
    
    
	START TRANSACTION;
    
    SET currentOnCallTeamId = (SELECT id FROM teams
				 WHERE isOnCall = b'1');
                 
	-- 0 if team active
    SET teamActive = (SELECT COUNT(*) FROM teams
				WHERE (isDeleted = b'1' AND id = teamId_param));          
                
    
		IF (currentOnCallTeamId IS NOT NULL AND teamActive = 0) THEN
			UPDATE teams
            SET isOnCall = b'0',
            updatedAt = now()
            WHERE id = currentOnCallTeamId;
            
            UPDATE teams
			SET isOnCall = b'1',
            updatedAt = now()
            WHERE id = teamId_param; 
            
            SET code = 1;
            
        ELSEIF (currentOnCallTeamId IS NULL AND teamActive = 0) THEN
			UPDATE teams
			SET isOnCall = b'1',
            updatedAt = now()
            WHERE id = teamId_param;
            
            SET code = 1;
            
        ELSEIF teamActive = 1 THEN
			SET code = 0;
        END IF;
        
	COMMIT;
    
END; //
DELIMITER ;



-- GET TEAM DETAILS
DELIMITER $$
DROP procedure IF EXISTS `spGetTeamDetails`;
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `spGetTeamDetails`(IN teamId_param INT)
BEGIN

SELECT teams.*, employees.firstName, employees.lastName, CONCAT(employees.id) AS employeeId 
FROM teams
INNER JOIN teammembers ON teammembers.TeamId = teams.id
INNER JOIN employees ON teammembers.EmployeeId = employees.id
WHERE TeamId = teamId_param;

END$$

DELIMITER ;



-- FinancilaDate For Dashboard
DELIMITER $$
DROP procedure IF EXISTS `spGetYearlyFinancialStats`;
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `spGetYearlyFinancialStats` ()
BEGIN
SELECT  SUM(operatingCost) AS 'totalCost', 
SUM(operatingRevenue) AS 'totalRevenue',
jobs.start
FROM jobs INNER JOIN jobstasks ON jobs.id = jobstasks.jobId
GROUP BY MONTH(jobs.start), YEAR(jobs.start)
ORDER BY jobs.start;
END$$

DELIMITER ;

DELIMITER $$
DROP procedure IF EXISTS spGetMonthlyJobCost;
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE spGetMonthlyJobCost()
BEGIN
	SELECT SUM(operatingCost) AS 'monthlyCost'
FROM jobs 
INNER JOIN jobstasks 
ON jobs.id = jobstasks.jobId
WHERE month(jobs.start) = month(current_date());
END$$

DELIMITER ;

DELIMITER $$
DROP procedure IF EXISTS spGetMonthlyJobRevenue;
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE spGetMonthlyJobRevenue()
BEGIN
	SELECT SUM(operatingRevenue) + (SUM(operatingRevenue) * 0.15) AS 'monthlyRevenue'
FROM jobs 
INNER JOIN jobstasks 
ON jobs.id = jobstasks.jobId
WHERE month(jobs.start) = month(current_date());
END$$

DELIMITER ;

DELIMITER $$
DROP procedure IF EXISTS spGetYearlyJobCost;
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE spGetYearlyJobCost()
BEGIN
	SELECT SUM(operatingCost) AS 'yearlyCost'
FROM jobs 
INNER JOIN jobstasks 
ON jobs.id = jobstasks.jobId
WHERE year(jobs.start) = year(current_date());
END$$

DELIMITER ;

DELIMITER $$
DROP procedure IF EXISTS spGetYearlyJobRevenue;
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE spGetYearlyJobRevenue()
BEGIN
	SELECT SUM(operatingRevenue) + (SUM(operatingRevenue) * 0.15) AS 'yearlyRevenue'
FROM jobs 
INNER JOIN jobstasks 
ON jobs.id = jobstasks.jobId
WHERE year(jobs.start) = year(current_date());
END$$

DELIMITER ;

DELIMITER $$
DROP procedure IF EXISTS spGetMonthlyNumOfJobs;
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE spGetMonthlyNumOfJobs()
BEGIN
	SELECT COUNT(*) AS 'jobCount'
FROM jobs 
WHERE month(jobs.start) = month(current_date());
END$$

DELIMITER ;

DELIMITER //
DROP procedure IF EXISTS spGetTeamOnCall;
DELIMITER ;

DELIMITER //
CREATE PROCEDURE spGetTeamOnCall()
BEGIN
SELECT teams.*, 
	employees.firstName, 
	employees.lastName, 
	CONCAT(employees.id) AS employeeId 
FROM teams
INNER JOIN teammembers
ON teammembers.TeamId = teams.id
INNER JOIN employees 
ON teammembers.EmployeeId = employees.id
WHERE teams.isOnCall = b'1';
END; //

DELIMITER ;

-- 2020-03-16 10:00:00	2020-03-16 11:00:00
-- 2020-03-16 10:00:00	2020-03-16 10:45:00
-- 2020-03-16 12:00:00	2020-03-16 14:00:00
