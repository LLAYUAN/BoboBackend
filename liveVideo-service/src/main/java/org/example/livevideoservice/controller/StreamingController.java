package org.example.livevideoservice.controller;

import org.example.livevideoservice.Feign.Feign;
import org.example.livevideoservice.entity.Result;
import org.example.livevideoservice.entity.StreamRequest;
import org.example.livevideoservice.entity.RoomInfo;
import org.example.livevideoservice.repository.RoomInfoRepository;
import org.example.livevideoservice.repository.UserActivityRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class StreamingController {
    public static final Map<String, Process> processMap = new ConcurrentHashMap<>();

    @Autowired
    Feign feign;

    @Autowired
    private UserActivityRepository userActivityRepository;

    @PostMapping("/start-stream")
    public Result startStream(@RequestBody StreamRequest request){
        Map<String,Object> result = new HashMap<>();
        result.put("roomID",request.getRoomId());
        result.put("status",true);
        feign.setStatus(result);

        return Result.success();
    }

    @PostMapping("/stop-stream")
    public Result stopStream(@RequestBody StreamRequest request) {

        // Update room status to 0
        Map<String, Object> result = new HashMap<>();
        result.put("roomID", request.getRoomId());
        result.put("status", false);
        feign.setStatus(result);


        // Clear user activities for the room
        userActivityRepository.deleteByRoomId(request.getRoomId());

        return Result.success();
    }

}
