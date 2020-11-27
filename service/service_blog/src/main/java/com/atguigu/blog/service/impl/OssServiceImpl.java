package com.atguigu.blog.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.blog.service.OssService;
import com.atguigu.blog.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadAvatar(MultipartFile file) {
        String endpoint = ConstantPropertiesUtils.END_POIND;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret =ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 上传文件流。
        try{
            //获取上传文件的输入流
            InputStream inputStream = file.getInputStream();
            //获取文件的原始名称
            String filename = file.getOriginalFilename();
            //确保文件不会重名被覆盖
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            filename="__"+uuid+filename;
            //以时间创建文件夹
            String filepath = new DateTime().toString("yyyy/MM/dd");
            filename=filepath+filename;


            //上传
            ossClient.putObject(bucketName, filename, inputStream);
// 关闭OSSClient。
            ossClient.shutdown();
            String url = "https://"+bucketName+"."+endpoint+"/"+filename;
            return url;
        }catch (Exception e){
            return null;
        }
    }
}
