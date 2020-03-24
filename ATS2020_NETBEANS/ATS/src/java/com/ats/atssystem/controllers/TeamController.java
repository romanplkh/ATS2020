/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.controllers;

import com.ats.atssystem.business.EmployeeServiceFactory;
import com.ats.atssystem.business.IEmployeeService;
import com.ats.atssystem.business.ITeamService;
import com.ats.atssystem.business.TeamServiceFactory;
import com.ats.atssystem.models.EmployeeFactory;
import com.ats.atssystem.models.ErrorFactory;
import com.ats.atssystem.models.ErrorViewModel;
import com.ats.atssystem.models.IEmployee;
import com.ats.atssystem.models.ITeam;
import com.ats.atssystem.models.TeamFactory;
import java.io.IOException;
import java.util.Date;
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
    private static final String TEAMS_VIEW = "/teams.jsp";
    private static final String TEAMS_DETAILS_VIEW = "/teamDetails.jsp";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        ITeamService teamService = TeamServiceFactory.createInstance();

        if (pathInfo != null) {

            String[] pathParts = super.getUrlParts(pathInfo);

            int id = super.getInteger(pathParts[1]);

            //Create
            if (id == 0) {
                IEmployeeService employeeService = EmployeeServiceFactory.createInstance();
                List<IEmployee> employees = employeeService.getEmployees();

                request.setAttribute("employees", employees);
                super.setView(request, TEAM_MAINT_VIEW);

                //Details, Update, Delete
            } else {

                // team/:id/[details]
                //Details
                String urlContext = pathParts[2];

                ITeam teamDetails = teamService.getTeamDetailsWithMembers(id);
                //
                if (teamDetails != null) {
                    switch (urlContext.toLowerCase()) {
                        case "details":
                            request.setAttribute("team", teamDetails);
                            super.setView(request, TEAMS_DETAILS_VIEW);
                            break;
                    }

                } else {
                    request.setAttribute("error",
                            new ErrorViewModel("Requested team was not found"));
                    super.setView(request, TEAMS_DETAILS_VIEW);
                }
            }

        } else {
            //SHOW ALL TEAMS WITH TEAM MEMBERS
            List<ITeam> teams = teamService.getAllTeamsWithMembers();

            request.setAttribute("teams", teams);
            super.setView(request, TEAMS_VIEW);

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
            int teamId = super.getInteger(request, "teamId");

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
                        ITeam teamToValidate = teamService.validateMembersInTeam(
                                team.getTeamMembers().get(0).getId(), team.getTeamMembers().get(1).getId()
                        );

                        if (teamToValidate.getErrors().size() > 0) {
                            request.setAttribute("error", teamToValidate.getErrors());
                            request.setAttribute("team", team);
                            super.setView(request, TEAM_MAINT_VIEW);
                        } else {

                            busRulesAreValid = true;
                            team = teamService.createTeam(team);

                            if (team.getId() == 0) {
                                team.addError(ErrorFactory.createInstance(1, "Something went wrong"
                                        + " during team creation"));
                                request.setAttribute("team", team);
                                super.setView(request, TEAM_MAINT_VIEW);
                            } else {

                                super.setView(request, TEAMS_VIEW);
                            }
                        }

                    }

                    //3. Business rule that they do not exist in other teams
                    //4. Create team
                    break;
                case "oncall":
                    team = teamService.placeTeamOnCall(teamService.getTeamDetailsWithMembers(teamId));

                    if (team.getErrors().isEmpty()) {
                        super.setView(request, TEAMS_VIEW);
                    } else {
                        request.setAttribute("team", team);
                        super.setView(request, TEAMS_DETAILS_VIEW);
                    }

                    break;
                case "delete":

                    team = teamService.deleteTeam(teamService.getTeamDetailsWithMembers(teamId));

                    if (team.getErrors().isEmpty()) {
                        super.setView(request, TEAMS_VIEW);
                    } else {
                        request.setAttribute("team", team);
                        super.setView(request, TEAMS_DETAILS_VIEW);
                    }
                    break;

            }
        } catch (Exception e) {
            super.setView(request, TEAM_MAINT_VIEW);
            request.setAttribute("error",
                    new ErrorViewModel("Something bad happened when attempting to maintain a team"));
        }

        //if (!teamService.isValid(team) || !busRulesAreValid) {
        if (team.getErrors().size() > 0) {
            super.getView().forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/teams");
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
        team.setCreatedAt(new Date());

        return team;
    }

}
