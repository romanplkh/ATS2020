package com.ats.dataaccess;

import javax.sql.rowset.CachedRowSet;
import java.util.List;

/**
 * @author Olena Stepanova
 */

/**
 * Data Access Object Interface
 * It provides specific data operations that can be
 * performed in a database
 */
public interface IDAL {

    /**
     * Executes Insert, Update and Delete statements on a relational database.
     * Supports parameterized statements and stored procedures
     * @param statement query statement to execute
     * @param params list of query parameters to pass
     * @return list of return values which are the output parameters or
     * the number of rows affected are returned
     */
    List<Object> executeNonQuery(String statement, List<IParameter> params);

    /**
     * Executes Select statement against database
     * @param statement query statement to execute
     * @param params list of query parameters to pass
     * @return rows of data from a result set
     */
    CachedRowSet executeFill(String statement, List<IParameter> params);

    /**
     * Executes the query and returns the first column of the row in
     * the result set returned by the query
     * @param statement query to execute
     * @param params list of query parameters to pass
     * @return the first column of the first row in a result set or null
     */
    Object executeScalar(String statement, List<IParameter> params);
}
