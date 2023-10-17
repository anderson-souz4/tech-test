package com.asouza.techtest.controller;

import com.asouza.techtest.model.Task;
import com.asouza.techtest.model.dto.TaskDTO;
import com.asouza.techtest.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@CrossOrigin("*")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDTO> create(@RequestBody TaskDTO taskDTO){
        TaskDTO savedTaskDTO = taskService.create(taskDTO);
        return ResponseEntity.ok(savedTaskDTO);
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> readAll(){
        List<TaskDTO> taskDTOs = taskService.readAll();
        return ResponseEntity.ok().body(taskDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> update(@PathVariable Long id, @RequestBody TaskDTO taskDTO){
        TaskDTO updatedTaskDTO = taskService.update(id, taskDTO);

        return ResponseEntity.ok(updatedTaskDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
