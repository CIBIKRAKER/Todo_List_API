package com.project.todoapi.repository;

import com.project.todoapi.model.Task;
import com.project.todoapi.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findAllByUserOrderByIdAsc(User user, Pageable pageable);
}
