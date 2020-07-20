package cn.sher6j.eduservice.service;

import cn.sher6j.eduservice.entity.EduCourse;
import cn.sher6j.eduservice.entity.chapter.CoursePublishVo;
import cn.sher6j.eduservice.entity.frontvo.FrontCouseVo;
import cn.sher6j.eduservice.entity.vo.CourseInfoVo;
import cn.sher6j.eduservice.entity.vo.CourseQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author sher6j
 * @since 2020-05-18
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程基本信息
     * @param courseInfoVo
     * @return 添加课程的id
     */
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程id查询课程信息
     * @param courseId 课程信息
     * @return
     */
    CourseInfoVo getCourseInfoById(String courseId);

    /**
     * 修改课程信息
     * @param courseInfoVo
     */
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程id查询课程确认信息
     * @param id
     * @return
     */
    CoursePublishVo coursePublishInfo(String id);

    /**
     * 分页查询课程信息
     * @param page
     * @param courseQueryVo
     */
    void pageQuery(Page<EduCourse> page, CourseQueryVo courseQueryVo);

    /**
     * 删除课程
     * @param courseId
     */
    void removeCourse(String courseId);

    /**
     * 前台条件查询带分页查询前台课程
     * @param coursePage 分页结果
     * @param couseVo 查询条件
     * @return
     */
    Map<String, Object> getFrontCourseList(Page<EduCourse> coursePage, FrontCouseVo couseVo);
}
