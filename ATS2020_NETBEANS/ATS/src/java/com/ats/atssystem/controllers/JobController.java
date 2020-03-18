/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.controllers;

import com.ats.atssystem.business.IJobService;
import com.ats.atssystem.business.JobService;
import com.ats.atssystem.business.JobServiceFactory;
import com.ats.atssystem.models.ErrorViewModel;
import com.ats.atssystem.models.IJob;
import com.ats.atssystem.models.ITeam;
import com.ats.atssystem.models.JobFactory;
import com.ats.atssystem.models.TeamFactory;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Olena Stepanova
 * @author Roman Pelikh
 */
public class JobController extends CommonController {

    private static String JOB_DETAILS_VIEW = "/job-details.jsp";
    private static String JOBS_VIEW = "/jobs.jsp";
//  ---------  NEED TO CHANGE AFTER  --------------------
    private static String JOB_MAINT_VIEW = "/dashboard.jsp";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       

        String pathInfo = request.getPathInfo();
        IJobService service = JobServiceFactory.createInstance();

        if (pathInfo == null) {
            //show all jobs
            
            
            List<ITeam> teams = service.getScheduledJobs("2020-03-16");
            
            request.setAttribute("teams", teams);
            
            super.setView(request, JOBS_VIEW);
        } else {
            //job/:id/[details]
            
            String[] pathParts = super.getUrlParts(pathInfo);
            //job id
            int jobId = super.getInteger(pathParts[1]);

            IJob job = JobFactory.createInstance();

            if (jobId != 0) {

                String page = pathParts[2].toLowerCase();
                job = service.getJobDetails(jobId);

                if (job != null) {
                    switch (page) {
                        case "details":
                            request.setAttribute("job", job);
                            super.setView(request, JOB_DETAILS_VIEW);
                        case "delete":

                    }
                } else {
                    request.setAttribute("error",
                            new ErrorViewModel(String.format("Requested job was not found")));

                }

            } else {
                //create job page
            }

        }
        super.getView().forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        IJobService jobService = JobServiceFactory.createInstance();
        IJob job = JobFactory.createInstance();

        try {
            //Get action of button
            String action = super.getValue(request, "action");

            //Get hidden id
            int id = super.getInteger(request, "jobId");
            switch (action.toLowerCase()) {
                case "delete":
                    job = jobService.getJobDetails(id);

                    if (job != null) {
                        //Delete job
                        job.setId(id);
                        job = jobService.deleteJob(job);
                    } else {
                        request.setAttribute("error",
                                new ErrorViewModel(String.format("Job you are trying to delete does not exist")));
                    }
                    break;
            }

        } catch (Exception e) {
            super.setView(request, JOB_DETAILS_VIEW);
            request.setAttribute("vmError", new ErrorViewModel("Something bad happened when attempting to delete the job"));
        }

        if (!jobService.isValid(job)) {
            request.setAttribute("job", job);
            super.setView(request, JOB_DETAILS_VIEW);
            super.getView().forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/dashboard");

        }

    }

}
