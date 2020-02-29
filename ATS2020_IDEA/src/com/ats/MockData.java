package com.ats;

import com.ats.models.Employee;
import com.ats.models.EmployeeLookupViewModel;
import com.ats.models.Task;

import java.time.LocalDateTime;
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

//    public static List<Task> getTaskList(){
//        return new ArrayList<Task>(){{
//            add(new Task(1, "Router Config", "Configure router", 60, LocalDateTime.now()));
//            add(new Task(2, "WIN OS installation", "Installation of Windows", 80, LocalDateTime.now()));
//            add(new Task(3, "LINUX server maintenance", "Linux installation", 60, LocalDateTime.now()));
//            add(new Task(4, "MYSQL installation", "MYSQL installation and configuration", 60, LocalDateTime.now()));
//        }};
//
//    }
//
//    public static Task getTask(){
//        return new Task(1, "Router Config", "Configure router", 60, LocalDateTime.now());
//    }


}
