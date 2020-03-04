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

    boolean isValid(ITeam team);

    ITeam validateMembersInTeam(int idMember_1, int idMember_2);

}
