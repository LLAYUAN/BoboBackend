package org.example.recommendservice.controller;

import org.example.recommendservice.common.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller {
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @GetMapping(value = "/getUserInfo")
//    public CommonResult getUserInfo(@RequestHeader("Authorization") String authorizationHeader) {
//        // 获取token然后进行解析，得到用户名，再进一步获取用户信息
//        System.out.println("authorizationHeader: " + authorizationHeader);
//        String token = authorizationHeader.substring("Bearer ".length());
//        System.out.println("token: " + token);
//        String email = jwtTokenUtil.getUserNameFromToken(token);
//        System.out.println("email: " + email);
//        return CommonResult.success("email: " + email);
//    }
}
