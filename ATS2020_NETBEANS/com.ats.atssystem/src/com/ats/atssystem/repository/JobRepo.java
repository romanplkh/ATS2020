/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.repository;

import com.ats.atssystem.models.IJob;
import com.ats.dataaccess.IDAL;
import com.ats.dataaccess.IParameter;
import com.ats.dataaccess.*;
import java.util.List;

/**
 *
 * @author Roman Pelikh
 */
public class JobRepo extends BaseRepo implements IJobRepo {

  private final String SP_JOB_DETAILS = "CALL getJobDetails(?)";
    private final String SPROC_INSERT_JOB = "CALL spInsertJob();";

    //Dependancy of Dataaccess layer
    private IDAL dataAccess;

    @Override
    public int addJob(IJob job) {
        int returnedId = 0;
        List<Object> returnedValues;

        List<IParameter> params = ParameterFactory.createListInstance();
//        params.add(ParameterFactory.createInstance(employee.getFirstName()));
//        params.add(ParameterFactory.createInstance(employee.getLastName()));
//        params.add(ParameterFactory.createInstance(employee.getSin()));
//        params.add(ParameterFactory.createInstance(employee.getHourlyRate()));

//Get back id of inserted employee
        //params.add(ParameterFactory.createInstance(returnedId, IParameter.Direction.OUT, Types.INTEGER));
        //returnedValues = dataAccess.executeNonQuery(SPROC_INSERT_EMPLOYEE, params);
//        try {
//            if (returnedValues != null) {
//                returnedId = Integer.parseInt(returnedValues.get(0).toString());
//            }
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }

        return returnedId;
    }

     /**
     * {@inheritDoc }
     */
    @Override
    public JobDetailsViewModel getJobDetails(int jobId) {
        JobDetailsViewModel jobDetails = new JobDetailsViewModel();

        try {
            List<IParameter> parms = ParameterFactory.createListInstance();
            parms.add(ParameterFactory.createInstance(jobId, IParameter.Direction.IN, Types.INTEGER));

            CachedRowSet rs = this.dataaccess.executeFill(SP_JOB_DETAILS, parms);

            jobDetails = populateJobDetails(rs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return jobDetails;
    }
    
    private JobDetailsViewModel populateJobDetails(CachedRowSet rs) throws SQLException {
        
        JobDetailsViewModel vm = null;
        ITeam team = null;
        IJob job = null;
        List<ITask> tasks = null;
        
        while (rs.next()) {
            vm = new JobDetailsViewModel();
            team = TeamFactory.createInstance();
            tasks = TaskFactory.createListInstance();

            
            //set job properties
            //set list of tasks to a job
            
                       
            team.setName(rs.getString("team"));
            
            vm.setTeam(team);
            vm.setJob(job);           
        }
        
        return vm;
    }

    }

