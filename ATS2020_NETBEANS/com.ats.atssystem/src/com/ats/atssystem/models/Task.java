package com.ats.atssystem.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Olena Stepanova
 */
public class Task extends Base implements ITask, Serializable {

    private int id;
    private String name;
    private String description;
    private int duration;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private double cost;
    private double revenue;

    public Task() {
    }

    public Task(String name, String description, int duration) {

        setName(name);
        setDescription(description);
        setDuration(duration);
    }

    public Task(int id, String name, String description, int duration) {

        setId(id);
        setName(name);
        setDescription(description);
        setDuration(duration);
    }

    public Task(String name, String description, int duration, LocalDateTime createdAt) {

        setName(name);
        setDescription(description);
        setDuration(duration);
        this.createdAt = createdAt;
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
        if (name.trim().isEmpty()) {
            super.addError(ErrorFactory
                    .createInstance(1, "Name is required"));
        } else {
            this.name = name;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description.trim().isEmpty()) {
            super.addError(ErrorFactory
                    .createInstance(2, "Description is required"));
        } else {
            this.description = description;
        }
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        if (duration == 0) {
            super.addError(ErrorFactory
                    .createInstance(3, "Duration is required"));
        } else {
            this.duration = duration;
        }
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

    @Override
    public double getCost() {
        return this.cost;
    }

    @Override
    public double getRevenue() {
        return this.revenue;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof Task)) {
            return false;
        }

        Task t = (Task) o;

        return this.getId() == t.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
