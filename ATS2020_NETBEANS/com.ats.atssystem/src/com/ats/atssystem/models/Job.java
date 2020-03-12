/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Olena Stepanova
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

    private double cost;
    private double revenue;

    public Job() {
    }

    public Job(int teamId, String description, String clientName, LocalDateTime start) {
        this.teamId = teamId;
        this.description = description;
        this.clientName = clientName;
        this.start = start;
    }

    @Override
    public List<ITask> getTasks() {
        return tasks;
    }

    @Override
    public void setTasks(List<ITask> tasks) {
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

    public LocalDateTime getEndCalculated() {
        return this.start.plusMinutes(this.calculateTotalTasksDuration());
    }

    @Override
    public boolean getIsEmergency() {
        return isEmergency;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public double getRevenue() {
        return revenue;
    }

    @Override
    public void setIsEmergency(boolean isEmergency) {
        this.isEmergency = isEmergency;
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
    public void calculateCost() {
        double result = this.team.getTeamMembers().stream().reduce(0.0, (subtotal, employee) -> subtotal + employee.getHourlyRate(), Double::sum) / (this.calculateTotalTasksDuration() / 60);

        this.cost = result;
    }

    @Override
    public void calculateRevenue() {
        int incrementRate = 3;
        if (isEmergency) {
            incrementRate = 4;
        }

        this.revenue = this.cost * incrementRate;
    }

    //It is a utility method, This is why interface does not have it
    private int calculateTotalTasksDuration() {
        return this.tasks.stream().reduce(0, (calculatedTime, task) -> calculatedTime + task.getDuration(), Integer::sum);
    }

}
