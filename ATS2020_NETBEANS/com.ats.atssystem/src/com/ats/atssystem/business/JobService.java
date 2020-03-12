/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.business;

import com.ats.atssystem.models.IJob;
import com.ats.atssystem.repository.IJobRepo;
import com.ats.atssystem.repository.JobRepoFactory;

/**
 *
 * @author Olena Stepanova
 */
public class JobService implements IJobService{

    public JobService() {
    }
    
    private IJobRepo repo = JobRepoFactory.createInstance();

    /**
     * {@inheritDoc } 
     */
    @Override
    public IJob getJobDetails(int jobId) {
        return repo.getJobDetails(jobId);
    }
    
    
    
}
