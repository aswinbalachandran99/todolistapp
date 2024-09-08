// index.jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Todo List</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <div class="container">
        <h1>Todo List</h1>
        <form id="todoForm">
            <input type="text" id="task" placeholder="Enter task" required>
            <input type="date" id="dueDate" required>
            <select id="priority" required>
                <option value="High">High</option>
                <option value="Medium">Medium</option>
                <option value="Low">Low</option>
            </select>
            <select id="category" required>
                <option value="Work">Work</option>
                <option value="Personal">Personal</option>
                <option value="Shopping">Shopping</option>
                <option value="Health">Health</option>
                <option value="Finance">Finance</option>
            </select>
            <button type="submit">Add Task</button>
        </form>
        <div class="search-container">
            <input type="text" id="searchInput" placeholder="Search tasks...">
        </div>
        <div id="categories" class="categories"></div>
        <ul id="todoList"></ul>
    </div>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>