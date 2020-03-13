package com.ats.atssystem.models;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Roman Pelikh
 */
public class EmployeeDTO implements IEmployeeDTO, Serializable {

    private IEmployee employee = EmployeeFactory.createInstance();

    private ITeam team = TeamFactory.createInstance();

    private List<ITask> skills = TaskFactory.createListInstance();

    public EmployeeDTO() {
    }

    public List<ITask> getSkills() {
        return this.skills;
    }

    public void setSkills(List<ITask> tasks) {
        this.skills = tasks;
    }

    public IEmployee getEmployee() {
        return employee;
    }

    public void setEmployee(IEmployee employee) {
        this.employee = employee;
    }

    public ITeam getTeam() {
        return team;
    }

    public void setTeam(ITeam team) {
        this.team = team;
    }

}
