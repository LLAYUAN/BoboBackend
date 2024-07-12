package org.example.livevideoservice.controller;

import org.example.livevideoservice.repository.UserActivityRepository;
import org.example.livevideoservice.entity.Result;
import org.example.livevideoservice.entity.UserActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class UserActivityController {
    @Autowired
    private UserActivityRepository userActivityRepository;

    @PostMapping("/user-enter")
    public Result userEnter(@RequestBody Map<String, String> payload) {
        String userId = payload.get("userId");
        String roomId = payload.get("roomId");

        List<UserActivity> existingActivities = userActivityRepository.findByUserIdAndExitTimeIsNull(userId);
        if (!existingActivities.isEmpty()) {
            return Result.error("User is already in a room");
        }

        UserActivity activity = new UserActivity();
        activity.setUserId(userId);
        activity.setRoomId(roomId);
        activity.setEnterTime(LocalDateTime.now());

        userActivityRepository.save(activity);
        return Result.success();
    }

    @PostMapping("/user-exit")
    public Result userExit(@RequestBody Map<String, String> payload) {
        String userId = payload.get("userId");

        List<UserActivity> activities = userActivityRepository.findByUserIdAndExitTimeIsNull(userId);
        if (!activities.isEmpty()) {
            for (UserActivity activity : activities) {
                activity.setExitTime(LocalDateTime.now());
                userActivityRepository.save(activity);
            }
        }

        return Result.success("User exited");
    }

    @GetMapping("/active-users/{roomId}")
    public Result getActiveUsers(@PathVariable String roomId) {
        List<UserActivity> activeUsers = userActivityRepository.findByRoomIdAndExitTimeIsNull(roomId);
        return Result.success(activeUsers);
    }

    @GetMapping("/getUserId")
    public Result getUserId(@RequestHeader("Authorization") String authorizationHeader) {
        // Assuming the Authorization header contains a token that needs to be parsed
        // Here we simply return the header as is, you should implement proper token parsing
        return Result.success(authorizationHeader);
    }
}
