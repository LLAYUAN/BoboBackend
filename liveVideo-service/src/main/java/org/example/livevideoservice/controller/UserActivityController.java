package org.example.livevideoservice.controller;

import org.example.livevideoservice.entity.Result;
import org.example.livevideoservice.entity.UserActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class UserActivityController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping("/user-enter")
    public Result userEnter(@RequestBody Map<String, String> payload) {
        String userId = payload.get("userId");
        String roomId = payload.get("roomId");

        // 插入新的用户进入记录
        UserActivity activity = new UserActivity();
        activity.setUserId(userId);
        activity.setRoomId(roomId);
        activity.setEnterTime(LocalDateTime.now());
        activity.setExitTime(null);

        mongoTemplate.save(activity);
        System.out.println(activity);
        return Result.success();
    }

    @PostMapping("/user-exit")
    public Result userExit(@RequestBody Map<String, String> payload) {
        String userId = payload.get("userId");
        String roomId = payload.get("roomId");

        // 查找最近一次用户进入的记录并更新退出时间
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId).and("roomId").is(roomId).and("exitTime").is(null));
        List<UserActivity> activities = mongoTemplate.find(query, UserActivity.class);

        if (!activities.isEmpty()) {
            UserActivity activity = activities.get(0);
            activity.setExitTime(LocalDateTime.now());
            mongoTemplate.save(activity);
        }

        return Result.success("User exited");
    }

    @GetMapping("/active-users/{roomId}")
    public Result getActiveUsers(@PathVariable String roomId) {
        // 查找所有exitTime为null的活动用户
        Query query = new Query();
        query.addCriteria(Criteria.where("roomId").is(roomId).and("exitTime").is(null));
        List<UserActivity> activeUsers = mongoTemplate.find(query, UserActivity.class);
        return Result.success(activeUsers);
    }

    @GetMapping("/getUserId")
    public Result getUserId(@RequestHeader("Authorization") String authorizationHeader) {
        // 假设Authorization头包含需要解析的token
        // 在这里我们直接返回头，您应当实现正确的token解析
        return Result.success(authorizationHeader);
    }
}
