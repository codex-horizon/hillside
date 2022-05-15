package com.metaverse.hillside;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class HillsideGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(HillsideGatewayApplication.class, args);
    }
}
