package com.project.todoapi.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;
}
