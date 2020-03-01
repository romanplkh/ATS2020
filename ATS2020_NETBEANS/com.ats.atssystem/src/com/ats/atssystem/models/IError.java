package com.ats.atssystem.models;


/**
 * @author Olena Stepanova
 */

/**
 * IError Interface.
 * Provides specific operations to retrieve an error code
 * and description as well as set code and description
 * for an error
 */
public interface IError {

    /**
     * Gets the error code
     * @return int error code number
     */
    public int getCode();

    /**
     * Sets the error code
     * @param code int code error number
     */
    public void setCode(int code);

    /**
     * Gets the description of an error
     * @return string error description
     */
    public String getDescription();

    /**
     * Sets the description of an error
     * @param description string error description
     */
    public void setDescription(String description);
}
