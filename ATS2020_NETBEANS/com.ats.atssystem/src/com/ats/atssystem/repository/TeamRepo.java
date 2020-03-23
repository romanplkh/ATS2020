/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.repository;

import com.ats.atssystem.models.EmployeeFactory;
import com.ats.atssystem.models.ErrorFactory;
import com.ats.atssystem.models.IEmployee;
import com.ats.atssystem.models.ITask;
import com.ats.atssystem.models.ITeam;
import com.ats.atssystem.models.TaskFactory;
import com.ats.atssystem.models.TeamFactory;
import com.ats.dataaccess.DALFactory;
import com.ats.dataaccess.IDAL;
import com.ats.dataaccess.IParameter;
import com.ats.dataaccess.ParameterFactory;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Olena Stepanova
 * @author Roman Pelikh
 */
public class TeamRepo extends BaseRepo implements ITeamRepo {

    private final String SP_ADD_NEW_TEAM = "CALL spCreateTeam(?,?,?,?,?);";
    private final String SP_MEMBERS_SELECTED_AVAILABLE = "CALL spCheckMembersSelected(?, ?);";
    private final String SP_GET_TEAM_WITH_EMP_DETAILS = "CALL spGetTeamWithEmployeesDetails(?)";
    private final String SP_GET_TEAMS_LOOKUP = "CALL spTeamLookup()";
    private final String SP_GET_ALL_TEAMS_WITH_MEMBERS = "CALL spGetAllTeams()";

    private final String SP_DELETE_TEAM = "CALL spDeleteTeam(?,?)";
    private final String SP_PLACE_TEAM_ON_CALL = "CALL spPlaceTeamOnCall(?,?)";

    private IDAL dataaccess = DALFactory.createInstance();

