/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Olena Stepanova
 */
public interface IJob extends IBase {

    public List<ITask> getTasks();

    public void setTasks(List<ITask> tasks);

    public int getId();

    public void setId(int id);

    public int getTeamId();

    public void setTeamId(int id);

    public String getDescription();

    public void setDescription(String description);

    public String getClientName();

    public void setClientName(String name);

    public LocalDateTime getStart();

    public void setStart(LocalDateTime ldt);

    public LocalDateTime getEnd();

    public void setEnd(LocalDateTime ldt);

    public void setIsEmergency(boolean value);

    public ITeam getTeam();

    public void setTeam(ITeam team);

    public boolean getIsEmergency();

    public double getCost();

    public double getRevenue();

    public void calculateCost();

    public LocalDateTime getEndCalculated();

    public void calculateRevenue();

}
