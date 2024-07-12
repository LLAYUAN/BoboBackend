package org.example.userservice.controller;

import lombok.Data;
import org.example.userservice.Feign.Feign;
import org.example.userservice.common.CommonResult;
import org.example.userservice.dto.BasicRoomDTO;
import org.example.userservice.dto.BasicUserDTO;
import org.example.userservice.dto.UserInfoDTO;
import org.example.userservice.entity.RoomInfo;
import org.example.userservice.entity.UserInfo;
import org.example.userservice.service.UserInfoService;
import org.example.userservice.service.UserService;
import org.example.userservice.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private Feign feign;

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
        System.out.println("follow: " + followeeID);
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
        System.out.println("unfollow: " + followeeID);
        // follower即发起关注的人，为当前用户
        Integer followerID = Integer.parseInt(authorizationHeader);
        userService.unfollow(followeeID, followerID);
        return CommonResult.success("Unfollow successfully");
    }

    // 判断是否是当前浏览界面从属用户的粉丝，决定了关注按钮是显示为关注还是未关注
    @GetMapping(value = "/checkIsFan")
    public CommonResult checkIsFan(@RequestHeader("Authorization") String authorizationHeader,
                                   @RequestParam("followeeID") Integer followeeID) {
//        Integer followeeID = requestBody.get("followeeID");
        Integer followerID = Integer.parseInt(authorizationHeader);
        boolean isFan = userService.checkIsFan(followeeID, followerID);
        return CommonResult.success(isFan);
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

//    // 创建或更改直播间信息
//    @PostMapping(value = "/startLive")
//    public CommonResult startLive(@RequestHeader("Authorization") String authorizationHeader,
//                                  @RequestPart("coverImage") MultipartFile coverImage,
//                                  @RequestPart("name") String name,
//                                  @RequestPart("tags") Integer[] tags) {
//        Integer userID = Integer.parseInt(authorizationHeader);
//        String coverUrl = feign.uploadFile(coverImage);
//        userService.startLive(userID, name,tags, coverUrl);
//        Map<String,Object> result = new HashMap<>();
//        result.put("coverUrl",coverUrl);
//        result.put("roomID",userInfoService.getRoomIDByUserID(userID));
//        return CommonResult.success(result);
//    }

    @GetMapping(value = "/getRoomInfo")
    public CommonResult getRoomInfo(@RequestHeader("Authorization") String authorizationHeader) {
        Integer userID = Integer.parseInt(authorizationHeader);
        RoomInfo roomInfo = userInfoService.getRoomInfoByUserID(userID);
        return CommonResult.success(new BasicRoomDTO(roomInfo));
    }

    @PostMapping(value = "/visitInfo")
    public CommonResult visitInfo(@RequestHeader("Authorization") String authorizationHeader,
                                  @RequestBody Map<String,Integer> requestBody) {
        Integer userID = requestBody.get("userID");
        System.out.println("visitInfo: " + userID);
        UserInfo userInfo = userInfoService.findUserInfoByUserID(userID);
        Integer followerCount = userService.getFollowerCount(userID);
        Integer followeeCount = userService.getFolloweeCount(userID);
        boolean isFan = userService.checkIsFan(userID, Integer.parseInt(authorizationHeader));
        UserInfoDTO userInfoDTO = new UserInfoDTO(userInfo, followeeCount, followerCount, isFan);
//        userService.visitInfo(visitorID, visitedID);
        return CommonResult.success(userInfoDTO);
    }

    // 更改直播间信息并开播
    @PostMapping(value = "/createRoom")
    public CommonResult createRoom(@RequestHeader("Authorization") String authorizationHeader,
                                   @RequestBody Map<String, Object> requestBody) {
        Integer userID = Integer.parseInt(authorizationHeader);
        String roomName = (String) requestBody.get("title");
        String coverUrl = (String) requestBody.get("cover_image");
        List<Integer> tags = (List<Integer>) requestBody.get("tags");
        System.out.println("roomName: " + roomName);
        System.out.println("coverUrl: " + coverUrl);
        System.out.println("tags: " + tags);

        Integer roomID = userService.createRoom(userID, roomName, coverUrl , tags);
        return CommonResult.success(roomID);
    }

    @PostMapping(value = "/modifyPassword")
    public CommonResult modifyPassword(@RequestHeader("Authorization") String authorizationHeader,
                                       @RequestBody Map<String, String> requestBody) {
        Integer userID = Integer.parseInt(authorizationHeader);
        String oldPassword = requestBody.get("oldPassword");
        String newPassword = requestBody.get("newPassword");
        Boolean result = userInfoService.modifyPassword(userID, oldPassword, newPassword);
        if(!result) {
            return CommonResult.failed("修改密码失败");
        }
        return CommonResult.success("修改密码成功");
    }
}
