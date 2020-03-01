package com.ats.atssystem.repository;

public abstract class EmployeeRepoFactory {

    public static IEmployeeRepo createInstance() {
        return new EmployeeRepo();
    }

}
