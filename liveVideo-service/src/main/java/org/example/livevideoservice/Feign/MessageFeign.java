package org.example.livevideoservice.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "message-service")
public interface MessageFeign {
    @DeleteMapping("/delete/{roomID}")
    void deleteRoomMessages(@PathVariable Integer roomID);
}
