/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.business;

import com.ats.atssystem.models.DashboardVM;
import com.ats.atssystem.models.IEmployee;
import com.ats.atssystem.models.IJob;
import com.ats.atssystem.models.ITeam;
import java.util.List;

/**
 *
 * @author Olena Stepanova
 */
/**
 * IJobService Interface Provides methods to work with repository and get necessary information about job,
 * list of jobs, create new entry
 */
public interface IJobService {

    /**
     * Validates presence of errors in a job;
     *
     * @param job job to validate
     * @return presence of errors in model
     */
    boolean isValid(IJob job);

    /**
     * Returns all details of specified job
     *
     * @param jobId int job id
     * @return job object with populated details
     */
    IJob getJobDetails(int jobId);

    /**
     * Returns all details of specified job
     *
     * @param jobId int job id
     * @return job object with populated details
     */
    IJob addJob(IJob jobId);

    /**
     * Deletes a selected job with all associated tasks and revenue
     *
     * @param job job to delete
     * @return deleted job with errors or not
     */
    IJob deleteJob(IJob job);

    /**
     * Returns all scheduled jobs with assigned teams
     *
     * @param date String date
     * @return list of teams
     */
    List<ITeam> getScheduledJobs(String date);

    /**
     * Validates if emergency job is scheduled off-hours
     *
     * @param job job to validate
     */
    //void validateEmergencyJobTime(IJob job);

    /**
     * Validate that team skill set corresponds to job tasks
     *
     * @param job job to validate
     */
    void validateSkillset(IJob job);

    /**
     * Validates if team is available for job and not overbooked
     *
     * @param job job to validate team in
     * @return boolean whether team is available or not
     */
    public boolean isTeamAvailableToBook(IJob job);

    /**
     * Validates if team is onEmergencyCall
     *
     * @param job job to validate
     * @return boolean true if is on call, otherwise false
     */
    public boolean isTeamOnEmergencyCall(IJob job);

    /**
     * Validates if job is scheduled within business hours
     *
     * @param job job to validate
     * @return boolean true if within hours, false otherwise
     */
    public boolean isJobWithinBusinessHours(IJob job);
    
    /**
     * Gets the aggregated financial data and currently on call team
     * for Dashboard
     * @return DashboardVM object
     */
    public DashboardVM getFinancialStats();

}
