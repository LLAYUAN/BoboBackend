package org.example.messageservice.controller;

import org.example.messageservice.entity.ChatMessage;
import org.example.messageservice.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/history")
public class HistoryController {
    @Autowired
    private ChatService chatService;

    @GetMapping("/{roomID}")
    public CompletableFuture<ResponseEntity<List<ChatMessage>>> getHistoryMessages(@PathVariable Integer roomID, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant timestamp) {
        //System.out.println("roomID: " + roomID + ", timestamp: " + timestamp);
        return chatService.getHistoryMessages(roomID, timestamp)
                .thenApply(ResponseEntity::ok);
    }
}
