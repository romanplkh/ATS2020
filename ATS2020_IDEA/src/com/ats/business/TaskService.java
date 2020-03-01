package com.ats.business;

import com.ats.models.ITask;
import com.ats.models.TaskFactory;
import com.ats.repository.ITaskRepo;
import com.ats.repository.TaskRepoFactory;

import java.util.List;

public class TaskService implements ITaskService {

    //repo property
    private ITaskRepo repo = TaskRepoFactory.createInstance();

    public TaskService() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITask createTask(ITask task) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITask getTask(int taskId) {
        ITask task = repo.getTask(taskId);

        return task;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ITask> getAllTasks() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(ITask task) {
        return task.getErrors().isEmpty();
    }
}
