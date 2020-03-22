/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.business;

/**
 *
 * @author Olena Stepanova
 */
/**
 * JobServiceFactory Class responsible for creating an instance of
 * TaskService class
 */
public abstract class JobServiceFactory {
    
    /**
     * Creates an instance of JobService class
     * @return an instance of JobService class
     */
    public static IJobService createInstance(){
        return new JobService();
    }
}
