package com.ats.atssystem.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Roman Pelikh
 */
/**
 * IEmployee interface. Defines properties of the Employee
 */
public interface IEmployee extends IBase {

    int getId();

    void setId(int id);

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getSin();

    void setSin(String sin);

    double getHourlyRate();

    void setHourlyRate(double hourlyRate);

    boolean isIsDeleted();

    void setIsDeleted(boolean isDeleted);

    Date getCreatedAt();

    void setCreatedAt(Date createdAt);

    Date getUpdatedAt();

    void setUpdatedAt(Date updatedAt);

    Date getDeletedAt();

    void setDeletedAt(Date deletedAt);

}
