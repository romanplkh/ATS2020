package com.ats.repository;

import com.ats.models.IEmployee;

import java.util.List;

public interface IEmployeeRepo {

    int addEmployee(IEmployee employee);

    int updateEmployee(IEmployee employee);

    int deleteEmployee(IEmployee employee);

    List<IEmployee> retrieveEmployees();

    IEmployee retrieveEmployee(int id);
}
