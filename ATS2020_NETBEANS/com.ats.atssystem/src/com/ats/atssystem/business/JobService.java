/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.business;

import com.ats.atssystem.models.ErrorFactory;
import com.ats.atssystem.models.IEmployee;
import com.ats.atssystem.models.IJob;
import com.ats.atssystem.models.ITask;
import com.ats.atssystem.models.ITeam;
import com.ats.atssystem.models.TaskFactory;
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

        //IF model is not valid ->>> other validations will crash
        if (job.getErrors().size() > 0) {
            return job.getErrors().isEmpty();
        } else {

            //5. On call team does not matter skills
            if (!job.getIsEmergency()) {
                validateSkillset(job);
            }

            //Calculate cost of a job and get End Time
            job.calculateTasksCost();

            // 1. Validate that team is not doublebooked
            if (!isTeamAvailableToBook(job)) {
                job.addError(ErrorFactory
                        .createInstance(2, "Selected team is already scheduled for a job during this hours. "
                                + "Please select diferent date or time"));
            }

            //2. Validate job within business hours if it is not emergency
            //4. Emergency only off hours
            if (!job.getIsEmergency() && !isJobWithinBusinessHours(job)) {
                job.addError(ErrorFactory
                        .createInstance(2, "Non emergency job can be scheduled only within business hours"));
            }

            // 3. I can book onCall Team for Emergency calls
            if (job.getIsEmergency() && !isTeamOnEmergencyCall(job)) {
                job.addError(ErrorFactory
                        .createInstance(2, "Selected team is not on emergency call and cannot be scheduled for emergency calls"));
            }

            return job.getErrors().isEmpty();
        }

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
    /*
    @Override
    public void validateEmergencyJobTime(IJob job) {
        // emergency calls are only off-hours - 
        //if emergency checked time should be after 5 pm or before 8am

        LocalDateTime bookingDate = job.getStart();

        LocalTime bookingTime = bookingDate.toLocalTime();

        //YOU COULD DO IT EASIER ==>
//        if (!job.getIsEmergency() && bookingTime.isAfter(LocalTime.of(17, 0))) {
//            job.addError(ErrorFactory
//                    .createInstance(6, "Only emergency calls can be scheduled off-hours"));
//        }
        //I COMMENTED OUT IT 
        //THIS VALIDATION ALLOWS TO BOOK JOB START AT 16 and JOB END at 14 :-)
        if (!(job.getIsEmergency()
                && (bookingTime.isAfter(LocalTime.of(16, 59)))
                || (bookingTime.isBefore(LocalTime.of(8, 0))))) {
            job.addError(ErrorFactory
                    .createInstance(2, "Only emergency calls can be scheduled off-hours"));
        }
    }
*/

    @Override
    public void validateSkillset(IJob job) {
        //Team skillset should correspond to selected tasks
        List<ITask> jobSkillset = job.getTasksList();

        if (jobSkillset.size() > 0) {
            List<IEmployee> employees = job.getTeam().getTeamMembers();

            List<ITask> empSkillset = TaskFactory.createListInstance();

            //ADD ALL SKILLS FROM EMPLOYEE TO ARRAY OF SKILLS
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
                        .createInstance(5, "There is no matching skillset to perform this job"));
            }
        }

    }

    @Override
    public boolean isTeamAvailableToBook(IJob job) {
        return repo.isTeamAvailableToBook(job);
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

            //EROR over here. WE DO not have job end time yet----------------------
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

        int newJobId = repo.addJob(job);
        job.setId(newJobId);
        return job;

    }

}
