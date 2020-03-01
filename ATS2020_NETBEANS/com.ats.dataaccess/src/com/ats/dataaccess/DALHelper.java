/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.dataaccess;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Chris.Cusack
 */
class DALHelper {

    /**
     * Loads db.properties file into an instance of properties object
     *
     * @return Properties object
     * @throws Exception
     */
    public static Properties getProperties() throws Exception {
        Properties props = new Properties();
        InputStream in = null;

        try {
            //Get the path to my db.properties file
            
            //Class Loader for loading the durrenct binary lcoation
            ClassLoader classLoader = DALHelper.class.getClassLoader();
            in = classLoader.getResourceAsStream("com/properties/db.properties");
           
            //Load the stream into our props object
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
