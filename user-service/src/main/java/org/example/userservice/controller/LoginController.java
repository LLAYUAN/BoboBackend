package org.example.userservice.controller;

import org.example.userservice.Feign.Feign;
import org.example.userservice.common.CommonResult;
import org.example.userservice.dto.UserInfoDTO;
import org.example.userservice.entity.UserInfo;
import org.example.userservice.service.UserInfoService;
//import org.example.userservice.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//@RefreshScope
@RestController
//@RequestMapping(value = "/user")
public class LoginController {

    @Autowired
    public UserInfoService userInfoService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${rsa.publicKey}")
    private String publicKey;

    @Autowired
    private Environment environment;

//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private Feign feign;

    @GetMapping(value = "/publicKey")
    public CommonResult getPublicKey(@RequestHeader("Authorization") String authorizationHeader) {
        Integer userID = Integer.parseInt(authorizationHeader);
        System.out.println("userID： " + userID.toString());
        System.out.println("publicKey: " + publicKey);
        return CommonResult.success(publicKey);
    }

    @GetMapping(value = "/test")
    public CommonResult test() {
        String test = feign.test();
        return CommonResult.success(test);
//        String port = environment.getProperty("local.server.port");
//        if ("8082".equals(port)) {
//            return CommonResult.success("This is instance running on port 8082");
//        } else if ("8086".equals(port)) {
//            return CommonResult.success("This is instance running on port 8086");
//        }
////        return "Unknown instance";
//        return CommonResult.success("test");
    }

    @PostMapping(value = "/login")
    public CommonResult login(@Validated @RequestBody Map<String,Object> loginRequest) {
        Logger log = LoggerFactory.getLogger(LoginController.class);
        String email = (String) loginRequest.get("email");
        String password = (String) loginRequest.get("password");
        log.info("email: " + email+ " password: " + password+" is going to login");
        // 通过用户名和密码获取token
        UserInfo userInfo = null;
        String token = userInfoService.login(email, password,userInfo);
        log.info("token: " + token);
        // 如果token为空，返回错误信息
        if (token == null) {
            return CommonResult.failed("用户名或密码错误");
        }
        // 如果token不为空，返回token
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("token", token);
        dataMap.put("tokenHead", tokenHead);
        userInfo = userInfoService.findUserInfoByEmail(email);
        UserInfoDTO userInfoDTO = new UserInfoDTO(userInfo.getUserID(), userInfo.getNickname(),email);
        dataMap.put("userInfo", userInfoDTO);
        return CommonResult.success(dataMap);
    }

    @PostMapping(value = "/register")
    public CommonResult register(@Validated @RequestBody Map<String,Object> registerRequest) {
        Logger log = LoggerFactory.getLogger(LoginController.class);
        String email = (String) registerRequest.get("email");
        String password = (String) registerRequest.get("password");
        log.info("email: " + email+ " password: " + password+" is going to register");
        // 通过用户名和密码获取token
        UserInfo userInfo = userInfoService.register(email, password);
        // 如果userInfo为空，返回错误信息
        if (userInfo == null) {
            return CommonResult.failed("注册失败");
        }
        // 如果userInfo不为空，返回userInfo
        userInfo.setNickname("用户"+userInfo.getUserID().toString());
        userInfoService.save(userInfo);
        return CommonResult.success(null);
    }

}
