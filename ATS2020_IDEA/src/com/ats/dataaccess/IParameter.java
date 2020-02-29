package com.ats.dataaccess;

/**
 * @author Olena Stepanova
 */

/**
 * Interface for query parameters.
 * It provides specific operations that set the direction of
 * parameter, its values and types
 */
public interface IParameter {

    /**
     * Parameter direction - input or output
     */
    public enum Direction{
        IN,
        OUT
    }

    /**
     * Sets the value of the parameter
     * @param value object as a parameter value
     */
    void setValue(Object value);

    /**
     * Sets the direction of the parameter
     * @param direction enum constant of a direction
     */
    void setDirection(Direction direction);

    /**
     * Set the type of the parameter
     * @param sqlType integer value of parameter type
     */
    void setSQLType(int sqlType);

    /**
     * Gets the value of the parameter
     * @return object value
     */
    Object getValue();

    /**
     * Gets the parameter direction
     * @return enum constant of the direction
     */
    Direction getDirection();

    /**
     * Gets the parameter type
     * @return int value of type
     */
    int getSQLType();
}
