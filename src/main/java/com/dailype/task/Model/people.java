package com.dailype.task.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

//import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class people {
    @Id
    @GeneratedValue
    private  UUID id;
    private String fullName;
    private String mobNum;
    private String panNum;
    @ManyToOne
    private Manager manager;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // getters and setters
}