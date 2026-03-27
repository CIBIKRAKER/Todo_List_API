package com.project.todoapi.controller;

import com.project.todoapi.exception.TaskNotFoundException;
import com.project.todoapi.model.Task;
import com.project.todoapi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> readAllTasks()
    {
        return taskService.readAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> readTaskById(@PathVariable Long id)
    {
        return taskService.readTaskById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(TaskNotFoundException::new);
    }

    @PostMapping
    public Task createTask(@RequestBody Task task)
    {
        return taskService.saveTask(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        return ResponseEntity.ok(taskService.updateTask(updatedTask, id));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<?> deleteTask(@PathVariable Long id)
    {
        taskService.deleteTaskById(id);
        return ResponseEntity.ok().build();
    }
}
