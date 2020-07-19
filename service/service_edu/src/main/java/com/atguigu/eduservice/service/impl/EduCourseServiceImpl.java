package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePulishVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.ExcptionHandler.GuliException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-11
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired //描述
    private EduCourseDescriptionService courseScription;

    //小节
    @Autowired
    private EduVideoService eduvideoservice;

    //章节
    @Autowired
    private EduChapterService educhapterservice;
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfovo) {

//        向课程表中添加数据
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfovo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert<=0){
            throw new GuliException(20001,"添加课程信息失败");
        }

        //向描述表中添加数据
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfovo.getDescription());
        courseDescription.setId(eduCourse.getId());
        courseScription.save(courseDescription);

return eduCourse.getId();
    }

    /**
     * 获取课程信息--回显--以便修改
     * @param courseId
     * @return
     */
    @Override
    public CourseInfoVo getCourseInfoByCourseId(String courseId) {
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        EduCourseDescription description = courseScription.getById(courseId);
        courseInfoVo.setDescription(description.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1 修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if(update == 0) {
            throw new GuliException(20001,"修改课程信息失败");
        }

        //2 修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        courseScription.updateById(description);
    }

    @Override
    public CoursePulishVo publishCourseInfo(String id) {
        CoursePulishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    //删除课程信息--小节--章节---描述--课程本身
    @Override
    public void deleteCourseByCourseId(String courseId) {
        //删除小节
        eduvideoservice.deleteByCourseId(courseId);

        //删除章节
        educhapterservice.deleteByCourseId(courseId);

        //删除描述
        courseScription.removeById(courseId);

        //删除本身
        int i = baseMapper.deleteById(courseId);
        if(i==0){
            throw new GuliException(2001,"课程删除失败");
        }
    }


}
