package com.ats.models;

import java.io.Serializable;

public class EmployeeLookupViewModel implements Serializable {


    private int id;
    private String firstName;
    private String lastName;


    public EmployeeLookupViewModel() {
    }

    public EmployeeLookupViewModel(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
