package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-11
 */
@RestController
@RequestMapping("/eduservice/educourse")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService educourseservice;

    @PostMapping("addCourseInfo")
    public R addCourse(@RequestBody CourseInfoVo courseInfovo){
        String id=educourseservice.saveCourseInfo(courseInfovo);

        return R.ok().data("courseId",id);
    }
}

