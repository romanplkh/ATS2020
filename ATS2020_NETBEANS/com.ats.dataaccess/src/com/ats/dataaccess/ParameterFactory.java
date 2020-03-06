/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.dataaccess;

import com.ats.dataaccess.IParameter.Direction;
import java.sql.SQLType;
import java.util.ArrayList;

/**
 *
 * @author Roman Pelikh
 */

//Responsible for generateing concrete parameter class
public abstract class ParameterFactory {
    
    public static IParameter createInstance(){
        return new Parameter();
    }
    
    public static IParameter createInstance(Object value){
        return new Parameter(value);
    }
    
    
     public static IParameter createInstance(Object value, Direction direction){
        return new Parameter(value, direction);
    }
     
     public static IParameter createInstance(Object value, Direction direction, int sqlType){
        return new Parameter(value, direction, sqlType);
    }
     
     //Generate list of params
     public static ArrayList<IParameter> createListInstance(){
         return new ArrayList<>();
     }
    
    
}
