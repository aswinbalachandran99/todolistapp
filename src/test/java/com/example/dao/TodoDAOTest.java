package com.example.dao;

import com.example.model.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TodoDAOTest {
    private TodoDAO todoDAO;

    @BeforeEach
    void setUp() {
        todoDAO = new TodoDAO();
    }

    @Test
    void getAllTodos() {
        List<Todo> todos = todoDAO.getAllTodos();
        assertFalse(todos.isEmpty());
        assertTrue(todos.size() >= 1);
    }

    @Test
    void addTodo() {
        int initialSize = todoDAO.getAllTodos().size();
        todoDAO.addTodo("New Todo", LocalDate.now().plusDays(7), Todo.Priority.HIGH);
        List<Todo> todos = todoDAO.getAllTodos();
        assertEquals(initialSize + 1, todos.size());
        assertTrue(todos.stream().anyMatch(todo -> todo.getTask().equals("New Todo")));
    }

    @Test
    void toggleTodo() {
        List<Todo> todos = todoDAO.getAllTodos();
        Todo todo = todos.get(0);
        boolean initialState = todo.isCompleted();
        todoDAO.toggleTodo(todo.getId());
        assertTrue(todoDAO.getAllTodos().stream().anyMatch(t -> t.getId() == todo.getId() && t.isCompleted() != initialState));
    }

    @Test
    void deleteTodo() {
        int initialSize = todoDAO.getAllTodos().size();
        Todo todo = todoDAO.getAllTodos().get(0);
        todoDAO.deleteTodo(todo.getId());
        assertEquals(initialSize - 1, todoDAO.getAllTodos().size());
        assertFalse(todoDAO.getAllTodos().stream().anyMatch(t -> t.getId() == todo.getId()));
    }

    @Test
    void getFilteredTodos() {
        todoDAO.addTodo("High Priority", LocalDate.now().plusDays(7), Todo.Priority.HIGH);
        todoDAO.addTodo("Completed Todo", LocalDate.now().minusDays(7), Todo.Priority.MEDIUM);
        todoDAO.toggleTodo(todoDAO.getAllTodos().stream().filter(t -> t.getTask().equals("Completed Todo")).findFirst().get().getId());

        List<Todo> highPriorityTodos = todoDAO.getFilteredTodos("high");
        assertTrue(highPriorityTodos.stream().allMatch(t -> t.getPriority() == Todo.Priority.HIGH));

        List<Todo> completedTodos = todoDAO.getFilteredTodos("completed");
        assertTrue(completedTodos.stream().allMatch(Todo::isCompleted));

        List<Todo> activeTodos = todoDAO.getFilteredTodos("active");
        assertTrue(activeTodos.stream().allMatch(t -> !t.isCompleted()));
    }
}