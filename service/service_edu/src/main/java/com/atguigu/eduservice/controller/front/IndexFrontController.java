package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.RedisPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.atguigu.eduservice.service.RedisPage;
import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private RedisPage RedisPage1;



    @GetMapping("index")
    public R index(){
        //查询签八条课程
        List<EduCourse> courseList =RedisPage1.getCouser8();

        //前四个名师
        List<EduTeacher> TeacherList =RedisPage1.getTeacher4();

        return R.ok().data("eduList",courseList).data("teacherList",TeacherList);
    }



}
