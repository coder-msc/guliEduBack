package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    //回显课程信息
    @GetMapping("getCouseInfo/{courseId}")
    public R getCouseInfo(@PathVariable String courseId){
     CourseInfoVo courseInfoVo=educourseservice.getCourseInfoByCourseId(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    //修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        educourseservice.updateCourseInfo(courseInfoVo);
        return R.ok();
    }
}

