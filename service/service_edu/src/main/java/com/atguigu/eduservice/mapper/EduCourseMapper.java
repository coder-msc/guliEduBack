package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CoursePulishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-07-11
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CoursePulishVo getPublishCourseInfo(String courseId);

}
