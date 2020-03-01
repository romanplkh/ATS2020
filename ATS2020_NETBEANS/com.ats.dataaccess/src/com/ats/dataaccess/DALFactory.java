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
public abstract class DALFactory {
    
    //Create instance of the requried IDAL
    public static IDAL createInstance(){
        
        //Determine which DAL to return        
        return new DAL();
    }
}
