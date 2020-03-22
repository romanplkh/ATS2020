/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Roman Pelikh
 */


public class Employee extends Base implements IEmployee {

    private int id;
    private String firstName;
    private String lastName;
    private String sin;
    private double hourlyRate;
    private boolean isDeleted;

    private LocalDate createdAt;
    private LocalDate updatedAt;
    private LocalDate deletedAt;


    public Employee() {
    }

    public Employee(String firstName, String lastName, String sin, double hourlyRate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sin = sin;
        this.hourlyRate = hourlyRate;
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
    public LocalDate getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public LocalDate getDeletedAt() {
        return deletedAt;
    }

    @Override
    public void setDeletedAt(LocalDate deletedAt) {
        this.deletedAt = deletedAt;
    }


}
