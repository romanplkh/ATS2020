package com.ats.dataaccess;
import com.ats.dataaccess.IParameter.Direction;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * @author Olena Stepanova
 */

/**
 * ParameterFactory abstract class.
 * Responsible for creating an instance of specified
 * parameter class
 */
public abstract class ParameterFactory {

    public static IParameter createInstance(){
        return new Parameter();
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static IParameter createInstance(Object value){
        return new Parameter(value);
    }

    public static IParameter createInstance(Object value, Direction direction){
        return new Parameter(value, direction);
    }

    public static IParameter createInstance(Object value, Direction direction, int sqlType){
        return new Parameter(value, direction, sqlType);
    }

    public static ArrayList<IParameter> createListInstance(){
        return new ArrayList();
    }
}

