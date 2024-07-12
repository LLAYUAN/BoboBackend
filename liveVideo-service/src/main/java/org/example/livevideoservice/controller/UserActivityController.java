package org.example.livevideoservice.controller;

import org.example.livevideoservice.Repository.UserActivityRepository;
import org.example.livevideoservice.entrty.Result;
import org.example.livevideoservice.entrty.UserActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserActivityController {
//    private Map<String, UserActivity> activeUsers = new HashMap<>();
//
//    @PostMapping("/api/user-enter")
//    public ResponseEntity<String> userEnter(@RequestBody Map<String, String> payload) {
//        String userId = payload.get("userId");
//        String roomId = payload.get("roomId");
//
//        UserActivity activity = new UserActivity();
//        activity.setUserId(userId);
//        activity.setRoomId(roomId);
//        activity.setEnterTime(LocalDateTime.now());
//
//        activeUsers.put(userId, activity);
//
//        return ResponseEntity.ok("User entered");
//    }
//
//    @PostMapping("/api/user-exit")
//    public ResponseEntity<String> userExit(@RequestBody Map<String, String> payload) {
//        String userId = payload.get("userId");
//
//        UserActivity activity = activeUsers.get(userId);
//        if (activity != null) {
//            activity.setExitTime(LocalDateTime.now());
//            // 在此处将活动记录保存到数据库中
//            activeUsers.remove(userId);
//        }
//
//        return ResponseEntity.ok("User exited");
//    }
//
//    @GetMapping("/api/active-users/{roomId}")
//    public ResponseEntity<List<UserActivity>> getActiveUsers(@PathVariable String roomId) {
//        List<UserActivity> activeUsersInRoom = new ArrayList<>();
//        for (UserActivity activity : activeUsers.values()) {
//            if (activity.getRoomId().equals(roomId)) {
//                activeUsersInRoom.add(activity);
//            }
//        }
//        return ResponseEntity.ok(activeUsersInRoom);
//    }
//
//    @GetMapping("/api/getUserId")
//    public Result getUserId(@RequestHeader("Authorization") String authorizationHeader) {
//        Integer userId = Integer.parseInt(authorizationHeader);
//
//        return Result.success(userId);
//    }
    @Autowired
    private UserActivityRepository userActivityRepository;

        @PostMapping("/api/user-enter")
        public ResponseEntity<String> userEnter(@RequestBody Map<String, String> payload) {
            String userId = payload.get("userId");
            String roomId = payload.get("roomId");

            UserActivity existingActivity = userActivityRepository.findByUserIdAndExitTimeIsNull(userId);
            if (existingActivity != null) {
                return ResponseEntity.badRequest().body("User is already in a room");
            }

            UserActivity activity = new UserActivity();
            activity.setUserId(userId);
            activity.setRoomId(roomId);
            activity.setEnterTime(LocalDateTime.now());

            userActivityRepository.save(activity);

            return ResponseEntity.ok("User entered");
        }

        @PostMapping("/api/user-exit")
        public ResponseEntity<String> userExit(@RequestBody Map<String, String> payload) {
            String userId = payload.get("userId");

            UserActivity activity = userActivityRepository.findByUserIdAndExitTimeIsNull(userId);
            if (activity != null) {
                activity.setExitTime(LocalDateTime.now());
                userActivityRepository.save(activity);
            }

            return ResponseEntity.ok("User exited");
        }

        @GetMapping("/api/active-users/{roomId}")
        public ResponseEntity<List<UserActivity>> getActiveUsers(@PathVariable String roomId) {
            List<UserActivity> activeUsers = userActivityRepository.findByRoomIdAndExitTimeIsNull(roomId);
            return ResponseEntity.ok(activeUsers);
        }
}
