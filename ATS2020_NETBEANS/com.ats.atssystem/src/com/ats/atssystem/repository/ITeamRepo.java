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
 * ITeamRepo class provides methods to work with database and perform necessary manipulations with team object
 */
public interface ITeamRepo {

    /**
     * Creates a team record in database
     *
     * @param team compound teamEmployee object to save in DB
     * @return int last inserted record id
     */
    int addTeam(ITeam team);

    ITeam getMembersOnTeamToValidate(int idMember_1, int idMember_2);

}
