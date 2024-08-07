package org.example.userservice.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "recordvideo-service")
public interface RecordFeign{
    @GetMapping("/test")
    String test();

    @PostMapping("/uploadFile")
    String uploadFile(@RequestPart("file") MultipartFile file);
}
