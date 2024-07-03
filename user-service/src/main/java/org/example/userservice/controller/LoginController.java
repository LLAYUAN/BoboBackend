package org.example.userservice.controller;

import org.example.userservice.common.CommonResult;
import org.example.userservice.entity.UserInfo;
import org.example.userservice.service.UserInfoService;
import org.example.userservice.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RefreshScope
@RestController
@RequestMapping(value = "/user")
public class LoginController {

    @Autowired
    public UserInfoService userInfoService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${rsa.publicKey}")
    private String publicKey;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping(value = "/publicKey")
    public CommonResult getPublicKey() {
        return CommonResult.success(publicKey);
    }

    @PostMapping(value = "/login")
    public CommonResult login(@Validated @RequestBody Map<String,Object> loginRequest) {
        Logger log = LoggerFactory.getLogger(LoginController.class);
        String email = (String) loginRequest.get("email");
        String password = (String) loginRequest.get("password");
        // 通过用户名和密码获取token
        String token = userInfoService.login(email, password);
        log.info("token: " + token);
        // 如果token为空，返回错误信息
        if (token == null) {
            return CommonResult.failed("用户名或密码错误");
        }
        // 如果token不为空，返回token
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @PostMapping(value = "/register")
    public CommonResult register(@Validated @RequestBody Map<String,Object> registerRequest) {
        Logger log = LoggerFactory.getLogger(LoginController.class);
        String email = (String) registerRequest.get("email");
        String password = (String) registerRequest.get("password");
        log.info("email: " + email+ " password: " + password+"is going to register");
        // 通过用户名和密码获取token
        UserInfo userInfo = userInfoService.register(email, password);
        // 如果userInfo为空，返回错误信息
        if (userInfo == null) {
            return CommonResult.failed("注册失败");
        }
        // 如果userInfo不为空，返回userInfo
        return CommonResult.success(userInfo);
    }

    @GetMapping(value = "/getUserInfo")
    public CommonResult getUserInfo(@RequestHeader("Authorization") String authorizationHeader) {
        // 获取token然后进行解析，得到用户名，再进一步获取用户信息
        Integer userID = jwtTokenUtil.getUserIDFromHeader(authorizationHeader);
        System.out.println("userID： " + userID.toString());
        return CommonResult.success("userID " + userID.toString());
    }
}
