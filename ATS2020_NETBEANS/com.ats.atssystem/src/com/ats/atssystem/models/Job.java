/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.models;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;
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
    private int jobDuration;
    private List<Triplet<Integer, Double, Double>> tasksCost;

    public Job() {
    }

    public Job(int teamId, String description, String clientName, LocalDateTime start) {
        this.teamId = teamId;
        this.description = description;
        this.clientName = clientName;
        this.start = start;
    }

    private void calculateJobTime() {
        if (this.isOnSite) {
            this.setStart(this.getStart().minusMinutes(30));
            this.setEnd(this.getStart().plusMinutes(this.jobDuration).plusMinutes(30));
        } else {
            this.setEnd(this.getStart().plusMinutes(this.jobDuration));
        }
    }

    private void calculateDuration(List<Pair<IEmployee, ITask>> jobTaskSetFiltered) {

        IEmployee emp1 = team.getTeamMembers().get(0);
        IEmployee emp2 = team.getTeamMembers().get(1);

        //NUMBER OF TASKS FOR EACH EMPLOYEE
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
            this.jobDuration = totalTasksDurationE1;
        } else {
            this.jobDuration = totalTasksDurationE2;
        }

        calculateJobTime();

    }

    ;


    public void calculateTasksCost() {

        IEmployee emp1 = team.getTeamMembers().get(0);
        IEmployee emp2 = team.getTeamMembers().get(1);

        //INT - empId
        List<Pair<IEmployee, ITask>> jobTasksEmployeeSet = new ArrayList<Pair<IEmployee, ITask>>();

        int index1 = 0;
        int index2 = 0;

        //TODO: second getmEmpSkillsID
//        List<Integer> emp1Skills = new ArrayList<>();
//
//        emp1.getSkills().forEach(sk -> {
//            emp1Skills.add(sk.getId());
//        });
        //FILL MAP WITH EMP_ID - TASK values
        for (ITask t : tasks) {

//            if (emp1Skills.contains(t.getId())) {
//                jobTasksEmployeeSet.add(Pair.with(emp1, t));
//            }
//
//            if (index2 < emp2.getSkills().size()) {
//                if (emp2.getSkills().get(index2).getId() == t.getId()) {
//                    jobTasksEmployeeSet.add(Pair.with(emp2, t));
//                }
//            }
//
//            index1++;
//            index2++;
            //ADD TASK IF EMP HAS SKILLS
            if (emp1.getSkills().contains(t)) {
                jobTasksEmployeeSet.add(Pair.with(emp1, t));
            }

            if (emp2.getSkills().contains(t)) {
                jobTasksEmployeeSet.add(Pair.with(emp2, t));
            }
        }

        //FILTER TASKS BY EMPLOYEE
        //HashMap<Integer, ITask> jobTaskSetFiltered = new HashMap<>();
        List<Pair<IEmployee, ITask>> jobTaskSetFiltered = new ArrayList<>();

        for (Pair<IEmployee, ITask> entry : jobTasksEmployeeSet) {

            IEmployee emp = entry.getValue0();
            ITask task = entry.getValue1();

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

    public boolean isIsOnSite() {
        return isOnSite;
    }

    public void setIsOnSite(boolean isOnSite) {
        this.isOnSite = isOnSite;
    }

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
        this.tasks = tasks;
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
        this.description = description;
    }

    @Override
    public String getClientName() {
        return clientName;
    }

    @Override
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public LocalDateTime getStart() {
        return start;
    }

    @Override
    public void setStart(LocalDateTime start) {
        this.start = start;
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

    private void buildTaskIdsString() {
        //this.tasks.forEach(t -> this.tasksIds += t.getId() + ",");
    }

//    private void buildCostValuesString(){
//        
//    }
    private double calculateCost(int duration, double empRate) {
        return (duration / 60) * empRate;

//        double result = this.team.getTeamMembers()
//                .stream()
//                .reduce(0.0, (subtotal, employee)
//                        -> subtotal + employee.getHourlyRate(), Double::sum)
//                / (this.calculateTotalTasksDuration() / 60);
//
//        this.cost = result;
    }

    private double calculateRevenue(double cost) {
        int incrementRate = 3;
        if (isEmergency) {
            incrementRate = 4;
        }

        return cost * incrementRate;
    }

    //It is a utility method, This is why interface does not have it
    private int calculateTotalTasksDuration() {
        return this.tasks.stream().reduce(0, (calculatedTime, task) -> calculatedTime + task.getDuration(), Integer::sum);
    }

//    @Override
//    public double calculateBillableCost() {
//        return this.revenue + (this.revenue * 0.15);
//    }
    @Override
    public List<Triplet<Integer, Double, Double>> getTasksCost() {
        return this.tasksCost;
    }

    @Override
    public void setTasksCost(List<Triplet<Integer, Double, Double>> list) {
        this.tasksCost = list;
    }

}
