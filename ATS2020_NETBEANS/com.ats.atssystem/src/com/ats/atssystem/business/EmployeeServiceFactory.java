package com.ats.atssystem.business;


/**
 * @author Roman Pelikh
 */


/**
 * EmployeeServiceFactory Class responsible for creating an instance of EmployeeService class
 */
public abstract class EmployeeServiceFactory {

    /**
     * Creates an instance of EmployeeService class
     *
     * @return instance of EmployeeService class
     */
    public static IEmployeeService createInstance() {
        return new EmployeeService();
    }

}
