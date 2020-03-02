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
public abstract class TeamFactory {

    public static ITeam createInstance() {
        return new Team();
    }

    public static List<ITeam> createListInstance() {
        return new ArrayList<>();
    }

}
