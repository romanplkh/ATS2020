package com.ats.atssystem.repository;


import com.ats.atssystem.models.EmployeeDTO;
import com.ats.atssystem.models.IEmployee;
import com.ats.atssystem.models.IEmployeeDTO;
import java.util.List;

public interface IEmployeeRepo {

    int addEmployee(IEmployee employee);

    int updateEmployee(IEmployee employee);

    int deleteEmployee(IEmployee employee);

    List<IEmployee> retrieveEmployees();
    
    IEmployeeDTO retrieveEmployeeDetails(int id);
    
    IEmployee retrieveEmployee(int id);
}
