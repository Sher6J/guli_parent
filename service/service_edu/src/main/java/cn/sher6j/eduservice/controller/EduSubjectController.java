package cn.sher6j.eduservice.controller;


import cn.sher6j.commonutils.R;
import cn.sher6j.eduservice.entity.subject.OneSubject;
import cn.sher6j.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author sher6j
 * @since 2020-05-17
 */
@Api(description = "课程分类管理")
@RestController
@RequestMapping("/eduservice/edu-subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    /**
     * 添加课程分类
     * @param file 上传的Excel文件
     * @return
     */
    @ApiOperation("添加课程分类")
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        //上传的Excel文件
        subjectService.addSubject(file, subjectService);
        return R.ok();
    }

    /**
     * 显示课程分类列表
     * @return
     */
    @ApiOperation(("课程分类列表"))
    @GetMapping("getAllSubject")
    public R getAllSubject() {
        //list集合的泛型是一级分类，一级分类本身就包含二级分类
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list", list);
    }
}

