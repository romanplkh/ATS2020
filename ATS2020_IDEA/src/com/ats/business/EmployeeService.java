package com.ats.business;

import com.ats.models.Employee;
import com.ats.models.EmployeeFactory;
import com.ats.models.ErrorFactory;
import com.ats.models.IEmployee;
import com.ats.repository.EmployeeRepo;
import com.ats.repository.EmployeeRepoFactory;
import com.ats.repository.IEmployeeRepo;

import java.util.List;

public class EmployeeService implements IEmployeeService {


    private IEmployeeRepo repo;

    EmployeeService() {

    }

    @Override
    public boolean isValid(IEmployee employee) {
        return employee.getErrors().isEmpty();
    }

    @Override
    public IEmployee createEmployee(IEmployee employee) {
        repo = EmployeeRepoFactory.createInstance();

        if (isValid(employee)) {
            employee.setId(repo.addEmployee(employee));
        }else {
            employee.addError(ErrorFactory.createInstance(11, "Employee was not valid to create"));
        }

        return employee;

    }

    @Override
    public int updateEmployee(IEmployee employee) {
        return 0;
    }

    @Override
    public int deleteEmployee(int id) {
        return 0;
    }

    @Override
    public IEmployee getEmployee(int id) {
        repo = EmployeeRepoFactory.createInstance();


        IEmployee employee = repo.retrieveEmployee(id);

        if(employee != null){

        }

        return  employee;

    }

    @Override
    public List<IEmployee> getEmployees() {
        repo = EmployeeRepoFactory.createInstance();

        List<IEmployee> employees = EmployeeFactory.createListInstance();

        employees = repo.retrieveEmployees();


        return employees;


    }
}
