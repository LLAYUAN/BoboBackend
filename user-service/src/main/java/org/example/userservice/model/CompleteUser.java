package org.example.userservice.model;

import lombok.Data;

import java.util.List;

@Data
public class CompleteUser {
    private Integer id;
    private String username;
    private Integer status;
    private List<String> followers;
    private List<String> subscriptions;

    // Getters and Setters
}
