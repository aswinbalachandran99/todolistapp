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
    void testGetAllTodos() {
        List<Todo> todos = todoDAO.getAllTodos();
        assertNotNull(todos, "Todos list should not be null");
        assertTrue(todos.size() >= 1, "There should be at least one todo initialized");
    }

    @Test
    void testAddTodo() {
        int initialSize = todoDAO.getAllTodos().size();
        todoDAO.addTodo("New Task", LocalDate.now().plusDays(5), Todo.Priority.MEDIUM);

        List<Todo> todos = todoDAO.getAllTodos();
        assertEquals(initialSize + 1, todos.size(), "Todos list size should increase by 1");
        assertEquals("New Task", todos.get(todos.size() - 1).getTask(), "Last added task should match the added task");
    }

    @Test
    void testToggleTodo() {
        List<Todo> todos = todoDAO.getAllTodos();
        int todoId = todos.get(0).getId();
        boolean initialStatus = todos.get(0).isCompleted();

        todoDAO.toggleTodo(todoId);

        todos = todoDAO.getAllTodos();
        assertEquals(!initialStatus, todos.get(0).isCompleted(), "Todo completion status should be toggled");
    }

    @Test
    void testDeleteTodo() {
        todoDAO.addTodo("Task to Delete", LocalDate.now().plusDays(5), Todo.Priority.LOW);
        int initialSize = todoDAO.getAllTodos().size();

        int todoId = todoDAO.getAllTodos().get(initialSize - 1).getId();
        todoDAO.deleteTodo(todoId);

        List<Todo> todos = todoDAO.getAllTodos();
        assertEquals(initialSize - 1, todos.size(), "Todos list size should decrease by 1");
        assertTrue(todos.stream().noneMatch(todo -> todo.getId() == todoId), "Deleted todo should not be in the list");
    }

    @Test
    void testGetFilteredTodos_completed() {
        todoDAO.addTodo("Completed Task", LocalDate.now().plusDays(1), Todo.Priority.LOW);
        int todoId = todoDAO.getAllTodos().get(todoDAO.getAllTodos().size() - 1).getId();
        todoDAO.toggleTodo(todoId);

        List<Todo> completedTodos = todoDAO.getFilteredTodos("completed");
        assertTrue(completedTodos.stream().allMatch(Todo::isCompleted), "All filtered todos should be completed");
    }

    @Test
    void testGetFilteredTodos_active() {
        todoDAO.addTodo("Active Task", LocalDate.now().plusDays(1), Todo.Priority.LOW);

        List<Todo> activeTodos = todoDAO.getFilteredTodos("active");
        assertTrue(activeTodos.stream().noneMatch(Todo::isCompleted), "All filtered todos should be active");
    }

    @Test
    void testGetFilteredTodos_highPriority() {
        todoDAO.addTodo("High Priority Task", LocalDate.now().plusDays(1), Todo.Priority.HIGH);

        List<Todo> highPriorityTodos = todoDAO.getFilteredTodos("high");
        assertTrue(highPriorityTodos.stream().allMatch(todo -> todo.getPriority() == Todo.Priority.HIGH),
                "All filtered todos should have HIGH priority");
    }
}
