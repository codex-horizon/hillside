package com.metaverse.hillside;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HillsideApplication {

    public static void main(String[] args) {
        SpringApplication.run(HillsideApplication.class, args);
    }
}
