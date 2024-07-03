package org.example.recommendservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class RecommendServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecommendServiceApplication.class, args);
    }

}
