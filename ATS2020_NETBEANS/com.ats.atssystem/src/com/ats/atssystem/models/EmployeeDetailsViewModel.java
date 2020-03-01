package com.ats.atssystem.models;



import java.io.Serializable;

public class EmployeeDetailsViewModel implements Serializable {

    private IEmployee employee = EmployeeFactory.createInstance();

    public IEmployee getEmployee() {
        return employee;
    }

    public void setEmployee(IEmployee employee) {
        this.employee = employee;
    }
}
