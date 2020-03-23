package com.ats.atssystem.business;

import com.ats.atssystem.models.ITask;

import java.util.List;

/**
 * @author Olena Stepanova
 */

/**
 * ITaskService Interface
 * Provides methods to work with repository and get necessary
 * information about task, list of
 * tasks, create new entry
 */
public interface ITaskService {

    /**
     *
     * @param task
     * @return
     */
    ITask createTask(ITask task);

    /**
     *
     * @param taskId
     * @return
     */
    ITask getTask(int taskId);

    /**
     *
     * @return
     */
    List<ITask> getAllTasks();

    /**
     * Verifies if model contains any errors
     * @param task task object to validate
     * @return boolean true or false if model is not valid
     */
    boolean isValid(ITask task);
    
    /**
     * Enables delete operation of task
     * @param task task to delete
     * @return task object
     */
    ITask deleteTask(ITask task);
    
    /**
     * Updates task info
     * @param task task to update
     * @return updated task
     */
    ITask updateTask(ITask task);
}
