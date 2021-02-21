package com.nocomet.holycard.controller;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/health")
public class HealthController {

    @Value("${phase}")
    private String phase;

    private final AmazonS3 s3Client;

    @GetMapping("/ping")
    public String ping() {
        ZonedDateTime now = ZonedDateTime.now();
        return "pong: " + now.toString();
    }

    @GetMapping("/phase")
    public String phase() {
        return phase;
    }

    @GetMapping("/s3")
    public String s3() {
        return s3Client.getRegionName();
    }
}