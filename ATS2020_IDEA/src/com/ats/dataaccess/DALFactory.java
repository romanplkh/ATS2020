package com.ats.dataaccess;


/**
 * @author Olena Stepanova
 */

/**
 * DALFactory abstract class.
 * Responsible for creating an instance of required IDAL
 */
public abstract class DALFactory {

    public static IDAL createInstance(){
        return new DAL();
    }
}
