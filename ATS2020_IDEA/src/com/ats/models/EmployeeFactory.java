package com.ats.models;

import java.util.ArrayList;
import java.util.List;

public abstract class EmployeeFactory {

    public static IEmployee createInstance() {
        return new Employee();
    }

    public static IEmployee createInstance(String firstName, String lastName, String sin, double hourlyRate) {
        return new Employee(firstName, lastName, sin, hourlyRate);
    }


    public static List<IEmployee> createListInstance() {
        return new ArrayList<>();
    }

}
