package com.ats.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class TaskFactory {

    public static ITask createInstance() {
        return new Task();
    }

    public static ITask createInstance(int id, String name, String description, int duration) {
        return new Task(id, name, description, duration);
    }

    public static ITask createInstance(int id, String name, String description, int duration,
                                       LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Task(id, name, description, duration, createdAt, updatedAt);
    }


    public static List<ITask> createListInstance() {
        return new ArrayList<>();
    }

}
