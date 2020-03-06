/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.repository;

/**
 *
 * @author Olena Stepanova
 */
public class TeamRepoFactory {
    
    public static ITeamRepo createInstance(){
        return new TeamRepo();
    }
}
