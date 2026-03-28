package com.project.todoapi.service;


import com.project.todoapi.exception.TaskNotFoundException;
import com.project.todoapi.model.Task;
import com.project.todoapi.model.User;
import com.project.todoapi.repository.TaskRepository;
import com.project.todoapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;


    public Task saveTask(Task task) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        task.setUser(user);
        return taskRepository.save(task);
    }

    public Page<Task> readAllTasks(Pageable pageable) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        return taskRepository.findAllByUserOrderByIdAsc(user, pageable);
    }

    public Optional<Task> readTaskById(Long id)
    {
        return taskRepository.findById(id);
    }

    public Task updateTask(Task task, Long id) {
        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        Task existingTask = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);

        if(!existingTask.getUser().getUsername().equals(username)){
            throw new RuntimeException("Not authorized");
        }

        existingTask.setDescription(task.getDescription());
        existingTask.setName(task.getName());
        return taskRepository.save(existingTask);
    }

    public void deleteTaskById(Long id) {
        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        Task  task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);

        if(!task.getUser().getUsername().equals(username)){
            throw new RuntimeException("Not authorized");
        }

        taskRepository.delete(task);
    }


}
