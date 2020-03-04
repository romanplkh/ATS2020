/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.controllers;

import com.ats.atssystem.business.ITeamService;
import com.ats.atssystem.business.TeamServiceFactory;
import com.ats.atssystem.models.ErrorViewModel;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Roman Pelikh
 */
public class TeamController extends CommonController {

    private static final String TEAM_MAINT_VIEW = "/team.jsp";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        if (pathInfo != null) {
            String[] pathParts = super.getUrlParts(pathInfo);

            int id = super.getInteger(pathParts[1]);

            //Create
            if (id == 0) {
                super.setView(request, TEAM_MAINT_VIEW);

                //
            } else {

            }

        } else {
            //show all teams
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            //Get action of button
            String action = super.getValue(request, "action").toLowerCase();

            ITeamService teamService = TeamServiceFactory.createInstance();

            switch (action) {
                case "create":

                    //1. Check that 2 employees are selected
                    if (!employeesSelected(request)) {
                        request.setAttribute("error", new ErrorViewModel("You must select both employees"));
                    }
                    
                     //2. Check that they are different
                    if(!employeesAreDifferent(request)){
                        request.setAttribute("error", new ErrorViewModel("You must select both employees"));
                    }
                   

                    //3. Business rule that they do not exist in other teams
                    
                    
                    
                    //4. Create team
                    
                    
                    break;
                case "update":
                    break;
                case "delete":
                    break;

            }
        } catch (Exception e) {
            super.setView(request, TEAM_MAINT_VIEW);
            request.setAttribute("error", new ErrorViewModel("Something bad happened when attempting to maintain employee"));
        }

        response.sendRedirect(request.getContextPath() + "/employees");

    }

    /**
     * Checks if both employees are selected
     *
     * @param request HttpServletRequest
     * @return boolean of a condition
     */
    private boolean employeesSelected(HttpServletRequest request) {

        int employeeId1 = super.getInteger(request, "emp1");
        int employeeId2 = super.getInteger(request, "emp2");

        return employeeId1 != 0 && employeeId2 != 0;

    }

    private boolean employeesAreDifferent(HttpServletRequest request) {

        int employeeId1 = super.getInteger(request, "emp1");
        int employeeId2 = super.getInteger(request, "emp2");

        return employeeId1 != employeeId2;

    }

}
