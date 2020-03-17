/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Roman Pelikh
 */
public class Team extends Base implements Serializable, ITeam {

    private int id;
    private String name;
    private List<IEmployee> teamMembers = EmployeeFactory.createListInstance();
    private List<IJob> jobs = JobFactory.createListInstance();
    private boolean isOnCall;
    private boolean isDeleted;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    public Team() {
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
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        if (name.isEmpty()) {
            super.addError(ErrorFactory.createInstance(3, "Team name is required"));
        } else {
            this.name = name;
        }

    }

    @Override
    public boolean getIsOnCall() {
        return isOnCall;
    }

    @Override
    public void setIsOnCall(boolean isOnCall) {
        this.isOnCall = isOnCall;
    }

    @Override
    public boolean getIsDeleted() {
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

    @Override
    public List<IEmployee> getTeamMembers() {
        return teamMembers;
    }

    @Override
    public void setTeamMembers(List<IEmployee> teamMembers) {

        int memberId_1 = teamMembers.get(0).getId();
        int memberId_2 = teamMembers.get(1).getId();

        if (memberId_1 == 0 || memberId_2 == 0) {
            super.addError(ErrorFactory.createInstance(3, "Team should contain 2 members"));

        } else {

            if (memberId_1 == memberId_2) {
                super.addError(ErrorFactory.createInstance(3, "Team members should be different"));
            } else {
                this.teamMembers = teamMembers;
            }

        }

    }

    @Override
    public List<IJob> getJobs() {
        return this.jobs;
    }

    @Override
    public void setJobs(List<IJob> jobsList) {
        this.jobs = jobsList;
    }
    
    

}
