/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.repository;

import com.ats.atssystem.models.IJob;
import com.ats.atssystem.models.JobDetailsViewModel;

/**
 *
 * @author Olena Stepanova
 */
public interface IJobRepo {
    
    /**
     * Returns specified job from database with all
     * the details
     * @param jobId int id of specified job
     * @return job vm object with details
     */
    JobDetailsViewModel getJobDetails(int jobId);
}
