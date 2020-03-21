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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import javax.sql.rowset.CachedRowSet;
import org.javatuples.Triplet;

/**
 *
 * @author Roman Pelikh
 * @author Olena Stepanova
 */
enum FieldTypes {
    TaskId,
    Cost,
    Revenue

}

public class JobRepo extends BaseRepo implements IJobRepo {

    private final String SP_JOB_DETAILS = "CALL spGetJobDetails(?)";
    private final String SPROC_INSERT_JOB = "CALL spInsertJob(?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private final String SPROC_DELETE_JOB = "CALL spDeleteJob(?, ?)";
    private final String SPROC_GET_SCHEDULED_JOBS = "CALL spGetJobsSchedule(?)";
    private final String SP_TEAM_IS_AVAILABLE = "CALL spTeamIsAvailable(?, ?);";
    private final String SP_TEAM_IS_ON_EMERGENCY = "CALL TeamIsOnEmergency(?);";

    //Dependancy of Dataaccess layer
    private IDAL dataAccess = DALFactory.createInstance();

    /**
     * {@inheritDoc }
     */
    @Override
    public int addJob(IJob job) {
        int returnedId = 0;
        List<Object> returnedValues;

        //GET STRINGS 
        job.calculateTasksCost();

        String taskparams = getStringValues(job.getTasksCost(), FieldTypes.TaskId);
        String costParams = getStringValues(job.getTasksCost(), FieldTypes.Cost);
        String revenueParams = getStringValues(job.getTasksCost(), FieldTypes.Revenue);

        List<IParameter> params = ParameterFactory.createListInstance();
        params.add(ParameterFactory.createInstance(job.getDescription()));
        params.add(ParameterFactory.createInstance(job.getClientName()));
        params.add(ParameterFactory.createInstance(job.getStart()));
        params.add(ParameterFactory.createInstance(job.getEnd()));
        params.add(ParameterFactory.createInstance(job.getTeamId()));
        params.add(ParameterFactory.createInstance(costParams)); //cost param
        params.add(ParameterFactory.createInstance(revenueParams)); //revenue param
        params.add(ParameterFactory.createInstance(taskparams)); //task param

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

    /**
     * {@inheritDoc }
     */
    @Override
    public int deleteJob(int jobId) {
        int rowsAffected = 0;

        List<Object> returnedValues;

        List<IParameter> params = ParameterFactory.createListInstance();

        params.add(ParameterFactory.createInstance(jobId));
        params.add(ParameterFactory.createInstance(rowsAffected, IParameter.Direction.OUT, Types.INTEGER));
        returnedValues = this.dataAccess.executeNonQuery(SPROC_DELETE_JOB, params);

        try {
            if (returnedValues != null) {
                rowsAffected = (int) returnedValues.get(0);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return rowsAffected;

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

    @Override
    public List<ITeam> getScheduledJobs(String date) {
        List<ITeam> teams = TeamFactory.createListInstance();

        try {
            List<IParameter> parms = ParameterFactory.createListInstance();
            parms.add(ParameterFactory.createInstance(date, IParameter.Direction.IN, Types.VARCHAR));
            CachedRowSet rs = this.dataAccess.executeFill(SPROC_GET_SCHEDULED_JOBS, parms);

            teams = populateTeamsWithJobs(rs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return teams;
    }

    private List<ITeam> populateTeamsWithJobs(CachedRowSet rs) throws SQLException {
        List<ITeam> list = TeamFactory.createListInstance();
        ITeam team = null;
        List<IJob> jobs = null;
        IJob job = null;

        while (rs.next()) {
            team = TeamFactory.createInstance();
            job = JobFactory.createInstance();
            jobs = JobFactory.createListInstance();

            team.setName(rs.getString("team"));
            job.setId(super.getInt("id", rs));
            job.setStartTime(super.getLocalTime("start_time", rs));
            job.setEndTime(super.getLocalTime("end_time", rs));
            jobs.add(job);

            if (rs.next()) {
                if (team.getName().equalsIgnoreCase(rs.getString("team"))) {
                    rs.previous();
                    while (rs.next() && team.getName().equalsIgnoreCase(rs.getString("team"))) {
                        job = JobFactory.createInstance();
                        job.setStartTime(super.getLocalTime("start_time", rs));
                        job.setEndTime(super.getLocalTime("end_time", rs));
                        jobs.add(job);
                    }
                    rs.previous();
                } else {
                    rs.previous();
                }
            } else {
                rs.previous();
            }

            team.setJobs(jobs);
            list.add(team);
        }

        return list;
    }

    private String getStringValues(List<Triplet<Integer, Double, Double>> values, FieldTypes type) {

        String result = "";

        switch (type) {

            case TaskId:
                result = tripletValuesIterator(values, 0);
                break;
            case Cost:
                result = tripletValuesIterator(values, 1);
                break;
            case Revenue:
                result = tripletValuesIterator(values, 2);
                break;
        }

        result = result.substring(0, result.lastIndexOf(","));

        return result;
    }

    private String tripletValuesIterator(List<Triplet<Integer, Double, Double>> values, int index) {

        String val = "";

        for (Triplet<Integer, Double, Double> entry : values) {

            if (index == 1 || index == 2) {
                double value = (double) entry.getValue(index);

                if (value % 1 == 0) {
                    val += Math.round(value) + ",";
                } else {
                    val += entry.getValue(index) + ",";
                }

            } else {
                val += entry.getValue(index) + ",";
            }

        }

        return val;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTeamAvailableToBook(IJob job) {

        int retValue = -1;

        List<IParameter> params = ParameterFactory.createListInstance();

        params.add(ParameterFactory.createInstance(job.getTeam().getId()));
        params.add(ParameterFactory.createInstance(job.getStart()));
        params.add(ParameterFactory.createInstance(job.getEnd()));

        try {

            retValue = Integer.parseInt(this.dataAccess.executeScalar(SP_TEAM_IS_AVAILABLE, params).toString());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return retValue == 0;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTeamOnEmergencyCall(IJob job) {

        int retValue = -1;

        List<IParameter> params = ParameterFactory.createListInstance();

        params.add(ParameterFactory.createInstance(job.getTeam().getId()));

        try {
            retValue = Integer.parseInt(this.dataAccess.executeScalar(SP_TEAM_IS_ON_EMERGENCY, params).toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return retValue == 1;

    }

}
