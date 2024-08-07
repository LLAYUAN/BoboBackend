package org.example.passwordservice.controller;

import lombok.Data;
import org.example.passwordservice.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PasswordController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Data
    static
    class PasswordRequest {
        private String plainPassword; // 未加密的密码
        private String encodedPassword; // 从数据库取出的加密密码

        public PasswordRequest(String plainPassword, String encodedPassword) {
            this.plainPassword = plainPassword;
            this.encodedPassword = encodedPassword;
        }

        public PasswordRequest() {
        }
    }

    @PostMapping("/matchPassword")
    public Boolean matchPassword(@RequestBody PasswordRequest passwordRequest) {
        System.out.println(passwordRequest);
        // 这里实现你的编码逻辑
        System.out.println("plainPassword: " + passwordRequest.getPlainPassword());
        System.out.println("encodedPassword: " + passwordRequest.getEncodedPassword());
        Boolean result = passwordEncoder.matches(passwordRequest.getPlainPassword(), passwordRequest.getEncodedPassword());
        System.out.println("result: " + result);
        return passwordEncoder.matches(passwordRequest.getPlainPassword(), passwordRequest.getEncodedPassword());
    }

    @PostMapping("/generateToken")
    public String generateToken(@RequestBody Integer userID) {
        System.out.println("userID: " + userID);
        String token = jwtTokenUtil.generateToken(userID);
//        String token = "";
        System.out.println("token: " + token);
        return token;
    }

    @PostMapping("/encode")
    public String encode(@RequestBody String password) {
        System.out.println("password: " + password);
        String encodedPassword = passwordEncoder.encode(password);
        System.out.println("encodedPassword: " + encodedPassword);
        return passwordEncoder.encode(password);
    }
}

