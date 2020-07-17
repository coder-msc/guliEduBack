package com.atguigu.eduservice.entity.vo;

import lombok.Data;

/**
 * 封装课程发布实体类
 */
@Data
public class CoursePulishVo {
    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示

}
