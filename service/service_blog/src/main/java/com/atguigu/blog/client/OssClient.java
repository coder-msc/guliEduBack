package com.atguigu.blog.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient("service-oss")
@Component
public interface OssClient {

    @RequestMapping(value = "/eduoss/fileoss", method = RequestMethod.POST, consumes = "multipart/form-data")
    public R uploadossFile(MultipartFile file);
}
