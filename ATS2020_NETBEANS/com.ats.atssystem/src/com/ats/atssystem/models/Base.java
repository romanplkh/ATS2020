package com.ats.atssystem.models;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Olena Stepanova
 */

/**
 * Base class for all model classes
 */
public abstract class Base implements IBase, Serializable {

    private ArrayList<IError> errors = ErrorFactory.createListInstance();

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<IError> getErrors() {
        return errors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addError(IError error) {
        errors.add(error);
    }

}
