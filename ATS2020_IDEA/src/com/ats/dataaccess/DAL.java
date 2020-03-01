package com.ats.dataaccess;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.ats.dataaccess.IParameter.Direction;

/**
 * @author Olena Stepanova
 */

/**
 * DAL class implements functionality to work with query statements and
 * database
 */
public class DAL implements IDAL {

    private String url = "";
    private String username = "";
    private String password = "";


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Object> executeNonQuery(String statement, List<IParameter> params) {
        List<Object> retVal = new ArrayList();

        try {

            dbPropertiesSetUp();
            DriverManager.registerDriver(new com.mysql.jdbc.Driver ());

            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                try (CallableStatement cstmt = conn.prepareCall(statement)) {

                    int i = 1;

                    // load in and out params
                    for (IParameter p : params) {
                        if (p.getDirection() == IParameter.Direction.IN) {
                            cstmt.setObject(i, p.getValue());
                        } else {
                            cstmt.registerOutParameter(i, p.getSQLType());
                        }

                        i++;
                    }

                    boolean rowsAffected = !cstmt.execute();

                    // if no out params included add rows affected to return
                    if (params.stream().filter(p -> p.getDirection() == Direction.OUT)
                            .toArray().length == 0) {
                        if (rowsAffected) {
                            retVal.add(cstmt.getUpdateCount());
                        }
                    } else {
                        i = 1;

                        //add out params to the return list
                        for (IParameter p : params) {
                            if (p.getDirection() == Direction.OUT) {
                                retVal.add(cstmt.getObject(i));
                            }
                            i++;
                        }
                    }

                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return retVal;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CachedRowSet executeFill(String statement, List<IParameter> params) {
        CachedRowSet rowSet = null;

        try {

            dbPropertiesSetUp();
            DriverManager.registerDriver(new com.mysql.jdbc.Driver ());

            rowSet = RowSetProvider.newFactory().createCachedRowSet();

            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                    loadInputParams(pstmt, params);

                    //execute query and populate rowSet with returned result from DB
                    try (ResultSet rs = (ResultSet) pstmt.executeQuery()) {
                        rowSet.populate(rs);
                    }
                }
            }


        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return rowSet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object executeScalar(String statement, List<IParameter> params) {
        Object retVal = null;

        try {
            dbPropertiesSetUp();
            DriverManager.registerDriver(new com.mysql.jdbc.Driver ());

            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                    loadInputParams(pstmt, params);

                    //execute query and save returned value
                    try (ResultSet rs = (ResultSet) pstmt.executeQuery()) {
                        if (rs.next()) {
                            retVal = rs.getObject(1);
                        }
                    }
                }
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }


        return retVal;
    }

    /**
     * Loads input parameters in sql prepared statement
     *
     * @param stmt   prepared statement
     * @param params list of query parameters
     * @throws SQLException
     */
    private void loadInputParams(PreparedStatement stmt, List<IParameter> params) throws SQLException {

        int index = 1;

        if (params != null) {
            for (IParameter p : params) {
                stmt.setObject(index, p.getValue());
            }
        }
    }

    /**
     * Gets properties from db.properties file
     *
     * @throws Exception
     */
    private void dbPropertiesSetUp() throws Exception {

        Properties props = DALHelper.getProperties();
        url = props.getProperty("database.url");
        username = props.getProperty("database.username");
        password = props.getProperty("database.password");

    }
}
