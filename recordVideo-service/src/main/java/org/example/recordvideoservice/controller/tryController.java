package org.example.recordvideoservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;

import java.util.HashMap;
import java.util.Map;

@RefreshScope
@RestController
public class tryController {
    @GetMapping(value = "/test")
    public String test() {
        return "recordvideo-test";
    }
}
