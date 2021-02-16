package com.nocomet.holycard;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class HolyCardApplication {

    @Value("${phase}")
    private String phase;

    public static void main(String[] args) {
        SpringApplication.run(HolyCardApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner() {
        return (a) -> {
            log.info("CommandLineRunner phase {}", phase);
        };
    };
}
