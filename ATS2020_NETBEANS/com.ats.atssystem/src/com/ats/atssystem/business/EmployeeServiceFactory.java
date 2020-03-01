package com.ats.atssystem.business;



public abstract class EmployeeServiceFactory {


    public static IEmployeeService createInstance() {
        return new EmployeeService();
    }

}
