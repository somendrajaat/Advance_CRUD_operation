package com.dailype.task.Model;

import lombok.Data;

import java.util.UUID;
@Data
public class UserRequest {
    private String fullName;
    private String mobNum;
    private String panNum;
    private UUID managerId;

    // getters and setters
}