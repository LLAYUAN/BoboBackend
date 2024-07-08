package org.example.userservice.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "gateway")
public interface LiveVideoFeign {
    @GetMapping("/internal/livevideo/test")
    String test();
}
