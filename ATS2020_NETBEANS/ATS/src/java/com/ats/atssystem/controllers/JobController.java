/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.controllers;

import com.ats.atssystem.business.IJobService;
import com.ats.atssystem.business.JobService;
import com.ats.atssystem.business.JobServiceFactory;
import com.ats.atssystem.business.TaskServiceFactory;
import com.ats.atssystem.business.TeamServiceFactory;
import com.ats.atssystem.models.ErrorFactory;
import com.ats.atssystem.models.ErrorViewModel;
import com.ats.atssystem.models.IJob;
import com.ats.atssystem.models.ITask;
import com.ats.atssystem.models.ITeam;
import com.ats.atssystem.models.JobFactory;
import com.ats.atssystem.models.JobViewModel;
import com.ats.atssystem.models.TaskFactory;
import com.ats.atssystem.models.TeamFactory;
import java.io.IOException;
import java.time.LocalDate;
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
    private static String JOB_MAINT_VIEW = "/job.jsp";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        IJobService service = JobServiceFactory.createInstance();

        if (pathInfo == null) {
            //show all jobs

            String currentDate = LocalDate.now().toString();
            //GET DATE
            String searchDate = super.getValue(request, "searchDate");

            if (searchDate != null) {
                currentDate = searchDate;
            }

            List<ITeam> teams = service.getScheduledJobs(currentDate);
            request.setAttribute("teams", teams);

            //Display date if not found for this date
            request.setAttribute("searchDate", currentDate);

            super.setView(request, JOBS_VIEW);
        } else {
            //job/:id/[details]
            String[] pathParts = pathInfo.split("/");
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
                JobViewModel jvm = new JobViewModel();
                jvm.setTasks(TaskServiceFactory.createInstance().getAllTasks());
                //------NED TO SET ALL TEAMS--------
                request.setAttribute("jvm", jvm);
                super.setView(request, JOB_MAINT_VIEW);
            }

        }
        super.getView().forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        IJobService jobService = JobServiceFactory.createInstance();
        IJob job = JobFactory.createInstance();
        JobViewModel jvm = new JobViewModel();

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

                case "create":
                    job = populateJobModel(request);

                    if (!jobService.isValid(job)) {
                        jvm.setJob(job);
                        jvm.setTasks(TaskServiceFactory.createInstance().getAllTasks());
                        // ------SET TEAMS HERE BACK
                        request.setAttribute("jvm", jvm);
                        super.setView(request, JOB_MAINT_VIEW);
                    } else {
                        job = jobService.addJob(job);
                        if (job.getId() == 0) {

                            job.addError(ErrorFactory
                                    .createInstance(9, "Sorry, something went wrong during adding a job"));
                            jvm.setJob(job);
                            jvm.setTasks(TaskServiceFactory.createInstance().getAllTasks());
                            // ------SET TEAMS HERE BACK
                            request.setAttribute("jvm", jvm);

                            super.setView(request, JOB_MAINT_VIEW);
                        }
                    }
            }

        } catch (Exception e) {
            super.setView(request, JOB_DETAILS_VIEW);
            request.setAttribute("vmError",
                    new ErrorViewModel("Something bad happened when attempting to delete the job"));
        }

        if (jvm.getJob().getErrors().size() > 0) {
//            request.setAttribute("jvm", job);
//            super.setView(request, JOB_MAINT_VIEW);
            super.getView().forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/jobs");

        }

    }

    private IJob populateJobModel(HttpServletRequest request) {
        IJob job = JobFactory.createInstance();
        job.setClientName(super.getValue(request, "client"));
        job.setDescription(super.getValue(request, "description"));

        if (!super.getValue(request, "startDate").isEmpty()) {
            job.setStart(LocalDateTime.parse(super.getValue(request, "startDate")));
        } else {
            job.setStart(null);
        }

        //set emergency and onsite
        if (request.getParameter("emergency") != null) {
            job.setIsEmergency(true);
        } else {
            job.setIsEmergency(false);
        }
        if (request.getParameter("onSite") != null) {
            job.setIsOnSite(true);
        } else {
            job.setIsOnSite(false);
        }

        int teamId = super.getInteger(request, "team");
        job.setTeamId(teamId);
        //SET TEAM OBJ
        job.setTeam(TeamServiceFactory.createInstance().getTeamDetails(teamId));

        //set tasks
        ITask task = null;
        List<ITask> tasks = TaskFactory.createListInstance();

        String skills = super.getValue(request, "tasksToAdd");
        if (!skills.isEmpty()) {
            String skillsArr[] = skills.split(",");
            for (int i = 0; i < skillsArr.length; i++) {
                int taskId = Integer.parseInt(skillsArr[i]);
                task = TaskServiceFactory.createInstance().getTask(taskId);
                tasks.add(task);

            }
        }
        job.setTasksList(tasks);

        return job;
    }

}
