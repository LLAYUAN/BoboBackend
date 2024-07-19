package org.example.recommendservice.controller;

import org.example.recommendservice.Service.UserBrowsHistoryService;
import org.example.recommendservice.entity.BrowsingRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history")
public class BrowsHistoryController {
    @Autowired
    UserBrowsHistoryService userBrowsHistoryService;

    @PostMapping("/add")
    public ResponseEntity<Void> addBrowsingRecord(
            @RequestHeader("Authorization") String userId,
            @RequestBody BrowsingRecord browsingRecord) {
//        System.out.println("addBrowsingRecord: " + browsingRecord);
        userBrowsHistoryService.addBrowsingRecord(userId, browsingRecord);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/set")
    public void setBrowsingRecord(
            @RequestHeader("Authorization") String userId,
            @RequestBody List<BrowsingRecord> browsingHistory) {
        userBrowsHistoryService.setBrowsingRecord(userId, browsingHistory);
    }
}
