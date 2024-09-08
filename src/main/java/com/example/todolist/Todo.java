// Todo.java
package com.example.todolist;

import java.time.LocalDate;

public class Todo {
    private int id;
    private String task;
    private boolean completed;
    private LocalDate dueDate;
    private String priority;
    private String category;

    // Constructor, getters, and setters
    // ... (implement these)

    public String toJson() {
        return String.format(
            "{\"id\":%d,\"task\":\"%s\",\"completed\":%b,\"dueDate\":\"%s\",\"priority\":\"%s\",\"category\":\"%s\"}",
            id, task, completed, dueDate, priority, category
        );
    }
}