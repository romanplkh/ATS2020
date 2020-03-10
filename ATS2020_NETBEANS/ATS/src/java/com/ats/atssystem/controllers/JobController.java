/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.controllers;

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null) {
            //show all jobs
        } else {
            //job/:id/[details]

            String[] pathParts = super.getUrlParts(pathInfo);
            //job id
            int jobId = super.getInteger(pathParts[1]);

            if (jobId != 0) {

                String page = pathParts[2].toLowerCase();

                switch (page) {
                    case "details":
                        //get a job with that id and assosiated info 
                        // VM instance
                        //request.setAttribute("jobVm", page);
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