    public TeamRepo() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int addTeam(ITeam team) {
        int newTeamId = 0;

        List<Object> retVal;

        List<IParameter> params = ParameterFactory.createListInstance();

        params.add(ParameterFactory.createInstance(team.getName()));
        params.add(ParameterFactory.createInstance(team.getCreatedAt()));
        params.add(ParameterFactory.createInstance(team.getTeamMembers().get(0).getId()));
        params.add(ParameterFactory.createInstance(team.getTeamMembers().get(1).getId()));

        // For OUT team Id
        params.add(ParameterFactory.createInstance(newTeamId, IParameter.Direction.OUT, Types.INTEGER));

        retVal = this.dataaccess.executeNonQuery(SP_ADD_NEW_TEAM, params);

        try {
            if (retVal != null) {
                newTeamId = (int) retVal.get(0);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return newTeamId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITeam getMembersOnTeamToValidate(int idMember_1, int idMember_2) {
        ITeam team = TeamFactory.createInstance();

        List<IParameter> params = ParameterFactory.createListInstance();
        params.add(ParameterFactory.createInstance(idMember_1));
        params.add(ParameterFactory.createInstance(idMember_2));

        try {
            CachedRowSet rs = this.dataaccess.executeFill(SP_MEMBERS_SELECTED_AVAILABLE, params);
            team = populateEntityWithErrors(rs);

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        return team;
    }

    /**
     * Fills Team model with errors
     *
     * @param rs result set from query
     * @return team model with errors
     * @throws SQLException
     */
    private ITeam populateEntityWithErrors(CachedRowSet rs) throws SQLException {

        // List<ITeam> teams = TeamFactory.createListInstance();
        ITeam team = TeamFactory.createInstance();

        while (rs.next()) {
            int existingMemberId = super.getInt("EmployeeId", rs);
            String memberName = rs.getString("FullName");
            String teamName = rs.getString("Name");
            team.addError(ErrorFactory.createInstance(2, "Member " + memberName + " with id " + existingMemberId
                    + " already a member in a team " + teamName));
        }

        return team;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ITeam> getAllTeamsWithMembers() {

        List<ITeam> teams = null;

        try {

            List<IParameter> parms = ParameterFactory.createListInstance();

            CachedRowSet rs = this.dataaccess.executeFill(SP_GET_ALL_TEAMS_WITH_MEMBERS, parms);

            teams = populateTeamsWithMembers(rs);

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        return teams;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ITeam> getTeamsLookup() {
        List<ITeam> team = null;

        List<IParameter> params = ParameterFactory.createListInstance();

        try {
            CachedRowSet rs = this.dataaccess.executeFill(SP_GET_TEAMS_LOOKUP, params);
            team = populateTeamsLookup(rs);

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        return team;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITeam getTeam(int id) {
        ITeam team = TeamFactory.createInstance();

        try {
            List<IParameter> parms = ParameterFactory.createListInstance();

            parms.add(ParameterFactory.createInstance(id, IParameter.Direction.IN, Types.INTEGER));
            CachedRowSet rs = this.dataaccess.executeFill(SP_GET_TEAM_WITH_EMP_DETAILS, parms);

            team = populateTeamWithDetails(rs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return team;
    }

    private ITeam populateTeamWithDetails(CachedRowSet rs) throws SQLException {
        ITeam team = null;

        List<IEmployee> employees = null;
        IEmployee emp = null;

        List<ITask> skills = null;
        ITask skill = null;

        if (rs.next()) {
            team = TeamFactory.createInstance();
            emp = EmployeeFactory.createInstance();
            employees = EmployeeFactory.createListInstance();

            skill = TaskFactory.createInstance();
            skills = TaskFactory.createListInstance();

            team.setId(super.getInt("teamId", rs));
            emp.setId(super.getInt("empId", rs));
            emp.setFirstName(rs.getString("empFname"));
            emp.setLastName("empLname");
            emp.setHourlyRate(super.getDouble("hRate", rs));

            // set skills
            skill.setId(super.getInt("empSkillId", rs));
            skills.add(skill);
            // add list of skilld to emp
            emp.setSkills(skills);

            while (rs.next()) {
                if (emp.getId() == super.getInt("empId", rs)) {
                    rs.previous();
                    while (rs.next() && (emp.getId() == super.getInt("empId", rs))) {
                        // add other skills
                        skill = TaskFactory.createInstance();
                        skill.setId(super.getInt("empSkillId", rs));
                        skills.add(skill);
                        emp.setSkills(skills);
                    }
                    rs.previous();
                } else {
                    // add first emp to list of employees
                    employees.add(emp);

                    // new empl -> create instance and populate info
                    // create new list of skills
                    emp = EmployeeFactory.createInstance();
                    emp.setId(super.getInt("empId", rs));
                    emp.setFirstName(rs.getString("empFname"));
                    emp.setLastName("empLname");
                    emp.setHourlyRate(super.getDouble("hRate", rs));

                    skill = TaskFactory.createInstance();
                    skills = TaskFactory.createListInstance();

                    // set skills
                    skill.setId(super.getInt("empSkillId", rs));
                    skills.add(skill);
                    employees.add(emp);
                }
            }

            team.setTeamMembers(employees);
        }

        return team;
    }

    /**
     * {@inheritDoc}
     */
    private List<ITeam> populateTeamsLookup(CachedRowSet rs) throws SQLException {

        List<ITeam> teams = null;

        ITeam team = null;

        try {

            if (rs.next()) {
                teams = TeamFactory.createListInstance();
                rs.previous();

                while (rs.next()) {
                    team = TeamFactory.createInstance();
                    team.setId(super.getInt("id", rs));
                    team.setName(rs.getString("Name"));
                    team.setIsOnCall(rs.getBoolean("isOnCall"));
                    teams.add(team);

                }

            }

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }

        return teams;

    }

    private List<ITeam> populateTeamsWithMembers(CachedRowSet rs) throws SQLException {
        List<ITeam> teams = TeamFactory.createListInstance();
        ITeam newTeam = null;
        IEmployee emp = null;

        try {

            while (rs.next()) {
                emp = EmployeeFactory.createInstance();
                newTeam = TeamFactory.createInstance();

                // SET VALUES FOR EMPLOYEE
                emp.setId(super.getInt("EmployeeId", rs));
                emp.setFirstName(rs.getString("firstName"));
                emp.setLastName(rs.getString("lastName"));

                // SET VALUES FOR TEAM
                newTeam.setName(rs.getString("name"));
                newTeam.setId(super.getInt("teamId", rs));
                newTeam.setIsOnCall(rs.getBoolean("isOnCall"));

                if (teams.size() == 0) {
                    // Add first member to lsit
                    newTeam.getTeamMembers().add(emp);
                    teams.add(newTeam);
                } else {
                    ITeam lastTeam = teams.get(teams.size() - 1);
                    if (lastTeam.getId() == newTeam.getId()) {
                        // Add teammember to team
                        lastTeam.getTeamMembers().add(emp);
                    } else {
                        // Create new team
                        newTeam.getTeamMembers().add(emp);
                        teams.add(newTeam);
                    }
                }

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return teams;

    }

    @Override
    public int deleteTeam(int id) {
        int rowAff = 0;

        List<Object> retVal;

        List<IParameter> params = ParameterFactory.createListInstance();

        params.add(ParameterFactory.createInstance(id));

        // For OUT code status
        params.add(ParameterFactory.createInstance(rowAff, IParameter.Direction.OUT, Types.INTEGER));

        retVal = this.dataaccess.executeNonQuery(SP_DELETE_TEAM, params);

        try {
            if (retVal != null) {
                rowAff = (int) retVal.get(0);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return rowAff;
    }

    @Override
    public int placeTeamOnCall(int teamId) {

        int code = 0;

        List<Object> retVal;

        List<IParameter> params = ParameterFactory.createListInstance();

        params.add(ParameterFactory.createInstance(teamId));

        // For OUT code status
        params.add(ParameterFactory.createInstance(code, IParameter.Direction.OUT, Types.INTEGER));

        retVal = this.dataaccess.executeNonQuery(SP_PLACE_TEAM_ON_CALL, params);

        try {
            if (retVal != null) {
                code = (int) retVal.get(0);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return code;

    }

}
