package com.ats.models;

/**
 * @author Olena Stepanova
 */

import java.util.ArrayList;

/**
 * IBase Interface.
 * Provides specific operations to retrieve a list of
 * errors that is present in a model class and also
 * add a new error to existed list of errors
 */
public interface IBase {

    /**
     * Gets a list of errors in a model
     * @return an ArrayList of errors
     */
    ArrayList<IError> getErrors();

    /**
     * Adds a new error to list of errors in a model
     * @param error IError error to add
     */
    void addError(IError error);

}
