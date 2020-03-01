package com.ats.repository;

import com.ats.dataaccess.DALFactory;
import com.ats.dataaccess.IDAL;
import com.ats.dataaccess.IParameter;
import com.ats.dataaccess.ParameterFactory;
import com.ats.models.ITask;
import com.ats.models.TaskFactory;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.List;

public class TaskRepo extends BaseRepo implements ITaskRepo {

    private final String SP_ADD_NEW_TASK = "CALL spCreateTask(?,?,?,?,?,?)";
    private final String SP_GET_TASK_DETAILS = "CALL spGetTasks(?)";
    private final String SP_GET_ALL_TASKS = "CALL spGetTasks()";


    private IDAL dataaccess = DALFactory.createInstance();

    public TaskRepo() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int addTask(ITask task) {
        return 0;
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
        return null;
    }

    private ITask populateTaskObject(CachedRowSet rs) throws SQLException {

        ITask task = null;

        if (rs.next()) {
            task = TaskFactory.createInstance();

            task.setId(super.getInt("id", rs));
            task.setName(rs.getString("name"));
            task.setDescription(rs.getString("description"));
            task.setDuration(super.getInt("duration", rs));
            task.setCreatedAt(super.getDate("createdAt", rs));

        }

        return task;

    }
}
