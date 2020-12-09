package com.atguigu.blog.controller;


import cn.hutool.core.io.FileUtil;
import com.atguigu.blog.client.OssClient;
import com.atguigu.blog.entity.EduBlog;
import com.atguigu.blog.service.EduBlogService;
import com.atguigu.blog.service.OssService;
import com.atguigu.commonutils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.util.Date;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jkma
 * @since 2020-11-15
 */
@RestController
@RequestMapping("/blogservice/blogs")
//@CrossOrigin
public class EduBlogController {
    @Autowired
    EduBlogService blogService;
    @Autowired
    OssClient ossClient;
    @Autowired
    OssService ossService;

/**展示博客列表  前台 后台 均展示8个
 * */
    @GetMapping("/blogs/{currentPage}")
    public R list(@PathVariable long currentPage) {
        Page page = new Page(currentPage, 8);
        IPage pageData = blogService.page(page, new QueryWrapper<EduBlog>().orderByDesc("created_time"));
        return R.ok().data("pageData",pageData);
    }

    @GetMapping("/blog/{id}")
    public R detail(@PathVariable(name = "id") Long id) {
        EduBlog blog = blogService.getById(id);
//        Assert.notNull(blog, "该博客已被删除");
        return R.ok().data("data",blog);
    }

//    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public R edit(@Validated @RequestBody EduBlog blog) {

//        Assert.isTrue(false, "公开版不能任意编辑！");

        EduBlog temp = null;
        if(blog.getId() != null) {
            temp = blogService.getById(blog.getId());
            // 只能编辑自己的文章
//            System.out.println(ShiroUtil.getProfile().getId());
//            Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(), "没有权限编辑");

        } else {

            temp = new EduBlog();
//            temp.setUserId(ShiroUtil.getProfile().getId());
//            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        blog.setUserId(1l);
        temp.setUserId(1l);


        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        LocalDate today = LocalDate.now();
        temp.setBlogTag("java");
//        temp.getCreatedTime(today);
        temp.setCreatedTime(new Date());
        temp.setBlogAvatar(blog.getBlogAvatar());
        blogService.saveOrUpdate(temp);

        return R.ok();
    }

//    @RequestMapping("/bolg/url")
//    public String getUrl(){
//        return "http:localhost:8080/hello/dfh.png";
//    }
/**上传头像方法
 * */
    @RequestMapping("/bolg/url")
    public R getUrl(@RequestParam(value = "image") MultipartFile image){
        System.out.println(image);
//        return R.ok().data("url","http://browser9.qhimg.com/bdm/1440_900_85/t01fdcd6377a309b28b.jpg");
        return ossClient.uploadossFile(image);
    }
    /**上传截图的方法
     * */
    @RequestMapping("/bolg/imgurl")
    public R getImgUrl(@RequestParam(value = "image") MultipartFile image){
        String name = image.getName();
        boolean empty = image.isEmpty();
        System.out.println(image);
//        return ossClient.uploadossFile(file);
        String url = ossService.uploadAvatar(image);
        return  R.ok().data("url",url);
    }
    //上传头像的方法
    @PostMapping("/getgsg/geg")
    public R uploadossFile(@RequestParam(value = "image") MultipartFile image){
        System.out.println(image+"--------------------");
        String url= ossService.uploadAvatar(image);
        return R.ok().data("url",url);
    }
}
