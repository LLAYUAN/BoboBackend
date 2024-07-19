package org.example.recommendservice.controller;

import org.example.recommendservice.DTO.RoomCardInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {


    //    let url = `${RECOMMENDPREFIX}/searchForRoom?query=${query}`;
    @GetMapping("/searchForRoom")
    public List<RoomCardInfo> searchForRoom(@RequestParam String query){
        return null;
    }
}
