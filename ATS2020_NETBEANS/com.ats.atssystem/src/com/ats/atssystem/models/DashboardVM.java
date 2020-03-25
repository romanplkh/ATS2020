/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Roman Pelikh
 */
public class DashboardVM implements Serializable {

    private List<IJob> currentYear;
    private List<IJob> previousYear;
    private double monthlyRevenue;
    private double yearlyRevenue;
    private double monthlyCost;
    private double yearlyCost;
    private ITeam teamOnCall;
    private int jobsCountToday;

    public DashboardVM() {
        currentYear = new ArrayList<>();
        previousYear = new ArrayList<>();
    }

    public List<IJob> getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(List<IJob> currentYear) {
        this.currentYear = currentYear;
    }

    public List<IJob> getPreviousYear() {
        return previousYear;
    }

    public void setPreviousYear(List<IJob> previousYear) {
        this.previousYear = previousYear;
    }

    public double getMonthlyRevenue() {
        return monthlyRevenue;
    }

    public void setMonthlyRevenue(double monthlyRevenue) {
        this.monthlyRevenue = monthlyRevenue;
    }

    public double getYearlyRevenue() {
        return yearlyRevenue;
    }

    public void setYearlyRevenue(double yearlyRevenue) {
        this.yearlyRevenue = yearlyRevenue;
    }

    public double getMonthlyCost() {
        return monthlyCost;
    }

    public void setMonthlyCost(double monthlyCost) {
        this.monthlyCost = monthlyCost;
    }

    public double getYearlyCost() {
        return yearlyCost;
    }

    public void setYearlyCost(double yearlyCost) {
        this.yearlyCost = yearlyCost;
    }

    public ITeam getTeamOnCall() {
        return teamOnCall;
    }

    public void setTeamOnCall(ITeam teamOnCall) {
        this.teamOnCall = teamOnCall;
    }

    public int getJobsCountToday() {
        return jobsCountToday;
    }

    public void setJobsCountToday(int jobsCountToday) {
        this.jobsCountToday = jobsCountToday;
    }

}
