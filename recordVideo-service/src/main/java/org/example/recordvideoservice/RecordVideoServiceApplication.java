package org.example.recordvideoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class RecordVideoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecordVideoServiceApplication.class, args);
    }

}
