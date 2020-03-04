/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.business;

/**
 *
 * @author Roman Pelikh
 */
/**
 * TeamServiceFactory Class responsible for creating an instance of TaskService class
 */
public abstract class TeamServiceFactory {

    public static ITeamService createInstance() {
        return new TeamService();
    }
}
