/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Roman Pelikh
 */
public class Employee  implements Serializable{
    
    private int id;
    private String firstName;
    private String lastName;
    private String sin;
    private double hourlyRate;
    private boolean isDeleted;
    
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private LocalDate deletedAt;
    
    private List<String> errors = new ArrayList();

    public Employee() {
    }

    public Employee(int id, String firstName, String lastName, String sin, double hourlyRate, boolean isDeleted, LocalDate createdAt, LocalDate updatedAt, LocalDate deletedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sin = sin;
        this.hourlyRate = hourlyRate;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSin() {
        return sin;
    }

    public void setSin(String sin) {
        this.sin = sin;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDate getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDate deletedAt) {
        this.deletedAt = deletedAt;
    }

    public List<String> getErrors() {
        return errors;
    }
    
    
    
    
    
    
    
}
