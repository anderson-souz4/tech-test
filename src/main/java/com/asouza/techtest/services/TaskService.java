package com.asouza.techtest.services;

import com.asouza.techtest.model.Status;
import com.asouza.techtest.model.Task;
import com.asouza.techtest.model.dto.TaskDTO;
import com.asouza.techtest.repositories.TaskRepository;
import com.asouza.techtest.services.exceptions.DataIntegrityViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ModelMapper modelMapper;

    public TaskDTO create(TaskDTO taskDTO) {
        Task task = modelMapper.map(taskDTO, Task.class);
        Task savedTask = taskRepository.save(task);
        return modelMapper.map(savedTask, TaskDTO.class);
    }

    public List<TaskDTO> readAll() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(task -> modelMapper.map(task, TaskDTO.class))
                .collect(Collectors.toList());

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


        public TaskDTO update(Long id, TaskDTO taskDTO) {
            return taskRepository.findById(id)
                    .map(existingTask -> {
                        modelMapper.map(taskDTO, existingTask);
                        Task updatedTask = taskRepository.save(existingTask);
                        return modelMapper.map(updatedTask, TaskDTO.class);
                    })
                    .orElseThrow(() -> new DataIntegrityViolationException("Task not found"));
        }
}

