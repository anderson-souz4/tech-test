package com.asouza.techtest.services;

import com.asouza.techtest.model.Status;
import com.asouza.techtest.model.Task;
import com.asouza.techtest.repositories.TaskRepository;
import com.asouza.techtest.services.exceptions.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task create(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> readAll() {
        return taskRepository.findAll();

    }

    public void delete(Long id) {
        Optional<Task> byId = taskRepository.findById(id);
        if (byId.isPresent()) {
            Task task = byId.get();
            if (task.getStatus() != Status.PENDING) {
                taskRepository.deleteById(id);
            } else {
                throw new DataIntegrityViolationException("PENDING tasks cannot  be deleted");
            }
        }
    }

    public Task update(Long id, Task task) {
        return taskRepository.findById(id).map(existingTask -> {
            existingTask.setTitle(task.getTitle());
            existingTask.setDescription(task.getDescription());
            existingTask.setStatus(task.getStatus());
            return taskRepository.save(existingTask);
        }).orElseThrow(() -> new DataIntegrityViolationException("Task not found"));
    }

    public Task updateStatus(Long id, String status) {
        Optional<Task> byId = taskRepository.findById(id);
        if (byId.isPresent()) {
            Task task = byId.get();
            task.setStatus(Status.valueOf(status));
            return taskRepository.save(task);
        }else {
            throw new DataIntegrityViolationException("PENDING tasks cannot  be deleted");
        }

    }
}

