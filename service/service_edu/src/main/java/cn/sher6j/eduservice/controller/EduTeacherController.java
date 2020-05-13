package cn.sher6j.eduservice.controller;


import cn.sher6j.commonutils.R;
import cn.sher6j.eduservice.entity.EduTeacher;
import cn.sher6j.eduservice.entity.vo.TeacherQueryVo;
import cn.sher6j.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author sher6j
 * @since 2020-05-13
 */
@Api(description = "讲师管理")
@RestController //@Controller(控制层注入到IoC中) + @ResponseBody(用来返回json数据)
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {

    //访问地址举例：http://localhost:8001/eduservice/edu-teacher/findAll

    @Autowired
    private EduTeacherService teacherService;

    /**
     * 查询讲师表所有数据，rest风格--查询一般用get提交方式
     * @return
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher() {
        //调用service方法
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list); //链式编程
    }


    /**
     * 根据id逻辑删除讲师
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id逻辑删除讲师")
    @DeleteMapping("{id}") //id通过路径传递
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 分页查询讲师
     * @param current 当前页
     * @param limit   每页记录数
     * @return
     */
    @ApiOperation(value = "分页查询讲师")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(
            @PathVariable long current,
            @PathVariable long limit
    ) {
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        //调用方法时，底层会将所以数据封装到pageTeacher对象中
        teacherService.page(pageTeacher, null);
        long total = pageTeacher.getTotal(); //总记录数
        List<EduTeacher> records = pageTeacher.getRecords(); //数据集合

        //手动模拟异常
//        int i = 1/0;

//        HashMap map = new HashMap<>();
//        map.put("total", total);
//        map.put("rows", records);
//        return R.ok().data(map); //也可以直接传入Map集合

        return R.ok().data("total", total).data("rows", records);//可以多次调用data，因为是个map集合
    }

    /**
     * 组合条件分页查询讲师
     * @param current          当前页
     * @param limit            每页记录数
     * @param teacherQueryVo   查询条件
     * @return
     */
    @ApiOperation(value = "带组合条件的分页查询讲师")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(
            @PathVariable long current,
            @PathVariable long limit,
            @RequestBody(required = false) TeacherQueryVo teacherQueryVo
            //@RequestBody：使用json传递数据，将json数据封装到对应对象中去，需要使用POST提交方式！！！GET取不到
    ) {
        //创建page对象
        Page<EduTeacher> page = new Page<>(current, limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        //多条件组合查询————类似于动态SQL
        //判断条件值是否为空，如果不为空拼接条件
        String name = teacherQueryVo.getName();
        Integer level = teacherQueryVo.getLevel();
        String begin = teacherQueryVo.getBegin();
        String end = teacherQueryVo.getEnd();
        if (!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("name", name); //column是表中字段名称
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }

        //调用方法
        teacherService.page(page, wrapper);

        long total = page.getTotal(); //总记录数
        List<EduTeacher> records = page.getRecords(); //数据集合
        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * 添加讲师
     * @param eduTeacher
     * @return
     */
    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        if (save == true) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 根据id查询讲师
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询讲师")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher", eduTeacher);
    }

    /**
     * 修改讲师
     * @param eduTeacher
     * @return
     */
    @ApiOperation(value = "修改更新讲师")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.updateById(eduTeacher);
        if (flag == true) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

