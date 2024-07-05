package org.example.userservice.controller;

import org.example.userservice.common.CommonResult;
import org.example.userservice.dto.UserInfoDTO;
import org.example.userservice.entity.UserInfo;
import org.example.userservice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class UserController {
    @Autowired
    private UserInfoService userInfoService;


    @GetMapping(value = "/getUserInfo")
    public CommonResult getUserInfo(@RequestHeader("Authorization") String authorizationHeader) {
        // 获取token然后进行解析，得到用户名，再进一步获取用户信息
        Integer userID = Integer.parseInt(authorizationHeader);
        UserInfo userInfo = userInfoService.findUserInfoByUserID(userID);
        UserInfoDTO userInfoDTO = new UserInfoDTO(userInfo);
        System.out.println("userID： " + userID.toString());
        return CommonResult.success(userInfoDTO);
    }
}
