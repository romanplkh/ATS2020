/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.repository;

import com.ats.atssystem.models.IJob;
import com.ats.atssystem.models.ITask;
import com.ats.atssystem.models.ITeam;
import com.ats.atssystem.models.JobDetailsViewModel;
import com.ats.atssystem.models.TaskFactory;
import com.ats.atssystem.models.TeamFactory;
import com.ats.dataaccess.DALFactory;
import com.ats.dataaccess.IDAL;
import com.ats.dataaccess.IParameter;
import com.ats.dataaccess.ParameterFactory;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Olena Stepanova
 */
public class JobRepo extends BaseRepo implements IJobRepo {

    private final String SP_JOB_DETAILS = "CALL getJobDetails(?)";

    private IDAL dataaccess = DALFactory.createInstance();

    public JobRepo() {
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
