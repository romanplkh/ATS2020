package com.ats.atssystem.repository;

import com.ats.atssystem.models.EmployeeDTO;
import com.ats.atssystem.models.IEmployee;
import com.ats.atssystem.models.IEmployeeDTO;
import com.ats.atssystem.models.ITask;
import java.util.List;
import java.util.Optional;

/**
 * @author Roman Pelikh
 */
/**
 * IEmployeeRepo class provides methods signatures to perform CRUD operations
 * with employee object
 */
public interface IEmployeeRepo {

    /**
     * Inserts a new employee record in database
     *
     * @param employee an employee to insert into database as a new record
     * @return int id of inserted employee
     */
    int addEmployee(IEmployee employee);

    /**
     * Updates an employee record in database
     *
     * @param employee an employee to update
     * @return number of affected entries
     */
    int updateEmployee(IEmployee employee);

    /**
     * Deletes an employee record in database
     *
     * @param employee an employee to delete
     * @return
     */
    int deleteEmployee(int id);

    /**
     * Removes one or many skills from selected employee
     *
     * @param id employee to perform operation
     * @param skillIds String of skill IDs
     * @return numbers of rows affected;
     */
    int deleteEmployeeSkill(int id, String skillIds);

    /**
     * Retrieves all employees from database
     *
     * @return list of employees
     */
    List<IEmployee> retrieveEmployees();

    /**
     * Retrieve employee information from database and all associated details
     * with
     *
     * @param id id of employee to retrieve
     * @return detailed information about employee
     */
    IEmployeeDTO retrieveEmployeeDetails(int id);

    /**
     * Retrieve a record of a particular employee from database
     *
     * @param id
     * @return
     */
    IEmployee retrieveEmployee(int id);

    /**
     * Adds necessary skills to an employee's skill set
     * @param employeeId employee Id
     * @param skillIds id of all skills to add
     * @return number of row affected
     */
    int addSkillsToEmployee(int employeeId, String skillIds);

    /**
     * Looks for an employees with specific sin or last name
     * @param searchCriteria Canadian SIN or lastName
     * @return list of matched employees
     */
    List<IEmployee> retrieveEmployees(String searchCriteria);

}
