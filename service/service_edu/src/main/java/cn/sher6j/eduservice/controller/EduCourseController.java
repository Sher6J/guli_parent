package cn.sher6j.eduservice.controller;


import cn.sher6j.commonutils.R;
import cn.sher6j.eduservice.entity.EduCourse;
import cn.sher6j.eduservice.entity.chapter.CoursePublishVo;
import cn.sher6j.eduservice.entity.vo.CourseInfoVo;
import cn.sher6j.eduservice.entity.vo.CourseQueryVo;
import cn.sher6j.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author sher6j
 * @since 2020-05-18
 */
@Api(description = "课程管理")
@RestController
@RequestMapping("/eduservice/edu-course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    @ApiOperation("分页查询课程列表")
    @GetMapping("pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(
            @ApiParam(name = "current", value = "当前页码", required = true)
            @PathVariable long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable long limit,
            @ApiParam(name = "courseQueryVo", value = "查询对象", required = false)
            @RequestBody(required = false)CourseQueryVo courseQueryVo) {

        Page<EduCourse> page = new Page<>(current, limit);

        courseService.pageQuery(page, courseQueryVo);
        List<EduCourse> eduCourses = page.getRecords();
        long total = page.getTotal();
        return R.ok().data("total", total).data("eduCourses", eduCourses);
    }

    /**
     * 添加课程基本信息
     * @param courseInfoVo
     * @return
     */
    @ApiOperation("添加课程基本信息")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        // 返回添加之后课程id，为了后面添加课程大纲
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

    /**
     * 根据课程id查询课程信息
     * @param courseId 课程id
     * @return
     */
    @ApiOperation("根据课程id查询课程基本信息")
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfoById(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    /**
     * 修改课程信息
     * @param courseInfoVo 课程信息封装的对象
     * @return
     */
    @ApiOperation("修改课程信息")
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    /**
     * 根据课程id查询课程确认信息
     * @param id
     * @return
     */
    @ApiOperation("根据课程id查询课程确认信息")
    @GetMapping("getCoursePublishInfo/{id}")
    public R getCoursePublishInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.coursePublishInfo(id);
        return R.ok().data("coursePublish", coursePublishVo);
    }

    /**
     * 课程最终发布，只需要把数据库中课程表edu_course对应字段status修改为Normal即可
     * @param id
     * @return
     */
    @ApiOperation("课程最终发布")
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return R.ok();
    }

}