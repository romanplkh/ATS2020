package com.ats.models;


import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Olena Stepanova
 */
public class Task implements Serializable {
    private int id;
    private String name;
    private String description;
    private int duration;
    private LocalDateTime createdAt;

    @Nullable
    private LocalDateTime updatedAt;

    private List<String> errors = new ArrayList();

    public Task() {
    }

    public Task(int id, String name, String description, int duration, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.createdAt = createdAt;
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
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
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

    public List<String> getErrors() {
        return errors;
    }

    public void addError(String error) {
        this.errors.add(error);
    }
}
