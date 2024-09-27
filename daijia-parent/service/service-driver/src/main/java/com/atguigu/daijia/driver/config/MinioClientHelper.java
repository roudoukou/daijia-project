package com.atguigu.daijia.driver.config;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 肉豆蔻吖
 * @date 2024/9/27
 */
@Component
public class MinioClientHelper {
    @Autowired
    MinioClient minioClient;

    @Autowired
    MinioProperties minioProperties;

    public void uploadObject(String object, MultipartFile file) {
        try {
            PutObjectArgs putObject = PutObjectArgs.builder().bucket(minioProperties.getBucketName()).object(object).stream(file.getInputStream(), file.getSize(), -1).build();
            minioClient.putObject(putObject);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getObjetPath(String object) {
        if (!StringUtils.hasText(object)) {
            return "";
        }
        Map<String, String> reqParams = new HashMap<String, String>();
        reqParams.put("response-content-type", "application/png");
        // reqParams.put("response-content-type", "application/octet-stream");

        String url;
        try {
            url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(minioProperties.getBucketName()).object(object).expiry(2, TimeUnit.HOURS).extraQueryParams(reqParams).build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return url;
    }

}
