package com.upsaude;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class UpSaudeApplication {

    public static void main(String[] args) {
        SpringApplication.run(UpSaudeApplication.class, args);
    }

}