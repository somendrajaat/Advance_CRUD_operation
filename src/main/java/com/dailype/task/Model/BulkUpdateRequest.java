package com.dailype.task.Model;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BulkUpdateRequest {
    private List<UUID> userIds;
    private UpdateData updateData;

    @Data
    public static class UpdateData {
        private String fullName;
        private String mobNum;
        private String panNum;
        private UUID managerId;
    }
}