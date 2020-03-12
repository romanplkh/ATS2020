/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.business;

import com.ats.atssystem.models.IJob;

/**
 *
 * @author Olena Stepanova
 */
/**
 * IJobService Interface
 * Provides methods to work with repository and get necessary
 * information about job, list of
 * jobs, create new entry
 */
public interface IJobService {
    
    /**
     * Returns all details of specified job
     * @param jobId int job id
     * @return job object with populated details
     */
    IJob getJobDetails(int jobId);
}
