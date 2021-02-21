package com.nocomet.mydefault;


import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AwsS3Test {

    private static final String BUCKET_NAME = "java-test-1";

    private AmazonS3 s3client;

    @BeforeEach
    public void before() {
        s3client = AmazonS3ClientBuilder
            .standard()
            .withCredentials(new DefaultAWSCredentialsProviderChain()) //#1 - env 환경변수나 ~/.aws에 저장된 값을 읽어드림
            .withRegion(Regions.AP_NORTHEAST_2)
            .build();
    }

    @Test
    public void test() {
        String[] bucketList = new String[] { BUCKET_NAME };
        for (String bucketName : bucketList) {
            if (s3client.doesBucketExist(bucketName)) {
                s3client.deleteBucket(bucketName);
            }
            s3client.createBucket(bucketName); //#1 - 버킷 생성

            assertTrue(s3client.doesBucketExist(bucketName));
        }
    }

    @Test
    public void test_buckets_목록_프린트하기() {
        List<Bucket> buckets = s3client.listBuckets();
        for (Bucket b : buckets) {
            System.out.println("* " + b.getName());
        }
    }

    @Test
    public void test_bucket에_파일_올리기() throws IOException {
        String webImageUrl = "https://images-na.ssl-images-amazon.com/images/I/51ADJwz5bwL._SY355_.png";
        String filename = "/Users/Naver/development/tmp/test.png";
        downloadFileFromURL(webImageUrl, filename);

        String bucketKey = "image/test.png";
        s3client.putObject(BUCKET_NAME, bucketKey, new File(filename)); //#1 - 파일을 올리는 메서드임
    }

    private void downloadFileFromURL(String sourceUrl, String destPath) throws IOException {
        FileUtils.copyURLToFile(new URL(sourceUrl), new File(destPath));
    }

    @Test
    public void test_bucket에서_파일_다운로드하기() throws IOException {
        String destFilename = "/Users/Naver/development/tmp/test2.png";
        S3Object s3object = s3client.getObject(BUCKET_NAME, "image/test.png");

        S3ObjectInputStream inputStream = s3object.getObjectContent();
        System.out.println("getRedirectLocation: " + s3object.getRedirectLocation());
        FileUtils.copyInputStreamToFile(inputStream, new File(destFilename));  //#2 - 스트림을 파일로 저장함
    }
}
