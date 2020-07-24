package cn.sher6j.eduservice.controller.front;

import cn.sher6j.commonutils.R;
import cn.sher6j.commonutils.ordervo.CourseVo;
import cn.sher6j.eduservice.entity.EduCourse;
import cn.sher6j.eduservice.entity.EduTeacher;
import cn.sher6j.eduservice.entity.chapter.ChapterVo;
import cn.sher6j.eduservice.entity.frontvo.CourseWebVo;
import cn.sher6j.eduservice.entity.frontvo.FrontCouseVo;
import cn.sher6j.eduservice.service.EduChapterService;
import cn.sher6j.eduservice.service.EduCourseService;
import cn.sher6j.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author sher6j
 * @create 2020-07-20-11:57
 */
@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @ApiOperation("条件查询带分页查询课程")
    @PostMapping("getFrontCourses/{page}/{limit}")
    public R getFrontCourses(@PathVariable long page, @PathVariable long limit,
                             @RequestBody(required = false) FrontCouseVo couseVo) {
        Page<EduCourse> coursePage = new Page<>(page, limit);
        Map<String, Object> map = courseService.getFrontCourseList(coursePage, couseVo);
        return R.ok().data(map);
    }

    @ApiOperation("查询课程详情的方法")
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId) {
        //根据课程id，编写SQL语句查询课程信息
        CourseWebVo courseWebVo = courseService.getFrontCourseInfoById(courseId);
        //根据课程id查询章节和小节（章节中包含小节）
        List<ChapterVo> chapters = chapterService.getChaptersById(courseId);
        //返回课程基本信息和课程大纲
        return R.ok().data("courseWebVo", courseWebVo).data("chapters", chapters);
    }

    @ApiOperation("根据课程id查询课程信息用于远程调用")
    @PostMapping("getCourseInfo{id}")
    public CourseVo getCourseInfo(@PathVariable String id) {
        CourseWebVo courseInfoById = courseService.getFrontCourseInfoById(id);
        CourseVo courseInfo = new CourseVo();
        BeanUtils.copyProperties(courseInfoById, courseInfo);
        return courseInfo;
    }
}
