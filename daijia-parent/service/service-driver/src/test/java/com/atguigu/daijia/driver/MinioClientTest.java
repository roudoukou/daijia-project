package com.atguigu.daijia.driver;

import com.atguigu.daijia.driver.config.MinioClientBuilder;
import com.atguigu.daijia.driver.config.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author 肉豆蔻吖
 * @date 2024/9/26
 */
@SpringBootTest
public class MinioClientTest {
    @Autowired
    MinioClient minioClient;

    @Autowired
    MinioProperties minioProperties;

    @Test
    public void test1() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // Create a minioClient with the MinIO server playground, its access key and secret key.
        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint(minioProperties.getEndPoint())
                        .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                        .build();

        // Make 'asiatrip' bucket if not exist.
        String daijia = "asiatrip";
        boolean found =
                minioClient.bucketExists(BucketExistsArgs.builder().bucket(daijia).build());
        if (!found) {
            // Make a new bucket called 'asiatrip'.
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(daijia).build());
        } else {
            System.out.println("Bucket 'asiatrip' already exists.");
        }

    }

    @Test
    public void testBucketIsExist() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // Make 'asiatrip' bucket if not exist.
        String bucket = "daijia";
        boolean found =
                minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        if (!found) {
            // Make a new bucket called 'asiatrip'.
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        } else {
            System.out.println("Bucket '"+bucket+"' already exists.");
        }
    }

    @Test
    public void testUpload() throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // Upload a video file.
        minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket("my-bucketname")
                        .object("my-objectname")
                        .filename("my-video.avi")
                        .build());
    }
}
