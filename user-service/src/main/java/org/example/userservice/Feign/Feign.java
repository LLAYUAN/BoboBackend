package org.example.userservice.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "gateway")
public interface Feign {
    @GetMapping("/internal/recordvideo/test")
    String test();

    @PostMapping("/internal/recordvideo/uploadFile")
    String uploadFile(@RequestPart("file") MultipartFile file);
}
