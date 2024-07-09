package org.example.userservice.controller;

import org.example.userservice.common.CommonResult;
import org.example.userservice.dto.BasicUserDTO;
import org.example.userservice.dto.UserInfoDTO;
import org.example.userservice.entity.UserInfo;
import org.example.userservice.service.UserInfoService;
import org.example.userservice.service.UserService;
import org.example.userservice.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RefreshScope
@RestController
public class UserController {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserService userService;

    // 汇总的接口
    @GetMapping(value = "/personalProfile")
    public CommonResult personalProfile(@RequestHeader("Authorization") String authorizationHeader) {
        System.out.println("getting personal profile: " + authorizationHeader);
        Integer userID = Integer.parseInt(authorizationHeader);
        UserInfo userInfo = userInfoService.findUserInfoByUserID(userID);
        UserInfoDTO userInfoDTO = new UserInfoDTO(userInfo);
        List<BasicUserDTO> followees = userService.getFollowees(userID);
        List<BasicUserDTO> followers = userService.getFollowers(userID);
        // 将三个部分分别叫做user,following,followers传给前端
        Map<String, Object> result = new HashMap<>();
        result.put("user", userInfoDTO);
        result.put("following", followees);
        result.put("followers", followers);
        return CommonResult.success(result);
    }

    @GetMapping(value = "/getUserInfo")
    public CommonResult getUserInfo(@RequestHeader("Authorization") String authorizationHeader) {
        // 获取token然后进行解析，得到用户名，再进一步获取用户信息
        Integer userID = Integer.parseInt(authorizationHeader);
        UserInfo userInfo = userInfoService.findUserInfoByUserID(userID);
        UserInfoDTO userInfoDTO = new UserInfoDTO(userInfo);
        System.out.println("userID： " + userID.toString());
        return CommonResult.success(userInfoDTO);
    }

    // 获取该用户关注的所有用户
    @GetMapping(value = "/getFollowees")
    public CommonResult getFollowees(@RequestHeader("Authorization") String authorizationHeader) {
        Integer userID = Integer.parseInt(authorizationHeader);
        List<BasicUserDTO> followees = userService.getFollowees(userID);
//        List<Integer> followees = userInfoService.getFollowees(userID);
//        return CommonResult.success(userInfoService.getFollowees(userID));
        return CommonResult.success(followees);
    }

    // 获取该用户的所有粉丝
    @GetMapping(value = "/getFollowers")
    public CommonResult getFollowers(@RequestHeader("Authorization") String authorizationHeader) {
        Integer userID = Integer.parseInt(authorizationHeader);
        List<BasicUserDTO> followers = userService.getFollowers(userID);
        return CommonResult.success(followers);
    }

    // 点击关注按钮
    @PostMapping(value = "/follow")
    public CommonResult follow(@RequestHeader("Authorization") String authorizationHeader,
                               @RequestBody Map<String, Integer> requestBody) {
        Integer followeeID = requestBody.get("followeeID");
        // follower即发起关注的人，为当前用户
        Integer followerID = Integer.parseInt(authorizationHeader);
        userService.follow(followeeID, followerID);
        return CommonResult.success("Follow successfully");
    }

    // 点击取消关注按钮
    @PostMapping(value = "/unfollow")
    public CommonResult unfollow(@RequestHeader("Authorization") String authorizationHeader,
                                 @RequestBody Map<String, Integer> requestBody) {
        Integer followeeID = requestBody.get("followeeID");
        // follower即发起关注的人，为当前用户
        Integer followerID = Integer.parseInt(authorizationHeader);
        userService.unfollow(followeeID, followerID);
        return CommonResult.success("Unfollow successfully");
    }

    // 更新用户信息
    @PostMapping(value = "/updateUserInfo")
    public CommonResult updateUserInfo(@RequestHeader("Authorization") String authorizationHeader,
                                       @RequestBody UserInfoDTO userInfoDTO) {
        Integer userID = Integer.parseInt(authorizationHeader);
        UserInfo userInfo = userInfoService.findUserInfoByUserID(userID);
        userInfo.setNickname(userInfoDTO.getNickname());
        userInfo.setAvatarUrl(userInfoDTO.getAvatarUrl());
        userInfo.setBirthday(CommonUtil.stringToDate(userInfoDTO.getBirthday()));
        userInfo.setSelfIntro(userInfoDTO.getIntroduction());
        userInfo.setEmail(userInfoDTO.getEmail());
        userInfoService.save(userInfo);
        return CommonResult.success("Update user info successfully");
    }

    //
}
