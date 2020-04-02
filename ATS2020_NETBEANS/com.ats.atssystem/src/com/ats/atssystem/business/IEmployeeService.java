package com.ats.atssystem.business;

import com.ats.atssystem.models.IEmployee;
import com.ats.atssystem.models.IEmployeeDTO;
import java.util.List;

/**
 * @author Roman Pelikh
 */
/**
 * IEmployee Interface Provides methods to work with repository and perform
 * necessary manipulations with employee to any class implementing this
 * interface
 */
public interface IEmployeeService {

    /**
     * Validates presence of errors in an employee;
     *
     * @param employee employee to validate
     * @return presence of errors in model
     */
    boolean isValid(IEmployee employee);

    /**
     * Enables creation of an employee
     *
     * @param employee employee to create
     * @return created employee
     */
    IEmployee createEmployee(IEmployee employee);

    /**
     * Enables update operation of an employee
     *
     * @param employee employee to update
     * @return updated employee with errors or not
     */
    IEmployee updateEmployee(IEmployee employee);

    /**
     * Enables deletion of an employee
     *
     * @param employee to delete
     * @return number of elements affected
     */
    IEmployee deleteEmployee(IEmployee emp);

    /**
     *
     * @param id employeeId
     * @param skillIds
     * @return numberOfElementsAffected
     */
    int deleteEmployeeSkill(int id, String skillIds);

    /**
     * Add skill set to employee record
     *
     * @param id employeeId
     * @param skillIds
     * @return number of rows affected
     */
    int addEmployeeSkill(int id, String skillIds);

    /**
     * Updates skill set of employee record
     *
     * @param id employeeId
     * @param skillsDelete skill Ids to delete
     * @param skillsAdd skill Ids to add
     * @return number of rows affected
     */
    int updateEmployeeSkills(int id, String skillsDelete, String skillsAdd);

    /**
     * Enables retrieve an employee
     *
     * @param id id of employee to retrieve
     * @return queried employee or null
     */
    IEmployee getEmployee(int id);

    /**
     * Enables to retrieve all associated with employee information
     *
     * @param id id of employee to retrieve information about
     * @return employee with team he/she belongs to and skills
     */
    IEmployeeDTO getEmployeeDetails(int id);

    /**
     * Enables to retrieve all employees
     *
     * @return List of all employees
     */
    List<IEmployee> getEmployees();

    /**
     * Enables to retrieve all employees that match search criteria by sin or
     * last name
     *
     * @param searchCriteria
     * @return List of all employees or null
     */
    List<IEmployee> getEmployees(String searchCriteria);

}
