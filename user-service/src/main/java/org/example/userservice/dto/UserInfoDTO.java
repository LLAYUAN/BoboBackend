package org.example.userservice.dto;

import lombok.Data;

@Data
public class UserInfoDTO {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String role;
    private String avatar;
    private String createTime;
    private String updateTime;
    private Integer status;
    private String publicKey;
}
