package com.ats.business;

/**
 * @author Olena Stepanova
 */

/**
 * TaskServiceFactory Class responsible for creating an instance of
 * TaskService class
 */
public class TaskServiceFactory {

    /**
     * Creates an instance of TaskService class
     * @return an instance of TaskService class
     */
    public static ITaskService createInstance(){
        return new TaskService();
    }
}
