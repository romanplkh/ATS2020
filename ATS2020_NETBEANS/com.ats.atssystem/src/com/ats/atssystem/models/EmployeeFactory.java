package com.ats.atssystem.models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Roman Pelikh
 */
/**
 * EmployeeFactory class responsible for creating an instance of Employee class
 */
public abstract class EmployeeFactory {

    /**
     * Creates an instance of IEmployee
     *
     * @return new instance of Employee class
     */
    public static IEmployee createInstance() {
        return new Employee();
    }

    /**
     * Creates an instance of IEmployee
     *
     * @param firstName employee first name
     * @param lastName employee last name
     * @param sin employee sin
     * @param hourlyRate employee hourly rate
     * @return new instance of Employee class
     */
    public static IEmployee createInstance(String firstName, String lastName, String sin, double hourlyRate) {
        return new Employee(firstName, lastName, sin, hourlyRate);
    }

    /**
     * Creates List of instances of IEmployee
     *
     * @return
     */
    public static List<IEmployee> createListInstance() {
        return new ArrayList<>();
    }

}
