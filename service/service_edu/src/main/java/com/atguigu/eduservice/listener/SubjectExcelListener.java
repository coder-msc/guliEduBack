package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.ExcptionHandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    public EduSubjectService edusubjectservice;
    public SubjectExcelListener() {}
    public SubjectExcelListener(EduSubjectService edusubjectservice) {
        this.edusubjectservice = edusubjectservice;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {


        if(subjectData==null){
        throw new GuliException(20001,"传入数据为空");
        }
//判断一级分类是否重复
        EduSubject eduSubject = this.existOneSubject(edusubjectservice, subjectData.getOneSubjectName());
        if(eduSubject==null){
             eduSubject = new EduSubject();
            eduSubject.setParentId("0");
            eduSubject.setTitle(subjectData.getOneSubjectName());
            edusubjectservice.save(eduSubject);
        }

        //判断二级分类是否重复
        String pid=eduSubject.getId();
        EduSubject twoeduSubject = this.existTwoSubject(edusubjectservice, subjectData.getOneSubjectName(),pid);
        if(twoeduSubject==null){
            twoeduSubject = new EduSubject();
            twoeduSubject.setParentId(pid);
            twoeduSubject.setTitle(subjectData.getTwoSubjectName());
            edusubjectservice.save(twoeduSubject);
        }



    }

//判断一级分类是否为空
        public EduSubject existOneSubject(EduSubjectService edusubjectservice,String name){
            QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
            wrapper.eq("title",name);
            wrapper.eq("parent_id","0");
            EduSubject eduSubject = edusubjectservice.getOne(wrapper);
            return eduSubject;
        }
//判断er级分类是否为空
public EduSubject existTwoSubject(EduSubjectService edusubjectservice,String name,String pid){
    QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
    wrapper.eq("title",name);
    wrapper.eq("parent_id",pid);
    EduSubject twoeduSubject = edusubjectservice.getOne(wrapper);
    return twoeduSubject;
}




    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
