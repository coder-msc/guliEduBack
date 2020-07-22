package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-11
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;
    @Override
    public void deleteByCourseId(String courseId) {
        //查询出来所有的videoList  ---调用删除
        QueryWrapper<EduVideo> Wrapper1 = new QueryWrapper<>();
        Wrapper1.eq("course_id",courseId);
        Wrapper1.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(Wrapper1);

        List<String> videoList = new ArrayList<>();
        for(EduVideo ed:eduVideos){
            if(!StringUtils.isEmpty(ed.getVideoSourceId())){
                videoList.add(ed.getVideoSourceId());
            }
        }
        if(videoList.size()>0){
            vodClient.removeAlyVidoes(videoList);
        }

        QueryWrapper<EduVideo> Wrapper = new QueryWrapper<>();
        Wrapper.eq("course_id",courseId);
        baseMapper.delete(Wrapper);

    }
}
