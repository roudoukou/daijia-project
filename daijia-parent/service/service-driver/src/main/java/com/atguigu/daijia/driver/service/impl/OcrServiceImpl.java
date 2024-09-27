package com.atguigu.daijia.driver.service.impl;

import com.atguigu.daijia.driver.config.TencentCloudProperties;
import com.atguigu.daijia.driver.service.CosService;
import com.atguigu.daijia.driver.service.OcrService;
import com.atguigu.daijia.model.vo.driver.IdCardOcrVo;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Random;

@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class OcrServiceImpl implements OcrService {

    @Autowired
    private TencentCloudProperties tencentCloudProperties;

    @Autowired
    private CosService cosService;

    // 身份证识别, 懒得对接, 每次返回默认值
    @Override
    public IdCardOcrVo idCardOcr(MultipartFile file) {
        // try{
        // 图片转换base64格式字符串
        //     byte[] base64 = Base64.encodeBase64(file.getBytes());
        //     String fileBase64 = new String(base64);
        //
        //     // 实例化一个认证对象，入参需要传入腾讯云账户 SecretId 和 SecretKey，此处还需注意密钥对的保密
        //     Credential cred = new Credential(tencentCloudProperties.getSecretId(),
        //             tencentCloudProperties.getSecretKey());
        //     // 实例化一个http选项，可选的，没有特殊需求可以跳过
        //     HttpProfile httpProfile = new HttpProfile();
        //     httpProfile.setEndpoint("ocr.tencentcloudapi.com");
        //     // 实例化一个client选项，可选的，没有特殊需求可以跳过
        //     ClientProfile clientProfile = new ClientProfile();
        //     clientProfile.setHttpProfile(httpProfile);
        //     // 实例化要请求产品的client对象,clientProfile是可选的
        //     OcrClient client = new OcrClient(cred,tencentCloudProperties.getRegion(), clientProfile);
        //     // 实例化一个请求对象,每个接口都会对应一个request对象
        //     IDCardOCRRequest req = new IDCardOCRRequest();
        //     //设置文件
        //     req.setImageBase64(fileBase64);
        //
        //     // 返回的resp是一个IDCardOCRResponse的实例，与请求对象对应
        //     IDCardOCRResponse resp = client.IDCardOCR(req);
        //
        //     //转换为IdCardOcrVo对象
        //     IdCardOcrVo idCardOcrVo = new IdCardOcrVo();
        //     if (StringUtils.hasText(resp.getName())) {
        //         //身份证正面
        //         idCardOcrVo.setName(resp.getName());
        //         idCardOcrVo.setGender("男".equals(resp.getSex()) ? "1" : "2");
        //         idCardOcrVo.setBirthday(DateTimeFormat.forPattern("yyyy/MM/dd").parseDateTime(resp.getBirth()).toDate());
        //         idCardOcrVo.setIdcardNo(resp.getIdNum());
        //         idCardOcrVo.setIdcardAddress(resp.getAddress());
        //
        //         //上传身份证正面图片到腾讯云cos
        //         CosUploadVo cosUploadVo = cosService.upload(file, "idCard");
        //         idCardOcrVo.setIdcardFrontUrl(cosUploadVo.getUrl());
        //         idCardOcrVo.setIdcardFrontShowUrl(cosUploadVo.getShowUrl());
        //     } else {
        //         //身份证反面
        //         //证件有效期："2010.07.21-2020.07.21"
        //         String idcardExpireString = resp.getValidDate().split("-")[1];
        //         idCardOcrVo.setIdcardExpire(DateTimeFormat.forPattern("yyyy.MM.dd").parseDateTime(idcardExpireString).toDate());
        //         //上传身份证反面图片到腾讯云cos
        //         CosUploadVo cosUploadVo = cosService.upload(file, "idCard");
        //         idCardOcrVo.setIdcardBackUrl(cosUploadVo.getUrl());
        //         idCardOcrVo.setIdcardBackShowUrl(cosUploadVo.getShowUrl());
        //     }
        //     return idCardOcrVo;
        // } catch (Exception e) {
        //     throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        // }
        IdCardOcrVo idCardOcrVo = new IdCardOcrVo();
        // 正面
        idCardOcrVo.setName("黄磊");
        Random random = new Random();
        idCardOcrVo.setGender(random.nextBoolean() ? "1" : "2");
        idCardOcrVo.setBirthday(DateTimeFormat.forPattern("yyyy/MM/dd").parseDateTime("1995/05/20").toDate());
        idCardOcrVo.setIdcardNo("123456789012" + random.nextInt(10));
        idCardOcrVo.setIdcardAddress("广州市天河区");

        //写死
        idCardOcrVo.setIdcardFrontUrl("http://example.com/front.jpg");
        idCardOcrVo.setIdcardFrontShowUrl("http://example.com/show.jpg");

        // 反面
        String idcardExpireString = "202" + random.nextInt(10) + ".07.21";
        idCardOcrVo.setIdcardExpire(DateTimeFormat.forPattern("yyyy.MM.dd").parseDateTime(idcardExpireString).toDate());

        idCardOcrVo.setIdcardBackUrl("http://example.com/back.jpg");
        idCardOcrVo.setIdcardBackShowUrl("http://example.com/show_back.jpg");

        return idCardOcrVo;
    }


}
