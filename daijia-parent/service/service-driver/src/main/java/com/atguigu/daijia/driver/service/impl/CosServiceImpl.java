package com.atguigu.daijia.driver.service.impl;

import com.atguigu.daijia.driver.config.MinioProperties;
import com.atguigu.daijia.driver.service.CosService;
import com.atguigu.daijia.model.vo.driver.CosUploadVo;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.model.ObjectMetadata;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class CosServiceImpl implements CosService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioProperties minioProperties;

    // @Autowired
    // private TencentCloudProperties tencentCloudProperties;

    @Override
    public CosUploadVo upload(MultipartFile file, String path) {
        // 获取cosClient对象
        // COSClient cosClient = this.getCosClient();
        // 文件上传
        // 元数据信息
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(file.getSize());
        meta.setContentEncoding("UTF-8");
        meta.setContentType(file.getContentType());

        // 向存储桶中保存文件
        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")); // 文件后缀名
        String uploadPath = "driver/" + path + "/" + UUID.randomUUID().toString().replaceAll("-", "") + fileType;
        // 01.jpg
        // /driver/auth/0o98754.jpg
        // PutObjectRequest putObjectRequest = null;
        // try {
        //     //1 bucket名称
        //     //2
        //     putObjectRequest = new PutObjectRequest(tencentCloudProperties.getBucketPrivate(),
        //             uploadPath,
        //             file.getInputStream(),
        //             meta);
        // } catch (IOException e) {
        //     throw new RuntimeException(e);
        // }
        // putObjectRequest.setStorageClass(StorageClass.Standard);
        // PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest); //上传文件
        // cosClient.shutdown();

        try {
            PutObjectArgs putObjectArgs =
                    PutObjectArgs.builder().bucket(minioProperties.getBucketName())
                            .object(uploadPath).stream(
                                    file.getInputStream(), file.getSize(), -1)
                            // .contentType(fileType)
                            .build();
            minioClient.putObject(putObjectArgs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 返回vo对象
        CosUploadVo cosUploadVo = new CosUploadVo();
        cosUploadVo.setUrl(uploadPath);
        // 图片临时访问url，回显使用
        String imageUrl = this.getImageUrl(uploadPath);
        cosUploadVo.setShowUrl(imageUrl);
        return cosUploadVo;
    }

    @Override
    public String getImageUrl(String path) {
        if (!StringUtils.hasText(path)) return "";

        // Get presigned URL string to download 'my-objectname' in 'my-bucketname'
        // with an expiration of 2 hours.
        //
        // Additionally also add 'response-content-type' to dynamically set content-type
        // for the server response.
        Map<String, String> reqParams = new HashMap<String, String>();
        reqParams.put("response-content-type", "application/png");
        // reqParams.put("response-content-type", "application/octet-stream");


        String url =
                null;
        try {
            url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(minioProperties.getBucketName())
                            .object(path)
                            .expiry(2, TimeUnit.HOURS)
                            .extraQueryParams(reqParams)
                            .build());
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        } catch (InsufficientDataException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (XmlParserException e) {
            throw new RuntimeException(e);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        }

        return url;

    }


    // 获取临时签名URL
    // @Override
    // public String getImageUrl(String path) {
    //     if(!StringUtils.hasText(path)) return "";
    //     //获取cosclient对象
    //     COSClient cosClient = this.getCosClient();
    //     //GeneratePresignedUrlRequest
    //     GeneratePresignedUrlRequest request =
    //             new GeneratePresignedUrlRequest(tencentCloudProperties.getBucketPrivate(),
    //                     path, HttpMethodName.GET);
    //     //设置临时URL有效期为15分钟
    //     Date date = new DateTime().plusMinutes(15).toDate();
    //     request.setExpiration(date);
    //     //调用方法获取
    //     URL url = cosClient.generatePresignedUrl(request);
    //     cosClient.shutdown();
    //     return url.toString();
    // }


    // public COSClient getCosClient() {
    //     String secretId = tencentCloudProperties.getSecretId();
    //     String secretKey = tencentCloudProperties.getSecretKey();
    //     COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
    //     // 2 设置 bucket 的地域, COS 地域
    //     Region region = new Region(tencentCloudProperties.getRegion());
    //     ClientConfig clientConfig = new ClientConfig(region);
    //     // 这里建议设置使用 https 协议
    //     clientConfig.setHttpProtocol(HttpProtocol.https);
    //     // 3 生成 cos 客户端。
    //     COSClient cosClient = new COSClient(cred, clientConfig);
    //     return cosClient;
    // }


}
