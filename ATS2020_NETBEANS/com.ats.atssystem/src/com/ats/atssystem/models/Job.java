/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.javatuples.Pair;
import org.javatuples.Triplet;

/**
 *
 * @author Olena Stepanova
 * @author Roman Pelikh
 *
 */
public class Job extends Base implements Serializable, IJob {

    private List<ITask> tasks = TaskFactory.createListInstance();
    private ITeam team = TeamFactory.createInstance();

    private int id;
    private int teamId;
    private String description;
    private String clientName;
    private LocalDateTime start;
    private LocalDateTime end;
    private boolean isEmergency;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isOnSite;
    private int totalJobDuration;
    private List<Triplet<Integer, Double, Double>> tasksCost;

    public Job() {
    }

    public Job(int teamId, String description, String clientName, LocalDateTime start) {
        this.teamId = teamId;
        this.description = description;
        this.clientName = clientName;
        this.start = start;
    }

    //Called inside  calculateDuration of JOB
    private void calculateJobTime() {
        if (this.isOnSite) {
            this.setStart(this.getStart().minusMinutes(30));
            this.setEnd(this.getStart().plusMinutes(this.totalJobDuration).plusMinutes(30));
        } else {
            this.setEnd(this.getStart().plusMinutes(this.totalJobDuration));
        }
    }

    //Calculates the TOTAL JOB DURATION
    //Called inside CalculateCost
    private void calculateDuration(List<Pair<IEmployee, ITask>> jobTaskSetFiltered) {

        IEmployee emp1 = team.getTeamMembers().get(0);
        IEmployee emp2 = team.getTeamMembers().get(1);

        //NUMBER OF TASKS DURATION FOR EACH EMPLOYEE
        int totalTasksDurationE1 = 0;
        int totalTasksDurationE2 = 0;

        //FIND WHICH EMNPLOYEE HAS THE LONGEST NUMBER OF SKILLS
        for (Pair<IEmployee, ITask> entry : jobTaskSetFiltered) {

            if (entry.getValue0().getId() == emp1.getId()) {
                totalTasksDurationE1 += entry.getValue1().getDuration();
            }

            if (entry.getValue0().getId() == emp2.getId()) {
                totalTasksDurationE2 += entry.getValue1().getDuration();
            }

        }

        if (totalTasksDurationE1 > totalTasksDurationE2) {
            this.totalJobDuration = totalTasksDurationE1;
        } else {
            this.totalJobDuration = totalTasksDurationE2;
        }

        calculateJobTime();

    }

