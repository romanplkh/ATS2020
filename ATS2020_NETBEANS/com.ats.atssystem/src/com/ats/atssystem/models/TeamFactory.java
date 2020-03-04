/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Roman Pelikh
 */
/**
 * TeamFactory class responsible for creating an instance of Team class
 */
public abstract class TeamFactory {

    /**
     * Creates an instance of Team
     *
     * @return new instance of Team class
     */
    public static ITeam createInstance() {
        return new Team();
    }

    /**
     * Creates list of instances of Team
     *
     * @return list of instances of Team
     */
    public static List<ITeam> createListInstance() {
        return new ArrayList<>();
    }

}
