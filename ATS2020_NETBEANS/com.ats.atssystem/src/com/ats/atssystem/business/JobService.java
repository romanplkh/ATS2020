/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.business;

import com.ats.atssystem.models.EmployeeFactory;
import com.ats.atssystem.models.ErrorFactory;
import com.ats.atssystem.models.IEmployee;
import com.ats.atssystem.models.IJob;
import com.ats.atssystem.models.ITask;
import com.ats.atssystem.models.ITeam;
import com.ats.atssystem.models.TaskFactory;
import com.ats.atssystem.models.TeamFactory;
import com.ats.atssystem.repository.IJobRepo;
import com.ats.atssystem.repository.JobRepoFactory;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 *
 * @author Olena Stepanova
 * @author Roman Pelikh
 */
public class JobService implements IJobService {

    public JobService() {
    }

    private IJobRepo repo = JobRepoFactory.createInstance();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(IJob job) {
        //--UNCOMMENT--------
        //validateEmergencyJobTime(job);
//        if (!job.getIsEmergency()) {
//            validateSkillset(job);
//        } else if (this.isTeamOnEmergencyCall(job) && job.getIsEmergency() == false) {
//            job.addError(ErrorFactory.createInstance(2, "Selected team is available only for emergency calls. Please select different team"));
//        }

        return job.getErrors().isEmpty();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public IJob getJobDetails(int jobId) {
        return repo.getJobDetails(jobId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public IJob deleteJob(IJob job) {
        if (repo.deleteJob(job.getId()) == 0) {
            job.addError(ErrorFactory
                    .createInstance(1, "Something went wrong. Job was not deleted. Try again"));

        }
        return job;
    }

    @Override
    public List<ITeam> getScheduledJobs(String date) {
        return repo.getScheduledJobs(date);
    }

    //TO TELL YOU
    @Override
    public void validateEmergencyJobTime(IJob job) {
        // emergency calls are only off-hours - 
        //if emergency checked time should be after 5 pm or before 8am

        LocalDateTime bookingDate = job.getStart();

        LocalTime bookingTime = bookingDate.toLocalTime();

        //YOU COULD DO IT EASIER ==>
        if (!job.getIsEmergency() && bookingTime.isAfter(LocalTime.of(17, 0))) {
            job.addError(ErrorFactory
                    .createInstance(2, "Only emergency calls can be scheduled off-hours"));
        }

        //I COMMENTED OUT IT 
        //THIS VALIDATION ALLOWS TO BOOK JOB START AT 16 and JOB END at 14 :-)
//        if (!(job.getIsEmergency()
//                && (bookingTime.isAfter(LocalTime.of(16, 59)))
//                || (bookingTime.isBefore(LocalTime.of(8, 0))))) {
//            job.addError(ErrorFactory
//                    .createInstance(2, "Only emergency calls can be scheduled off-hours"));
//        }
    }

    @Override
    public void validateSkillset(IJob job) {
        //Team skillset should correspond to selected tasks

        List<ITask> jobSkillset = job.getTasksList();

        List<IEmployee> employees = job.getTeam().getTeamMembers();

        List<ITask> empSkillset = TaskFactory.createListInstance();

        for (IEmployee e : employees) {
            empSkillset.addAll(e.getSkills());
        }

        int count = 0;
        for (ITask skill : jobSkillset) {
            if (!empSkillset.contains(skill)) {
                count++;
            }
        }

        if (count > 0) {
            job.addError(ErrorFactory
                    .createInstance(3, "There is no matching skillset to perform this job"));
        }

    }

    @Override
    public IJob isTeamAvailableToBook(IJob job) {
        if (!repo.isTeamAvailableToBook(job)) {
            job.addError(ErrorFactory
                    .createInstance(4, "This team already has a job during this time. Please select another hours"));
        }

        return job;

    }

    @Override
    public boolean isTeamOnEmergencyCall(IJob job) {
        return repo.isTeamOnEmergencyCall(job);

    }

    public boolean isJobWithinBusinessHours(IJob job) {

        boolean isValid = true;
        if (!job.getIsEmergency()) {

            //Validate DAY 
            DayOfWeek startDOW = job.getStart().getDayOfWeek();
            DayOfWeek endDOW = job.getEnd().getDayOfWeek();

            if (startDOW == DayOfWeek.SATURDAY
                    || startDOW == DayOfWeek.SUNDAY
                    || endDOW == DayOfWeek.SATURDAY
                    || endDOW == DayOfWeek.SUNDAY) {

                isValid = false;
            }

            if (job.getStart().getHour() < 8
                    || job.getStart().getHour() >= 17
                    || (job.getEnd().getHour() == 17 && job.getEnd().getMinute() > 0)
                    || job.getEnd().getHour() > 17) {
                isValid = false;
            }

        }

        return isValid;
    }

    @Override
    public IJob addJob(IJob job) {

        job.setClientName("ClientName");
        job.setDescription("Description");
        job.setStart(LocalDateTime.now().minusHours(5));
        job.setTeamId(1);

        ITask t1 = TaskFactory.createInstance(1, "Network design", "descripoto", 45);
        ITask t2 = TaskFactory.createInstance(2, "Network design", "descripoto", 60);
        ITask t3 = TaskFactory.createInstance(3, "Network design", "descripoto", 240);
        ITask t4 = TaskFactory.createInstance(4, "Network design", "descripoto", 120);

        IEmployee e1 = EmployeeFactory.createInstance();
        e1.setId(1);
        e1.setHourlyRate(34);
        e1.getSkills().add(TaskFactory.createInstance(2, "Network", "xxxx", 45));
        e1.getSkills().add(TaskFactory.createInstance(4, "Network", "xxxx", 45));

        IEmployee e2 = EmployeeFactory.createInstance();
        e2.setId(2);
        e2.setHourlyRate(42);
        e2.getSkills().add(TaskFactory.createInstance(1, "Network", "xxxx", 45));
        e2.getSkills().add(TaskFactory.createInstance(3, "Network", "xxxx", 45));

        List<IEmployee> teamMembers = EmployeeFactory.createListInstance();
        teamMembers.add(e1);
        teamMembers.add(e2);

        List<ITask> tasks = TaskFactory.createListInstance();
        tasks.add(t1);
        tasks.add(t2);
        tasks.add(t3);
        tasks.add(t4);

        job.setTasksList(tasks);

        ITeam team = TeamFactory.createInstance();
        team.setId(1);

        team.setTeamMembers(teamMembers);;

        job.setClientName("ClientName");
        job.setTeam(team);

        repo.addJob(job);

        return job;

    }

}
