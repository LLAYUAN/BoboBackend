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

//    @PostMapping("/saveRoomHotIndex")
//    public RoomHotIndex saveRoomHotIndex(@RequestBody RoomHotIndex roomHotIndex) {
//        return roomInfoService.saveRoomHotIndex(roomHotIndex);
//    }

    @PostMapping("/addRoomHotIndex")
    public void addRoomHotIndex(@RequestBody AddHotIndex addHotIndex) throws JsonProcessingException {
//        System.out.println(roomHotIndexList);
        String json = objectMapper.writeValueAsString(addHotIndex);
        rabbitTemplate.convertAndSend("addHotIndexQueue", json);
//        roomInfoService.addRoomHotIndex(addHotIndex);
    }

    @PostMapping("/saveRoomHotIndex")
    public void saveRoomHotIndex(@RequestBody List<RoomHotIndex> roomHotIndexList) {
//        System.out.println(roomHotIndexList);
        roomInfoService.saveRoomHotIndexList(roomHotIndexList);
    }


    @GetMapping("/{id}")
    public RoomCardInfo getRank(@PathVariable Integer id) {
        return roomInfoService.getRoomInfo(id);
    }
}
