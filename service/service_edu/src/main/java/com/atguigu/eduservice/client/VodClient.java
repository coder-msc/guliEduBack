package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("service-vod")
@Component
public interface VodClient {
    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")
    public R removeAlyVidoe(@PathVariable("id") String id);

    //批量删除视频
    @DeleteMapping("/eduvod/video/removebatch")
    public R removeAlyVidoes(@RequestParam("videoList") List<String> videoList);
}
