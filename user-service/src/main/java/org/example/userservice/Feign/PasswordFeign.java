package org.example.userservice.Feign;

import org.example.userservice.model.PasswordRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "password-service")
public interface PasswordFeign {

    @PostMapping("/matchPassword")
    Boolean matchPassword(@RequestBody PasswordRequest passwordRequest);

    @PostMapping("/encode")
    String encode(@RequestBody String password);

    @PostMapping("/generateToken")
    String generateToken(@RequestBody Integer userID);
}

