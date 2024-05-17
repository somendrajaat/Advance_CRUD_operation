package com.dailype.task.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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
    @JoinColumn(name = "manager_id")
    @JsonBackReference
    private Manager manager;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // getters and setters
}