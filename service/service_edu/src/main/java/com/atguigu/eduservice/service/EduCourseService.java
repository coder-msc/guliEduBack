package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePulishVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-11
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfovo);

    CourseInfoVo getCourseInfoByCourseId(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePulishVo publishCourseInfo(String id);
//删除课程信息--小节--章节---描述--课程本身
    void deleteCourseByCourseId(String courseId);
}
