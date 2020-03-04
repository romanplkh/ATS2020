package com.ats.atssystem.models;

import java.io.Serializable;

/**
 *
 * @author Roman Pelikh
 */
public class EmployeeDTO implements IEmployeeDTO, Serializable {

    private IEmployee employee = EmployeeFactory.createInstance();

    private ITeam team = TeamFactory.createInstance();

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
