package cn.sher6j.eduservice.controller.front;

import cn.sher6j.commonutils.R;
import cn.sher6j.eduservice.entity.EduCourse;
import cn.sher6j.eduservice.entity.EduTeacher;
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
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    @ApiOperation("分页查询讲师")
    @PostMapping("getTeacherList/{page}/{limit}")
    public R getTeacherList(@PathVariable long page, @PathVariable long limit) {
        Page<EduTeacher> teacherPage = new Page<>(page, limit);
        Map<String, Object> map = teacherService.getFrontTeacherList(teacherPage);
        //返回分页所有数据
        return R.ok().data(map);
    }

    @ApiOperation("讲师详情的功能")
    @GetMapping("getFrontTeacherInfo/{teacherId}")
    public R getFrontTeacherInfo(@PathVariable String teacherId) {
        //1.根据讲师id查询讲师基本信息
        EduTeacher teacher = teacherService.getById(teacherId);

        //2.根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        List<EduCourse> courses = courseService.list(wrapper);

        return R.ok().data("teacher", teacher).data("courses", courses);
    }
}
