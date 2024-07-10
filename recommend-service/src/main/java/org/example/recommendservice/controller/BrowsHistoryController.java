package org.example.recommendservice.controller;

import org.example.recommendservice.Service.UserBrowsHistoryService;
import org.example.recommendservice.entity.BrowsingRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/history")
public class BrowsHistoryController {
    @Autowired
    UserBrowsHistoryService userBrowsHistoryService;

    @PostMapping("/add")
    public ResponseEntity<String> addBrowsingRecord(
            @RequestHeader("Authorization") String userId,
//            @PathVariable String userId,
            @RequestBody BrowsingRecord browsingRecord) {
        userBrowsHistoryService.addBrowsingRecord(userId, browsingRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body("Browsing record added successfully.");
    }
}
