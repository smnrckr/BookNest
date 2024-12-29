package com.project.backend.dto;

public class FriendshipStatusDTO {
    String status;

    public FriendshipStatusDTO(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
