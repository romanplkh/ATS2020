/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.javatuples.Triplet;

/**
 *
 * @author Olena Stepanova
 */
public interface IJob extends IBase {

    public List<ITask> getTasksList();

    public void setTasksList(List<ITask> tasks);

    public List<Triplet<Integer, Double, Double>> getTasksCost();

    public void setTasksCost(List<Triplet<Integer, Double, Double>> list);

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

    public void calculateTasksCost();

//    public double calculateBillableCost();

    public LocalTime getStartTime();

    public void setStartTime(LocalTime startTime);

    public LocalTime getEndTime();

    public void setEndTime(LocalTime endTime);

}
