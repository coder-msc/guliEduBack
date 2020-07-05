package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.ExcptionHandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jkma
 * @since 2020-06-20
 */
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;


    @GetMapping("findAll")
    public R findAll() {
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    //逻辑删除的方法
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")  //通过路径传值
    public R removeTeacher(@PathVariable String id) { //得到路径中的id值
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 分页查询教师
     */
    @ApiOperation("分页查询教师")
    @GetMapping("pageTeahers/{current}/{limit}")
    public R getTeachersPage(@PathVariable long current,
                             @PathVariable long limit) {

        try {
            int a = 10 / 0;  //测试异常的数据
        }catch (Exception e){
            throw new GuliException(20001,"执行了自定义的异常");
        }

        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        teacherService.page(pageTeacher, null);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        Map map = new HashMap();
        map.put("total", total);
        map.put("rows", records);
        return R.ok().data(map);
    }
    /*
    根据Id查询讲师信息
         */
    @ApiOperation("根据Id查询教师去修改信息")
    @GetMapping("getTeacherInfo/{id}")
public R getTeacherbyId(@PathVariable String id){
    EduTeacher eduTeacher = teacherService.getById(id);
    return  R.ok().data("teacher",eduTeacher);

}
    //讲师修改功能
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.updateById(eduTeacher);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    /**
     * 带分页的条件查询
     */

    @ApiOperation("带条件的分页查询")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {

        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);

        //构建条件
        QueryWrapper<EduTeacher> wapper = new QueryWrapper<>();

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)) {
            wapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wapper.le("gmt_modified", end);
        }
        //调用方法实现条件查询分页
        teacherService.page(pageTeacher, wapper);

        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords(); //数据list集合
        Map map = new HashMap();
        map.put("total", total);
        map.put("rows", records);
        return R.ok().data(map);
    }

    @PostMapping("addTeacher")
    @ApiOperation("添加讲师 ")
    public R addteacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        if (save) {
            return R.ok();

        } else {
            return R.error();
        }


    }
}

