package org.example.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompleteUser {
    private Integer id;
    private String username;
    private Integer status;
    private List<String> followers;
    private List<String> subscriptions;

}
