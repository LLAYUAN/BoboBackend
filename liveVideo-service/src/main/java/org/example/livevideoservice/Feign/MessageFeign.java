package org.example.livevideoservice.Feign;

import cn.hutool.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "message-service")
public interface MessageFeign {
    @DeleteMapping("/delete/{roomID}")
    void deleteRoomMessages(@PathVariable Integer roomID);
}
