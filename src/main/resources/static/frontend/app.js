document.addEventListener('DOMContentLoaded', function () {
    getTaskList();
    const taskForm = document.getElementById('task-form');
    const taskList = document.getElementById('task-list');

    taskForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const taskTitle = document.getElementById('task-title').value;
        if (taskTitle.trim() !== "") {
            createTask(taskTitle);
            document.getElementById('task-title').value = "";
        }
    });

    taskList.addEventListener('click', function (event) {
        const target = event.target;

        if (target.classList.contains('task-completed')) {
            const taskId = target.parentElement.dataset.id;
            const completed = target.checked;
            updateTaskStatus(taskId, completed);
        }

        if (target.classList.contains('task-delete')) {
            const taskId = target.parentElement.dataset.id;
            deleteTask(taskId);
        }
    });

    function createTask(title, description) {
        const url = 'http://localhost:8080/api/task';
        const data = {title: title, description: description};

        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => response.json())
            .then(data => {
                console.log('Task created successfully:', data);
                addTaskToList(data.id, title);
            })
            .catch(error => {
                console.error('Error creating task:', error);
            });
    }

    function addTaskToList(id, title, description) {
        const li = document.createElement('li');
        li.dataset.id = id;
        li.innerHTML = `
            <span class="task-title">${title}</span>
            <span class="task-description">${description}</span>
            <input type="checkbox" class="task-completed">
            <button class="task-delete">Delete</button>
        `;

        const checkbox = li.querySelector('.task-completed');
        checkbox.addEventListener('change', () => {
            const completed = checkbox.checked;
            updateTaskStatus(id, completed);
        });

        taskList.appendChild(li);
    }

    function updateTaskStatus(id, completed) {
        fetch(`/api/task/updateStatus/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({completed})
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao atualizar o status da tarefa');
                }
                return response.json();
            })
            .then(data => {
                console.log('Status da tarefa atualizado com sucesso:', data);
            })
            .catch(error => {
                console.error(error);
            });
    }

    function getTaskList() {
        const url = 'http://localhost:8080/api/task';

        fetch(url)
            .then(response => response.json())
            .then(data => {
                data.forEach(task => {
                    addTaskToList(task.id, task.title, task.description);
                });
            })
            .catch(error => {
                console.error('Error fetching task list:', error);
            });
    }

    function deleteTask(taskId) {
        const url = `http://localhost:8080/api/task/${taskId}`;

        fetch(url, {
            method: 'DELETE'
        })
            .then(response => response.json())
            .then(data => {
                console.log('Task deleted successfully:', data);
                taskElement.remove();
            })
            .catch(error => {
                console.error('Error deleting task:', error);
            });
    }
});
