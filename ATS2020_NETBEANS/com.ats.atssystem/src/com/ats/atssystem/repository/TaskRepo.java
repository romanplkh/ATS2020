package com.ats.atssystem.repository;

import com.ats.dataaccess.DALFactory;
import com.ats.dataaccess.IDAL;
import com.ats.dataaccess.IParameter;
import com.ats.dataaccess.ParameterFactory;
import com.ats.atssystem.models.ITask;
import com.ats.atssystem.models.TaskFactory;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class TaskRepo extends BaseRepo implements ITaskRepo {

    private final String SP_ADD_NEW_TASK = "CALL spCreateTask(?,?,?,?,?)";
    private final String SP_GET_TASK_DETAILS = "CALL spGetTasks(?)";
    private final String SP_GET_ALL_TASKS = "CALL spGetTasks(?)";


    private IDAL dataaccess = DALFactory.createInstance();

    public TaskRepo() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int addTask(ITask task) {

        int newTaskId = 0;

        List<Object> retVal;

        List<IParameter> params = ParameterFactory.createListInstance();

        params.add(ParameterFactory.createInstance(task.getName()));
        params.add(ParameterFactory.createInstance(task.getDuration()));
        params.add(ParameterFactory.createInstance(task.getDescription()));
        params.add(ParameterFactory.createInstance(task.getCreatedAt()));

        //For OUT task Id
        params.add(ParameterFactory.createInstance(newTaskId, IParameter.Direction.OUT, Types.INTEGER));

        retVal = this.dataaccess.executeNonQuery(SP_ADD_NEW_TASK, params);

        try {
            if (retVal != null) {
                newTaskId = (int) retVal.get(0);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return newTaskId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITask getTask(int taskId) {
        ITask task = TaskFactory.createInstance();

        try {
            List<IParameter> parms = ParameterFactory.createListInstance();
            parms.add(ParameterFactory.createInstance(taskId));

            CachedRowSet rs = this.dataaccess.executeFill(SP_GET_TASK_DETAILS, parms);

            task = populateTaskObject(rs);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return task;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ITask> getTasks() {
        List<ITask> tasks = TaskFactory.createListInstance();

        try {
            List<IParameter> parms = ParameterFactory.createListInstance();
            parms.add(ParameterFactory.createInstance(null, IParameter.Direction.IN, Types.NULL));

            CachedRowSet rs = this.dataaccess.executeFill(SP_GET_ALL_TASKS, parms);

            tasks = populateListOfTasks(rs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tasks;
    }

    /**
     * Populates task's properties with values returned from database for
     * specified task
     *
     * @param rs CachedRowSet data set returned from database as a result
     *           of sql statement execution
     * @return task populated task object or null
     * @throws SQLException
     */
    private ITask populateTaskObject(CachedRowSet rs) throws SQLException {

        ITask task = null;

        if (rs.next()) {
            task = TaskFactory.createInstance();

            task.setId(super.getInt("id", rs));
            task.setName(rs.getString("name"));
            task.setDescription(rs.getString("description"));
            task.setDuration(super.getInt("duration", rs));

        }

        return task;

    }

    /**
     * Returns a list of tasks retrieved from database table
     * @param rs CachedRowSet data set returned from database as a result
     *       of sql statement execution
     * @return list of tasks or null
     * @throws SQLException
     */
    private List<ITask> populateListOfTasks(CachedRowSet rs) throws SQLException {
        List<ITask> tasksList = TaskFactory.createListInstance();
        ITask task;

        while (rs.next()) {
            task = TaskFactory.createInstance();

            task.setId(super.getInt("id", rs));
            task.setName(rs.getString("name"));
            task.setDescription(rs.getString("description"));
            tasksList.add(task);
        }

        return tasksList;
    }
}
