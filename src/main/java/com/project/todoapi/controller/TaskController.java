package com.project.todoapi.controller;

import com.project.todoapi.exception.TaskNotFoundException;
import com.project.todoapi.model.Task;
import com.project.todoapi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/todos")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public Map<String, Object> readAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> result = taskService.readAllTasks(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("data", result.getContent());
        response.put("page", page);
        response.put("limit", size);
        response.put("total", result.getTotalElements());
        return response;
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
