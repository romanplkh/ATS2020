package com.ats;

import com.ats.models.Employee;
import com.ats.models.EmployeeLookupViewModel;

import java.util.ArrayList;
import java.util.List;

public class MockData {


    public static List<EmployeeLookupViewModel> getEmployees() {
        List<EmployeeLookupViewModel> employees = new ArrayList<>();

        employees.add(new EmployeeLookupViewModel(1, "John", "Doe"));
        employees.add(new EmployeeLookupViewModel(2, "Tom", "Smith"));
        employees.add(new EmployeeLookupViewModel(3, "Jane", "Oliver"));
        employees.add(new EmployeeLookupViewModel(4, "Mark", "Otto"));

        return employees;

    }
}
