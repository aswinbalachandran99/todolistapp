package com.example.todolist;

import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/todo/*")
public class TodoServlet extends HttpServlet {
    private Gson gson = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.getWriter().write(gson.toJson(TodoDAO.getAllTodos()));
        } else if (pathInfo.startsWith("/search/")) {
            String query = pathInfo.substring("/search/".length());
            response.getWriter().write(gson.toJson(TodoDAO.searchTodos(query)));
        } else if (pathInfo.startsWith("/category/")) {
            String category = pathInfo.substring("/category/".length());
            response.getWriter().write(gson.toJson(TodoDAO.getTodosByCategory(category)));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String task = request.getParameter("task");
        LocalDate dueDate = LocalDate.parse(request.getParameter("dueDate"));
        String priority = request.getParameter("priority");
        String category = request.getParameter("category");
        
        if (task != null && !task.trim().isEmpty()) {
            TodoDAO.addTodo(task, dueDate, priority, category);
        }
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(TodoDAO.getAllTodos()));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean completed = Boolean.parseBoolean(request.getParameter("completed"));
        TodoDAO.updateTodo(id, completed);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(TodoDAO.getAllTodos()));
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        TodoDAO.deleteTodo(id);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(TodoDAO.getAllTodos()));
    }
}