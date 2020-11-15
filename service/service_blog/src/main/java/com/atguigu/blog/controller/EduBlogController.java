package com.atguigu.blog.controller;


import com.atguigu.blog.entity.EduBlog;
import com.atguigu.blog.service.EduBlogService;
import com.atguigu.commonutils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import cn.hutool.core.bean.BeanUtil;
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
@CrossOrigin
public class EduBlogController {
    @Autowired
    EduBlogService blogService;

    @GetMapping("/blogs")
    public R list(@RequestParam(defaultValue = "1") Integer currentPage) {

        Page page = new Page(currentPage, 5);
        IPage pageData = blogService.page(page, new QueryWrapper<EduBlog>().orderByDesc("created_time"));

        return R.ok().data("pageData",pageData);
    }

    @GetMapping("/blog/{id}")
    public R detail(@PathVariable(name = "id") Long id) {
        EduBlog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已被删除");

        return R.ok();
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

        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(temp);

        return R.ok();
    }
}
