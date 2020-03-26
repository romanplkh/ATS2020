/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.controllers;

import com.ats.atssystem.models.DashboardVM;
import com.ats.atssystem.repository.IJobRepo;
import com.ats.atssystem.repository.JobRepoFactory;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Roman Pelikh
 */
public class DashboardController extends CommonController {

    private static final String DASHBOARD_VIEW = "/dashboard.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null) {

            //SHOW ALL DATA ON DASHBOARD
            IJobRepo repos = JobRepoFactory.createInstance();
            DashboardVM vm = repos.getFinancialYearlyStats();

            Gson gson = new Gson();

            String financialYear = gson.toJson(vm);

            request.setAttribute("vm", financialYear);
            super.setView(request, DASHBOARD_VIEW);

        }

        super.getView().forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
