/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.models;

/**
 *
 * @author Olena Stepanova
 */
public class JobDetailsViewModel {
    private ITeam team;
    private IJob job;
    
    
    
    //add private List<Tasks> to display all tasks
    //List<Team> to display all teams

    public ITeam getTeam() {
        return team;
    }

    public void setTeam(ITeam team) {
        this.team = team;
    }

    public IJob getJob() {
        return job;
    }

    public void setJob(IJob job) {
        this.job = job;
    }
}
