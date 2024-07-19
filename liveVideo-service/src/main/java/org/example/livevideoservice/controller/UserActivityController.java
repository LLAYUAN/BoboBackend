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
        String nickname = payload.get("nickname");

        System.out.println("User"+nickname+ "entered room: " + roomId);

        // 查找该用户在该房间的所有活动记录
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId).and("roomId").is(roomId));
        List<UserActivity> activities = mongoTemplate.find(query, UserActivity.class);

        System.out.println("activities: " + activities);

        boolean alreadyEntered = false;
        for (UserActivity activity : activities) {
            if (activity.isEnter()) {
                alreadyEntered = true;
                break;
            }
        }

        if (alreadyEntered) {
            return Result.error("User already entered");
        } else {
            UserActivity userActivity = new UserActivity();
            userActivity.setEnter(true);
            userActivity.setRoomId(roomId);
            userActivity.setUserId(userId);
            userActivity.setNickname(nickname);
            mongoTemplate.save(userActivity);
        }
        return Result.success("User entered");
    }

    @PostMapping("/user-exit")
    public Result userExit(@RequestBody Map<String, String> payload) {
        String userId = payload.get("userId");
        String roomId = payload.get("roomId");

        System.out.println("User exited room: " + roomId);

        // 查找最近一次用户进入的记录并更新退出时间
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId).and("roomId").is(roomId).and("isEnter").is(true));
        List<UserActivity> activities = mongoTemplate.find(query, UserActivity.class);

        System.out.println("activities: " + activities);

        if (!activities.isEmpty()) {
            for (UserActivity activity : activities) {
                activity.setEnter(false);
                mongoTemplate.save(activity);

                System.out.println(activity);
            }
        }

        return Result.success("User exited");
    }

    @GetMapping("/active-users/{roomId}")
    public Result getActiveUsers(@PathVariable String roomId) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("roomId").is(roomId).and("isEnter").is(true)),
                Aggregation.group("userId").first("$$ROOT").as("activeUser"),
                Aggregation.replaceRoot("activeUser")
        );

        AggregationResults<UserActivity> results = mongoTemplate.aggregate(aggregation, "userActivity", UserActivity.class);
        List<UserActivity> activeUsers = results.getMappedResults();

        return Result.success(activeUsers);
    }

    @GetMapping("/getUserId")
    public Result getUserId(@RequestHeader("Authorization") String authorizationHeader) {
        return Result.success(authorizationHeader);
    }
}
