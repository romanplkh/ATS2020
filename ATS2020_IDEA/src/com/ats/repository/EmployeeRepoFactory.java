package com.ats.repository;

import com.ats.models.Employee;
import com.ats.models.IEmployee;

public abstract class EmployeeRepoFactory {

    public static IEmployeeRepo createInstance() {
        return new EmployeeRepo();
    }

}
