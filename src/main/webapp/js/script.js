// script.js
document.addEventListener('DOMContentLoaded', function() {
    loadTodos();

    document.getElementById('todoForm').addEventListener('submit', function(e) {
        e.preventDefault();
        addTodo();
    });

    document.getElementById('searchInput').addEventListener('input', function(e) {
        searchTodos(e.target.value);
    });

    loadCategories();
});

function loadTodos() {
    fetch('todo')
        .then(response => response.json())
        .then(todos => displayTodos(todos))
        .catch(error => console.error('Error:', error));
}

function addTodo() {
    const task = document.getElementById('task').value;
    const dueDate = document.getElementById('dueDate').value;
    const priority = document.getElementById('priority').value;
    const category = document.getElementById('category').value;

    fetch('todo', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `task=${encodeURIComponent(task)}&dueDate=${encodeURIComponent(dueDate)}&priority=${encodeURIComponent(priority)}&category=${encodeURIComponent(category)}`
    })
    .then(response => response.json())
    .then(todos => {
        displayTodos(todos);
        document.getElementById('todoForm').reset();
    })
    .catch(error => console.error('Error:', error));
}

function displayTodos(todos) {
    const todoList = document.getElementById('todoList');
    todoList.innerHTML = '';

    todos.forEach(todo => {
        const li = document.createElement('li');
        li.className = todo.priority.toLowerCase();
        if (todo.completed) li.classList.add('completed');

        li.innerHTML = `
            <div class="task-info">
                <span>${todo.task}</span>
                <small>Due: ${todo.dueDate} | Category: ${todo.category}</small>
            </div>
            <div class="task-actions">
                <input type="checkbox" ${todo.completed ? 'checked' : ''} onchange="updateTodo(${todo.id}, this.checked)">
                <button onclick="deleteTodo(${todo.id})">Delete</button>
            </div>
        `;

        todoList.appendChild(li);
    });
}

function updateTodo(id, completed) {
    fetch(`todo?id=${id}&completed=${completed}`, { method: 'PUT' })
        .then(response => response.json())
        .then(todos => displayTodos(todos))
        .catch(error => console.error('Error:', error));
}

function deleteTodo(id) {
    fetch(`todo?id=${id}`, { method: 'DELETE' })
        .then(response => response.json())
        .then(todos => displayTodos(todos))
        .catch(error => console.error('Error:', error));
}

function searchTodos(query) {
    fetch(`todo/search/${encodeURIComponent(query)}`)
        .then(response => response.json())
        .then(todos => displayTodos(todos))
        .catch(error => console.error('Error:', error));
}

function loadCategories() {
    const categories = ['Work', 'Personal', 'Shopping', 'Health', 'Finance'];
    const categoriesContainer = document.getElementById('categories');
    
    categories.forEach(category => {
        const button = document.createElement('button');
        button.textContent = category;
        button.className = 'category-btn';
        button.onclick = () => filterByCategory(category);
        categoriesContainer.appendChild(button);
    });
}

function filterByCategory(category) {
    fetch(`todo/category/${encodeURIComponent(category)}`)
        .then(response => response.json())
        .then(todos => displayTodos(todos))
        .catch(error => console.error('Error:', error));
}