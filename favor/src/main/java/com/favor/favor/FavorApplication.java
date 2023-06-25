package com.favor.favor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FavorApplication {

    public static void main(String[] args) {
        SpringApplication.run(FavorApplication.class, args);
    }

}
