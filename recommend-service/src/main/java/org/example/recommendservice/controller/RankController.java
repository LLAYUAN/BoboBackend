package org.example.recommendservice.controller;

import org.example.recommendservice.DTO.RoomCardInfo;
import org.example.recommendservice.Service.RoomInfoService;
import org.example.recommendservice.Service.UserBrowsHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rank")
public class RankController {
    @Autowired
    private RoomInfoService roomInfoService;

    @Autowired
    private UserBrowsHistoryService userBrowsHistoryService;
    @GetMapping("/{tag}")
    public List<RoomCardInfo> getRank(@PathVariable Integer tag) {
        return roomInfoService.getRank(tag);
    }

    @GetMapping("/recommend")
    public List<RoomCardInfo> recommendRooms(@RequestHeader("Authorization") String userId) {
        System.out.println("personal recommend userId: " + userId);
        return userBrowsHistoryService.recommendRooms(userId);
    }
}
