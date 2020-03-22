/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.models;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Roman Pelikh
 */
public class EmployeeSkillsViewModel implements Serializable {

    private IEmployee employee = EmployeeFactory.createInstance();
    private List<ITask> tasks = TaskFactory.createListInstance();

    public EmployeeSkillsViewModel() {
    }

    public IEmployee getEmployee() {
        return employee;
    }

    public void setEmployee(IEmployee employee) {
        this.employee = employee;
    }

    public List<ITask> getTasks() {
        return tasks;
    }

    public void setTasks(List<ITask> tasks) {
        this.tasks = tasks;
    }
    
}
