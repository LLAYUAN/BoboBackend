package org.example.recommendservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.recommendservice.DTO.AddHotIndex;
import org.example.recommendservice.DTO.UserBrowsingRecord;
import org.example.recommendservice.Service.UserBrowsHistoryService;
import org.example.recommendservice.entity.BrowsingRecord;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/history")
public class BrowsHistoryController {
    @Autowired
    UserBrowsHistoryService userBrowsHistoryService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/add")
    public ResponseEntity<Void> addBrowsingRecord (
            @RequestHeader("Authorization") String userId,
            @RequestBody BrowsingRecord browsingRecord) throws JsonProcessingException {
//        System.out.println("userId: " + userId + "  addBrowsingRecord: " + browsingRecord);
        UserBrowsingRecord userBrowsingRecord = new UserBrowsingRecord(userId, browsingRecord);
        String json = objectMapper.writeValueAsString(userBrowsingRecord);
        rabbitTemplate.convertAndSend("historyQueue", json);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/set")
    public void setBrowsingRecord(
            @RequestHeader("Authorization") String userId,
            @RequestBody List<BrowsingRecord> browsingHistory) {
        userBrowsHistoryService.setBrowsingRecord(userId, browsingHistory);
    }
}
