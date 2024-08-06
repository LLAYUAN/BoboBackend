package org.example.userservice.Feign;

import cn.hutool.json.JSONObject;
import org.example.userservice.entity.RoomHotIndex;
import org.example.userservice.model.PasswordRequest;
import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "gateway")
public interface Feign {
    @GetMapping("/internal/recordvideo/test")
    String test();

    @PostMapping("/internal/recordvideo/uploadFile")
    String uploadFile(@RequestPart("file") MultipartFile file);

    @PostMapping("/internal/recommend/roomInfo/saveRoomHotIndex")
    void saveRoomHotIndex(@RequestBody List<RoomHotIndex> roomHotIndexList);

    @PostMapping("/internal/gateway/matchPassword")
    Boolean matchPassword(@RequestBody PasswordRequest passwordRequest);

    @PostMapping("/internal/gateway/encode")
    String encode(@RequestBody String password);

    @PostMapping("/internal/gateway/generateToken")
    String generateToken(@RequestBody Integer userID);

    @PostMapping("/internal/recommend/roomInfo/createRoomHotIndex")
    void createRoomHotIndex(@RequestBody JSONObject request);
}
