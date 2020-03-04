package com.ats.atssystem.repository;

/**
 * @author Roman Pelikh
 */
/**
 * EmployeeRepoFactory class responsible for creating an instance of EmployeeRepo class
 */
public abstract class EmployeeRepoFactory {

    /**
     * Creates an instance of EmployeeRepo
     *
     * @return new instance of EmployeeRepo class
     */
    public static IEmployeeRepo createInstance() {
        return new EmployeeRepo();
    }

}
