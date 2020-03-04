/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.repository;

import com.ats.atssystem.models.ITeam;
import com.ats.dataaccess.DALFactory;
import com.ats.dataaccess.IDAL;
import com.ats.dataaccess.IParameter;
import com.ats.dataaccess.ParameterFactory;
import java.sql.Types;
import java.util.List;


/**
 *
 * @author Olena Stepanova
 */
public class TeamRepo extends BaseRepo implements ITeamRepo{

    private final String SP_ADD_NEW_TEAM = "CALL spCreateTeam(?,?,?,?,?,?)";
    
    private IDAL dataaccess = DALFactory.createInstance();

    public TeamRepo() {
    }

    @Override
    public int addTeam(ITeam team) {
        int newTeamId = 0;

        List<Object> retVal;

        List<IParameter> params = ParameterFactory.createListInstance();

        params.add(ParameterFactory.createInstance(team.getName()));
        params.add(ParameterFactory.createInstance(team.getIsOnCall()));
        params.add(ParameterFactory.createInstance(team.getCreatedAt()));
        params.add(ParameterFactory.createInstance(team.getTeamMembers().get(0)));
        params.add(ParameterFactory.createInstance(team.getTeamMembers().get(1)));

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

}
