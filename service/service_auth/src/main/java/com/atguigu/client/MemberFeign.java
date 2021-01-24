package com.atguigu.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-ucenter")
@Component
public interface MemberFeign {
    @GetMapping("/educenter/member/getmemberbyusername/{username}")
    public R getmemberByuserName(@PathVariable("username") String username);

}
