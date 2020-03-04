/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.controllers;

import com.ats.atssystem.business.EmployeeService;
import com.ats.atssystem.business.EmployeeServiceFactory;
import com.ats.atssystem.business.IEmployeeService;
import com.ats.atssystem.business.ITeamService;
import com.ats.atssystem.business.TeamServiceFactory;
import com.ats.atssystem.models.EmployeeFactory;
import com.ats.atssystem.models.ErrorViewModel;
import com.ats.atssystem.models.IEmployee;
import com.ats.atssystem.models.ITeam;
import com.ats.atssystem.models.TeamFactory;
import java.io.IOException;
import java.util.List;
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

            IEmployeeService employeeService = EmployeeServiceFactory.createInstance();

            List<IEmployee> employees = employeeService.getEmployees();

            ITeam team = TeamFactory.createInstance();

            String[] pathParts = super.getUrlParts(pathInfo);

            int id = super.getInteger(pathParts[1]);

            //Create
            if (id == 0) {

                request.setAttribute("employees", employees);
                request.setAttribute("team", team);
                super.setView(request, TEAM_MAINT_VIEW);

                //
            } else {

            }

        } else {
            //show all teams
        }

        super.getView().forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ITeamService teamService = TeamServiceFactory.createInstance();
        ITeam team = TeamFactory.createInstance();
        boolean busRulesAreValid = false;

        try {
            //Get action of button
            String action = super.getValue(request, "action").toLowerCase();

            switch (action) {
                case "save":

                    team = mapPropertiesToTeam(request);
                    //Populate dropdowns in case of failure validation
                    List<IEmployee> employees = EmployeeServiceFactory.createInstance().getEmployees();
                    request.setAttribute("employees", employees);
                    if (!teamService.isValid(team)) {

                        request.setAttribute("modelErrors", team.getErrors());
                        request.setAttribute("team", team);

                        super.setView(request, TEAM_MAINT_VIEW);
                    } else {

                        //Try to insert, but valdate with bussiness rules
                        ITeam teamValidated = teamService.validateMembersInTeam(
                                team.getTeamMembers().get(0).getId(), team.getTeamMembers().get(1).getId()
                        );

                        if (teamValidated.getErrors().size() > 0) {
                            request.setAttribute("error", teamValidated.getErrors());
                            super.setView(request, TEAM_MAINT_VIEW);

                        } else {

                            busRulesAreValid = true;
                            teamService.createTeam(team);
                            //TODO CHANGE WITH NEXT ITERATION CASES
                            super.setView(request, "/employees.jsp");

                        }

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

        if (!teamService.isValid(team) || !busRulesAreValid) {
            super.getView().forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/team");
        }

    }

    private ITeam mapPropertiesToTeam(HttpServletRequest request) {

        List<IEmployee> members = EmployeeFactory.createListInstance();

        String teamName = super.getValue(request, "teamName");
        int member_1 = super.getInteger(request, "member1");
        int member_2 = super.getInteger(request, "member2");

        ITeam team = TeamFactory.createInstance();

        IEmployee emp1 = EmployeeFactory.createInstance();
        emp1.setId(member_1);
        IEmployee emp2 = EmployeeFactory.createInstance();
        emp2.setId(member_2);

        members.add(emp1);
        members.add(emp2);

        team.setName(teamName);
        team.setTeamMembers(members);

        return team;
    }

}
