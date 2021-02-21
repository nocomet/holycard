package com.nocomet.holycard.config;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class S3Configuration {

    @Bean
    public AmazonS3 s3Client() {
        return AmazonS3ClientBuilder
            .standard()
            .withCredentials(new DefaultAWSCredentialsProviderChain()) //#1 - env 환경변수나 ~/.aws에 저장된 값을 읽어드림
            .withRegion(Regions.AP_NORTHEAST_2)
            .build();
    }
}