package org.example.livevideoservice.controller;

import org.example.livevideoservice.entity.Result;
import org.example.livevideoservice.entity.UserActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
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

        System.out.println("User entered room: " + roomId);

        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId).and("roomId").is(roomId).and("exitTime").is(null));
        List<UserActivity> activities = mongoTemplate.find(query, UserActivity.class);

        System.out.println("activities: " + activities);
        if (!activities.isEmpty()){
            return Result.error("User already entered");
        }

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

        System.out.println("User exited room: " + roomId);

        // 查找最近一次用户进入的记录并更新退出时间
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId).and("roomId").is(roomId).and("exitTime").is(null));
        List<UserActivity> activities = mongoTemplate.find(query, UserActivity.class);

        System.out.println("activities: " + activities );

        if (!activities.isEmpty()) {
            for(UserActivity activity: activities){
                activity.setExitTime(LocalDateTime.now());
                mongoTemplate.save(activity);

                System.out.println(activity);
            }
        }

        return Result.success("User exited");
    }

    @GetMapping("/active-users/{roomId}")
    public Result getActiveUsers(@PathVariable String roomId) {
        // 使用 Aggregation 框架按照 userId 分组，选择每个分组的第一个活动记录
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("roomId").is(roomId).and("exitTime").is(null)),
                Aggregation.group("userId").first("$$ROOT").as("activeUser"),
                Aggregation.replaceRoot("activeUser")
        );

        AggregationResults<UserActivity> results = mongoTemplate.aggregate(aggregation, "userActivity", UserActivity.class);
        List<UserActivity> activeUsers = results.getMappedResults();

        return Result.success(activeUsers);
    }

    @GetMapping("/getUserId")
    public Result getUserId(@RequestHeader("Authorization") String authorizationHeader) {
        // 假设Authorization头包含需要解析的token
        // 在这里我们直接返回头，您应当实现正确的token解析
        return Result.success(authorizationHeader);
    }
}
