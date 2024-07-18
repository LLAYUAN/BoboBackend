package org.example.livevideoservice.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "gateway")
public interface Feign {
    @PostMapping("/internal/user/setStatus")
    void setStatus(@RequestBody Map<String, Object> requestBody);
}
