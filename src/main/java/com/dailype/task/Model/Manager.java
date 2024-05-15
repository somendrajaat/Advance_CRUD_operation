package com.dailype.task.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.UUID;
@Data
@Entity
public class Manager {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // getters and setters
}