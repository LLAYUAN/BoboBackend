package org.example.recommendservice.controller;

import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.recommendservice.DTO.AddHotIndex;
import org.example.recommendservice.DTO.RoomCardInfo;
import org.example.recommendservice.Service.RoomInfoService;
import org.example.recommendservice.entity.RoomHotIndex;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roomInfo")
public class RoomInfoController {
    @Autowired
    private RoomInfoService roomInfoService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/saveRoomHotIndex")
    public void saveRoomHotIndex(@RequestBody List<RoomHotIndex> roomHotIndexList) {
        roomHotIndexList.forEach(System.out::println);
        roomInfoService.saveRoomHotIndexList(roomHotIndexList);
    }

    @PostMapping("/createRoomHotIndex")
    public void createRoomHotIndex(@RequestBody JSONObject request) {
        int roomId = request.getInt("roomId");
        List<Boolean> tags = request.getJSONArray("tags").toList(Boolean.class);
        System.out.println("createRoomHotIndex::  roomId: " + roomId + " ,  tags: " + tags);
        roomInfoService.createRoomHotIndex(roomId, tags);
    }

    @PostMapping("/addRoomHotIndex")
    public ResponseEntity<Void> addRoomHotIndex(@RequestBody AddHotIndex addHotIndex) throws JsonProcessingException {
        System.out.println("addRoomHotIndex:: AddHotIndex: " + addHotIndex.getRoomId());
        String json = objectMapper.writeValueAsString(addHotIndex);
        rabbitTemplate.convertAndSend("addHotIndexQueue", json);
//        roomInfoService.addRoomHotIndex(addHotIndex);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public RoomCardInfo getRank(@PathVariable Integer id) {
        System.out.println("getRoomInfo: " + id);
        return roomInfoService.getRoomInfo(id);
    }
}
