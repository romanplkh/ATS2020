package com.ats.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ErrorViewModel implements Serializable {
    private List<String> errors = new ArrayList();

    public ErrorViewModel(){}

    public ErrorViewModel(String error) {
        this.errors.add(error);
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
