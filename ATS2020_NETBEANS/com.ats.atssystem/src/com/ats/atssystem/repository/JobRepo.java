/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.repository;

import com.ats.atssystem.models.IJob;
import com.ats.atssystem.models.ITask;
import com.ats.atssystem.models.ITeam;
import com.ats.atssystem.models.JobFactory;
import com.ats.atssystem.models.TaskFactory;
import com.ats.atssystem.models.TeamFactory;
import com.ats.dataaccess.IDAL;
import com.ats.dataaccess.IParameter;
import com.ats.dataaccess.*;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Roman Pelikh
 * @author Olena Stepanova
 */
public class JobRepo extends BaseRepo implements IJobRepo {

    private final String SP_JOB_DETAILS = "CALL spGetJobDetails(?)";
    private final String SPROC_INSERT_JOB = "CALL spInsertJob(?, ?, ?, ?, ?, ?, ?, ?);";

    //Dependancy of Dataaccess layer
    private IDAL dataAccess = DALFactory.createInstance();

    /**
     * {@inheritDoc }
     */
    @Override
    public int addJob(IJob job) {
        int returnedId = 0;
        List<Object> returnedValues;

        List<IParameter> params = ParameterFactory.createListInstance();
        params.add(ParameterFactory.createInstance(job.getDescription()));
        params.add(ParameterFactory.createInstance(job.getClientName()));
        params.add(ParameterFactory.createInstance(job.getStart()));
        params.add(ParameterFactory.createInstance(job.getEnd()));
        params.add(ParameterFactory.createInstance(job.getTeamId()));
        params.add(ParameterFactory.createInstance("100,200,300,400")); //cost param
        params.add(ParameterFactory.createInstance("200,300,400,500")); //revenue param
        params.add(ParameterFactory.createInstance(job.getTasks())); //revenue param

//Get back id of inserted employee
        params.add(ParameterFactory.createInstance(returnedId, IParameter.Direction.OUT, Types.INTEGER));
        returnedValues = dataAccess.executeNonQuery(SPROC_INSERT_JOB, params);
        try {
            if (returnedValues != null) {
                returnedId = Integer.parseInt(returnedValues.get(0).toString());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return returnedId;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public IJob getJobDetails(int jobId) {
        IJob jobDetails = JobFactory.createInstance();

        try {
            List<IParameter> parms = ParameterFactory.createListInstance();
            parms.add(ParameterFactory.createInstance(jobId, IParameter.Direction.IN, Types.INTEGER));

            CachedRowSet rs = this.dataAccess.executeFill(SP_JOB_DETAILS, parms);

            jobDetails = populateJobDetails(rs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return jobDetails;
    }

    private IJob populateJobDetails(CachedRowSet rs) throws SQLException {

        IJob job = null;
        ITeam team = null;
        List<ITask> tasks = null;

        if (rs.next()) {
            job = JobFactory.createInstance();
            team = TeamFactory.createInstance();
            tasks = TaskFactory.createListInstance();
            ITask task = null;

            //set job properties
            job.setId(super.getInt("id", rs));
            job.setClientName(rs.getString("clientName"));
            job.setDescription(rs.getString("description"));
            job.setStart(super.getLocalDate("start", rs));
            job.setEnd(super.getLocalDate("end", rs));
            //set team name
            team.setName(rs.getString("team"));

            //set list of tasks to a job
            rs.beforeFirst();
           
            while (rs.next()) {
                task = TaskFactory.createInstance();
                task.setName(rs.getString("task"));
                tasks.add(task);
            }

            job.setTeam(team);
            job.setTasksList(tasks);
        }

        return job;
    }

}
