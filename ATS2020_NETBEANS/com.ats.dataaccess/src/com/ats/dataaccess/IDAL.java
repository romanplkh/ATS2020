/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.dataaccess;

import java.util.List;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Chris.Cusack
 */
public interface IDAL {
    
    /**
     * 
     * @param statement sql procedure
     * @param params params procedure accept
     * @return 
     */
    List<Object> executeNonQuery(String statement, List<IParameter> params);
    
    /**
     * 
     * @param statement sql procedure
     * @param params params for procedure 
     * @return cacherowset - like dataset, we load if from db to datatable - CachedRowSet 
     */
    CachedRowSet executeFill(String statement, List<IParameter> params);
    
    /**
     * @param statement
     * @param params
     * @return object becasue back we can get int, string, data
     */
    Object executeScalar(String statement,List<IParameter> params);
}
