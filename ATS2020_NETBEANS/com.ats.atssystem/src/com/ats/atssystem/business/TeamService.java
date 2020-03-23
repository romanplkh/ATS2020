/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.business;

import com.ats.atssystem.models.ITeam;
import com.ats.atssystem.models.TeamFactory;
import com.ats.atssystem.repository.ITeamRepo;
import com.ats.atssystem.repository.TeamRepoFactory;
import java.util.List;

/**
 *
 * @author Roman Pelikh
 * @author Olena Stepanova
 */
public class TeamService implements ITeamService {

    //Repo depenancy
    private ITeamRepo repo;

    /**
     * {@inheritDoc}
     */
    @Override
    public ITeam createTeam(ITeam team) {

        repo = TeamRepoFactory.createInstance();
        team.setId(repo.addTeam(team));
        return team;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(ITeam team) {
        return team.getErrors().isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    public ITeam validateMembersInTeam(int idMember_1, int idMember_2) {
        repo = TeamRepoFactory.createInstance();
        ITeam team = TeamFactory.createInstance();
        team = repo.getMembersOnTeamToValidate(idMember_1, idMember_2);
        return team;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITeam getTeamDetails(int id) {
        repo = TeamRepoFactory.createInstance();
        return repo.getTeam(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ITeam> getTeamsLookup() {
        repo = TeamRepoFactory.createInstance();
        return repo.getTeamsLookup();
    }

    @Override
    public List<ITeam> getAllTeamsWithMembers() {
        repo = TeamRepoFactory.createInstance();
        return repo.getAllTeamsWithMembers();
    }

}
