package org.example.recommendservice.controller;

import org.example.recommendservice.DTO.RoomCardInfo;
import org.example.recommendservice.Service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {
    @Autowired
    private SearchService searchService;

    //    let url = `${RECOMMENDPREFIX}/searchForRoom?query=${query}`;
    @GetMapping("/searchForRoom")
    public List<RoomCardInfo> searchForRoom(@RequestParam String query,@RequestHeader("Authorization") String userId){
        return searchService.searchByRelevancy(query,userId);
    }
}
