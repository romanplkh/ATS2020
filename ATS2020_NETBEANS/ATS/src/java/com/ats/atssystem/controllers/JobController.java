/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.controllers;

import com.ats.atssystem.business.IJobService;
import com.ats.atssystem.business.JobServiceFactory;
import com.ats.atssystem.models.ErrorViewModel;
import com.ats.atssystem.models.IJob;
import com.ats.atssystem.models.JobFactory;
import java.io.IOException;
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
//  ---------  NEED TO CHANGE AFTER  --------------------
    private static String JOB_MAINT_VIEW = "/dashboard.jsp";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        IJobService service = JobServiceFactory.createInstance();

        if (pathInfo == null) {
            //show all jobs
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
                    }
                } else {
                    request.setAttribute("error",
                            new ErrorViewModel(String.format("Requested job was not found")));
                    super.setView(request, JOB_DETAILS_VIEW);
                }

            } else {
                //create job page
            }

        }
        super.getView().forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
