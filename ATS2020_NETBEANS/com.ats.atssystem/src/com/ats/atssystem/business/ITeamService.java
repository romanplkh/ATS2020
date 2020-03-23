/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.business;

import com.ats.atssystem.models.ITeam;
import java.util.List;

/**
 *
 * @author Roman Pelikh
 */
public interface ITeamService {

    /**
     * Enables adding team
     *
     * @param team team to add
     * @return
     */
    ITeam createTeam(ITeam team);

    /**
     * Validates presence of error messages in a Team object
     *
     * @param team team to check errors
     * @return true if no errors, otherwise false
     */
    boolean isValid(ITeam team);

    /**
     * Validates that selected members for team are different persons
     *
     * @param idMember_1 member of team
     * @param idMember_2 member of team
     * @return Team Object with validation errors
     */
    ITeam validateMembersInTeam(int idMember_1, int idMember_2);

    /**
     * Gets team details
     *
     * @param id id of team to get details about
     * @return Object Team filled with details
     */
    ITeam getTeamDetails(int id);

    /**
     * Gets all teams with members
     *
     * @return List<ITeam> populated list of teams
     */
    List<ITeam> getAllTeamsWithMembers();

    /**
     * Gets list of teams for drop down menus
     *
     * @return List of object Team
     */
    public List<ITeam> getTeamsLookup();
    
    /**
     * Deletes a team 
     * @param team team to delete
     * @return deleted team
     */
    public ITeam deleteTeam(ITeam team);
    
    public ITeam placeTeamOnCall(ITeam team); 

}
