package com.atguigu.daijia.driver.config;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 肉豆蔻吖
 * @date 2024/9/26
 */
// @Configuration
@Component
public class MinioClientBuild {

    @Autowired
    MinioProperties minioProperties;

    @Bean
    public MinioClient minioClient() {
        MinioClient client = MinioClient.builder()
                .endpoint(minioProperties.getEndPoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
        return client;
    }
}
