/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.repository;

import com.ats.atssystem.models.EmployeeFactory;
import com.ats.atssystem.models.ErrorFactory;
import com.ats.atssystem.models.IEmployee;
import com.ats.atssystem.models.ITeam;
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

    private IDAL dataaccess = DALFactory.createInstance();

    public TeamRepo() {
    }

    @Override
    public int addTeam(ITeam team) {
        int newTeamId = 0;

        List<Object> retVal;

        List<IParameter> params = ParameterFactory.createListInstance();

        params.add(ParameterFactory.createInstance(team.getName()));
        params.add(ParameterFactory.createInstance(team.getCreatedAt()));
        params.add(ParameterFactory.createInstance(team.getTeamMembers().get(0).getId()));
        params.add(ParameterFactory.createInstance(team.getTeamMembers().get(1).getId()));

        //For OUT team Id
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

    private ITeam populateEntityWithErrors(CachedRowSet rs) throws SQLException {

//        List<ITeam> teams = TeamFactory.createListInstance();
        ITeam team = TeamFactory.createInstance();

        while (rs.next()) {
            int existingMemberId = super.getInt("EmployeeId", rs);
            String memberName = rs.getString("FullName");
            String teamName = rs.getString("Name");
            team.addError(ErrorFactory.createInstance(2, "Member " + memberName + " with id " + existingMemberId + " already a member in a team " + teamName));
        }

        return team;

    }

}
