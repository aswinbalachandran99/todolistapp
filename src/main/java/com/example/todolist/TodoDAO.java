// TodoDAO.java
package com.example.todolist;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class TodoDAO {
    private static List<Todo> todos = new ArrayList<>();
    private static int idCounter = 1;

    public static void addTodo(String task, LocalDate dueDate, String priority, String category) {
        Todo todo = new Todo();
        todo.setId(idCounter++);
        todo.setTask(task);
        todo.setCompleted(false);
        todo.setDueDate(dueDate);
        todo.setPriority(priority);
        todo.setCategory(category);
        todos.add(todo);
    }

    public static List<Todo> getAllTodos() {
        return todos;
    }

    public static void updateTodo(int id, boolean completed) {
        for (Todo todo : todos) {
            if (todo.getId() == id) {
                todo.setCompleted(completed);
                break;
            }
        }
    }

    public static void deleteTodo(int id) {
        todos.removeIf(todo -> todo.getId() == id);
    }

    public static List<Todo> searchTodos(String query) {
        return todos.stream()
                    .filter(todo -> todo.getTask().toLowerCase().contains(query.toLowerCase()) ||
                                    todo.getCategory().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
    }

    public static List<Todo> getTodosByCategory(String category) {
        return todos.stream()
                    .filter(todo -> todo.getCategory().equalsIgnoreCase(category))
                    .collect(Collectors.toList());
    }
}