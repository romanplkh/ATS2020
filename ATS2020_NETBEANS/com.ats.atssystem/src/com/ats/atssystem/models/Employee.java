package com.ats.atssystem.models;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Roman Pelikh
 */
public class Employee extends Base implements IEmployee, Serializable {

    private int id;
    private String firstName;
    private String lastName;
    private String sin;
    private double hourlyRate;
    private boolean isDeleted;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private String fullName;

    private List<ITask> skills = TaskFactory.createListInstance();

    public Employee() {
    }

    public Employee(String firstName, String lastName, String sin, double hourlyRate) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setSin(sin);
        this.setHourlyRate(hourlyRate);

    }

    @Override
    public List<ITask> getSkills() {
        return skills;
    }

    @Override
    public void setSkills(List<ITask> skills) {
        this.skills = skills;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {

        if (firstName.trim().isEmpty()) {
            super.addError(ErrorFactory.createInstance(1, "First name is required"));
        } else {
            this.firstName = firstName;
        }

    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        if (lastName.trim().isEmpty()) {
            super.addError(ErrorFactory.createInstance(2, "Last Name is required"));
        } else {
            this.lastName = lastName;
        }
    }

    @Override
    public String getSin() {
        return sin;
    }

    @Override
    public void setSin(String sin) {
        String sinPattern = "\\d{3}-\\d{3}-\\d{3}";
        if (!sin.matches(sinPattern)) {
            super.addError(ErrorFactory.createInstance(3, "SIN is invalid"));
        } else {
            this.sin = sin;
        }
    }

    @Override
    public double getHourlyRate() {
        return hourlyRate;
    }

    @Override
    public void setHourlyRate(double hourlyRate) {

        if (hourlyRate == 0 || hourlyRate < 0) {
            super.addError(ErrorFactory.createInstance(4, "Rate should be a valid number greater than zero"));
        } else {
            this.hourlyRate = hourlyRate;
        }

    }

    @Override
    public boolean isIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public Date getDeletedAt() {
        return deletedAt;
    }

    @Override
    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

}
