package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-06-20
 */
public interface EduTeacherService extends IService<EduTeacher> {
    // 讲师列表 分页  当前页  每页记录数
    Map<String, Object> getTeacherFront(Page<EduTeacher> page1);
}
