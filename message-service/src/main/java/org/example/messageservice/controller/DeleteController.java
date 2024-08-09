package org.example.messageservice.controller;

import org.example.messageservice.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteController {
    @Autowired
    ChatService chatService;

    @DeleteMapping("/delete/{roomID}")
    public void deleteRoomMessages(@PathVariable Integer roomID) {

        System.out.println("Received request to delete messages for roomID: " + roomID);

        chatService.deleteRoomMessages(roomID);
    }
}
