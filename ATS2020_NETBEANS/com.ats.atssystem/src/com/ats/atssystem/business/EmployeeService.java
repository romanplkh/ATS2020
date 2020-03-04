package com.ats.atssystem.business;

import com.ats.atssystem.models.EmployeeDTOFactory;
import com.ats.atssystem.models.EmployeeFactory;
import com.ats.atssystem.models.ErrorFactory;
import com.ats.atssystem.models.IEmployee;
import com.ats.atssystem.models.IEmployeeDTO;
import com.ats.atssystem.repository.EmployeeRepoFactory;
import com.ats.atssystem.repository.IEmployeeRepo;
import java.util.List;

/**
 * @author Roman Pelikh
 */


public class EmployeeService implements IEmployeeService {

    //Repo depenancy
    private IEmployeeRepo repo;

    EmployeeService() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(IEmployee employee) {
        return employee.getErrors().isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IEmployee createEmployee(IEmployee employee) {
        repo = EmployeeRepoFactory.createInstance();

        if (isValid(employee)) {
            employee.setId(repo.addEmployee(employee));
        } else {
            employee.addError(ErrorFactory.createInstance(11, "Employee was not valid to create"));
        }

        return employee;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateEmployee(IEmployee employee) {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteEmployee(int id) {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IEmployee getEmployee(int id) {
        repo = EmployeeRepoFactory.createInstance();

        IEmployee employee = repo.retrieveEmployee(id);

        if (employee != null) {

        }

        return employee;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IEmployee> getEmployees() {
        repo = EmployeeRepoFactory.createInstance();

        List<IEmployee> employees = EmployeeFactory.createListInstance();

        employees = repo.retrieveEmployees();

        return employees;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IEmployeeDTO getEmployeeDetails(int id) {
        repo = EmployeeRepoFactory.createInstance();

        IEmployeeDTO employeeDetails = EmployeeDTOFactory.createInstance();

        employeeDetails = repo.retrieveEmployeeDetails(id);

        return employeeDetails;

    }

}
