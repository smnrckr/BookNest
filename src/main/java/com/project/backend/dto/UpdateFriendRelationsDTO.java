package com.project.backend.dto;

public class UpdateFriendRelationsDTO {
    private String username;
    private String friendUsername;
    private String newStatus;

    public UpdateFriendRelationsDTO(String username, String friendUsername, String newStatus) {
        this.username = username;
        this.friendUsername = friendUsername;
        this.newStatus = newStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFriendUsername() {
        return friendUsername;
    }

    public void setFriendUsername(String friendUsername) {
        this.friendUsername = friendUsername;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }
}
