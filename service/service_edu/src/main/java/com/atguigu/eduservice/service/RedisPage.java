package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;

import java.util.List;

public interface RedisPage {

    List<EduCourse> getCouser8();

    List<EduTeacher> getTeacher4();
}
