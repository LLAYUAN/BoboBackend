package org.example.recommendservice.controller;

import org.example.recommendservice.DTO.RoomCardInfo;
import org.example.recommendservice.Service.RoomInfoService;
import org.example.recommendservice.Service.UserBrowsHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        List<RoomCardInfo> recommendList = new ArrayList<>(userBrowsHistoryService.recommendRooms(userId));
        List<RoomCardInfo> rankList = roomInfoService.getRank(-1);

        Set<RoomCardInfo> recommendSet = new HashSet<>(recommendList);
        for (RoomCardInfo room : rankList) {
            if (!recommendSet.contains(room)) {
                recommendList.add(room);
            }
        }

        return recommendList;
    }

}
