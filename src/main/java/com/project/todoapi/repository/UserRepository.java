package com.project.todoapi.repository;

import com.project.todoapi.model.Task;
import com.project.todoapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
