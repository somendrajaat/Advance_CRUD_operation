package com.dailype.task.Model;

import jakarta.persistence.Id;
import lombok.Data;

import java.net.SocketOption;
import java.util.UUID;

@Data
public class managerRequest {
    @Id
    private UUID id;
    private String name;


}
