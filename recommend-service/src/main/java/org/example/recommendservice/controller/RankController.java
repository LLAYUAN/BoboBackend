package org.example.recommendservice.controller;

import com.alibaba.fastjson.JSONObject;
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
    @GetMapping("/{tag}/{page}/{size}")
    public List<RoomCardInfo> getRank(@PathVariable Integer tag, @PathVariable Integer page, @PathVariable Integer size) {
        return roomInfoService.getRank(tag, page, size);
    }
    @GetMapping("/count")
    public int getRoomCount() {
        return roomInfoService.getRoomCount();
    }
    @GetMapping("/recommend")
    public JSONObject recommendRooms(@RequestHeader("Authorization") String userId) {
        System.out.println("personal recommend userId: " + userId);
        return userBrowsHistoryService.recommendRooms(userId);

//        List<RoomCardInfo> rankList = roomInfoService.getRank(-1);
//        Set<RoomCardInfo> recommendSet = new HashSet<>(recommendList);
//        for (RoomCardInfo room : rankList) {
//            if (!recommendSet.contains(room)) {
//                recommendList.add(room);
//            }
//        }
    }

}
