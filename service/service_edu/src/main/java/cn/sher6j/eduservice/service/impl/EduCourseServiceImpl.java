package cn.sher6j.eduservice.service.impl;

import cn.sher6j.eduservice.entity.EduCourse;
import cn.sher6j.eduservice.entity.EduCourseDescription;
import cn.sher6j.eduservice.entity.chapter.CoursePublishVo;
import cn.sher6j.eduservice.entity.vo.CourseInfoVo;
import cn.sher6j.eduservice.entity.vo.CourseQueryVo;
import cn.sher6j.eduservice.mapper.EduCourseMapper;
import cn.sher6j.eduservice.service.EduCourseDescriptionService;
import cn.sher6j.eduservice.service.EduCourseService;
import cn.sher6j.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author sher6j
 * @since 2020-05-18
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    /**
     * 分页查询课程信息
     * @param page
     * @param courseQueryVo
     */
    @Override
    public void pageQuery(Page<EduCourse> page, CourseQueryVo courseQueryVo) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");

        if (courseQueryVo == null) {
            baseMapper.selectPage(page, queryWrapper);
            return;
        }

        String title = courseQueryVo.getTitle();
        String teacherId = courseQueryVo.getTeacherId();
        String oneSubjectId = courseQueryVo.getOneSubjectId();
        String twoSubjectId = courseQueryVo.getTwoSubjecctId();

        if (!StringUtils.isEmpty(title)) queryWrapper.like("title", title);
        if (!StringUtils.isEmpty(teacherId)) queryWrapper.eq("teacher_id", teacherId);
        if (!StringUtils.isEmpty(oneSubjectId)) queryWrapper.eq("subject_parent_id", oneSubjectId);
        if (!StringUtils.isEmpty(twoSubjectId)) queryWrapper.eq("subject_id", twoSubjectId);

        baseMapper.selectPage(page, queryWrapper);
    }

    /**
     * 添加课程信息
     * @param courseInfoVo
     * @return 添加课程的id
     */
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1.向课程表中添加课程基本信息
        //CourseInfoVo转换成EduCourse对象
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse); //影响行数

        if (insert == 0) {
            throw new GuliException(20001, "添加课程信息失败");
        }

        //获取添加之后课程id，将课程id赋值给描述id，这样才能实现课程和课程描述的一对一关系
        String cid = eduCourse.getId();

        //2.向课程简介表添加课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        //设置描述id为课程id
        eduCourseDescription.setId(cid);
        eduCourseDescriptionService.save(eduCourseDescription);

        return cid;
    }

    /**
     * 根据课程id查询课程信息
     * @param courseId 课程id
     * @return
     */
    @Override
    public CourseInfoVo getCourseInfoById(String courseId) {
        //1.查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        //2.查询描述表
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();

        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    /**
     * 修改课程信息
     * @param courseInfoVo
     */
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1.修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int updateRows = baseMapper.updateById(eduCourse);
        if (updateRows == 0) {
            throw new GuliException(20001, "修改课程信息失败");
        }

        //2.修改描述表
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(courseInfoVo.getId());
        courseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.updateById(courseDescription);
    }

    /**
     * 根据课程id查询课程确认信息
     * @param id
     * @return
     */
    @Override
    public CoursePublishVo coursePublishInfo(String id) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }
}