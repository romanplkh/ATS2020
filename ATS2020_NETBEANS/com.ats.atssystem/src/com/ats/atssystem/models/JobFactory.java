/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Roman Pelikh
 */
/**
 * JobFactory class responsible for creating an instance of Job class
 */
public abstract class JobFactory {

    /**
     * Creates an instance of a Job
     *
     * @return new instance of Job class
     */
    public static IJob createInstance() {
        return new Job();
    }

    /**
     * Creates an instance of a Job
     *
     * @return new instance of a Job class
     */
    public static IJob createInstance(int teamId, String description, String clientName, LocalDateTime start) {
        return new Job(teamId, description, clientName, start);
    }

    /**
     * Creates list of instances of a Job
     *
     * @return list of instances of a Job
     */
    public static List<IJob> createListInstance() {
        return new ArrayList<IJob>();
    }
}
