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
/**
 * EmployeeDTOFactory Class responsible for creating an instance of EmployeeDTO class
 */
public abstract class EmployeeDTOFactory {

    /**
     * Creates an instance of EmployeeDTO class
     *
     * @return instance of EmployeeDTO class
     */
    public static IEmployeeDTO createInstance() {
        return new EmployeeDTO();
    }

}
