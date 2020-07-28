package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
public class IndexFrontController {

    @Autowired
    private EduCourseService educourseservice;

    @Autowired
    private EduTeacherService teacherService;

    //查询签八条课程
    @GetMapping()
    public R index(){
        QueryWrapper<EduCourse> coursewrapper = new QueryWrapper<>();
        coursewrapper.orderByDesc("id");
        coursewrapper.last("LIMIT 8");
        List<EduCourse> courseList = educourseservice.list(coursewrapper);
        //前四个名师
        QueryWrapper<EduTeacher> teacherwrapepr = new QueryWrapper<>();
        teacherwrapepr.orderByDesc("id");
        teacherwrapepr.last("LIMIT 4");
        List<EduTeacher> TeacherList = teacherService.list(teacherwrapepr);
        return R.ok().data("eduList",courseList).data("teacherList",TeacherList);
    }



}