    public void calculateTasksCost() {

        IEmployee emp1 = team.getTeamMembers().get(0);
        IEmployee emp2 = team.getTeamMembers().get(1);

        //INT - empId
        List<Pair<IEmployee, ITask>> jobTasksEmployeeSet = new ArrayList<Pair<IEmployee, ITask>>();

        int index1 = 0;
        int index2 = 0;

        //FILL LIST WITH EMP_ID - TASK values
        for (ITask t : tasks) {
            //ADD TASK IF EMP HAS SKILLS
            if (emp1.getSkills().contains(t)) {
                jobTasksEmployeeSet.add(Pair.with(emp1, t));
            }

            if (emp2.getSkills().contains(t)) {
                jobTasksEmployeeSet.add(Pair.with(emp2, t));
            }
        }

        //FILTER TASKS BY EMPLOYEE
        List<Pair<IEmployee, ITask>> jobTaskSetFiltered = new ArrayList<>();

        for (Pair<IEmployee, ITask> entry : jobTasksEmployeeSet) {
            int filteredSize = jobTaskSetFiltered.size();

            IEmployee emp = entry.getValue0();
            ITask task = entry.getValue1();

            if (filteredSize > 0) {
                int taskIdInFiltered = jobTaskSetFiltered.get(filteredSize - 1).getValue1().getId();

                //If task is already present in filtered do not add it
                if (entry.getValue1().getId() == taskIdInFiltered) {
                    continue;
                }

            }

            if (emp2.getSkills().contains(task) && emp1.getSkills().contains(task)) {
                if (emp2.getHourlyRate() > emp1.getHourlyRate()) {
                    jobTaskSetFiltered.add(Pair.with(emp2, task));
                } else {
                    jobTaskSetFiltered.add(Pair.with(emp1, task));;
                }
            } else {
                jobTaskSetFiltered.add(Pair.with(emp, task));;
            }

        }

        //GET TOTAL JOB DURATION
        this.calculateDuration(jobTaskSetFiltered);

        //Calculate COST and REVENUE FOR EACH TASK IN THIS JOB
        List<Triplet<Integer, Double, Double>> taskCostRevenueInfo = new ArrayList<>();

        for (Pair<IEmployee, ITask> entry : jobTaskSetFiltered) {

            double cost = 0;
            double revenue = 0;
            ITask task = entry.getValue1();
            int curEmpId = entry.getValue0().getId();

            double dur = task.getDuration();

            if (emp1.getId() == curEmpId) {
                cost = emp1.getHourlyRate() * (dur / 60);
            } else {
                cost = emp2.getHourlyRate() * (dur / 60);
            }

            revenue = cost * 3;

            if (this.isEmergency) {
                revenue = cost * 4;
            }

            taskCostRevenueInfo.add(Triplet.with(task.getId(), cost, revenue));

        }

        this.tasksCost = taskCostRevenueInfo;

    }

    //------FOR TOTAL BILLABLE JOB COST FUNC------
//    @Override
//    public double calculateBillableCost() {
//        return this.revenue + (this.revenue * 0.15);
//    }
    @Override
    public ITeam getTeam() {
        return team;
    }

    @Override
    public void setTeam(ITeam team) {
        this.team = team;
    }

    @Override
    public List<ITask> getTasksList() {
        return tasks;
    }

    @Override
    public void setTasksList(List<ITask> tasks) {
        if (tasks.isEmpty()) {
            super.addError(ErrorFactory.createInstance(1, "Tasks are required"));
        } else {
            this.tasks = tasks;
        }

    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getTeamId() {
        return teamId;
    }

    @Override
    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        if ("".equals(description.trim())) {
            super.addError(ErrorFactory.createInstance(2, "Description is required"));
        } else {
            this.description = description;
        }

    }

    @Override
    public String getClientName() {
        return clientName;
    }

    @Override
    public void setClientName(String clientName) {
        if (clientName.isEmpty()) {
            this.addError(ErrorFactory.createInstance(3, "Client name is required"));
        } else {
            this.clientName = clientName;
        }

    }

    @Override
    public LocalDateTime getStart() {
        return start;
    }

    @Override
    public void setStart(LocalDateTime start) {
        if (start == null) {
            super.addError(ErrorFactory.createInstance(4, "Start time and date is required"));
        } else {
            this.start = start;
        }

    }

    @Override
    public void setEnd(LocalDateTime ldt) {
        this.end = ldt;
    }

    @Override
    public LocalDateTime getEnd() {

        return this.end;
    }

    @Override
    public LocalTime getStartTime() {
        return startTime;
    }

    @Override
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean getIsEmergency() {
        return isEmergency;
    }

    @Override
    public void setIsEmergency(boolean isEmergency) {
        this.isEmergency = isEmergency;
    }

    @Override
    public List<Triplet<Integer, Double, Double>> getTasksCost() {
        return this.tasksCost;
    }

    @Override
    public void setTasksCost(List<Triplet<Integer, Double, Double>> list) {
        this.tasksCost = list;
    }

    @Override
    public boolean getIsOnSite() {
        return this.isOnSite;
    }

    @Override
    public void setIsOnSite(boolean isOnSite) {
        this.isOnSite = isOnSite;
    }

}
