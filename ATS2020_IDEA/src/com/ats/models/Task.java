package com.ats.models;


import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @author Olena Stepanova
 */
public class Task extends Base implements ITask, Serializable {
    private int id;
    private String name;
    private String description;
    private int duration;
    private LocalDateTime createdAt;
    @Nullable
    private LocalDateTime updatedAt;


    public Task() {
    }

    public Task(int id, String name, String description, int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
    }

    public Task(int id, String name, String description, int duration,
                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        setName(name);
        setDescription(description);
        setDuration(duration);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            super.addError(ErrorFactory
                    .createInstance(1, "Name is required"));
        }
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null) {
            super.addError(ErrorFactory
                    .createInstance(2, "Description is required"));
        }
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        if (duration == 0) {
            super.addError(ErrorFactory
                    .createInstance(3, "Duration is required"));
        }
        this.duration = duration;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
