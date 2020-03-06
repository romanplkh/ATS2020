package com.ats.dataaccess;

/**
 * @author Olena Stepanova
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * DALHelper class.
 * Responsible for reading db.properties file with
 * database connection information and loading it in Properties
 * object
 */
public abstract class DALHelper {

    /**
     * Loads db.properties file into an instance of Properties object
     *
     * @return Properties object with data populated from db file
     * @throws Exception
     */
    public static Properties getProperties() throws Exception {
        Properties props = new Properties();
        InputStream in = null;

        try {
            ClassLoader cl = DALHelper.class.getClassLoader();
            in = cl.getResourceAsStream("db.properties");


            if (in != null) {
                props.load(in);
                in.close();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        } finally {
            if (in != null) {
                in.close();
            }
        }

        return props;
    }

}