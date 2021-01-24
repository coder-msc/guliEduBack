package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author scma
 * @since 2020-08-01
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService userService;

    //登录
    @PostMapping("login")
    public R loginUser(@RequestBody UcenterMember member) {
        //member对象封装手机号和密码
        //调用service方法实现登录
        //返回token值，使用jwt生成
        String token = userService.login(member);
        return R.ok().data("token",token);
    }

    //注册
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        userService.register(registerVo);
        return R.ok();
    }
    /**获取到用户信息，根据token*/
    @GetMapping("getmemberinfo")
    public R getmemberInfo(HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember ucenterMember = userService.getById(memberId);
        return R.ok().data("userInfo",ucenterMember);
    }

    /**获取到用户信息，username*/
    @GetMapping("getmemberbyusername/{username}")
    public R getmemberByuserName(@PathVariable String username){
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        UcenterMember ucenterMember = userService.getOne(wrapper);
        return R.ok().data("userInfo",ucenterMember);
    }

}

