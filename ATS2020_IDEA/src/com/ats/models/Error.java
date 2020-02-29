package com.ats.models;

import java.io.Serializable;

/**
 * Error class
 */
public class Error implements IError, Serializable {
    private int code;
    private String description;

    public Error() {
    }

    public Error(int code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCode() {
        return code;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}
