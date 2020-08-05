package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.frontvo.CourseFrontVo;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {
    @Autowired
    private EduTeacherService teacherService;

    // 讲师列表 分页  当前页  每页记录数
    @Autowired
    private EduTeacherService teacherService1;

    @Autowired
    private EduCourseService courseService;

    //1 分页查询课程方法
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page,@PathVariable long limit,
    @RequestBody(required = false) CourseFrontVo coursefront) {
        Page<EduCourse> pageTeacher = new Page<>(page,limit);
        Map<String,Object> map = courseService.getFrontCourseList(pageTeacher,coursefront);
        //返回分页所有数据
        return R.ok().data(map);
    }



}
