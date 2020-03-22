/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.models;

import java.util.List;

/**
 *
 * @author Roman Pelikh
 */
/**
 * IEmployeeDTO interface. Defines all properties for details related to an employee
 */
public interface IEmployeeDTO {

    IEmployee getEmployee();

    void setEmployee(IEmployee employee);

    ITeam getTeam();

    void setTeam(ITeam team);

    List<ITask> getSkills();

    void setSkills(List<ITask> skills);

}
