package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePulishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**课程列表*/
    @GetMapping("")
    public R getCourseList(){
        List<EduCourse> courseList = educourseservice.list(null);
        return R.ok().data("courseList",courseList);
    }
    /**带条件查询课程列表，带分页功能*/
    @PostMapping("getCourseListCondition/{current}/{limit}")
    public R getCourseListCondition(@PathVariable long current ,@PathVariable long limit,
                                    @RequestBody(required = false) CourseQuery courseQuery){
        Page<EduCourse> page = new Page<>(current, limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        String courseName=courseQuery.getTitle();
        String status = courseQuery.getStatus();
        String begin = courseQuery.getBegin();
        String end = courseQuery.getEnd();
        if(!StringUtils.isEmpty(courseName)){
            wrapper.like("title",courseName);
        }
        if(!StringUtils.isEmpty(status)){
            wrapper.eq("status",status);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_modified", end);
        }
        //执行分页查询
        //调用方法实现条件查询分页
        educourseservice.page(page,wrapper);

        long total = page.getTotal();//总记录数
        List<EduCourse> records = page.getRecords(); //数据list集合
        Map map = new HashMap();
        map.put("total", total);
        map.put("rows", records);
        return R.ok().data(map);
    }
    @DeleteMapping("deleteCourse/{courseId}")
    public  R deleteCourse(@PathVariable String courseId){
        educourseservice.deleteCourseByCourseId(courseId);

        return R.ok();
    }

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

    //根据课程Id查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public  R getPublishCourseInfo(@PathVariable String id){
        CoursePulishVo coursepublishVO= educourseservice.publishCourseInfo(id);
        return R.ok().data("publishCourse",coursepublishVO);
    }
    //最终发布课程
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");//normal为已发布，Draft为未发布
        boolean b = educourseservice.updateById(eduCourse);

        return R.ok();
    }

}

