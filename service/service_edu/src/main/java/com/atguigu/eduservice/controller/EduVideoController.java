package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-11
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    //添加小节
    @PostMapping("addVideo")
    public R addvideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }
    //查小节
    @GetMapping("getVideoById/{videoId}")
    public R getVideoById(@PathVariable String videoId){
        EduVideo eduvideo = videoService.getById(videoId);
        return R.ok().data("eduvideo",eduvideo);
    }
    //修改小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return  R.ok();
    }
    //删除小节
    @DeleteMapping("{videoId}")
    public R deledtVideo(@PathVariable String videoId){
        videoService.removeById(videoId);
        return R.ok();
    }
}

