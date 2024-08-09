package org.example.livevideoservice.Feign;

import cn.hutool.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "recommend-service")
public interface RecommendFeign {
    @PostMapping("/roomInfo/createRoomHotIndex")
    void createRoomHotIndex(@RequestBody JSONObject request);

    @PostMapping("/roomInfo/deleteRoomHotIndex")
    void deleteRoomHotIndex(@RequestBody JSONObject request);
}