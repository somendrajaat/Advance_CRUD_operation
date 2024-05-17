package com.dailype.task.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
@Data
@Entity
public class Manager {
    @Id

    private UUID id;
    private String name;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "manager", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<people> people;


    // getters and setters
}