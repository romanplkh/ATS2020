/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.models;

/**
 *
 * @author Roman Pelikh
 */
public interface IEmployeeDTO {

    IEmployee getEmployee();

    void setEmployee(IEmployee employee);

    ITeam getTeam();

    void setTeam(ITeam team);

}
