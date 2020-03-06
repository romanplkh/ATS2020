package com.ats.atssystem.repository;

/**
 * @author Olena Stepanova
 */

/**
 * TaskRepoFactory class responsible for creating an
 * instance of TaskRepo class
 */
public class TaskRepoFactory {

    /**
     * Creates an instance of TaskRepo
     * @return new instance of TaskRepo class
     */
    public static ITaskRepo createInstance() {
        return new TaskRepo();
    }
}
