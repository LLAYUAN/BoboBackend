package org.example.userservice.model;

import lombok.Data;

@Data
public class PasswordRequest {
    private String plainPassword; // 未加密的密码
    private String encodedPassword; // 从数据库取出的加密密码

    public PasswordRequest(String plainPassword, String encodedPassword) {
        this.plainPassword = plainPassword;
        this.encodedPassword = encodedPassword;
    }

    public PasswordRequest() {
    }
}
