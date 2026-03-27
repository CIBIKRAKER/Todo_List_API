package com.project.todoapi.service;


import com.project.todoapi.exception.TaskNotFoundException;
import com.project.todoapi.model.Task;
import com.project.todoapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;


    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> readAllTasks() {
        return taskRepository.findAllByOrderByIdAsc();
    }

    public Optional<Task> readTaskById(Long id)
    {
        return taskRepository.findById(id);
    }

    public Task updateTask(Task task, Long id) {
        Task existingTask = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);

        existingTask.setDescription(task.getDescription());
        existingTask.setName(task.getName());
        return taskRepository.save(existingTask);
    }

    public void deleteTaskById(Long id) {
        Task  task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        taskRepository.delete(task);
    }


}
