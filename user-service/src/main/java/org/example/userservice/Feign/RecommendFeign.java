package org.example.userservice.Feign;

import cn.hutool.json.JSONObject;
import org.example.userservice.entity.RoomHotIndex;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "recommend-service")
public interface RecommendFeign {
    @PostMapping("/roomInfo/saveRoomHotIndex")
    void saveRoomHotIndex(@RequestBody List<RoomHotIndex> roomHotIndexList);

    @PostMapping("/roomInfo/createRoomHotIndex")
    void createRoomHotIndex(@RequestBody JSONObject request);
}
