package com.asouza.techtest.controller;

import com.asouza.techtest.model.Task;
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
    public ResponseEntity<Task> create(@RequestBody Task task){
        return ResponseEntity.ok().body(taskService.create(task));
    }

    @GetMapping
    public ResponseEntity<List<Task>> readAll(){
        return ResponseEntity.ok().body(taskService.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody Task task){
        return ResponseEntity.ok().body(taskService.update(id, task));
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<Task> updateStatus(@PathVariable Long id, @RequestBody String status){
        return ResponseEntity.ok().body(taskService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
