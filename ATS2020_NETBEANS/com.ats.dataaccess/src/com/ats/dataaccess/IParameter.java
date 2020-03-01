/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.dataaccess;

/**
 *
 * @author Chris.Cusack
 */
public interface IParameter {
    /**
     * Parameter Direction
     * Input or Outputs
     */
    public enum Direction{
        IN,
        OUT
    }
    
    void setValue(Object value);
    void setDirection(Direction type);
    void setSQLType(int sqlType);
    
    Object getValue();
    Direction getDirection();
    int getSQLType();
}
