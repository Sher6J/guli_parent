package cn.sher6j.eduservice.controller.front;

import cn.sher6j.commonutils.R;
import cn.sher6j.eduservice.entity.EduCourse;
import cn.sher6j.eduservice.entity.EduTeacher;
import cn.sher6j.eduservice.entity.frontvo.FrontCouseVo;
import cn.sher6j.eduservice.service.EduCourseService;
import cn.sher6j.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("条件查询带分页查询课程")
    @PostMapping("getFrontCourses/{page}/{limit}")
    public R getFrontCourses(@PathVariable long page, @PathVariable long limit,
                             @RequestBody(required = false) FrontCouseVo couseVo) {
        Page<EduCourse> coursePage = new Page<>(page, limit);
        Map<String, Object> map = courseService.getFrontCourseList(coursePage, couseVo);
        return R.ok().data(map);
    }
}
