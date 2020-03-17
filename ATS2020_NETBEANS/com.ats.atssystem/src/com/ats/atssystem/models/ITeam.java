/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ats.atssystem.models;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Roman Pelikh
 */
/**
 * ITeam interface. Defines properties of the Team
 */
public interface ITeam extends IBase {

    int getId();

    void setId(int id);

    String getName();

    void setName(String firstName);

    boolean getIsOnCall();

    void setIsOnCall(boolean value);

    boolean getIsDeleted();

    void setIsDeleted(boolean value);

    Date getCreatedAt();

    void setCreatedAt(Date value);

    Date getUpdatedAt();

    void setUpdatedAt(Date value);

    Date getDeletedAt();

    void setDeletedAt(Date value);
    
    List<IEmployee> getTeamMembers();
    
    void setTeamMembers(List<IEmployee> employees);

    List<IJob> getJobs();
    
    void setJobs(List<IJob> jobsList);
}
