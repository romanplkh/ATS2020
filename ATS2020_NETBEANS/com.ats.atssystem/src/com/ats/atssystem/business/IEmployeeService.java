package com.ats.atssystem.business;

import com.ats.atssystem.models.IEmployee;
import com.ats.atssystem.models.IEmployeeDTO;
import java.util.List;

public interface IEmployeeService {

    boolean isValid(IEmployee employee);

    IEmployee createEmployee(IEmployee employee);

    int updateEmployee(IEmployee employee);

    int deleteEmployee(int id);

    IEmployee getEmployee(int id);

    IEmployeeDTO getEmployeeDetails(int id);

    List<IEmployee> getEmployees();

}
