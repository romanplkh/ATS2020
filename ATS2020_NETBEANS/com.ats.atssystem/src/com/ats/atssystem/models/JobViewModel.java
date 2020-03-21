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
public class JobViewModel implements Serializable{
    private IJob job = JobFactory.createInstance();
    private List<ITask> tasks = TaskFactory.createListInstance();
    private List<ITeam> teams = TeamFactory.createListInstance();

    public JobViewModel() {
    }

    public IJob getJob() {
        return job;
    }

    public void setJob(IJob job) {
        this.job = job;
    }

    public List<ITask> getTasks() {
        return tasks;
    }

    public void setTasks(List<ITask> tasks) {
        this.tasks = tasks;
    }

    public List<ITeam> getTeams() {
        return teams;
    }

    public void setTeams(List<ITeam> teams) {
        this.teams = teams;
    }
    
    

}
