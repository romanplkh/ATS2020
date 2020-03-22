package com.ats.utils;

public class Validator {

    public static boolean validateRequiredField(String value){
        return !value.isEmpty();
    }
}
