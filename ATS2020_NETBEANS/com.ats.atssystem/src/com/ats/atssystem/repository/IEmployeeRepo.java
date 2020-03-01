package com.ats.atssystem.repository;


import com.ats.atssystem.models.IEmployee;
import java.util.List;

public interface IEmployeeRepo {

    int addEmployee(IEmployee employee);

    int updateEmployee(IEmployee employee);

    int deleteEmployee(IEmployee employee);

    List<IEmployee> retrieveEmployees();

    IEmployee retrieveEmployee(int id);
}
