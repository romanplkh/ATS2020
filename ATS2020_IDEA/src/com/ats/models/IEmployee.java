package com.ats.models;

import java.time.LocalDate;
import java.util.ArrayList;

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

    LocalDate getCreatedAt();

    void setCreatedAt(LocalDate createdAt);

    LocalDate getUpdatedAt();

    void setUpdatedAt(LocalDate updatedAt);

    LocalDate getDeletedAt();

    void setDeletedAt(LocalDate deletedAt);


}
