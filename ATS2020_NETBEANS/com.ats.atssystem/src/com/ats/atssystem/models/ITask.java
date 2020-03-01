package com.ats.atssystem.models;



/**
 * @author Olena Stepanova
 */

import java.time.LocalDateTime;

/**
 * ITask interface. Defines properties of the Task
 */
public interface ITask extends IBase {

    public int getId();

    public void setId(int id);

    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public int getDuration();

    public void setDuration(int duration);

    public LocalDateTime getCreatedAt();

    public void setCreatedAt(LocalDateTime createdAt);

    public LocalDateTime getUpdatedAt();

    public void setUpdatedAt(LocalDateTime updatedAt);

}
