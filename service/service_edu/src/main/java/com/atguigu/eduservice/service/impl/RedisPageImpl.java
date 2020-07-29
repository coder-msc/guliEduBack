package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.eduservice.service.RedisPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RedisPageImpl implements RedisPage {
    @Autowired
    private EduCourseService educourseservice;

    @Autowired
    private EduTeacherService teacherService;

    @Cacheable(value = "courselist",key = "'course8list'")
    @Override
    public List<EduCourse> getCouser8() {
        QueryWrapper<EduCourse> coursewrapper = new QueryWrapper<>();
        coursewrapper.orderByDesc("id");
        coursewrapper.last("LIMIT 8");
        List<EduCourse> courseList = educourseservice.list(coursewrapper);
        return courseList;
    }

    @Cacheable(value = "TeacherList",key = "'teacher4List'")
    @Override
    public List<EduTeacher> getTeacher4() {
        QueryWrapper<EduTeacher> teacherwrapepr = new QueryWrapper<>();
        teacherwrapepr.orderByDesc("id");
        teacherwrapepr.last("LIMIT 4");
        List<EduTeacher> TeacherList = teacherService.list(teacherwrapepr);
        return TeacherList;
    }
}
