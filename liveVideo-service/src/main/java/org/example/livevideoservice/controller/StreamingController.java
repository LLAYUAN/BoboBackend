package org.example.livevideoservice.controller;

import cn.hutool.json.JSONObject;
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

//    @Autowired
//    private RoomInfoRepository roomInfoRepository;

    @Autowired
    private UserActivityRepository userActivityRepository;

    @PostMapping("/start-stream")
    public Result startStream(@RequestBody StreamRequest request){
        Logger log = org.slf4j.LoggerFactory.getLogger(StreamingController.class);
//        Map<String,Object> result = new HashMap<>();
//        result.put("roomID",request.getRoomId());
//        result.put("status",true);
//        feign.setStatus(result);
        String roomId = request.getRoomId();
        Boolean[] tagsArray = request.getTags();
        JSONObject json = new JSONObject();
        json.put("roomId",roomId);
//        Boolean[] tagsArray = new Boolean[3];
//        RoomInfo roomInfo = roomInfoRepository.findByRoomID(Integer.parseInt(roomId));
//        tagsArray[0] = roomInfo.getStudy();
//        tagsArray[1] = roomInfo.getEntertain();
//        tagsArray[2] = roomInfo.getOther();
        json.put("tags", tagsArray);
        log.info("json: " + json);
        feign.createRoomHotIndex(json);
        return Result.success();
    }

    @PostMapping("/stop-stream")
    public Result stopStream(@RequestBody StreamRequest request) {

        // Update room status to 0
        Map<String, Object> result = new HashMap<>();
        result.put("roomID", request.getRoomId());
        result.put("status", false);
//        feign.setStatus(result);


        // Clear user activities for the room
        userActivityRepository.deleteByRoomId(request.getRoomId());

        return Result.success();
    }

}
