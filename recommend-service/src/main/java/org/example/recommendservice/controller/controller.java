package org.example.recommendservice.controller;

import org.example.recommendservice.common.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class controller {
    @GetMapping(value = "/test")
    public String test() {
        return "test";
    }
}
