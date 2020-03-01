package com.ats.business;

import com.ats.models.ErrorFactory;
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


        if (isValid(task)) {
            int id = repo.addTask(task);
            task.setId(id);
        } else {
            task.addError(ErrorFactory
                    .createInstance(6, "Please correct errors specified before save"));
        }

        return task;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITask getTask(int taskId) {
        return repo.getTask(taskId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ITask> getAllTasks() {
        return repo.getTasks();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(ITask task) {
        checkDuration(task);

        return task.getErrors().size() == 0;
    }

    //Business validation

    /**
     * Validates task duration for min time and set of 15min
     * @param task task object to validate business rules
     *             for duration field
     */
    private void checkDuration(ITask task){
        if (task.getDuration() % 15 != 0) {
            task.addError(ErrorFactory
                    .createInstance(4, "Duration should be a set of 15 minutes"));
        }
        if(task.getDuration() < 30){
            task.addError(ErrorFactory
                    .createInstance(5, "Duration should be minimum 30 minutes long"));
        }
    }
}
