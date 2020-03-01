package com.ats.business;

import com.ats.models.IEmployee;

import java.util.List;

public interface IEmployeeService {

    boolean isValid(IEmployee employee);

    IEmployee createEmployee(IEmployee employee);

    int updateEmployee(IEmployee employee);

    int deleteEmployee(int id);

    IEmployee getEmployee(int id);

    List<IEmployee> getEmployees();



}
