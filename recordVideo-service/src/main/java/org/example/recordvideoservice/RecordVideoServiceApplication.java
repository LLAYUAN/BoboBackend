package org.example.recordvideoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class RecordVideoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecordVideoServiceApplication.class, args);
    }

}
