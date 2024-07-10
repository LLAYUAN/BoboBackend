package org.example.recommendservice.controller;

import org.example.recommendservice.DTO.RoomCardInfo;
import org.example.recommendservice.Service.RoomInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rank")
public class RankController {
    @Autowired
    private RoomInfoService roomInfoService;
    @GetMapping("/{tag}")
    public List<RoomCardInfo> getRank(@PathVariable Integer tag) {
        return roomInfoService.getRank(tag);
    }

}
