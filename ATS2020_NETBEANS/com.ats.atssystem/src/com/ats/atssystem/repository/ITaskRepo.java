package com.ats.atssystem.repository;

import com.ats.atssystem.models.ITask;

import java.util.List;

/**
 * @author Olena Stepanova
 */

/**
 * ITaskRepo class provides methods to work with
 * database and perform necessary manipulations with task object
 */
public interface ITaskRepo {

    /**
     * Inserts a new task record in database
     * @param task a task to insert into database as a new record
     * @return int Id of last inserted record
     */
    int addTask(ITask task);

    /**
     * Searches for specified task in a database table
     * @param taskId int task Id
     * @return task record from database or null
     */
    ITask getTask(int taskId);

    /**
     * Returns a list of task records from database table
     * @return list of tasks retrieved from database
     */
    List<ITask> getTasks();
    
    /**
     * Deletes specified task from a database table
     * @param taskId int task Id
     * @return status code with SP execution result
     */
    int deleteTask(int taskId);
}
