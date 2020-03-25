/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.repository;

import com.ats.atssystem.models.DashboardVM;
import com.ats.atssystem.models.IJob;
import com.ats.atssystem.models.ITeam;
import java.util.List;

/**
 * @author Roman Pelikh
 * @author Olena Stepanova
 */
/**
 * IJobeRepo class provides methods signatures to perform CRUD operations with
 * employee object
 */
public interface IJobRepo {

    /**
     * Inserts a new job record in database
     *
     * @param job a job to insert into database as a new record
     * @return int Id of last inserted record
     */
    int addJob(IJob job);

    /**
     * Returns specified job from database with all the details
     *
     * @param jobId int id of specified job
     * @return job object with details
     */
    IJob getJobDetails(int jobId);

    /**
     * Deletes a job with all associated tasks and revenue
     *
     * @param jobId id of job to delete
     * @return number of rows affected
     */
    int deleteJob(int jobId);

    /**
     * Retrieves all teams and scheduled jobs for specified date
     *
     * @param date string date
     * @return list of teams and theirs scheduled jobs
     */
    List<ITeam> getScheduledJobs(String date);

    /**
     * Validates if team is available for job and not overbooked
     *
     * @param job job to validate team in
     * @return boolean true if available, false otherwise
     */
    public boolean isTeamAvailableToBook(IJob job);

    /**
     * Validates if team is onEmergencyCall
     *
     * @param job job to validate
     * @return boolean true if is on call, false otherwise
     */
    public boolean isTeamOnEmergencyCall(IJob job);

    public DashboardVM getFinancialYearlyStats();

}
