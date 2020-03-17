/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.business;

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
     * Deletes a selected job with all associated tasks and revenu
     *
     * @param job job to delete
     * @return deleted job with errors or not
     */
    IJob deleteJob(IJob job);
    
    /**
     * Returns all scheduled jobs with assigned teams
     * @param date String date
     * @return list of teams
     */
    List<ITeam> getScheduledJobs(String date);
}
