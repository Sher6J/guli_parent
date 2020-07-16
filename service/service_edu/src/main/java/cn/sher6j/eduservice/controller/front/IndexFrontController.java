package cn.sher6j.eduservice.controller.front;

import cn.sher6j.commonutils.R;
import cn.sher6j.eduservice.entity.EduCourse;
import cn.sher6j.eduservice.entity.EduTeacher;
import cn.sher6j.eduservice.service.EduCourseService;
import cn.sher6j.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 前台首页面的前端控制器
 * @author sher6j
 * @create 2020-07-16-11:58
 */
@RestController
@RequestMapping("/eduservice/indexfont")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation("查询前8条热门课程，前4条讲师")
    @GetMapping("index")
    public R index() {
        QueryWrapper<EduCourse> courseWrapper = new QueryWrapper<>();
        courseWrapper.orderByDesc("id");
        courseWrapper.last("limit 8");
        List<EduCourse> eduCourses = courseService.list(courseWrapper);

        QueryWrapper<EduTeacher> teacherWrapper = new QueryWrapper<>();
        teacherWrapper.orderByDesc("id");
        teacherWrapper.last("limit 4");
        List<EduTeacher> eduTeachers = teacherService.list(teacherWrapper);

        return R.ok().data("courses", eduCourses).data("teachers", eduTeachers);
    }
}
