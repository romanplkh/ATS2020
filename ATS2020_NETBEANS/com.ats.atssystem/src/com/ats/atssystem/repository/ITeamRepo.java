/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.repository;

import com.ats.atssystem.models.ITeam;
import java.util.List;

/**
 *
 * @author Olena Stepanova
 */
/**
 * ITeamRepo class provides methods to work with database and perform necessary
 * manipulations with team object
 */
public interface ITeamRepo {

    /**
     * Creates a team record in database
     *
     * @param team compound teamEmployee object to save in DB
     * @return int last inserted record id
     */
    int addTeam(ITeam team);

    /**
     * Validates that selected members for team are different persons
     *
     * @param idMember_1 member of team
     * @param idMember_2 member of team
     * @return Team Object with validation errors
     */
    ITeam getMembersOnTeamToValidate(int idMember_1, int idMember_2);

    /**
     * Retrieves a team from data base
     *
     * @param id team id to retrieve
     * @return Team Object filled with data
     */
    ITeam getTeam(int id);

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
     * Deletes specified team from database
     *
     * @param id team Id param
     * @return number of rows affected
     */
    public int deleteTeam(int id);

    /**
     * Places team on call
     *
     * @param teamId team id to place on call
     * @return code number of performed action
     */
    public int placeTeamOnCall(int teamId);

    /**
     * Gets team details with team members
     *
     * @param teamId id of team to get details about
     * @return Object Team filled with data or null
     */
    ITeam getTeamDetails(int teamId);
    
    /**
     * Returns currently on call team
     * @return team object or null
     */
    ITeam getTeamOnCall();
}
