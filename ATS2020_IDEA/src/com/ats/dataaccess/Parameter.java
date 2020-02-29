package com.ats.dataaccess;

/**
 * @author Olena Stepanova
 */

/**
 * Parameter class implements IParameter interface.
 * Responsible for creation of parameters that will be
 * passed into sql statement
 */
public class Parameter implements IParameter {

    private Object value;
    private Direction direction;
    private int sqlType;

    public Parameter() {
        this.direction = Direction.IN;
    }

    public Parameter(Object value) {
        this.value = value;
        this.direction = Direction.IN;
    }

    public Parameter(Object value, Direction direction) {
        this.value = value;
        this.direction = direction;
    }

    public Parameter(Object value, Direction direction, int sqlType) {
        this.value = value;
        this.direction = direction;
        this.sqlType = sqlType;
    }

    /**
     * {@inheritDoc}
     * @param value object as a parameter value
     */
    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * {@inheritDoc}
     * @param direction enum constant of a direction
     */
    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * {@inheritDoc}
     * @param sqlType integer value of parameter type
     */
    @Override
    public void setSQLType(int sqlType) {
        this.sqlType = sqlType;
    }

    /**
     * {@inheritDoc}
     * @return parameter value
     */
    @Override
    public Object getValue() {
        return this.value;
    }

    /**
     * {@inheritDoc}
     * @return parameter direction
     */
    @Override
    public Direction getDirection() {
        return this.direction;
    }

    /**
     * {@inheritDoc}
     * @return parameter type
     */
    @Override
    public int getSQLType() {
        return this.sqlType;
    }
}
