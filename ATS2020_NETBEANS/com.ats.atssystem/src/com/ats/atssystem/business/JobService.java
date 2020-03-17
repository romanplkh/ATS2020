/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.business;

import com.ats.atssystem.models.ErrorFactory;
import com.ats.atssystem.models.IJob;
import com.ats.atssystem.models.ITeam;
import com.ats.atssystem.repository.IJobRepo;
import com.ats.atssystem.repository.JobRepoFactory;
import java.util.List;

/**
 *
 * @author Olena Stepanova
 * @author Roman Pelikh
 */
public class JobService implements IJobService {

    public JobService() {
    }

    private IJobRepo repo = JobRepoFactory.createInstance();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(IJob job) {
        return job.getErrors().isEmpty();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public IJob getJobDetails(int jobId) {
        return repo.getJobDetails(jobId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public IJob deleteJob(IJob job) {
        if (repo.deleteJob(job.getId()) == 0) {
            job.addError(ErrorFactory.createInstance(1, "Something went wrong. Job was not deleted. Try again"));

        }
        return job;
    }

    @Override
    public List<ITeam> getScheduledJobs(String date) {
        return repo.getScheduledJobs(date);
    }

}
