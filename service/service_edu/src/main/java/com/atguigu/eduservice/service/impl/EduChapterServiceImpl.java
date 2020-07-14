package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.ChapterVo;
import com.atguigu.eduservice.entity.vo.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.ExcptionHandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-11
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoservice;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        List<EduChapter> chapterList = baseMapper.selectList(queryWrapper);


        QueryWrapper<EduVideo> videoqueryWrapper = new QueryWrapper<>();
        videoqueryWrapper.eq("course_id", courseId);
        List<EduVideo> videoList = videoservice.list(videoqueryWrapper);

        List<ChapterVo> finallList=new ArrayList<>();

        for (int i = 0; i < chapterList.size(); i++) {
            EduChapter eduChapter = chapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            finallList.add(chapterVo);

            List<VideoVo> videoVoList=new ArrayList<>();
            for (int m = 0; m < videoList.size(); m++) {
                EduVideo eduVideo = videoList.get(m);

                 if(eduChapter.getId().equals(eduVideo.getChapterId())){
                        VideoVo videoVo = new VideoVo();
                        BeanUtils.copyProperties(eduVideo,videoVo);
                        videoVoList.add(videoVo);
                    }

            }
            chapterVo.setChildren(videoVoList);

        }

        return finallList;
    }

    //删除章节
    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoservice.count(wrapper);
        if(count>=1){
            throw new GuliException(20001,"章节下有小节，不能删除");
        }else{
            int i = baseMapper.deleteById(chapterId);
            return i>0;
        }
    }
}
